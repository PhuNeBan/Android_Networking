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
} from "@chakra-ui/react";

import { useNavigate } from "react-router-dom";
import swal from "sweetalert";

const ListTopics = (props) => {
  const { setUser } = props;
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await AxiosInstance().get("/get-topics.php");
        setData(response.topics);
        // console.log(response.news);
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
    //reload page
  }, []);

  //logout
  const handleLogout = () => {
    setUser(null);
  };

  // redirect to add new page
  const navigate = useNavigate();

  const handleAddTopic = () => {
    navigate("/add-topic");
  };

  //redirect to edit page
  const handleEdit = (id) => {
    navigate(`/update-topic/${id}`);
  };

  //redirect to news page
  const handleNews = () => {
    navigate("/");
  };

  //get list user
  const [users, setUsers] = useState([]);
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await AxiosInstance().get("/get-users.php");
        setUsers(response.users);
        // console.log(response.users);
      } catch (error) {
        console.log("failed to fetch data: ", error.message);
      }
    };
    fetchUsers();
  }, []);

  //delete news
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
          console.log("data: ", newData);
          const res = await AxiosInstance().delete(
            `/delete-topic.php?id=${id}`
          );
          if (res.status) {
            swal("Xóa thành công!", {
              icon: "success",
            });
          } else {
            setData([...data]);
            swal("Xóa thất bại!", {
              icon: "error",
            });
          }
        } catch (error) {
          console.log("xóa thất bại: ", error);
          swal("Xóa thất bại!", {
            icon: "error",
          });
        }
      } else {
        swal("Bảo toàn dữ liệu!");
      }
    });
  };

  return (
    <div className="List">
      <Heading style={{ marginBottom: "20px" }}>List topics</Heading>
      <Button onClick={handleLogout} colorScheme="blue" size="sm">
        Log Out
      </Button>
      <Button
        onClick={handleAddTopic}
        colorScheme="green"
        size="sm"
        style={{ padding: "0 10px", marginLeft: "10px" }}
      >
        Add Topic
      </Button>
      <Button
        onClick={handleNews}
        colorScheme="yellow"
        size="sm"
        style={{ padding: "0 10px", marginLeft: "10px" }}
      >
        News
      </Button>
      <TableContainer>
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
              <Th>Name</Th>
              <Th>Description</Th>
              <Th>Created at</Th>
              <Th>Created by</Th>
              <Th>Updated at</Th>
              <Th>Updated by</Th>
            </Tr>
          </Thead>
          <Tbody>
            {data.map((item, index) => (
              <Tr key={index}>
                <Td>{item.id}</Td>
                <Td>{item.name}</Td>
                <Td>{item.description}</Td>
                <Td>{item.created_at}</Td>
                <Td>
                  {users.map((user, index) => {
                    if (item.created_by === user.id) {
                      return user.name;
                    }
                    return null;
                  })}
                </Td>
                <Td>{item.updated_at}</Td>
                <Td>
                  {users.map((user, index) => {
                    if (item.updated_by === user.id) {
                      return user.name;
                    }
                    return null;
                  })}
                </Td>
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
              <Th>Name</Th>
              <Th>Description</Th>
              <Th>Created at</Th>
              <Th>Created by</Th>
              <Th>Updated at</Th>
              <Th>Updated by</Th>
            </Tr>
          </Tfoot>
        </Table>
      </TableContainer>
    </div>
  );
};

export default ListTopics;

const tableStyle = {
  overflowX: "auto", // Cho phép cuộn ngang nếu cần thiết
  whiteSpace: "nowrap", // Ngăn chặn các phần tử bên trong bị ngắt dòng
  maxWidth: "50%", // Chỉ định chiều rộng tối đa
  margin: "0 auto", // Canh giữa
  overflowY: "hidden", // Ẩn thanh cuộn dọc
  border: "1px solid #ccc", // Viền bảng
  marginTop: "20px",
};
