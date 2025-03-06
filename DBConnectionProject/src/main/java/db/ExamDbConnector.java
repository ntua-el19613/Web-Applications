// Update if being necessary
package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class ExamDbConnector {
	
	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/leaguedb";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;	
	
	// Update Constructor, if being necessary
	public ExamDbConnector() {
		
	}
	
	/** Opens a DB connection (connection is internally stored in a private variable) */
	public void openDbConnection() throws Exception {
		// DB Connection Properties
		final Properties DB_PROP = new Properties();
		DB_PROP.setProperty("user"	, DB_USERNAME);
		DB_PROP.setProperty("password", DB_PASSWORD);
		DB_PROP.setProperty("charSet", "UTF-8");
		
		// Ensure that the DB Connector (i.e., Java Class) is available in your CLASSPATH
		// Update if being necessary
		Class.forName("com.mysql.cj.jdbc.Driver");
				
		// Get DB Connection
		this.conn = DriverManager.getConnection(DB_URL, DB_PROP);
	}
	
	/** Closes the DB connection (if any - internally stored in a private variable) */
	public void closeDbConnection() throws Exception {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}
	
	
	/**
	 * Examines if the user is valid or not. 
	 * 
	 * @param username
	 * 		The username
	 * @param passwordHash
	 * 		The password hash using SHA-256 algorithm
	 * @return
	 * 		<code>True</code> if the user is valid, otherwise <code>False</code>
	 * @throws Exception
	 * 		In case of an error (e.g., cannot connect to DB)
	 */
	public boolean examineUser(String username, String passwordHash) throws Exception {
		
		boolean response = false;
		
		// ** Question (a) **
		
		// TODO: Write your code here (examine if there is a row with the given data in the appropriate table)
		final String SELECT_USER_SQL_QUERY = 
				"SELECT * FROM USERS WHERE USERNAME = ? and PASSWORDHASH = ?";
			User user = null;
			final PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL_QUERY);
			ps.setString(1, username);
			ps.setString(2, passwordHash);
			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				final int id = rs.getInt("ID");
				final Date dateCreated = rs.getDate("DATECREATED");
				user = new User(id, username, passwordHash, dateCreated);
				break;
			}	
			if (user != null) response = true;
			rs.close();
			ps.close();
			
			return response;
		}


	/**
	 * Stores the given data in the appropriate DB table. 
	 * 
	 * @param date
	 * 		The {@link Date} when the game took place
	 * 
	 * @param team1id
	 * 		Team 1 unique ID (foreign key)
	 * @param team1score
	 * 		Team 1 score (positive integer)
	 * @param team2id
	 * 		Team 2 unique ID (foreign key)
	 * @param team2score
	 * 		Team 2 score (positive integer)
	 * @return
	 *		The number of DB rows affected
	 * @throws Exception
	 * 		In case of an error (e.g., cannot connect to DB) or when the data are invalid (e.g., negative numbers)		
	 */
	public int recordData(Date date, int team1id, int team1score, int team2id, int team2score) throws Exception {
		
		int rowsAffected = -1; 
		
		// ** Question (b) **
		
		// TODO: Write your code here (throw Exception in case of an error or inappropriate data)
		final String INSERT_USER_SQL_QUERY = 
			"INSERT INTO `game_data` VALUES (null, ?, ?, ?, ?, ?)";
		
		final PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL_QUERY);
		ps.setDate(1, date);
		ps.setInt(2, team1id);
		ps.setInt(3, team1score);
		ps.setInt(4, team2id);
		ps.setInt(5, team2score);
		rowsAffected = ps.executeUpdate();
		ps.close();
		
		return rowsAffected;
	}
	
	private static final String SELECT_GAME_DATA_QUERY = 
			"SELECT g.GAME_DATE, g.TEAM_1_ID, g.TEAM_1_SCORE, g.TEAM_2_ID, g.TEAM_2_SCORE FROM game_data g";

	public List<gameData> getGameData() throws SQLException {
	    final List<gameData> gameList = new ArrayList<>();
	    final PreparedStatement ps = conn.prepareStatement(SELECT_GAME_DATA_QUERY);
	    final ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
//	    	final Integer id = rs.getInt("ID");
	    	final Date gameDate = rs.getDate("GAME_DATE");
	        final Integer team1id = rs.getInt("TEAM_1_ID");
	        final Integer team1Score = rs.getInt("TEAM_1_SCORE");
	        final Integer team2id = rs.getInt("TEAM_2_ID");
	        final Integer team2Score = rs.getInt("TEAM_2_SCORE");
	        gameList.add(new gameData(gameDate, team1id, team1Score, team2id, team2Score));
	    }
	    rs.close();
	    ps.close();
	    return gameList;
	}

	/** For testing purposes ...  */
	public static void main(String[] args) throws Exception {
		
//		System.out.println(ExamDbConnector.class.getSimpleName());
//		
//		// Get DB connector instance
//		ExamDbConnector dbConnector = new ExamDbConnector();
//		
//		dbConnector.openDbConnection();
//		System.out.println("DB connection opened");
//		
//		
//		// View Data
//		final List<gameData> lista = dbConnector.getGameData();
//		System.out.println("View-Response: gameList.size(): " + lista.size());
//		for (gameData game : lista) {
//			System.out.println(" - " + game);
//		}
//		
//		// Check method 1 (password: pa) - It should return true
//		final String username = "ua";
//		final String passwordHash = "9513229d435978263d82902c59d4e0fb86c941a9bf09b605cd8a6643f40149a8";
//		boolean response1 = dbConnector.examineUser(username, passwordHash);
//		System.out.println(" - Check Method 1 - Response: " + response1);
//		
//		// Check method 2 - Normally, it should return 1
//		java.sql.Date date = new java.sql.Date(1000000L); // 1970-01-01
//		int team1id = 1;
//		int team1score = 90; 
//		int team2id = 2;
//		int team2score = 80;
//		int response2 = dbConnector.recordData(date, team1id, team1score, team2id, team2score);
//		System.out.println(" - Check Method 2 - Response: " + response2);
//		
//		// You can add here more tests, if being necessary
//				
//		dbConnector.closeDbConnection();
//		System.out.println("DB connection closed");

	}
	
}
