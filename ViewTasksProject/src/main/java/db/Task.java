package db;

import java.sql.Date;

public class Task {
	
	private final Integer id;
	private final Integer user_id;
	private final String title;
	private final String description;
	private final Integer status_id;
	private final Date date;
	
	public Task(Integer id, Integer user_id, String title, String description, Integer status_id, Date date) {
		this.id = id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.status_id = status_id;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return user_id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	public Integer getStatusId() {
		return status_id;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", user_id=" + user_id + ", title=" + title + ", description=" + description + ", status_id=" + status_id + ", date=" + date +"]";
	}

}
