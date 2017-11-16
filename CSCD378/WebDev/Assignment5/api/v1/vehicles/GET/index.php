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
        $sql = $pdo->prepare("SELECT * FROM `vehicles`");
        $sql->execute();
        echo json_encode($sql->fetchAll());
    }
    else{
        $id = (isset($_GET['ID']) ? $_GET['ID'] : "IS NOT NULL");
        $fuel = (isset($_GET['FUEL']) ? $_GET['FUEL'] : "IS NOT NULL");
        $type = (isset($_GET['TYPE']) ? $_GET['TYPE'] : "IS NOT NULL");
        $make = (isset($_GET['MAKE']) ? $_GET['MAKE'] : "IS NOT NULL");
        $model = (isset($_GET['MODEL']) ? $_GET['MODEL'] : "IS NOT NULL");
        $year = (isset($_GET['YEAR']) ? $_GET['YEAR'] : "IS NOT NULL");


        $sql = $pdo->prepare("SELECT * FROM `vehicles` WHERE `ID` = ? AND `Fuel` = ? AND `Type` = ? AND 
                              `Make` = ? AND `Model` = ? AND `Year` = ?");
        $sql->execute([$id, $fuel, $type, $make, $model, $year]);
        echo json_encode($sql->fetchAll());
    }
}