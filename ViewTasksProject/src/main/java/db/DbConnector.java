package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class DbConnector {

	// DB URL with HOST IP, PORT and DB NAME
	private static final String DB_URL = "jdbc:mysql://localhost:3306/exam0623db";
	// DB credentials
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	
	// DB Connection - Used by the other methods of this class
	private Connection conn;
	
	// Singleton Design Pattern
	private static DbConnector instance = null;
	/** Ensure that we will create only one instance of this class */
	public static DbConnector getInstance() {
		synchronized (DbConnector.class) {
			if (instance == null) {
				instance  = new DbConnector();
			}
			return instance;
		}
	}
	private DbConnector() {
		
	}
	
	/** Open DB Connection*/
	public void openDbConnection() throws SQLException, ClassNotFoundException {
		// DB Connection Properties
		final Properties DB_PROP = new Properties();
		DB_PROP.setProperty("user"	, DB_USERNAME);
		DB_PROP.setProperty("password", DB_PASSWORD);
		DB_PROP.setProperty("charSet", "UTF-8");
		
		// Ensure that the DB Connector (i.e., Java Class) is available in your CLASSPATH
		Class.forName("com.mysql.cj.jdbc.Driver");
				
		// Get DB Connection
		this.conn = DriverManager.getConnection(DB_URL, DB_PROP);
	}
		
	private static final String SELECT_USER_SQL_QUERY = 
		"SELECT * FROM user, role WHERE user.ROLE_ID = role.ID and USERNAME = ? and USERPASS = ?";
	
	public User getUser(final String username, final String password) throws SQLException {
		User user = null;
		final PreparedStatement ps = conn.prepareStatement(SELECT_USER_SQL_QUERY);
		ps.setString(1, username);
		ps.setString(2, password);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			final int id = rs.getInt("ID");
			final Integer role = rs.getInt("ROLE_ID");
			user = new User(id, username, password, role);
			break;
		}
		rs.close();
		ps.close();
		return user;
	}
	
	private static final String SELECT_USER_TASK_QUERY = 
			"SELECT * FROM TASK , STATUS WHERE TASK.STATUS_ID = STATUS.ID AND USER_ID = ?";
	
	public List<Task> getTasks(final Integer userid) throws SQLException {
		final List<Task> TaskList = new ArrayList<>();
		final PreparedStatement ps = conn.prepareStatement(SELECT_USER_TASK_QUERY);
		ps.setInt(1, userid);
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			final Integer id = rs.getInt("ID");
			final Integer userID = rs.getInt("USER_ID");
			final String title = rs.getString("TITLE");
			final String description = rs.getString("DESCRIPTION");
			final Integer statusid = rs.getInt("STATUS_ID");
			final Date dateUpdated = rs.getDate("DATE_UPDATED");
			TaskList.add(new Task(id, userID,  title, description, statusid, dateUpdated));
		}
		rs.close();
		ps.close();
		return TaskList;
	}
	
	private static final String SELECT_ALL_FROM_STATUS_SQL_QUERY = 
			"SELECT * FROM STATUS";

	public List<Status> getStatus() throws SQLException {
		final List<Status> statusList = new ArrayList<>();
		final Statement st = conn.createStatement();
		final ResultSet rs = st.executeQuery(SELECT_ALL_FROM_STATUS_SQL_QUERY);
		while (rs.next()) {
			final Integer id = rs.getInt("ID");
			final String name = rs.getString("NAME");
			statusList.add(new Status(id, name));
		}
		rs.close();
		st.close();
		return statusList;
	}
	
	private static final String UPDATE_TASK_STATUS_QUERY = 
	    "UPDATE TASK SET STATUS_ID = ? , DATE_UPDATED = now() WHERE ID = ?";

	public void updateTaskStatus(final Integer statusid, final Integer taskid) throws SQLException {
	    PreparedStatement ps = null;
	    try {
	        ps = conn.prepareStatement(UPDATE_TASK_STATUS_QUERY);
	        ps.setInt(1, statusid); 
	        ps.setInt(2, taskid);   
	        ps.executeUpdate();
	    } finally {
	        if (ps != null) {
	            ps.close();
	        }
	    }
	}

	
	/** Close DB Connection */
	public void closeDbConnection() throws SQLException {
		if (conn != null && !conn.isClosed()) {
			this.conn.close();
		}
	}

	/**
	 * For Testing Purposes ...
	 */
	public static void main(String[] args) throws Exception {
		final DbConnector db = DbConnector.getInstance();
		db.openDbConnection();
		db.closeDbConnection();
		System.out.println("all good");
	}

}
