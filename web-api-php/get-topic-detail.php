<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/get-topic-detail.php?topic_id=1

    try {
        //get id from query string
        $topic_id = $_GET['topic_id'];

        //get data from database

        $query = "SELECT id, name, description FROM topics WHERE id = $topic_id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        //fetch() returns object, fetchAll() returns associative array

        if (!$result) {
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Topic not found!"
                )
            );
            return;
        }
        
        echo json_encode(
            array(
                "status" => true,
                "topic" => $result,
                "message" => "Get topic detail successfully!"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Get topic detail failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }

?>