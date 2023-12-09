<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';
    include_once 'helpers/jwt.php';

    //get data from database
    // url: http://192.168.1.105:8686/get-topics.php

    try {
        $query = "SELECT id, name, description, created_at, created_by,
                updated_at, updated_by FROM topics";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "topics" => $result
            )
        );
    } catch (exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => $e->getMessage(),
                "topics" => null
            )
        );
    }
    

?>