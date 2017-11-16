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

post();

function post()
{
    global $pdo;
    $id = $_POST['ID'];
    $fuel = $_POST['FUEL'];
    $type = $_POST['TYPE'];
    $make = $_POST['MAKE'];
    $model = $_POST['MODEL'];
    $year = $_POST['YEAR'];
    $created = false;
    $updated = false;

    try{
        $sql = $pdo->prepare('INSERT INTO `vehicles` (`ID`, `Fuel`, `Type`, `Make`, `Model`, `Year`) 
            VALUES (?, ?, ?, ?, ?, ?)');
        $created = $sql->execute([$id, $fuel, $type, $make, $model, $year]);
    } catch (PDOException $e){
        $sql = $pdo->prepare('UPDATE `vehicles` SET `Fuel` = ?, `Type` = ?, Make = ?, Model = ?, Year = ? WHERE `ID` = ?');
        $updated = $sql->execute([$fuel, $type, $make, $model, $year, $id]);
    }
    echo json_encode(array("ID" => $id, "created" => $created, "updated" => $updated));
}