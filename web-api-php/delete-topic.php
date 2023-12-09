<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: DELETE");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    // url:  http://192.168.1.105:8686/delete-topic.php?id=1
    try {
        //get data from query string
        $id = $_GET['id'];

        //check if exist topic_id in news table
        $query = "SELECT * FROM news WHERE topic_id = $id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
        if(count($result) > 0){
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "Delete topic failed! Topic is used in news table!"
                )
            );
            return;
        }

        //get data from database
        $query = "DELETE FROM topics WHERE id = $id";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "status" => true,
                "message" => "Delete topic successfully!"
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Delete topic failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }
    
    

?>