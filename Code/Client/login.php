<?php

require_once 'vendor/autoload.php';

// Twig
$loader = new Twig_Loader_Filesystem(__DIR__ . '/templates');
$twig = new Twig_Environment($loader, array(
	'cache' => __DIR__ . '/cache',
	'auto_reload' => true // set to false on production
));

session_start();

if (isset($_SESSION['user'])) {
	header('Location: index.php');
	exit();
}

// var to tell if we have a login error
$formErrors = array();

// form submitted
if (isset($_POST['moduleAction']) && ($_POST['moduleAction'] == 'login')) {

	// extract sent in username & password
	$email = isset($_POST['email']) ? trim($_POST['email']) : '';
	$password = isset($_POST['password']) ? $_POST['password'] : '';

	if (($email === '') || ($password === '')) {
		array_push($formErrors, 'Empty fields');
	}

	// username & password are 'valid'
	else {

		//  API Connection
		$api = 'http://localhost:8080';
		$api_user = $api . '/user?email=' . $email;

		$user = json_decode(file_get_contents($api_user));
		$hash = $user->password;

		if (empty($hash)) {
			array_push($formErrors, 'Invalid email and/or password');
		} else {
			if (password_verify($password, $hash)) {

				// store user (usually returned from database) in session
				$_SESSION['user'] = $user;
				setcookie('persist', $user->email);

				// redirect to index
				header('Location: index.php');
				exit();
			} else {
				array_push($formErrors, 'Invalid email and/or password');
			}
		}
	}
}

$tpl = $twig->loadTemplate('login.twig');
echo $tpl->render(array(
	'pageTitle' => 'Log in',
	'active' => 'login',
	'PHP_SELF' => $_SERVER['PHP_SELF'],
	'formErrors' => $formErrors,
	'persist' => isset($_COOKIE['persist']) ? $_COOKIE['persist'] : ""
));
