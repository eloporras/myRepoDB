<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Donations Page"/>
	</jsp:include>
	
	<script type="text/javascript">
	function chooseCategory(){
		var selectedStorageType = $('#category').find(":selected").val();
		if(selectedStorageType == "Food"){
			$("#foodCategoryDiv").show();
			$("#supplyCategoryDiv").hide();
		}
		if(selectedStorageType == "Supply"){
			$("#foodCategoryDiv").hide();
			$("#supplyCategoryDiv").show();
		}		
	}
	
	$( function() {
	    $( "#datePicker" ).datepicker({
	        altField: "#expireDate",
	        altFormat: "yy-mm-dd",
	        dateFormat: "MM dd yy",
	        changeYear: true,
	        appendText: "(yyyy-mm-dd)"
	    });
	} );
	
	</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Add/Edit ASACS Client</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	<form action="/AddClientServlet" onsubmit="return validate();" method="POST">
		<div class="form-group">
			<label for="desc">Desc:</label> 
			<input type="text" id="desc" name="desc" class="form-control"/>
		</div>		
		<div class="form-group">
			<label for="name">Name:</label> 
			<input type="text" id="name" name="name" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="phone">Phone:</label> 
			<input type="text" id="phone" name="phone" class="form-control"/>
		</div>
		<input type="submit" value="AddClient" id="action" name="action" class="btn btn-default" class="form-control"/>
	</form>
	<hr />
	<br/>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>