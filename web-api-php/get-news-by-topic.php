<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/get-news-by-topic.php?topic_id=1

    //get topic id from query string
    $topic_id = $_GET['topic_id'];

    //get data from database

    $query = "SELECT id, title, content FROM news WHERE topic_id = $topic_id";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "result" => $result
        )
    );

?>