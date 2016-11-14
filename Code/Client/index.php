<?php

	$api_path = "http://ergast.com/api/f1/current/next.json";
	$options = "";

	$json = file_get_contents($api_path);

	$object = json_decode($json);

	echo '<pre>';
	print_r($object);
	echo '</pre>';
