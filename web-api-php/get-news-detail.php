<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/get-news-detail.php?news_id=1

    try {
        //get id from query string
        $news_id = $_GET['news_id'];

        //get data from database
//SELECT n.*, t.name as topic_name, u.name as author_name FROM news n 
// INNER JOIN topics t ON t.id = n.topic_id
// INNER JOIN users u ON u.id = n.author_id LIMIT 0,100

        $query = "SELECT n.*, t.name as topic_name, u.name as author_name
                    FROM news n 
                    INNER JOIN topics t ON t.id = n.topic_id
                    INNER JOIN users u ON u.id = n.author_id
                    WHERE n.id = $news_id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetch(PDO::FETCH_ASSOC);
        //fetch() returns object, fetchAll() returns associative array

        echo json_encode(
            array(
                "status" => true,
                "news" => $result,
                "message" => "Get news detail successfully!"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Get news detail failed!",
                "news" => null,
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }

?>