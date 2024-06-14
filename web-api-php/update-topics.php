<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: PUT");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/update-topics.php?id=1
    try {
        //get id from url
        $id = isset($_GET['id']) ? $_GET['id'] : die();

        //get data from body
        $data = json_decode(file_get_contents("php://input"));
        $name = $data->name;
        $description = $data->description;
        $updated_by = $data->updated_by;

        //get data from database
        $query = "SELECT * FROM topics WHERE id = '$id'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        if(count($result) == 0){
            echo json_encode(
                array(
                    "message" => "Topic not found!"
                )
            );
            return;
        }else{
            $news = $result[0];
            if($name == null){
                $name = $news['name'];
            }
            if($description == null){
                $description = $news['description'];
            }
        }
        $query = "UPDATE topics SET name = '$name',
                 description = '$description', updated_at = NOW(), updated_by = '$updated_by'
                  WHERE id = '$id'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "status" => true,
                "message" => "Update topic successfully!",
                "topic" => array(
                    "id" => $id,
                    "name" => $name,
                    "description" => $description
                ),
                "topic" => $result
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Update topic failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }

?>