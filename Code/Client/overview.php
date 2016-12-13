<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

session_start();

$min_seats = isset($_GET['seats']) ? $_GET['seats'] : 0;
$shift = isset($_GET['shift']) ? $_GET['shift'] : header('Location: index.php');
$date = !empty($_GET['date']) ? $_GET['date'] : header('Location: index.php');

$api = 'http://localhost:8080';
$api_overview = $api . '/restaurants';

if (isset($_GET["name"]) && $_GET["name"] != "") {
	$api_overview = $api . "/restaurant?name=" .$_GET["name"];
	var_dump($api_overview);
}


$json = file_get_contents($api_overview);

$restaurants = json_decode($json);

foreach ($restaurants as $key => $value) {
	$api_tables = $api . '/tables?id=' . $value->id . '&shift=' . $shift . '&date=' . $date;
	$tables = json_decode(file_get_contents($api_tables));
	$seats = 0;
	if (!empty($tables)) {
		foreach ($tables as $value) {
			$seats += $value->seats;
		}
	}

	$restaurants[$key]->seats = $seats;
}

$filtered_restaurants = array();

foreach ($restaurants as $key => $value) {
	if ($value->seats >= $min_seats) {
		array_push($filtered_restaurants, $value);
	}
}

$tpl = $twig->loadTemplate('overview.twig');
echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => 'Restaurants',
	'active' => 'overview',
	'restaurants' => $filtered_restaurants,
	'user' => isset($_SESSION['user']) ? $_SESSION['user'] : null,
	'seats' => $min_seats,
	'date' => $date,
	'shift' => $shift,
	'name' => isset($_GET['name']) ? $_GET['name'] : ""
));
