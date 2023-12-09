<?php
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Origin: *");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
include_once 'connection.php';

use PHPMailer\PHPMailer\PHPMailer;

include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/src/PHPMailer.php';
include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/src/SMTP.php';
include_once $_SERVER['DOCUMENT_ROOT'] . '/helpers/PHPMailer-master/src/Exception.php';



//http://192.168.1.105:8686/forgot-password.php
// method post
// đọc data từ body
$data = json_decode(file_get_contents('php://input'));
$email = $data->email;

// kiểm tra email có trong db hay không
$query = "SELECT * FROM users WHERE email = '$email' ";
$stmt = $dbConn->prepare($query);
$stmt->execute();
$user = $stmt->fetch(PDO::FETCH_ASSOC);
if ($user) {
    // send email otp
    // tạo token bằng cách mã hóa email và thời gian
    $token = md5(time() . $email);
    // lưu token vào database
    $query = "insert into password_resets (email, token)
                        values ('$email', '$token') ";
    $stmt = $dbConn->prepare($query);
    $stmt->execute();
    // gửi email có link reset mật khẩu
    $link = "<a href='http://172.16.114.50:3000/reset_password?email="
        . $email . "&token=" . $token . "'>Click to reset password</a>";
    $mail = new PHPMailer();
    $mail->CharSet = "utf-8";
    $mail->isSMTP();
    $mail->SMTPAuth = true;
    $mail->Username = "dohoangphu.it@gmail.com";
    $mail->Password = "bomdwtymzgjrgbdp";
    $mail->SMTPSecure = "ssl";
    $mail->Host = "ssl://smtp.gmail.com";
    $mail->Port = "465";
    $mail->From = "dohoangphu.it@gmail.com";
    $mail->FromName = "Đỗ Hoàng Phú";
    $mail->addAddress($email, 'Hello');
    $mail->Subject = "Reset Password";
    $mail->isHTML(true);
    $mail->Body = "Click on this link to reset password " . $link . " ";
    $res = $mail->Send();
    if ($res) {
        echo json_encode(array(
            "message" => "Email sent.",
            "status" => true
        ));
    } else {
        echo json_encode(array(
            "message" => "Email sent failed.",
             $mail->ErrorInfo,
            "status" => false
        ));
    }
} else {
    echo json_encode(
        array(
            "message" => "Email không tồn tại!",
            "status" => false
        )
    );
}
?>
