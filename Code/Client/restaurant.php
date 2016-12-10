<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

session_start();

$restaurant_id = $_GET['id'] ? $_GET['id'] : header('Location: overview.php');

$api = 'http://localhost:8080';
$api_restaurant = $api . '/restaurantid?id=' . $restaurant_id;
$api_tables = $api . '/tables?id=' . $restaurant_id . '&shift=1&date=2016-12-10';

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
	'user' => isset($_SESSION['user']) ? $_SESSION['user'] : null
));
