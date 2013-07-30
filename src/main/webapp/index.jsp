<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h1>Please enter job Id</h1>
		<form name="taskForm" method="POST" id="taskForm" action="jobInfo.form">
			<input id="jobId" type="text" name="jobId"/>
			<input id="sendButton" type="button" value="Send">
		</form>
	</body>
</html>
