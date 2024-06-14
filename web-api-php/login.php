<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';
    include_once 'helpers/jwt.php';

    //http://192.168.1.105:8686/login.php

    //get data from body
    $data = json_decode(file_get_contents("php://input"));
    $email = $data->email;
    $password = $data->password;

    //get data from database
    $query = "SELECT * FROM users WHERE email = '$email' ";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $result = $stmt->fetch(PDO::FETCH_ASSOC);

    if($result){
        //check password
        if(!password_verify($password, $result['password'])){
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Password is not correct!",
                    "user" => null
                )
            );
            return;
        }

        //generate token
        $headers = array('alg' => 'HS256', 'typ' => 'JWT');
        $payload = array( //luu thong tin nguoi dung vao payload để sử dụng sau này
            'id' => $result['id'],
            'email' => $result['email'],
            'role' => $result['role'],
            'exp' => (time() + 3600) //hết hạn sau 1 giờ
        );
        $token = generate_jwt($headers, $payload);
        //token này dùng để xác thực người dùng sau khi đăng nhập thành công
        //token này sẽ được gửi về cho client và client sẽ lưu trữ token này
        //sau đó mỗi lần gọi api thì sẽ gửi token này lên server để xác thực
        //nếu token này hợp lệ thì mới cho phép gọi api
        //token này sẽ hết hạn sau 1 giờ (3600s)
        //nếu token hết hạn thì client sẽ phải đăng nhập lại để lấy token mới


        echo json_encode(
            array(
                "status" => true,
                "message" => "Login successfully!",
                "user" => array(
                    "id" => $result['id'],
                    "email" => $result['email'],
                    "name" => $result['name'],
                    "phone" => $result['phone'],
                    "address" => $result['address'],
                    "role" => $result['role']
                ),
                "token" => $token
            )
        );
    }else{
        echo json_encode(
            array(
                "status" => false,
                "message" => "Login failed!",
                "user" => null
            )
        );
    }




?>