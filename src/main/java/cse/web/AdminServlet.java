 package cse.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
private CourseDbUtil courseDbUtil;
	
	@Resource(name="jdbc/course_management")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			courseDbUtil = new CourseDbUtil(dataSource);
		} catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Declare a new variable to hold the list of courses
		List<Course> courses;
		
		try {
			// Retrieve all courses from the database using a custom utility class
			courses = courseDbUtil.getAllCourses();
			
			// Set the list of courses as an attribute in the request object
	        request.setAttribute("Courses", courses);
	     
	        // Forward the request and response objects to the "admin.jsp" page
	        request.getRequestDispatcher("admin.jsp").forward(request, response);
	        
		} catch (Exception e) {
			// If an exception occurs, print the stack trace to the console
			e.printStackTrace();
		}
	}
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		List<Course> courses;
//		try {
//			courses = courseDbUtil.getAllCourses();
//			
//	        request.setAttribute("Courses", courses);
//	     
//	        request.getRequestDispatcher("admin.jsp").forward(request, response);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
