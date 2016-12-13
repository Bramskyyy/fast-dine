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

$api = 'http://localhost:8080';
$api_reservations = $api . '/reservationsByUserId?id=' . $user->id;

if (isset($_GET["moduleAction"]) && $_GET["moduleAction"] == "delete") {
	$shift = isset($_GET["shift"]) ? $_GET["shift"] : header('Location: account.php');
	$date = isset($_GET["date"]) ? $_GET["date"] : header('Location: account.php');

	$api_delete = $api . "/deleteReservation?id=" . $user->id . "&shift=" . $shift . "&date=" . $date;
	$bool = file_get_contents($api_delete);
}

$reservations = json_decode(file_get_contents($api_reservations));

$tpl = $twig->loadTemplate('account.twig');
echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => null,
	'active' => 'home',
	'user' => $user,
	'reservations' => $reservations
));
