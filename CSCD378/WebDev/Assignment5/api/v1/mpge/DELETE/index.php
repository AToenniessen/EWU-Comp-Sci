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

get();

function get(){
    global $pdo;
    $id = $_GET['ID'];
    $sql = $pdo->prepare('DELETE FROM `mpge` WHERE `ID` = ?');
    $success = $sql->execute([$id]);
    echo(json_encode(array("ID"=>$id, "Deleted" => $success)));
}