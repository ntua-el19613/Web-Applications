import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.DbConnector;
import db.Task;
import db.User;

/**
 * Servlet implementation class ViewTasks
 */
@WebServlet("/ViewTasks")
public class ViewTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTasks() {
        super();
    }
   
    String truncateDescription(String description) {
        if (description.length() > 50) {
            return description.substring(0, 50) + "..."; // Truncate and add ellipsis
        }
        return description;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get Session Data
		final HttpSession session = request.getSession();
		
		// Check User Data if being necessary
		if (session.getAttribute("user") == null) {
			// Get Request Parameters
			final String username = request.getParameter("username");
			final String password = request.getParameter("password");
			
			try {
				// Find User (if any)
				DbConnector.getInstance().openDbConnection();
				final User user = DbConnector.getInstance().getUser(username, password);
				DbConnector.getInstance().closeDbConnection();
				
				// Update Session Data on condition that the User was found
				if (user != null) {
					session.setAttribute("user", user);
				}
			
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
				return;
			}
		}
		// Get User Data from Session
		final User sessionUser = (User) session.getAttribute("user");
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		} else {
			if(sessionUser.getRole() == 1) {
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
	            out.println("<html><head>");
	            out.println("<title>Admin Page</title>\n");
	            out.println("</head><body>");
	            
	            out.print("<p>Welcome, ADMINISTRATOR: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>LET'S PASS THIS TEST!</h1>");
				out.println("</body></html>");
				
				out.close();
			} else if(sessionUser.getRole() == 2) {
				
				final List<Task> taskList;
				try {
					// Get ALL vehicles
					DbConnector.getInstance().openDbConnection();
					taskList = DbConnector.getInstance().getTasks(sessionUser.getId());
					DbConnector.getInstance().closeDbConnection();
				} catch (Throwable t) {
					// Inform the user in case of an Error
					final String errMsg = "Error ... " + t.getMessage() + " Ask system administrators for details !";
					response.getWriter().append(errMsg);
					t.printStackTrace();
					return;
				}
				
				response.setContentType("text/html; charset=UTF-8");
				final PrintWriter out = response.getWriter();
				
				out.println("<!DOCTYPE html>");
	            out.println("<html><head>");
	            out.println("<title>Tasks</title>\n");
	            //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/WebApp2.css\" >");
	            out.println("</head><body>");
	            
	            out.print("<p>Welcome, REGULAR USER: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>View Tasks</h1>");
				out.print("<style>table{border-collapse: collapse; width: 100%;} </style>");	
				out.print("<style>td{border: 1px solid black; padding: 8px;} </style>");	
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>ID</th>");
				out.println("<th>Title</th>");
				out.println("<th>Description</th>");	
				out.println("<th>Status</th>");
				out.println("<th>Date Updated</th>");
				out.println("</tr>");
				for (Task task : taskList) {
					out.println("<tr>");
					out.println("<td>" + task.getId() + "</td>");
					out.println("<td>" + task.getTitle() + "</td>");
					out.println("<td>" + truncateDescription(task.getDescription()) + "</td>");
					if(task.getStatusId() == 1) out.println("<td>ASSIGNED</td>");
					else if(task.getStatusId() == 2) out.println("<td>PENDING</td>");
					else if(task.getStatusId() == 3) out.println("<td>COMPLETED</td>");
					Date utilDate = new Date(task.getDate().getTime());
			        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			        String UpdatedDate = sdf.format(utilDate);
					out.println("<td>" + UpdatedDate + "</td>");
					out.print("<td><a href=\"UpdateTask.jsp?taskId=" + task.getId() + "&statusId=" + task.getStatusId() + "\">Update Task</a></td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body></html>");
				
				out.close();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
