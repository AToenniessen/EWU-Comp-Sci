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
        $sql = $pdo->prepare("SELECT * FROM `mpg`");
        $sql->execute();
        echo json_encode($sql->fetchAll());
    }
    else{
        $id = (isset($_GET['ID']) ? $_GET['ID'] : "IS NOT NULL");
        $gasMiles = (isset($_GET['GAS MILES']) ? $_GET['GAS MILES'] : "IS NOT NULL");
        $gallonsUsed = (isset($_GET['GALLONS USED']) ? $_GET['GALLONS USED'] : "IS NOT NULL");
        $mpg = (isset($_GET['MPG']) ? $_GET['MPG'] : "IS NOT NULL");
        $pricegallon = (isset($_GET['$/GALLON']) ? $_GET['$/GALLON'] : "IS NOT NULL");
        $pricemile = (isset($_GET['$/MILE']) ? $_GET['$/MILE'] : "IS NOT NULL");

        $sql = $pdo->prepare("SELECT * FROM `mpg` WHERE `ID` = ? AND `Gas Miles` = ? AND `Gallons used` = ? AND 
                              `MPG` = ? AND `$/Gallon` = ? AND `$/Mile-Gas` = ?");
        $sql->execute([$id, $gasMiles, $gallonsUsed, $mpg, $pricegallon, $pricemile]);
        echo json_encode($sql->fetchAll());
    }
}