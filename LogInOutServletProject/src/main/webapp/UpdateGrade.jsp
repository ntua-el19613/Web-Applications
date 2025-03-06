<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.ServletException" %>
<%@ page import="javax.servlet.annotation.WebServlet" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.lang.NumberFormatException" %>
<%@ page import="db.DbConn" %>
<%@ page import="db.UserData" %>

<!DOCTYPE html>
<html><head>

	<meta charset="ISO-8859-1">
	<title>Exam Sep 23 | Updated Data</title>
	
	<script type="text/javascript">
	
	document.addEventListener('DOMContentLoaded', function() {
	    // Select HTML elements by their names and IDs
	    const grade = document.querySelector('input[name=studentgrade]');
	    const submitbtn = document.querySelector('input[type=submit]');

	    // Initialize the submit button as disabled
	    submitbtn.disabled = true;

	    // Add an event listener for the username field when a key is released
	    grade.addEventListener("click", function() {
	        var input = grade.value;
	        // Check if the input is empty and disable/enable the submit button accordingly
	        if (input < 0 || input > 10) {
	        	submitbtn.disabled = true;
	        	grade.style.backgroundColor = "red";
	        }
	        else {
	        	submitbtn.disabled = false;
	        	grade.style.backgroundColor = "green";
	        }
	            
	    });
	})
	</script>
	
</head><body>

<%
    final UserData sessionUser = (UserData) session.getAttribute("user");

    if (sessionUser == null) {
        // Redirect User to Login Page
        response.sendRedirect("Login.html");
    } 
%>
	<p>Welcome, REGULAR USER: <%= sessionUser.getUsername() %> <a href="Logout">Logout</a></p>
	<h3>Update Student Course Grade</h3>

	<form action="UpdateDataAction" method="GET">
	
		<input type="hidden" name="courseid" value="xyz">
		<input type="hidden" name="studentid" value="xyz">
		
		<table>
			<tr>
				<th>Grade:</th>
				<td> <input type="number" name="studentgrade"> </td>
				<td>Integer between 0 and 10</td>
			</tr>
			<tr>
				<td> <input type="reset" value="Reset" > </td>
				<td colspan="2"> <input type="submit" value="Update Data"> </td>
			</tr>
		</table>
		
	</form>

</body></html>