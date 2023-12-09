<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';
    include_once 'helpers/jwt.php';

    //get data from database
    // url: http://192.168.1.105:8686/get-news.php
    try {

        //check token
        $bearer_token = get_bearer_token();
        if(!$bearer_token){
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Missing token!"
                )
            );
            exit();
        }
        $is_jwt_valid = is_jwt_valid($bearer_token);
        if(!$is_jwt_valid){
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Token is invalid!"
                )
            );
            exit();
        }

        //get data from token
        $data = get_data_from_token($bearer_token);
        // token: authentication and authorization
        // authentication: check user is valid
        // authorization: check user has permission to access this api
        // set authorization
        // role: 1: admin, 2: user
        //chuyển role từ string sang int
        // if($data['role'] == 'admin'){
        //     $data['role'] = 1;
        // }else{
        //     $data['role']= 2;
        // }
        // if($data['role'] != 2){
        //     echo json_encode(
        //         array(
        //             "status" => false,
        //             "message" => "You don't have permission to access this api!"
        //         )
        //     );
        //     exit();
        // }

        //get news from database

        $query = "SELECT id, title, content, image, created_at, author_id, topic_id,
             updated_at, updated_by FROM news";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "news" => $result,
                "status" => true,
                "data" => $data,

            )
        );
    } catch (exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => $e->getMessage(),
                "news" => null
            )
        );
    }

    


?>

