<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS View Client Logs"/>
</jsp:include>
<style>
	.count {
	font-size: 50px;
	}
	.ui-widget.ui-widget-content {
		border: 10px solid #c5c5c5;
	}
	.ui-widget-header {
		border: 1px solid #dddddd;
		background: #337ab7;
		color: #ffffff;
		font-weight: bold;
	}	
</style>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>View Client Log Entries</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	Viewing from <c:out value="${authenticatedUsersSite.shortName}"></c:out>
	
	<table class="table table-striped table-hover">
		<thead>
			<tr>					
				<td>Client Id</td>
				<td>Date/Time</td>
				<td>Description of Service</td>
				<td>Notes</td>
				<td>SiteId</td>			
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${log}" var="entry">
			<tr>					
				<td><c:out value="${entry.clientId }"></c:out></td>
				<td><c:out value="${entry.dateTime }"></c:out></td>				
				<td><c:out value="${entry.descOfService }"></c:out></td>
				<td><c:out value="${entry.notes }"></c:out></td>
				<td><c:out value="${entry.siteId }"></c:out></td>
			</tr>		
		</c:forEach>
		</tbody>
	</table>	

	<%@ include file="/jsp/includes/footer.jsp" %>			
</div>
</body>
</html>