<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Lista klientów</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">Lista klientów </span></div>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>Imię</th>
				        <th>Nazwisko</th>
				        <th>E-mail</th>
				        <th>Pesel</th>
				        <th width="100"></th>
				        <th width="100"></th>
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.pesel}</td>
						<td><a href="<c:url value='/edit-user-${user.pesel}' />" class="btn btn-success custom-width">edytuj</a></td>
						<td><a href="<c:url value='/delete-user-${user.pesel}' />" class="btn btn-danger custom-width">usuń</a></td>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
	 	<div class="well">
	 		<a href="<c:url value='/newuser' />">Dodaj nowego klienta</a>
	 	</div>
   	</div>
</body>
</html>