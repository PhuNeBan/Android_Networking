<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Origin: *"); //allow access from all domains
    header("Access-Control-Allow-Methods: PUT");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once 'connection.php';

    
    // url:  http://192.168.1.105:8686/update-news.php?id=1
    try {
        //get id from url
        $id = isset($_GET['id']) ? $_GET['id'] : die();

        //get data from body
        $data = json_decode(file_get_contents("php://input"));
        $title = $data->title;
        $content = $data->content;
        $image = $data->image;
        $topic_id = $data->topic_id;
        //update by
        $updated_by = $data->updated_by;

        //get data from database
        $query = "SELECT * FROM news WHERE id = '$id'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        if(count($result) == 0){
            echo json_encode(
                array(
                    "status" => false,
                    "message" => "News not found!"
                )
            );
            return;
        }else{
            $news = $result[0];
            if($title == null){
                $title = $news['title'];
            }
            if($content == null){
                $content = $news['content'];
            }
            if($image == null){
                $image = $news['image'];
            }
            if($topic_id == null){
                $topic_id = $news['topic_id'];
            }
        }
        $query = "UPDATE news SET title = '$title', content = '$content', 
                    image = '$image', topic_id = '$topic_id', updated_by = '$updated_by',
                    updated_at = NOW() WHERE id = '$id'";
        $stmt = $dbConn->prepare($query);
        $stmt->execute();
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode(
            array(
                "status" => true,
                "message" => "Update news successfully!",
                "news" => array(
                    "id" => $id,
                    "title" => $title,
                    "content" => $content,
                    "image" => $image,
                    "topic_id" => $topic_id,
                    "updated_by" => $updated_by,
                    "updated_at" => date('Y-m-d H:i:s')
                )
            )
        );
    } catch (Exception $e) {
        echo json_encode(
            array(
                "status" => false,
                "message" => "Update news failed!",
                "error" => $e->getMessage() //get error message from exception object
            )
        );
    }

?>