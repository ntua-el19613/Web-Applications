package db;

import java.sql.Date;

public class User {

	private final Integer id;
	private final String username;
	private final String passwordHash;
	private final Date dateCreated;
	
	public User(Integer id, String username, String passwordHash, Date dateCreated) {
		this.id = id;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = dateCreated;
	}
	
	public User(String username, String passwordHash) {
		this.id = null;
		this.username = username;
		this.passwordHash = passwordHash;
		this.dateCreated = null;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", dateCreated=" + dateCreated + "]";
	}

}
