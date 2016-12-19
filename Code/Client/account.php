<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

session_start();

$user = isset($_SESSION['user']) ? $_SESSION['user'] : header('Location: login.php');

$api = 'http://localhost:8090';

if ($user->type == "customer") {
	$api_reservations = $api . '/reservationsByUserId?id=' . $user->id;

	if (isset($_GET["moduleAction"]) && $_GET["moduleAction"] == "delete") {
		$shift = isset($_GET["shift"]) ? $_GET["shift"] : header('Location: account.php');
		$date = isset($_GET["date"]) ? $_GET["date"] : header('Location: account.php');

		$api_delete = $api . "/deleteReservation?id=" . $user->id . "&shift=" . $shift . "&date=" . $date;
		$bool = file_get_contents($api_delete);
	}

	$reservations = json_decode(file_get_contents($api_reservations));

	$tpl = $twig->loadTemplate('account_customer.twig');
} elseif ($user->type == "owner") {

	$api_resto_id = $api . "/restaurant?name=" . $user->name;
	$resto_id = json_decode(file_get_contents($api_resto_id))[0]->id;

	$api_reservations = $api . '/reservations?id=' . $resto_id . "&date=" . (!empty($_POST["date"]) ? $_POST["date"] : "");

	$reservations_wtf = json_decode(file_get_contents($api_reservations));
	$reservations = array();

	$items_per_res = !empty($_POST["date"]) ? 5 : 6;

	$count = count($reservations_wtf) / $items_per_res;

	for ($i = 0; $i < $count; $i++) {
		$reservation = array_slice($reservations_wtf, $items_per_res * $i, $items_per_res);
		array_push($reservations, $reservation);
	}

	$tpl = $twig->loadTemplate('account_owner.twig');
}



echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => null,
	'active' => 'account',
	'user' => $user,
	'reservations' => $reservations,
	'date' => !empty($_POST["date"]) ? $_POST["date"] : ""
));
