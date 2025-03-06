import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.ExamDbConnector;
import db.User;
import db.gameData;

/**
 * Servlet implementation class InsertDataServlet
 */
@WebServlet("/InsertDataServlet")
public class InsertDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get User Data from Session
		final HttpSession session = request.getSession();
		final User sessionUser = (User) session.getAttribute("user");

		Integer rows = 0;
		
		if (sessionUser == null) {
			// Redirect User to Login Page
			response.sendRedirect( "Login.html"); 
		} else {
		
			// Get HTTP Request Parameters
			final String team1idstr = request.getParameter("team1id");
			final String team1scorestr = request.getParameter("team1score");
			final String team2idstr = request.getParameter("team2id");
			final String team2scorestr = request.getParameter("team2score");
			final String datestr = request.getParameter("datestr");
			
			try {
				// Process Data
				final Integer team1id = Integer.parseInt(team1idstr);
				final Integer team1score = Integer.parseInt(team1scorestr);
				final Integer team2id = Integer.parseInt(team2idstr);
				final Integer team2score = Integer.parseInt(team2scorestr);
				
				String dateFormatPattern = "dd-MM-yyyy"; // Corrected pattern
				SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
				java.util.Date utilDate = dateFormat.parse(datestr);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				
				ExamDbConnector dbConnector = new ExamDbConnector();
				dbConnector.openDbConnection();
				rows = dbConnector.recordData(sqlDate, team1id, team1score, team2id, team2score);
				dbConnector.closeDbConnection();
				
			} catch (Throwable t) {
				// Inform the user in case of an Error
				final String errMsg = "Storing Reservation Problem ... " + t.getMessage() + " Ask system administrators for details !";
				response.getWriter().append(errMsg);
				t.printStackTrace();
			}
			
			if(rows == 1) {
				
				final List<gameData> gameList;
				try {
					ExamDbConnector dbConnector = new ExamDbConnector();
					dbConnector.openDbConnection();
					gameList = dbConnector.getGameData();
					dbConnector.closeDbConnection();
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
	            out.println("<title>Game Data Page</title>\n");
	            //out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"CSS/WebApp2.css\" >");
	            out.println("</head><body>");
	            
	            out.print("<p>Welcome: " + sessionUser.getUsername() + " <a href=\"LogoutServlet\">Logout</a></p>");
	            
				out.print("<h1>Game Data</h1>");
				out.println("<table>");
				out.println("<tr>");
				out.println("<th>Game Date</th>");
				out.println("<th>Team1_ID</th>");
				out.println("<th>Team1_Score</th>");	
				out.println("<th>Team2_ID</th>");
				out.println("<th>Team2_Score</th>");
				out.println("</tr>");
				for (gameData game : gameList) {
					out.println("<tr>");
					Date UtilDate = game.getGameDate();
			        java.sql.Date SqlDate = new java.sql.Date(UtilDate.getTime());
			        out.println("<td>" + SqlDate + "</td>");
					out.println("<td>" + game.getTeam1id() + "</td>");
					out.println("<td>" + game.getTeam1score() + "</td>");
					out.println("<td>" + game.getTeam2id() + "</td>");
					out.println("<td>" + game.getTeam2score() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("</body></html>");
				
				out.close();
			}
			else {
				final String Msg = "DATA INSERTION UNSUCCESSFUL :(";
				response.getWriter().append(Msg);
			}
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
