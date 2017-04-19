<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS View Food Bank Items"/>
</jsp:include>
<script type="text/javascript">
function grant(requestId){
	var qty = $("#numUnits-" + requestId).val();
	window.location = "/RequestsServlet?action=grant&requestId=" + requestId + "&qty=" + qty
}
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Pending Food Bank Requests</h1>

	<%@ include file="/jsp/includes/messages.jsp" %>
	<table class="table table-striped table-hover" border="0">
		<thead>
			<tr>
				<!-- 1 --><td>Item ID</td>
				<!-- 2 --><td>Name</td>			
				<!-- 3 --><td>Requesting Site</td>
				<!-- 4 --><td>Requesting User</td>
				<!-- 5 --><td>Units Requested</td>
				<!-- 6 --><td>Units Available</td>
				<!-- 7 --><td>Units Filled</td>
				<!-- 8 --><td>Status</td>
				<!-- 9 --><td></td>
				<!-- 10--><td></td>
				<!-- 11 --><td></td>											
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${items}" var="item">
			<tr>
				<!-- 1 --><td><c:out value="${item.itemId}"></c:out></td>
				<!-- 2 --><td><c:out value="${item.name}"></c:out></td> 
				<!-- 3 --><td><c:out value="${item.requesteeSiteId}"></c:out></td>
				<!-- 4 --><td><c:out value="${item.userId}"></c:out></td>
				<!-- 5 --><td><c:out value="${item.numberRequested}"></c:out></td>
				<!-- 6 --><td><c:out value="${item.item.numberUnits}"></c:out></td>
				<!-- 7 --><td><c:out value="${item.numberFilled}"></c:out></td>
				<!-- 8 --><td><c:out value="${item.status.value}"></c:out></td>
				<!-- 9 --><td><a href="javascript:grant(<c:out value="${item.requestId}"></c:out>)" class="btn btn-default" role="button">Grant <span class='glyphicon glyphicon-ok' aria-hidden='true'></span></a></td>
				<!-- 10--><td><input type="text" id="numUnits-<c:out value="${item.requestId}"></c:out>" maxlength="2" size="2" value="<c:out value="${item.numberRequested}"></c:out>"> <small>Units</small></td>
				<!-- 11--><td><a href="/RequestsServlet?action=deny&requestId=<c:out value="${item.requestId}"></c:out>" class="btn btn-default" role="button">Deny <span class='glyphicon glyphicon-ban-circle' aria-hidden='true'></span></a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>

</div>
</body>
</html>