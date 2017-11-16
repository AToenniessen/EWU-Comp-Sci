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

function options()
{
    echo "{
		    \"/api/v1/vehicles/\": {
		      \"POST\": {
		        \"description\": \"Update or create an entry. This acts as PUT and POST.\",
		        \"parameters\": {
		          \"ID\": {
		            \"type\": \"int\",
		            \"description\": \"The ID number of the data to be updated or added\",
		            \"required\": true
		            },
		          \"FUEL\": {
		            \"type\": \"string\",
		            \"description\": \"The type of fuel the vehicle runs on\",
		            \"required\": true
		            },
		          \"TYPE\": {
		            \"type\": \"string\",
		            \"description\": \"The type of vehicle (i.e., sedan, hatchback, truck, ect)\",
		            \"required\": true
		            },
		          \"MAKE\": {
		            \"type\": \"string\",
		            \"description\": \"The make the vehicle\",
		            \"required\": true
		            },
		          \"MODEL\": {
		            \"type\": \"string\",
		            \"description\": \"The model the vehicle\",
		            \"required\": true
		            },
		          \"YEAR\": {
		            \"type\": \"date\",
		            \"description\": \"The year the vehicle was manufactured (YYYY)\",
		            \"required\": true
		            },
		        }
		      }
		    },
		    \"/api/v1/mpge/GET/\": {
		      \"GET\": {
		        \"description\": \"Get all entries based on parameters\",
		        \"parameters\": {
		          \"ID\": {
		            \"type\": \"int\",
		            \"description\": \"The ID number of the data to be updated or added\",
		            \"required\": false
		            },
		          \"FUEL\": {
		            \"type\": \"string\",
		            \"description\": \"The type of fuel the vehicle runs on\",
		            \"required\": false
		            },
		          \"TYPE\": {
		            \"type\": \"string\",
		            \"description\": \"The type of vehicle (i.e., sedan, hatchback, truck, ect)\",
		            \"required\": false
		            },
		          \"MAKE\": {
		            \"type\": \"string\",
		            \"description\": \"The make the vehicle\",
		            \"required\": false
		            },
		          \"MODEL\": {
		            \"type\": \"string\",
		            \"description\": \"The model the vehicle\",
		            \"required\": false
		            },
		          \"YEAR\": {
		            \"type\": \"date\",
		            \"description\": \"The year the vehicle was manufactured (YYYY)\",
		            \"required\": false
		            },
		        }
		      \"GET\"
		        \"description\": \"Get a specific entry\",
		        \"parameters\": {
		          \"ID\": {
		            \"type\": \"int\",
		            \"description\": \"The ID of the data to be returned\",
		            \"required\": true
		          }
		        }
		      }
		    },
		    \"/api/v1/vehicle/DELETE\": {
		      \"GET\": {
		        \"description\": \"Delete an entry\",
		        \"parameters\": {
		          \"ID\": {
		            \"type\": \"int\",
		            \"description\": \"The ID number of the data to be deleted\",
		            \"required\": true
		          }
		        }
		      }
		    }
		  }";
}