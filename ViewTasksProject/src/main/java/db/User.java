package db;

//import java.util.Date;

public class User {

	private final Integer id;
	private final String username;
	private final String password;
	private final Integer role;
	
	public User(Integer id, String username, String password, Integer role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getRole() {
		return role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
	}

}
