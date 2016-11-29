<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));


$api = 'http://localhost:8080';
$api_overview = $api . '/restaurants';

$json = file_get_contents($api_overview);

$restaurants = json_decode($json);

$tpl = $twig->loadTemplate('overview.twig');
echo $tpl->render(array(
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'pageTitle' => 'Restaurants',
	'active' => 'overview',
	'restaurants' => $restaurants
));
