import React, { useEffect, useState } from "react";
import AxiosInstance from "../Helper/AxiosInstance";
import {
  Table,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
  Button,
  Heading,
  Box,
} from "@chakra-ui/react";

import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

const List = (props) => {
  const { setUser } = props;
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await AxiosInstance().get("/get-news.php");
        setData(response.news);
      } catch (error) {
        console.log(
          "failed to fetch data: ",
          error.message ||
            error.response.data.message ||
            error.response.data.error ||
            error.response.data ||
            error.response ||
            error
        );
      }
    };
    fetchData();
  }, []);

  const [users, setUsers] = useState([]);
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await AxiosInstance().get("/get-users.php");
        setUsers(response.users);
      } catch (error) {
        console.log(
          "failed to fetch data: ",
          error.message ||
            error.response.data.message ||
            error.response.data.error ||
            error.response.data ||
            error.response ||
            error
        );
      }
    };
    fetchUsers();
  }, []);

  const Created_by = (id) => {
    const user = users.find((item) => item.id === id);
    if (user) {
      return user.name;
    }
  };

  const [topics, setTopics] = useState([]);
  useEffect(() => {
    const fetchTopics = async () => {
      try {
        const response = await AxiosInstance().get("/get-topics.php");
        setTopics(response.topics);
      } catch (error) {
        console.log(
          "failed to fetch data: ",
          error.message ||
            error.response.data.message ||
            error.response.data.error ||
            error.response.data ||
            error.response ||
            error
        );
      }
    };
    fetchTopics();
  }, []);

  const navigate = useNavigate();

  const handleLogout = () => {
    setUser(null);
  };

  const handleAddNew = () => {
    navigate("/add");
  };

  const handleEdit = (id) => {
    navigate(`/update/${id}`);
  };

  const handleTopic = () => {
    navigate("/list-topics");
  };

  const handleDelete = async (id) => {
    swal({
      title: "Xác nhận xóa?",
      text: "Xóa dữ liệu khỏi hệ thống!",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    }).then(async (willDelete) => {
      if (willDelete) {
        try {
          const newData = data.filter((item) => item.id !== id);
          setData([...newData]);
          const res = await AxiosInstance().delete(`/delete-news.php?id=${id}`);
          if (res) {
            swal("Xóa thành công!", {
              icon: "success",
            });
          }
        } catch (error) {
          console.log("xóa thất bại: ", error);
          swal("Xóa thất bại!", {
            icon: "error",
          });
        }
      } else {
        swal("Dữ liệu không thay đổi!");
      }
    });
  };

  return (
    <div className="List">
      <Heading style={{ marginBottom: "20px" }}>List News</Heading>
      <Box>
        <Button
          onClick={handleLogout}
          colorScheme="blue"
          size="sm"
          mr="2"
          _hover={{ bg: "blue.500" }}
        >
          Log Out
        </Button>
        <Button
          onClick={handleAddNew}
          colorScheme="green"
          size="sm"
          mr="2"
          _hover={{ bg: "green.500" }}
        >
          Add New
        </Button>
        <Button
          onClick={handleTopic}
          colorScheme="yellow"
          size="sm"
          _hover={{ bg: "yellow.500" }}
        >
          Topic
        </Button>
      </Box>
      <TableContainer style={{ borderRadius: "8px", marginTop: "8px" }}>
        <Table
          style={tableStyle}
          size="sm"
          variant="striped"
          colorScheme="teal"
        >
          <TableCaption></TableCaption>
          <Thead>
            <Tr>
              <Th>ID</Th>
              <Th>Title</Th>
              <Th>Content</Th>
              <Th>Topic</Th>
              <Th>Image</Th>
              <Th>Actions</Th>
            </Tr>
          </Thead>
          <Tbody>
            {data.map((item, index) => (
              <Tr key={index}>
                <Td>{item.id}</Td>
                <Td
                  style={{
                    textOverflow: "ellipsis",
                    maxWidth: "150px",
                    overflow: "hidden",
                  }}
                >
                  {item.title}
                </Td>
                <Td
                  style={{
                    textOverflow: "ellipsis",
                    maxWidth: "150px",
                    overflow: "hidden",
                  }}
                >
                  {item.content}
                </Td>
                <Td>
                  {topics.map((topic, index) => {
                    if (item.topic_id === topic.id) {
                      return topic.name;
                    }
                    return null;
                  })}
                </Td>
                <td className="d-flex">
                  <Box
                    w="150px" // Đặt chiều rộng của Box bằng "100%"
                    h="100px" // Đặt chiều cao của Box bằng "100%"
                    bg="gray.200"
                    borderRadius="10px"
                    overflow="hidden"
                    position="relative"
                    _hover={{ transform: "scale(1.05)" }}
                  >
                    <img
                      src={item.image}
                      alt=""
                      width="150px"
                      height="150px"
                      objectFit="cover"
                      style={{ borderRadius: "10px" }}
                    />
                  </Box>
                </td>
                <Td>{Created_by(item.author_id)}</Td>

         
                <Td>
                  <Button
                    colorScheme="teal"
                    size="sm"
                    marginRight="2" // Thêm margin-right 2 chấm để tạo khoảng cách
                    onClick={() => {
                      handleEdit(item.id);
                    }}
                  >
                    Edit
                  </Button>
                  <Button
                    colorScheme="red"
                    size="sm"
                    onClick={() => handleDelete(item.id)}
                  >
                    Delete
                  </Button>
                </Td>
              </Tr>
            ))}
          </Tbody>
          <Tfoot>
            <Tr>
              <Th>ID</Th>
              <Th>Title</Th>
              <Th>Content</Th>
              <Th>Topic</Th>
              <Th>Image</Th>
              <Th>Actions</Th>
            </Tr>
          </Tfoot>
        </Table>
      </TableContainer>
    </div>
  );
};

export default List;

const tableStyle = {
    overflowX: 'auto',
    whiteSpace: 'nowrap',
    maxWidth: '80%',
    margin: '0 auto',
    overflowY: 'hidden',
    border: '1px solid #ccc',
    marginTop: '20px',
    borderRadius: '10px',  // Điều chỉnh giá trị để bo góc
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',  // Thêm hiệu ứng bóng đổ
  };
  
