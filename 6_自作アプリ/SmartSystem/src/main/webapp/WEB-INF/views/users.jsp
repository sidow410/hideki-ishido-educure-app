<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>

<html>

	<head>
		<meta charset="UTF-8">
		<title>SmartSystem_Users</title>
		<link href="css/commons.css" rel="stylesheet">
		<link href="css/users.css" rel="stylesheet">
	</head>
	
	<body>
	
		<form:form action="users" method="post" modelAttribute="users">
				
			<div>
				<label><spring:message code="number.label" /><input type="text" name="number"></label>
				<label><spring:message code="name.label" /><input type="text" name="name"></label>
				<label><spring:message code="password.label" /><input type="text" name="password"></label>
				<label><spring:message code="authority.label" />
				<select name="authority">
					<option name="null"> </option>
					<option name="マネージャー">マネージャー</option>
					<option name="従業員">従業員</option>
				</select></label>
			</div>	
					
				<button type="submit" name="btn" value="search" title="ユーザー番号 or ユーザー名"><spring:message code="search.label" /></button>
				<button type="submit" name="btn" value="insert" title="全ての項目"><spring:message code="insert.label" /></button>
				<button type="submit" name="btn" value="update" title="ユーザー番号 と 更新したい項目"><spring:message code="update.label" /></button>
				<button type="submit" name="btn" value="delete" title="ユーザー番号"><spring:message code="delete.label" /></button>
			
		</form:form>
		
		<c:if test="${not empty success}"><p class="success">${success}</p></c:if>
		<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
		
		<table>
			
			<h1>Users List</h1>
			
			<thead>
				<tr>
					<th><spring:message code="number.label" /></th>
					<th><spring:message code="name.label" /></th>
					<th><spring:message code="password.label" /></th>
					<th><spring:message code="authority.label" /></th>
				</tr>
			</thead>
		
			<tbody>
				<c:forEach var="usersList" items="${usersResult}">
					<tr>
						<td>${fn:escapeXml(usersList.getNumber())}</td>
						<td>${fn:escapeXml(usersList.getName())}</td>
						<td>${fn:escapeXml(usersList.getPassword())}</td>
						<td>${fn:escapeXml(usersList.getAuthority())}</td>
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