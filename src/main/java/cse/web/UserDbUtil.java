package cse.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;




public class UserDbUtil {
	
	private DataSource datasource;
	
	public UserDbUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	 public User getUser(String Email, String Password) throws Exception {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			//get a connection
			conn = datasource.getConnection();
			
			
			String sql = "select * from users where email=? and password=?";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, Email);
			stmt.setString(2, Password);

			
			rs = stmt.executeQuery();
			
			
			if(rs.next()) {
				int userId = rs.getInt ("user_id");
				String username = rs.getString("user_name");
				String email = rs.getString("email");
				int userType = rs.getInt("type");
				
				
				User user = new User(userId, username, email, userType);
				return user;
			} else {
				return null;
			}
		} finally {
			close(conn, stmt, rs);
		}
	}

//add new courses
	 
public void addCourse(String code, String title, int id) throws Exception{
			Connection conn = null;
			PreparedStatement stmt = null;
			PreparedStatement stmt2 = null;
		     ResultSet rs2 = null;
			
			try {
				conn = datasource.getConnection();
				
				
				String sql = "insert into courses ( course_code,c_title,info, course_teacher_id, course_teacher_name) values (?,?,?,?,?)";
				
				String sql2= "select user_name from users where user_id= ?";
				stmt = conn.prepareStatement(sql);
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1,id);
				rs2 = stmt2.executeQuery();
				if(rs2.next())
				{ 
					String user_name =rs2.getString("user_name");
					stmt.setString(5,user_name);
				}
				 
				stmt.setString(1, code);
				stmt.setString(2, title);	
				stmt.setString(3,"theory");   
				stmt.setInt(4,id);
				
				stmt.execute();
				
				
			} finally {
				
				close(conn, stmt, null);
			}
			
		}
		
	 
	 
	 
	 
	
	
// get all teacher list
	 public ArrayList<Teacher> getTeachers() throws Exception{
			ArrayList<Teacher> teachersList = new ArrayList<Teacher>();
			
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			
			try {
				conn = datasource.getConnection();
				
				String sql = "select * from users where type=2";
				
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql);
				
				while(rs.next()) {
					int userId = rs.getInt("user_id");
					String username = rs.getString("user_name");
					
					
					Teacher user = new Teacher(userId, username);
					teachersList.add(user);
				}
				
				return teachersList;
				
			}finally {
				close(conn, stmt, rs);
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