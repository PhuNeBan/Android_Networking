<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    //url http://192.168.1.105:8686/get-user-info.php?user_id=1

    try {
        //get id from query string
        $user_id = $_GET['user_id'];
        //get data from database
        $query = "SELECT id, name, email, phone, address, role FROM users WHERE id = $user_id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        if (!$result) {
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "User not found!"
                )
            );
            return;
        }else{
            echo json_encode(
                array(
                    "status" => true,
                    "user" => $result,
                    "message" => "Get user info successfully!"
                )
            );
        }
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Get user info failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }
   
?>

