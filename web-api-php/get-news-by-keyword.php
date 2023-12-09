<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/get-news-by-keyword.php?keyword=abc

    //get keyword from query string
    $keyword = $_GET['keyword'];

    //get data from database

    $query = "SELECT id, title, content FROM news WHERE title LIKE '%$keyword%' OR content LIKE '%$keyword%'";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

    echo json_encode(
        array(
            "result" => $result
        )
    );

?>