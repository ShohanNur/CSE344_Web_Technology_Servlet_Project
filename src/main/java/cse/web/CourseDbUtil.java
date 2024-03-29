package cse.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;


public class CourseDbUtil {

	private DataSource datasource;
	
	public CourseDbUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	//admin servlet
	public ArrayList<Course> getAllCourses() throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from courses";
			String sql2="select count(user_name) as total from enrolled_students  where course_code=?";
			
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				int courseId = rs.getInt("c_id");
				System.out.println(courseId);
				String course_code= rs.getString("course_code");
				String c_title = rs.getString("c_title");
				String info = rs.getString("info");
				int courseTeacherId = rs.getInt("course_teacher_id");
				String courseTeacherName = rs.getString("course_teacher_name");
				
                stmt2 = conn.prepareStatement(sql2);
				
				stmt2.setString(1, course_code);
				
				rs2 = stmt2.executeQuery();
				if(rs2.next())
				{
					
					int noStudents = rs2.getInt("total");
					Course course = new Course(course_code, c_title, courseTeacherName,noStudents);
					courses.add(course);	
				}
				else {
					Course course = new Course(course_code, c_title, courseTeacherName,0);
					courses.add(course);
				}
				
			
				
				
				
				
				
				
			}
			
			return courses;
			
		}finally {
			close(conn, stmt, rs);
		}
		
	}
	
	//course details againts c_code 
	
		public Course fetchCourseDetails(String c_code) throws Exception{
			
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			PreparedStatement stmt2 = null;
			ResultSet rs2 = null;
			
			try {
				conn = datasource.getConnection();
				
				System.out.println(c_code);
				
				String sql = "select * from courses where course_code=?";
				String sql2="select count(user_name) as total from enrolled_students  where course_code=?";
				
				stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, c_code);
				
				rs = stmt.executeQuery();
			
				
				if(rs.next()) {
					System.out.println("yooooo");
					String course_code= rs.getString("course_code");
					String c_title = rs.getString("c_title");
					int courseTeacherId = rs.getInt("course_teacher_id");
					String courseTeacherName = rs.getString("course_teacher_name");
					
					 
					

					stmt2 = conn.prepareStatement(sql2);
					
					stmt2.setString(1, c_code);
					
					rs2 = stmt2.executeQuery();
					System.out.println(rs2);
					if(rs2.next()) {
						int noStudents = rs2.getInt("total");
						Course course = new Course(course_code, c_title, courseTeacherName,noStudents);
						
						return course;
					}
					else return null;
					
					
					
					
				
				
					
					
				}else return null;
					 

					
				
			}finally {
				close(conn, stmt, rs);
			}
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//for student servlet
	public ArrayList<Course> getRegisteredCourses(int studentId) throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from courses inner join (select * from enrolled_students where user_id = ?) reg on courses.c_id = reg.c_id";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, studentId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				String c_code=rs.getString("course_code");
				String c_title = rs.getString("c_title");
				String courseTeacherName = rs.getString("course_teacher_name");
				
				Course course = new Course(c_code, c_title, courseTeacherName,10);
				courses.add(course);
				
			}
			
			return courses;
			
		}finally {
			close(conn, stmt, rs);
		}
		
	}
	
	//For Teacher servlet
	public ArrayList<Course> getTeacherCourses(int teacherId) throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		PreparedStatement stmt2 = null;
		ResultSet rs2 = null;
		
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from courses where course_teacher_id=?";
			String sql2="select count(user_name) as total from enrolled_students  where course_code=?";
			
			
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, teacherId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int courseId = rs.getInt("c_id");
				String course_code = rs.getString("course_code");
				String title = rs.getString("c_title");
				
				String courseTeacherName = rs.getString("course_teacher_name");
				
                 stmt2 = conn.prepareStatement(sql2);
				
				stmt2.setString(1, course_code);
				
				rs2 = stmt2.executeQuery();
				if(rs2.next())
				{
					
					int noStudents = rs2.getInt("total");
					Course course = new Course(course_code, title, courseTeacherName,noStudents);
					courses.add(course);	
				}
				else {
					Course course = new Course(course_code, title, courseTeacherName,0);
					courses.add(course);
				}
				
				
				
			}
			
			return courses;
			
		}finally {
			close(conn, stmt, rs);
		}
		
	}
	
	//Course's student list
	
	public ArrayList<Student> fetchStudents(String c_code) throws Exception{
		ArrayList<Student> enrolledStudents = new ArrayList<Student>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from enrolled_students where course_code = ? ";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, c_code);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				System.out.println("c_id="+userId);

				String username = rs.getString("user_name");
				String email = rs.getString("email");
				
				Student user = new Student(userId, username, email);
				enrolledStudents.add(user);
			}
			
			return enrolledStudents;
			
		}finally {
			close(conn, stmt, rs);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	//Add entrolled Students
	public void addStudents(String courseCode, int userId, String username,String email) throws Exception{
		
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
	     
	     ResultSet rs2 = null; 
		
		try {
			conn = datasource.getConnection();
			
			String sql = "insert into enrolled_students (c_id,course_code,user_id, user_name, email) values (?, ?, ?, ?,?)";
			String sql2= "select c_id from courses where course_code=?";
			
			stmt = conn.prepareStatement(sql);
			
            stmt2 = conn.prepareStatement(sql2);
            stmt2.setString(1, courseCode);
			
			rs2 = stmt2.executeQuery();
			if(rs2.next())
			{   int c_id =rs2.getInt("c_id");
			    
				stmt.setInt(1,c_id);
			}
		
			
			
			stmt.setString(2, courseCode);
			stmt.setInt(3, userId);
			stmt.setString(4, username);
			stmt.setString(5, email);
			
			stmt.execute();
			
		} finally {
			
			close(conn, stmt, null);
		}
		
	}
	
	
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}


}
