<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>SmartSystem_Function</title>
		<link href="css/commons.css" rel="stylesheet">
		<link href="css/function.css" rel="stylesheet">
	</head>

	<body>
		
		<form:form action="function" method="post" modelAttribute="function">
		
			<button type="submit" name="btn" value="Android"><spring:message code="Android.label" /></button>
			<button type="submit" name="btn" value="iPhone"><spring:message code="iPhone.label" /></button>
			
			<div>
				<c:if test="${userAuthority}">
					<label><spring:message code="id.label" /><input type="text" name="id"></label>
				</c:if>
				<label><spring:message code="model.label" /><input type="text" name="model"></label>
				<label><spring:message code="size.label" /><input type="text" name="size"></label>
				<c:if test="${userAuthority}">
					<label><spring:message code="color.label" /><input type="text" name="color"></label>
					<label><spring:message code="stock.label" /><input type="text" name="stock"></label>
				</c:if>
				
				<label><spring:message code="os.label" />
				<select name="os">
					<option name="null"> </option>
					<option name="Android"><spring:message code="Android.label" /></option>
					<option name="iPhone"><spring:message code="iPhone.label" /></option>
				</select></label>
			</div>
			
			<button type="submit" name="btn" value="search" title="機種名 or 容量 or OS"><spring:message code="search.label" /></button>
			<c:if test="${userAuthority}">
				<button type="submit" name="btn" value="insert" title="全ての項目"><spring:message code="insert.label" /></button>
				<button type="submit" name="btn" value="update" title="id と 在庫数"><spring:message code="update.label" /></button>
				<button type="submit" name="btn" value="delete" title="id"><spring:message code="delete.label" /></button>
			</c:if>
			
		
		
			<c:if test="${not empty success}"><p class="success">${success}</p></c:if>
			<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
		
			<table>
		
				<h1>Smart Phones List</h1>
		
				<thead>
					<tr>
						<th><spring:message code="id.label" /></th>
						<th><spring:message code="model.label" /></th>
						<th><spring:message code="size.label" /></th>
						<th><spring:message code="color.label" /></th>
						<th><spring:message code="stock.label" /></th>
						<th><spring:message code="os.label" /></th>
						<th>販売</th>
					</tr>
				</thead>
			
				<tbody>
					<c:forEach var="smartPhonesList" items="${searchResult}">
						<tr>
							<td>${fn:escapeXml(smartPhonesList.getId())}</td>
							<td>${fn:escapeXml(smartPhonesList.getModel())}</td>
							<td>${fn:escapeXml(smartPhonesList.getSize())}GB</td>
							<td>${fn:escapeXml(smartPhonesList.getColor())}</td>
							<td>${fn:escapeXml(smartPhonesList.getStock())}</td>
							<td>${fn:escapeXml(smartPhonesList.getOs())}</td>
							<td><button type="submit" name="btn" value="${fn:escapeXml(smartPhonesList.getId())}">販売</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</form:form>
		
		<div class="userName">
			<spring:message code="name.label" /> : ${name}
		</div>
		
		<div class="menu">
			<a href="menu" method="get"><spring:message code="menu.label" /></a>
		</div>
		
	</body>
	
</html>