package db;
public class StudentCourseData {

	private final Integer id;
	private final Integer studentid;
	private final Integer courseid;
	private final Integer studentgrade;
	
	public StudentCourseData(Integer id, Integer studentid, Integer courseid, Integer studentgrade) {
		this.id = id;
		this.studentid = studentid;
		this.courseid = courseid;
		this.studentgrade = studentgrade;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getStudentID() {
		return studentid;
	}
	
	public Integer getCourseID() {
		return courseid;
	}
	
	public Integer getStudentGrade() {
		return studentgrade;
	}
		
	@Override
	public String toString() {
		return "User [id=" + id + ", studentid=" + studentid + ", courseid=" + courseid + ", studentgrade=" + studentgrade + "]";
	}

}
