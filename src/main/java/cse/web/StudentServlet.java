package cse.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.util.*;
/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentServlet() {
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
        // Retrieve the student's registered courses from the database.
    	// Student Id needed as parameter to get all reg courses
    	 HttpSession session = request.getSession();
    	int user_id=(int) session.getAttribute("userId");
    	
    	
    	
        List<Course> registeredCourses;
		try {
			registeredCourses = courseDbUtil.getRegisteredCourses(user_id);
			 // Set the retrieved courses as a request attribute.
	        request.setAttribute("registeredCourses", registeredCourses);
	        // Forward the request to the student homepage JSP page.
	        request.getRequestDispatcher("student.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
    }
    
    // Method to retrieve the student's registered courses from the database.
   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String course_code = request.getParameter("courseCode");
		 HttpSession session = request.getSession();
	     int user_id=(int) session.getAttribute("userId");
	     String user_name = (String) session.getAttribute("username");
	     String email = (String) session.getAttribute("email");
	     try {
			courseDbUtil.addStudents(course_code, user_id,user_name,email);
			response.sendRedirect("student");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	     
	     
		
		
		
		
		
	}

}

