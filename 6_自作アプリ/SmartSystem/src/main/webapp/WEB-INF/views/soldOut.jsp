<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>SmartSystem_Sold Out</title>
		<link href="css/commons.css" rel="stylesheet">
		<style>
		
			h1{
				text-align: center;
				font-size: 50px;
			}
			
		</style>
	</head>

	<body>
		
		<table>
			
			<h1>Sold Out</h1>
			
			<thead>
				<tr>
					<th><spring:message code="id.label" /></th>
					<th><spring:message code="model.label" /></th>
					<th><spring:message code="size.label" />(GB)</th>
					<th><spring:message code="color.label" /></th>
					<th><spring:message code="stock.label" /></th>
					<th><spring:message code="os.label" /></th>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="soldOut" items="${soldOutList}">
					<tr>
						<td>${fn:escapeXml(soldOut.getId())}</td>
						<td>${fn:escapeXml(soldOut.getModel())}</td>
						<td>${fn:escapeXml(soldOut.getSize())}</td>
						<td>${fn:escapeXml(soldOut.getColor())}</td>
						<td>${fn:escapeXml(soldOut.getStock())}</td>
						<td>${fn:escapeXml(soldOut.getOs())}</td>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>
		
		<div class="userName">
			<p><spring:message code="name.label" /> : ${name}</p>
		</div>
		
		<div class="menu">
			<a href="menu" method="get"><spring:message code="menu.label" /></a>
		</div>
		
	</body>
	
</html>