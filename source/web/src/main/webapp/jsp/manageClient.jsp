<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS Manage Client"/>
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
<script type="text/javascript">

function addCriteria(){

	var lastTerm = $(".searchcriteria").children(".row").length;
	var newId = lastTerm + 1;
	
	if(newId > 1){
    	var connectTerm = "<select id='sel-connect-" + lastTerm + "' name='sel-connect-" + lastTerm + "' class='form-control'>";
    	connectTerm += "<option value='AND'>AND</option>";
    	connectTerm += "<option value='OR'>OR</option>";
    	connectTerm += "</select>";
    	$("#criteria-" + lastTerm + " > .form-group > #sc-connect").html(connectTerm);
	}
	
	criteriaSelect = "" + 
	"<div class='row' id='criteria-" + newId + "'>" +
	"	<div class='form-group'>" +
	"		<div class='col-sm-3' id='sc-column'>" +
	"			<select id='sel-criteria-" + newId + "' name='sel-criteria-" + newId + "' onchange='showCriteria(this, "  + newId + ")' class='form-control'>" +
	"				<option value='0'>	</option>" +
	"				<option value='1'>Description</option>" +
	"				<option value='2'>Name</option>" +
	"			</select>" +
	"		</div>" +
	"		<div class='col-sm-3' id='sc-selector'></div>" +
	"		<div class='col-sm-3' id='sc-value'></div>" +
	"		<div class='col-sm-3' id='sc-connect'></div>" +
	"	</div>" + 
	"</div>";
	
	$(".searchcriteria").append(criteriaSelect);
    $( ".loading").hide();
}
function clearCriteria(){
	var lastTerm = $(".searchcriteria").empty();
	
	if ( $.fn.dataTable.isDataTable( 'table' ) ) {
	    table = $('table').DataTable();
		table.clear().draw();
	}	
    $("table tbody").empty();
    
    table = $('table').DataTable( {
        bFilter: false,
        retrieve: true
    } );
    
    $( ".loading").hide();
}

function showCriteria(chosenCriteria, newId){
	var selected = parseInt($("#" + chosenCriteria.id).find(":selected").val());
	switch(selected) {
    case 1://description
    	selectorSelect = "<select id='sel-selector-" + newId + "' name='sel-selector-" + newId + "' class='form-control'>";
    	selectorSelect += "<option value='MATCH'>Equals</option>";
    	selectorSelect += "<option value='LIKE'>Like</option>";
    	selectorSelect += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).html(selectorSelect);
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html("<input type='text' id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'></input>");

        break;
    case 2://name
    	selectorSelect = "<select id='sel-selector-" + newId + "' name='sel-selector-" + newId + "' class='form-control'>";
    	selectorSelect += "<option value='MATCH'>Equals</option>";
    	selectorSelect += "<option value='LIKE'>Like</option>";
    	selectorSelect += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).html(selectorSelect);
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html("<input type='text' id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'></input>");

        break;        
	}
}

function getColumn(elem, columnName){
	return elem.parents(".row").children(".form-group").children(columnName);
}


function search(){
	var formData = $( "#searchItems" ).serialize();
	$("#messagesarea").empty();

    $.ajax({
        data: formData,
        type: 'POST',
        url: '/ClientServlet',
        success: function(data, textStatus, xhr) {
            var json = $.parseJSON(xhr.responseText);
            updateView(json);
        },
        error: function(xhr, textStatus, errorThrown) {
			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error Searching : " + errorThrown + "<br /></p>");

        },
        complete: function(xhr, textStatus){
            $( ".loading").hide();
        }
    });	
}

var dialog, dialog2, form;

function clientCheckIn(clientId, clientName, serviceType){
	$("#checkin-clientId").val(clientId);
	$("#checkin-name").html(clientName);
	$("#checkin-service").html(serviceType);
	$("#checkin-notes").val('');

	dialog.dialog( "open" );
}

var cache;

function editUser(clientId){
	for(i=0; i<cache.length; i++){
		if(cache[i]["identifier"] == clientId){
			$("#editcl-clientId").val(clientId);
			$("#editcl-name").html(cache[i]["name"]);
			$("#editcl-editname").val(cache[i]["name"]);
			$("#editcl-desc").val(cache[i]["description"]);
			$("#editcl-phone").val(cache[i]["phone"]);
		}
	}
	
	dialog2.dialog( "open" );
}

/*
 *function updateView - Shows the search results
 */
function updateView(clients){
	var service = "<c:out value="${checkInType}"></c:out>";
	if ( $.fn.dataTable.isDataTable( 'table' ) ) {
	    table = $('table').DataTable();
		table.clear().draw();
		table.destroy();
	} //end if
    $("table tbody").empty();

	for(i=0; i<clients.length; i++){
		var category = clients[i]["category"];
		var row = "<tr>";
		row += "<td>";
		row += "<span style='padding:0 5px 0 0'>"
		row += "<a href='javascript:clientCheckIn(" + clients[i]["identifier"] + ", \"" + clients[i]["name"] + "\", \"" + service + "\")' class='btn btn-default btn-sm' role='button' data-toggle='tooltip' title='Checkin'><span class='glyphicon glyphicon-log-in' aria-hidden='true'></span></a>";
		row += "<a href='javascript:editUser(" + clients[i]["identifier"] + ")' class='btn btn-default btn-sm' role='button' data-toggle='tooltip' title='View/Edit Client'><span class='glyphicon glyphicon-user' aria-hidden='true'></span></a>";
		row += "<a href='/ViewLogEntryServlet?init=true&clientId=" + clients[i]["identifier"] + "' class='btn btn-default btn-sm' role='button' data-toggle='tooltip' title='See Logs'><span class='glyphicon glyphicon-list' aria-hidden='true'></span></a>";
		row += "</span>"+ clients[i]["name"] + "</td>";
		row += "<td>" + clients[i]["description"] + "</td>"
		row += "</tr>";
		
        $("table tbody").append(row);
	} //end for
	
    table = $('table').DataTable( {
        bFilter: false,
        retrieve: true
    } );// end table
    
    $('[data-toggle="tooltip"]').tooltip(); 
    
    cache = clients;
} //end function update view

//DIALOG
$( function() {
    
    function callCheckin(){
    	
    	var clientId = $("#checkin-clientId").val();
    	var clientName = $("#checkin-name").html();
    	var serviceType = $("#checkin-service").html();
    	var notes = $("#checkin-notes").val();
    	
   		var formData = { 'action':'doCheckin', 'clientId':clientId, 'serviceType':serviceType, 'notes':notes }
		  $.ajax({
		        data: formData,
		        type: 'POST',
		        url: '/ClientServlet',
		        success: function(data, textStatus, xhr) {
		            if(xhr.responseText == "Success"){
						$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> " + clientName + " successfully checked in for this " +serviceType+ ".<br /></p>");
		            }
		        },
		        error: function(xhr, textStatus, errorThrown) {
					$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error During Checkin : " + errorThrown + "<br /></p>");

		        },
		        complete: function(xhr, textStatus){
		            $( ".loading").hide();
		        }
		    });				
		
        dialog.dialog( "close" );
    }

    function callSaveClient(){
    	var clientId = $("#editcl-clientId").val();
    	var clientName = $("#editcl-editname").val();
    	var clientDesc = $("#editcl-desc").val();
    	var clientPhone = $("#editcl-phone").val();
    	
   		var formData = { 'action':'doClientUpdate', 'clientId':clientId, 'name':clientName, 'desc':clientDesc, 'phone': clientPhone }
		  $.ajax({
		        data: formData,
		        type: 'POST',
		        url: '/ClientServlet',
		        success: function(data, textStatus, xhr) {
		            if(xhr.responseText == "Success"){
						$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> " + clientName + " updated successfully.<br /></p>");
		            }
		        },
		        error: function(xhr, textStatus, errorThrown) {
					$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error During Update : " + errorThrown + "<br /></p>");

		        },
		        complete: function(xhr, textStatus){
		            $( ".loading").hide();
		        }
		    });				
		
        dialog2.dialog( "close" );
    }
    dialog = $( "#dialog-checkin" ).dialog({
        autoOpen: false,
        height: 350,
        width: 350,
        modal: true,
        buttons: {
          "Checkin": callCheckin,
          Cancel: function() {
            dialog.dialog( "close" );
          }
        },
        close: function() {
        }
      });
    
    dialog2 = $( "#dialog-editclient" ).dialog({
        autoOpen: false,
        height: 350,
        width: 350,
        modal: true,
        buttons: {
          "Update": callSaveClient,
          Cancel: function() {
            dialog2.dialog( "close" );
          }
        },
        close: function() {
        }
      });     
});
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Manage Clients</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	Viewing from <c:out value="${authenticatedUsersSite.shortName}"></c:out>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Client Search Criteria</h3>
	  </div> 
	  <div class="panel-body">
	  	<a href="javascript:addCriteria();" class="btn btn-default" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Criteria</a>
	  
	  	<form id="searchItems">
	  	<input type="hidden" value="search" name="action"/>
		<div class='row' style="background-color: #337ab7; color: white; margin: 10px 0 10px 0;">
			<div class='col-sm-3'>Search By</div>
			<div class='col-sm-3'></div>
			<div class='col-sm-3'>Value</div>
			<div class='col-sm-3'></div>
		</div>
	  	<div class="searchcriteria" style="padding:5px 0 20px 0">			
		</div>
		</form>
		<a href="javascript:search();" class="btn btn-default btn-md" role="button"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search</a>
		<a href="javascript:clearCriteria();" class="btn btn-default btn-md" role="button"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> Reset</a>
	  </div>
	</div>
	<table class="table table-striped table-hover">
		<thead>
			<tr>					
				<td>Name</td>
				<td>Description</td>			
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>	
	
	<%@ include file="/jsp/includes/footer.jsp" %>
	
	
	<!-- Begin Check In Form -->
	<div id="dialog-checkin" title="Check in Client"> 
		<h4><span id="checkin-client"></span></h4>
		<p class="validateTips">Check-in <span id="checkin-name"></span> to  <span id="checkin-service"></span>.</p>
		<form>
		  <fieldset>
		    <label for="name">Client Notes</label><br/>
		    <textarea rows="7" cols="41" name="checkin-notes" id="checkin-notes"  class="text ui-widget-content ui-corner-all">
		    </textarea>
		    <input type="hidden" name="checkin-clientId" id="checkin-clientId">
		    <input type="submit" style="position:absolute; top:-1000px">
		  </fieldset>
		</form>
	</div>	
	<!-- End Check In Form -->
	<div id="dialog-editclient" title="Edit Client"> 
		<h4><span id="editcl-client"></span></h4>
		<p class="validateTips">Update <span id="editcl-name"></span> to  <span id="editcl-service"></span>.</p>
		<form>
		  <fieldset>
		    <label for="editcl-desc">Description</label><br/>
		   	<input type="text" name="editcl-desc" id="editcl-desc" value="" class="text ui-widget-content ui-corner-all"><br/>

		    <label for="editcl-editname">Name</label><br/>
		   	<input type="text" name="editcl-editname" id="editcl-editname" value="" class="text ui-widget-content ui-corner-all"><br/>
		   	
		   	<label for="editcl-phone">Phone</label><br/>
		   	<input type="text" name="editcl-phone" id="editcl-phone" value="" class="text ui-widget-content ui-corner-all"><br/>
		   			    
		    <input type="hidden" name="editcl-clientId" id="editcl-clientId">
		    <input type="submit" style="position:absolute; top:-1000px">
		  </fieldset>
		</form>
	</div>			
</div>
</body>
</html>