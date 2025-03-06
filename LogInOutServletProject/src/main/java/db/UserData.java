package db;
import java.sql.Date;

public class UserData {

	private final Integer id;
	private final String username;
	private final String password;
	private final Date dateupdated;
	private final String roleid;
	
	public UserData(Integer id, String username, String password, Date dateupdated, String roleid) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.dateupdated = dateupdated;
		this.roleid = roleid;
	}
	
	public UserData(String username, String password) {
		this.id = null;
		this.username = username;
		this.password = password;
		this.dateupdated = null;
		this.roleid = null;
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
	
	public Date getDate() {
		return dateupdated;
	}
	
	public String getRole() {
		return roleid;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", roleid=" + roleid + ", dateupdated=" + dateupdated + "]";
	}

}
