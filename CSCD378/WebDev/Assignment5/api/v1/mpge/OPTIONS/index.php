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
		    \"/api/v1/mpge/\": {
		      \"POST\": {
		        \"description\": \"Update or create an entry. This acts as PUT and POST.\",
		        \"parameters\": {
		          \"ID\": {
		            \"type\": \"int\",
		            \"description\": \"The ID number of the data to be updated or added\",
		            \"required\": true
		            },
		          \"MILES\": {
		            \"type\": \"int\",
		            \"description\": \"The number of miles driven on electricity\",
		            \"required\": true
		            },
		          \"MPGE\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The efficiency of the vehicle since last charge\",
		            \"required\": true
		            },
		          \"\$kWHR\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The price per kilo-watt hr to recharge the vehicle\",
		            \"required\": true
  		          }
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
		          \"ELECTRIC MILES\": {
		            \"type\": \"int\",
		            \"description\": \"The number of miles driven on electricity\",
		            \"required\": false
		            },
		          \"MPGE\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The efficiency of the vehicle since last charge\",
		            \"required\": false
		            },
		          \"KW-HR/100\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The number of kiloWatt hours used by the vehicle every 100 miles\",
		            \"required\": false
		            },
		          \"\$/KW-HR\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The price per kiloWatt hours to recharge the vehicle\",
		            \"required\": false
  		          }
  		          \"$/MILE\": {
		            \"type\": \"decimal up to 2 places\",
		            \"description\": \"The price per mile driven by the vehicle\",
		            \"required\": false
  		          }
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
		    \"/api/v1/mpge/DELETE\": {
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