package cse.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class CourseServlet
 */
@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
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
     
    //course details+ students against course_id
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  // get course ID from request parameter
        String course_code = request.getParameter("courseCode");
      
        
		try {
			// fetch course details from database or other data source using the course ID
			Course course = courseDbUtil.fetchCourseDetails(course_code);
			
			 request.setAttribute("courseTitle", course.getCourseName());
		        request.setAttribute("courseCode", course.getCourseCode());
				request.setAttribute("instructor", course.getInstructor());
				
				// fetch list of students from database
		        List<Student> students = courseDbUtil.fetchStudents(course_code);

		        // set course details as attribute on request object
		        request.setAttribute("students", students);

		        // forward request to course.jsp page
		        RequestDispatcher dispatcher = request.getRequestDispatcher("course.jsp");
		        dispatcher.forward(request, response);
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
       
        
	}
	
	
	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
