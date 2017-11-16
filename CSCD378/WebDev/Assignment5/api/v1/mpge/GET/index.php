<?php
$host = 'localhost';
$db = 'vehiclempg&mpge';
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

get();

function get()
{
    global $pdo;
    if(empty($_GET)) {
        $sql = $pdo->prepare("SELECT * FROM `mpge`");
        $sql->execute();
        echo json_encode($sql->fetchAll());
    }
    else{
        $id = (isset($_GET['ID']) ? $_GET['ID'] : "IS NOT NULL");
        $electricMiles = (isset($_GET['ELECTRIC MILES']) ? $_GET['ELECTRIC MILES'] : "IS NOT NULL");
        $mpge = (isset($_GET['MPGE']) ? $_GET['MPGE'] : "IS NOT NULL");
        $kwhrmiles = (isset($_GET['KW-HR/100']) ? $_GET['KW-HR/100'] : "IS NOT NULL");
        $pricekwhr = (isset($_GET['$/KW-HR']) ? $_GET['$/KW-HR'] : "IS NOT NULL");
        $pricemile = (isset($_GET['$/MILE']) ? $_GET['$/MILE'] : "IS NOT NULL");

        $sql = $pdo->prepare("SELECT * FROM `mpge` WHERE `ID` = ? AND `Electric Miles` = ? AND `MPGe` = ? AND 
                              `kW-hr/100 Miles` = ? AND `$/kW-hr` = ? AND `$/Mile-kW` = ?");
        $sql->execute([$id, $electricMiles, $mpge, $kwhrmiles, $pricekwhr, $pricemile]);
        echo json_encode($sql->fetchAll());
    }
}