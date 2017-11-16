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

switch ($rq) {
    case "POST":
        post();
        break;
    case "OPTIONS":
        options();
        break;
}
function post(){
    global $pdo;
    $id = $_POST['ID'];
    $gasMiles = $_POST['MILES'];
    $gallonsUsed = $_POST['GALLONS'];
    $pricegallon = $_POST['$GALLON'];
    $mpg = ($gasMiles/$gallonsUsed);
    $priceMile = (($gallonsUsed*$pricegallon)/$gasMiles);
    if($gasMiles > 0 || $gallonsUsed > 0 || $pricegallon > 0)
        $update = 1;
    else
        $update = 0;
    $created = false;
    $updated = false;
    try{
        $sql = $pdo->prepare('INSERT INTO `mpg` (`ID`, `#updates`, `Gas Miles`, `Gallons used`, `MPG`, `$/Gallon`, `$/Mile-Gas`)
        VALUES (?, ?, ?, ?, ?, ?, ?)');
        $created = $sql->execute([$id, $update, $gasMiles, $gallonsUsed, $mpg , $pricegallon, $priceMile]);
    } catch (PDOException $e){
        $sql = $pdo->prepare('UPDATE `mpg` SET `#updates` = `#updates` + 1, `Gas Miles` = `Gas Miles` + ?, `Gallons used` = `Gallons used` + ?,
                                        `MPG` = `Gas Miles`/`Gallons used`, `$/Gallon` = (`$/Gallon` + ?)/`#updates`, `$/Mile-Gas` =  (`$/Mile-Gas` + ?)/`#updates` WHERE `ID` = ?');
        $updated = $sql->execute([$gasMiles, $gallonsUsed, $pricegallon, $priceMile, $id]);
    }
    echo json_encode(array("ID" => $id, "created" => $created, "updated" => $updated));
}

