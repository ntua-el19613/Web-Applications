

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ExamDbConnector;
import db.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get Session Data
		final HttpSession session = request.getSession();
		boolean usercheck;
		
		// Check User Data if being necessary
		if (session.getAttribute("user") == null) {
			// Get Request Parameters
			final String username = request.getParameter("username");
			final String password = request.getParameter("password");
			
			try {
				// Find User (if any)
				ExamDbConnector dbConnector = new ExamDbConnector();
				dbConnector.openDbConnection();
				usercheck = dbConnector.examineUser(username, Util.getHash256(password));
				User user = new User(username, Util.getHash256(password)); // Create a User object with user information
				dbConnector.closeDbConnection();
				
				// Update Session Data on condition that the User was found
				if (usercheck == true) {
				    // Assuming you have a User object to store in the session
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
		
		if (sessionUser != null) {
			response.sendRedirect( "InsertData.html"); 
		} else {
			response.sendRedirect( "index.html"); 
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
