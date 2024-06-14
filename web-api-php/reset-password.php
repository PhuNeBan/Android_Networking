<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once 'connection.php';

//http://192.168.1.105:8686/check-reset-password.php
// method get
// đọc data email, token, password và confirmPassword từ url;
$data = json_decode(file_get_contents('php://input'));
$email = $data->email;
$token = $data->token;
$password = $data->password;
$confirmPassword = $data->confirmPassword;

// kiểm tra password và confirmPassword có giống nhau hay không
if ($password != $confirmPassword) {
    echo json_encode(array(
        "message" => "Password and confirm password not match.",
        "status" => false
    ));
    exit;
}

// kiểm tra email và token có trong db hay không, và token có còn hạn hay không (1 ngày) và đã được sử dụng hay chưa
$query = "select * from password_resets where email = '$email' and token = '$token'
            and created_at >= DATE_SUB(NOW(), INTERVAL 1 DAY) and available = 1 ";
$stmt = $dbConn->prepare($query);
$stmt->execute();
$reset = $stmt->fetch(PDO::FETCH_ASSOC);

if ($reset) {
    // cap nhat bang users
    $password = password_hash($password, PASSWORD_DEFAULT);
    
    $query = "update users set password = '$password' where email = '$email' ";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();

    // cap nhat bang password_resets
    $query = "update password_resets set available = 0 where email = '$email'";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    echo json_encode(array(
        "message" => "Reset password successfully.",
        "status" => true
    ));

} else {
    echo json_encode(array(
        "message" => "Reset password failed.",
        "status" => false
    ));
}



?>
