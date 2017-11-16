<?php
$host = 'localhost';
$db   = 'vehiclempg&mpge';
$user = 'Toenniessen';
$pass = 'l9NcR77bPkceOhcB';
$charset = 'utf8mb4';


$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$opt = [
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES => false,
];
$pdo = new PDO($dsn, $user, $pass, $opt);

$rq = $_SERVER['REQUEST_METHOD'];

post();

function post(){
    global $pdo;
    $id = $_POST['ID'];
    $electricMiles = $_POST['MILES'];
    $mpge = $_POST['MPGE'];
    $pricekwhr = $_POST['$kWHR'];
    $kwhr100 = 3370.5/$mpge;
    $pricemilekwhr = $pricekwhr * ((3370.6/$mpge)/100);
    if($electricMiles > 0 || $mpge > 0 || $pricekwhr > 0)
        $update = 1;
    else
        $update = 0;
    $created = false;
    $updated = false;
    try{
        $sql = $pdo->prepare('INSERT INTO `mpge` (`ID`, `#updates`, `Electric Miles`, MPGe, `kW-hr/100 Miles`, `$/kW-hr`, `$/Mile-kW`)
        VALUES (?, ?, ?, ?, ?, ?, ?)');
        $created = $sql->execute([$id, $update,$electricMiles, $mpge, $kwhr100, $pricekwhr, $pricemilekwhr]);
    } catch (PDOException $e){
        $sql = $pdo->prepare('UPDATE `mpge` SET `#updates` = `#updates` + 1, `Electric Miles` = `Electric Miles` + ?, `MPGe` = (`MPGe` + ?)/`#updates`,
                                        `kW-hr/100 Miles` = 3370.5/`MPGe`, `$/kW-hr` = (`$/kW-hr` + ?)/`#updates`, `$/Mile-kW` =  (`$/Mile-kW` + ?)/`#updates` WHERE `ID` = ?');
        $updated = $sql->execute([$electricMiles, $mpge, $pricekwhr, $pricemilekwhr, $id]);
    }
    echo json_encode(array("ID" => $id, "created" => $created, "updated" => $updated));
}