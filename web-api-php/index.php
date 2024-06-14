<?php
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: GET");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    //get data from client (from query string)
    // url: http://192.168.1.105:8686/index.php?a=1&b=2
    $a = $_GET['a'];
    $b = $_GET['b'];
    $c = $_GET['c'];

    //giai phuong trinh bac 2
    $delta = $b*$b - 4*$a*$c;
    if($delta < 0){
        $result = "Phuong trinh vo nghiem";
    }else if($delta == 0){
        $result = "Phuong trinh co nghiem kep x1 = x2 = ".(-$b/2*$a);
    }else{
        $result = "Phuong trinh co 2 nghiem phan biet x1 = ".((-$b+sqrt($delta))/2*$a)." va x2 = ".((-$b-sqrt($delta))/2*$a);
    }


    echo json_encode(
        array(
        "result" => $result
        )
    );
?>