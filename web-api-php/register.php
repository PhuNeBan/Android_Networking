<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    //http://192.168.1.245:8686/register.php

    /*Method: POST
    {
        "email": "dohoangphultv01@gmail",
        "password": "Admin@123456",
        "confirmPassword": "Admin@123456",
        "name": "Do Hoang Phu",
        "phone": "123456",
        "address": "HCM",
        "role": "user"
    }*/

    //get data from body
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $password = $data->password;
    $confirmPassword = $data->confirmPassword;
    $name = $data->name;
    $phone = $data->phone;
    $address = $data->address;
    $role = $data->role;

    //validate data
    //check empty
    if(!$email || !$password || !$confirmPassword || !$name || !$phone || !$address || !$role){
        echo json_encode(
            array(
                "status" => false,
                "message" => "Please fill all required fields!",
                "user" => null
            )
        );
        return;
    }


    //check confirm password
    if($password != $confirmPassword){
        echo json_encode(
            array(
                "status" => false,
                "message" => "Confirm password is not correct!",
                "user" => null
            )
        );
        return;
    }

    //check email exist
    $query = "SELECT * FROM users WHERE email = '$email'";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if($result){
        echo json_encode(
            array(
                "status" => false,
                "message" => "Email already exist!",
                "user" => null
            )
        );
        return;
    }else{
        //hash password
        $password = password_hash($password, PASSWORD_DEFAULT);
        //insert data to database
        $query = "INSERT INTO users(email, password, name, phone, address, role) 
                    VALUES('$email', '$password', '$name', '$phone', '$address', '$role')";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();

        //get data from database
        $query = "SELECT * FROM users WHERE email = '$email'
                    AND password = '$password'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        if($result){
            echo json_encode(
                array(
                    "status" => true,
                    "message" => "Register successfully!",
                    "user" => $result
                )
            );
        }else{
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Register failed!",
                    "user" => null
                )
            );
        }
    }

    // {
    //     "email": "phuc@gmail.com",
    //     "password": "777777",
    //     "confirmPassword": "777777",
    //     "name": "phuc ne",
    //     "phone": "777777",
    //     "address": "hcm",
    //     "role": "user"
    //   }




?>