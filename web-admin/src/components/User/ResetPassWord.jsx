import React, { useState, useEffect } from "react";

import {
  Button,
  Flex,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Stack,
  useColorModeValue,
} from "@chakra-ui/react";
import AxiosInstance from "../Helper/AxiosInstance";
import { useSearchParams } from "react-router-dom";
import Swal from "sweetalert2";

const ResetPassWord = (props) => {
  const [getParams, setParams] = useSearchParams();
  // console.log(getParams.get('token'), getParams.get('email'));
  const [token, setToken] = useState(getParams.get("token"));
  const [email, setEmail] = useState(getParams.get("email"));

  const [isValidate, setIsValidate] = useState(false);

  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const bgColor = useColorModeValue("gray.50", "gray.800");

  useEffect(() => {
    const checkResetPassword = async () => {
      try {
        if (!token || !email) {
          throw new Error("Token or email is not valid");
        }
        const body = { email: email, token: token };
        const res = await AxiosInstance().post(
          "check-reset-password.php",
          body
        );
        console.log(res);
        setIsValidate(res.status);
      } catch (error) {
        console.log(error);
      }
    };
    checkResetPassword();
  }, []);

  const handleResetPassword = async () => {
    try {
      if (!token || !email) {
        throw new Error("Token or email is not valid");
      }
      if (password !== confirmPassword) {
        throw new Error("Password and confirm password is not match");
      }
      if (!password || !confirmPassword) {
        throw new Error("Password or confirm password is empty");
      }
      const body = {
        email: email,
        token: token,
        password: password,
        confirmPassword: confirmPassword,
      };
      const res = await AxiosInstance().post("reset-password.php", body);
      console.log(res);
      if (res.status) {
        Swal.fire({
          position: "top-end",
          icon: "success",
          title: "Your work has been saved",
          showConfirmButton: false,
          timer: 1500,
        });
      } else {
        Swal.fire({
          position: "top-end",
          icon: "error",
          title: "Reset password failed",
          showConfirmButton: false,
          timer: 1500,
        });
      }
      //reset page after 2s
      setTimeout(() => {
        window.location.reload();
      }, 2000);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <Flex minH={"100vh"} align={"center"} justify={"center"} bg={bgColor}>
      <Stack
        spacing={4}
        w={"full"}
        maxW={"md"}
        bg={useColorModeValue("white", "gray.700")}
        rounded={"xl"}
        boxShadow={"lg"}
        p={6}
        my={12}
      >
        {isValidate ? (
          // Giao diện khi isValidate là true
          <Heading lineHeight={1.1} fontSize={{ base: "2xl", md: "3xl" }}>
            Enter new password
          </Heading>
        ) : (
          // Giao diện khi isValidate là false
          <div>
            <h1
              style={{
                textAlign: "center",
                color: "red",
                fontSize: "50px",
              }}
            >
              Token invalid!
            </h1>
          </div>
        )}
        {isValidate && (
          // Render các thành phần khi isValidate là true
          <>
            <FormControl id="password" isRequired>
              <FormLabel>Password</FormLabel>
              <Input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="your-password"
                _placeholder={{ color: "gray.500" }}
                type="password"
              />
            </FormControl>
            <FormControl id="confirm-password" isRequired>
              <FormLabel>Confirm Password</FormLabel>
              <Input
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                placeholder="your-password confirm"
                _placeholder={{ color: "gray.500" }}
                type="password"
              />
            </FormControl>
            <Stack spacing={6}>
              <Button
                onClick={handleResetPassword}
                bg={"blue.400"}
                color={"white"}
                _hover={{
                  bg: "blue.500",
                }}
              >
                Submit
              </Button>
            </Stack>
          </>
        )}
      </Stack>
    </Flex>
  );
};

export default ResetPassWord;
