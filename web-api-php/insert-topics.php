<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // url:  http://192.168.1.105:8686/insert-topics.php

    try {
        //get data from body
        $data = json_decode(file_get_contents("php://input"));
        $name = $data->name;
        $description = $data->description;
        $created_by = $data->created_by;

        //insert data to database
        $query = "INSERT INTO topics (name, description, created_by, created_at, updated_by, updated_at) 
                    VALUES ('$name', '$description', '$created_by', NOW(), '$created_by', NOW())";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "status" => true,
                "message" => "Insert topic successfully!"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Insert topic failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }
