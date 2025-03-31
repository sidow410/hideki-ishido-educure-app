<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>SmartSystem_Menu</title>
		<link href="css/commons.css" rel="stylesheet">
		<style>
		
			.select{
				font-size: 40px;
				text-align: center;
				margin-top: 100px;
			}
			
			a{
				margin: 10px;
			}
			
		</style>
	</head>
	
	<body>
	
		<div class="select">
			<a href="function" method="get">${title1}</a>
			<br>
			<br>
			<a href="soldOut" method="get">2. Sold Out</a>
			<br>
			<br>
			<a href="users" method="get">${title3}</a>
		</div>
		
		<div class="menu">
			<form action="logout" method="post">
				<button type="submit"><spring:message code="logout.label" />
		</button></form></div>
		
		<div class="userName">
			<p><spring:message code="name.label" /> : ${name}</p>
		</div>
		
	</body>
	
</html>
