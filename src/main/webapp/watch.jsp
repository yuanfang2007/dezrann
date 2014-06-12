<!DOCTYPE html>
<html>
<head>
	<title>Dezrann - User behaviour analysis</title>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="monitor.css"/>
	<script src="js/monitoring.js"></script>
	<script src="js/watch.js"></script>
	<script>watcherInit('${session.id}', {path: '/socket/watch'})</script>
</head>
<body>
	<h1>You are monitoring session ${session.id}</h1>
	<button onclick="location='/'">&#8962;</button>
	<button id="toggleRecording">Start recording</button>
	<div id="cursor"></div>
	<div id="frame">
		<iframe id="iframe"></iframe>
		<div id="filter"></div>
	</div>
</body>
</html>