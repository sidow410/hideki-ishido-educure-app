<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>SmartSystem_Login</title>
		<link href="css/commons.css" rel="stylesheet">
		<style>
			h1{
				text-align: center;		/*中央寄せにする*/
				font-size: 100px;		/*フォントサイズをpxに設定する*/
			}
			.error{
				text-align: center;
				font-size: 25px;
				color: red;				/*文字の色を変更する*/
				margin: 40px;
			}
			
			.form{
				text-align: center;
				font-size: 20px;
				margin: 50px;
			}
			
			.form input{
				border-radius: 5px;
				width: 150px;
				height: 20px;
			}
			
			button{
				text-align: center;
				width: 100px;			/* 幅をpxに設定 */
				height: 30px;			/*高さをpxに設定*/
				border-radius: 20px;		/* ボタンの角を丸くする */
				display: block;			/* ボタンをブロック要素にする */
				margin: 0 auto;			/* 左右のマージンを自動にして中央に配置 */
			}
		</style>
	</head>
	
	<body>
		
		<h1>Smart System</h1>
		
		<form:form action="menu" method="post" modelAttribute="login">
		
			<div class="form">
				<spanclass="error"><form:errors path="name" cssStyle="color: red"/></span><br>
				<spring:message code="name.label" /> ： <input type="text" name="name"><br>
				<br>
				<spanclass="error"><form:errors path="password" cssStyle="color: red"/></span><br>
				<spring:message code="password.label" /> ： <input type="password" name="password">
				
			</div>
			
			<button type="submit">ログイン</button>
			
			<c:if test="${not empty miss}"><div class="error">${miss}</div></c:if>
			
		</form:form>
		
		
	</body>
	
</html>
