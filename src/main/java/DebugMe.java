

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class DebugMe
 */
@WebServlet("/DebugMe")
public class DebugMe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public DebugMe() {
        super();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	PrintWriter output = response.getWriter();
    	output.append("Thank for fixing me.");  	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
