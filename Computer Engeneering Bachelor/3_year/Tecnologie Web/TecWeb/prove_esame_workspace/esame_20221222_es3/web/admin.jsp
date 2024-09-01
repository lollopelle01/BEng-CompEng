<%@page import="beans.UserInfo"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Administrator</title>
	</head>
	<body>
	
	<button onclick="myFuncion()">aggiorna</button>
	<div id="text"></div>

	</body>
	<script>
		setTimeout(function(){
			   window.location.reload();
			}, 5000);
	</script>
</html>