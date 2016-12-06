<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

$restaurant_id = $_GET['id'] ? $_GET['id'] : header('Location: overview.php');

$api = 'http://localhost:8080';
$api_restaurant = $api . '/restaurant?name=' . urlencode($restaurant_id);

//var_dump($api_restaurant);

$json = file_get_contents($api_restaurant);

$restaurant = json_decode($json);

$description = json_decode(file_get_contents("https://baconipsum.com/api/?type=all-meat&paras=1"))[0];

$tpl = $twig->loadTemplate('restaurant.twig');
echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => $restaurant['name'],
	'active' => 'overview',
	'restaurant' => $restaurant[0],
	'description' => $description
));
