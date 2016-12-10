<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

session_start();

$api = 'http://localhost:8080';

$formErrors = array();

if (isset($_POST['moduleAction']) && ($_POST['moduleAction'] == 'book')) {

	if (!isset($_POST['check'])) {
		array_push($formErrors, 'Please select a table');
	}

	if (!isset($_POST['shift'])) {
		array_push($formErrors, 'Something went wrong');
	}

	if (!isset($_POST['date'])) {
		array_push($formErrors, 'Something went wrong');
	}

	if (!isset($_SESSION['user'])) {
		header('Location: login.php');
		exit();
	}

	$tables = "";

	for ($i = 0; $i <= 2; $i++) {
		if (isset($_POST['check'][$i])) {
			$tables = $tables . "&table" . ($i + 1) . "=" . $_POST['check'][$i];
		}
	}

	$api_reservation = $api . "/newReservation?date=" . $_POST['date'] . "&shift=" . $_POST['shift'] . "&email=" . $_SESSION["user"]["email"] . $tables;

	$response = file_get_contents($api_reservation);

	var_dump($response);

	if ($response == "succes") {
		// header('Location: index.php');
		// exit();
	} else {
		array_push($formErrors, 'Something went wrong with the database :(');
	}
}


$restaurant_id = $_GET['id'] ? $_GET['id'] : header('Location: overview.php');

$api_restaurant = $api . '/restaurantid?id=' . $restaurant_id;
$shift = "1";
$date = "2017-01-01";
$api_tables = $api . '/tables?id=' . $restaurant_id . '&shift=' . $shift . '&date=' . $date;

$json_restaurant = file_get_contents($api_restaurant);
$json_tables = file_get_contents($api_tables);

$restaurant = json_decode($json_restaurant);
$tables = json_decode($json_tables);

$description = json_decode(file_get_contents("https://baconipsum.com/api/?type=all-meat&paras=1"))[0];

$tpl = $twig->loadTemplate('restaurant.twig');
echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => $restaurant->name,
	'active' => 'overview',
	'restaurant' => $restaurant,
	'tables' => $tables,
	'description' => $description,
	'user' => isset($_SESSION['user']) ? $_SESSION['user'] : null,
	'shift' => $shift,
	'date' => $date
));
