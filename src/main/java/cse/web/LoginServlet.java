package cse.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;



public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/course_management")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			userDbUtil = new UserDbUtil(dataSource);
		} catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}
    
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
         
        User user;
		try {
			user = userDbUtil.getUser(email,password);
			 if (user!=null) {
				 
				 HttpSession session = request.getSession();
					session.setAttribute("userId", user.getUserId());
					session.setAttribute("username", user.getUsername());
					session.setAttribute("email", user.getEmail());
					session.setAttribute("userType", user.getUserType());
					Object o = session.getAttribute("userType");
					int userType = (int)session.getAttribute("userType");
				    if(userType == 3)
				    {
				    	response.sendRedirect("student");
				    }
				    else if(userType==2)
				    {
				    	response.sendRedirect("teacher");
				    }
				    else {
				    	response.sendRedirect("admin");
				    }
	        	   
	        } else {
	        	response.sendRedirect("login?error=Invalid credentials. Please try again.");
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       
    }
}