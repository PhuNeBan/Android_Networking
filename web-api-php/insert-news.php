<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/insert-news.php

    try {
        //get data from body
        $data = json_decode(file_get_contents("php://input"));
        $title = $data->title;
        $content = $data->content;
        $image = $data->image;
        $topic_id = $data->topic_id;
        $author_id = $data->author_id;


        //get data from database

        $query = "INSERT INTO news (title, content, image, topic_id, 
                    author_id, created_at, updated_at, updated_by) 
                    VALUES ('$title', '$content', '$image', '$topic_id', 
                    '$author_id', NOW(), NOW(), '$author_id')";
                    //now() is a function of mysql to get current time.

        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        //fetchAll() returns an array containing all of the result set rows
        //PDO::FETCH_ASSOC: returns an array indexed by column name as returned in your result set
        //fetch() returns an array containing the next row from the result set

        echo json_encode(
            array(
                "status" => true,
                "message" => "Insert news successfully!"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Insert news failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }
    

?>