<?php
$host = 'localhost';
$db   = 'id3007086_webdevdb';
$user = 'id3007086_atoenniessen';
$pass = file_get_contents('pass');
$charset = 'utf8mb4';


$dsn = "mysql:host=$host;dbname=$db;charset=$charset";
$opt = [
    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
    PDO::ATTR_EMULATE_PREPARES => false,
];
$pdo = new PDO($dsn, $user, $pass, $opt);

$rq = $_SERVER['REQUEST_METHOD'];
switch ($rq) {
    case "GET":
        get();
        break;
    case "POST":
        post();
        break;
    case "PUT":
        //000WebHost does not allow for use of PUT calls unless you pay (PUT bundled with POST)
        break;
    case "DELETE";
        //000WebHost does not allow for use of DELETE calls unless you pay as well (DELETE bundled with GET)
        break;
}
function get()
{
    global $pdo;
    if (empty($_GET)) {
        $sql = $pdo->prepare("SELECT * FROM `MovieDBTable`");
        $sql->execute();
        echo json_encode($sql->fetchAll());
    } else {
        $id = $_GET['ID'];
        $sql = $pdo->prepare('DELETE FROM `MovieDBTable` WHERE `ID` = ?');
        $sql->execute([$id]);
    }
}

function post()
{
    global $pdo;
    $ID = $_POST['ID'];
    $MovieTitle = $_POST['MovieTitle'];
    $ReleaseDate = $_POST['ReleaseDate'];
    $Studio = $_POST['Studio'];
    $Description = $_POST['Description'];
    $Price = $_POST['Price'];
    try {
        $sql = $pdo->prepare('INSERT INTO `MovieDBTable` (`ID`, `MovieTitle`, `ReleaseDate`, `Studio`, `Description`, `Price`)
        VALUES (?, ?, ?, ?, ?, ?)');
        $sql->execute([$ID, $MovieTitle, $ReleaseDate, $Studio, $Description, $Price]);
    } catch (PDOException $e) {
        $sql = $pdo->prepare('UPDATE `MovieDBTable` SET `MovieTitle` = ?, `ReleaseDate` = ?, `Studio` = ?, `Description` = ?,
        `Price` = ? WHERE `ID` = ?');
        $sql->execute([$MovieTitle, $ReleaseDate, $Studio, $Description, $Price, $ID]);
    }
    echo json_encode($sql->fetchAll());
}
