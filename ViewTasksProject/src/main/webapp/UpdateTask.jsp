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
<%@ page import="db.DbConnector" %>
<%@ page import="db.Task" %>
<%@ page import="db.User" %>
<%@ page import="db.Status" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Task</title>
</head>
<body>
<%
    final User sessionUser = (User) session.getAttribute("user");
    List<Status> statusList = null;  // Declare the statusList variable

    if (sessionUser == null) {
        // Redirect User to Login Page
        response.sendRedirect("Login.html");
    } else {
        try {
            // Get ALL status options
            DbConnector.getInstance().openDbConnection();
            statusList = DbConnector.getInstance().getStatus();
            DbConnector.getInstance().closeDbConnection();
        } catch (Throwable t) {
            // Inform the user in case of an Error
            final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
            response.getWriter().append(errMsg);
            t.printStackTrace();
            return;
        }
    }

    // Retrieve the taskId and statusId from the request parameters
    String taskId = request.getParameter("taskId");
    String statusId = request.getParameter("statusId");

    %>
    <p>Welcome, REGULAR USER: <%= sessionUser.getUsername() %> <a href="LogoutServlet">Logout</a></p>
    <h1>Update Task Status</h1>

    <form method="post">
    <input type="hidden" name="taskId" value="<%= taskId %>">
    <select name="status" id="status">
        <%-- Populate options based on the database values --%>
        <%
        for (Status status : statusList) {
            String statusIdStr = String.valueOf(status.getId());
        %>
        <option value="<%= statusIdStr %>" <%= statusIdStr.equals(statusId) ? "selected" : "" %>>
            <%= status.getName() %>
        </option>
        <%
        }
        %>
    	</select>
    	<input type="submit" value="Update Status">
	</form>
	<%
	if ("POST".equalsIgnoreCase(request.getMethod())) {
	    String newStatusId = request.getParameter("status");
	
	    try {
	        // Convert newStatusId to an integer
	        int statusID = Integer.parseInt(newStatusId);
	
	        // Update the database with the new status
	        DbConnector.getInstance().openDbConnection();
	        DbConnector.getInstance().updateTaskStatus(Integer.parseInt(taskId), statusID);
	        DbConnector.getInstance().closeDbConnection();
	
	        out.println("<p>Status updated successfully.</p>");
	    } catch (NumberFormatException | SQLException e) {
	        e.printStackTrace();
	        out.println("<p>Error occurred while updating status.</p>");
	    }
	}
	%>	
</body>
</html>
