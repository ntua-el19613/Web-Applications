package db;
public class courseData {

	private final Integer id;
	private final Integer coursesemester;
	private final String coursename;
	private final Integer courseteacherid;
	
	public courseData(Integer id, Integer coursesemester, String coursename, Integer courseteacherid) {
		this.id = id;
		this.coursesemester = coursesemester;
		this.coursename = coursename;
		this.courseteacherid = courseteacherid;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getCourseSemester() {
		return coursesemester;
	}
	
	public String getCcourseName() {
		return coursename;
	}
	
	public Integer getCourseTeacherID() {
		return courseteacherid;
	}
		
	@Override
	public String toString() {
		return "User [id=" + id + ", coursesemester=" + coursesemester + ", coursename=" + coursename + ", courseteacherid=" + courseteacherid + "]";
	}
}


