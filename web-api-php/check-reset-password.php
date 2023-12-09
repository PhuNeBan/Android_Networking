<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once 'connection.php';

//http://192.168.1.105:8686/check-reset-password.php
// method get
// đọc data email và token từ url;
$data = json_decode(file_get_contents('php://input'));
$email = $data->email;
$token = $data->token;

// kiểm tra email và token có trong db hay không
$query = "select * from password_resets where email = '$email' and token = '$token'
            and created_at >= DATE_SUB(NOW(), INTERVAL 1 DAY) and available = 1 ";
$stmt = $dbConn->prepare($query);
$stmt->execute();
$reset = $stmt->fetch(PDO::FETCH_ASSOC);

if ($reset) {
    echo json_encode(array(
        "status" => true
    ));
} else {
    echo json_encode(array(
        "status" => false
    ));
}



?>
