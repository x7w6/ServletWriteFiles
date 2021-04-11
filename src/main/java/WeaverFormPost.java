//
// Java class created by Zachary Weaver
// Bellevue University
// Project for a servlet that produces a form and accepts input to write inside of a file
//


// imports for servlet
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;


// URL to access at: i.e http://localhost:7070/WeaverFormPost
@WebServlet("/WeaverFormPost")
public class WeaverFormPost extends HttpServlet {
	// generated variable
	private static final long serialVersionUID = 1L;
	
	// HTML related variables
	String path = "";
    String[] startingMarkup;
    String[] endingMarkup;
	String[] formMarkup;
	String greeting = "<h1>Welcome to Week 4</h1>";
	String stylesheet = "<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css' rel='stylesheet'>";
	
	// creating a My HashTable Dictionary
	Hashtable<String, String> data = new Hashtable<String, String>();
	RandomAccessFile file;
	
    
    // contstructor, initial assignment
    public WeaverFormPost() {
        super();
       
		startingMarkup = new String[] {
			"<html>",
			"<head>",
			"<title>Let's Get Started</title>",
			stylesheet,
			"</head>",
			"<body>",
			"<div class='container'>"
		};
		
		endingMarkup = new String[] {
			"</div>",
			"</body>",
			"</html>"
		};
    }


    // GET REQUEST HANDLER
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter page = response.getWriter();
        greeting = "<h1>Daily Food Logger</h1>";
        path = request.getRequestURL().toString();
       
        setForm(); // after path change set form
        printFormPage(page);
	}

	
	// POST REQUEST HANDLER
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter page = response.getWriter();
		
		// GET DATA
		String isNew = request.getParameter("isNewDay");
		String item = request.getParameter("foodName");
		String info = request.getParameter("foodInfo");
		String count = request.getParameter("foodCount");
		
		// TEMP STORE DATA
		data.put("item", item);
		data.put("info", info);
		data.put("count", count);
		
		// SAVE TO FILE
		String path = getSystemPath()+"/WeaverWeek4.dat";
		File customDir = new File(path);

		if (!customDir.exists())
		    customDir.mkdirs();
			
		file = new RandomAccessFile(path, "rw");
		
		// If new day clear previous entries
		if(isNew != null) {
			System.out.println("Deleting previous logs...");
			PrintWriter write = new PrintWriter(path);
			write.print("");
			write.close();
		}
			
		
		// save form data
		writeToFile(file);
		
		// output form data to user browser
		readFile(file, page);
		
		// close connection
		file.close();
			
	}
	
	
	// Determine whether files are under windows or mac/linux
	private String getSystemPath() {
		String os = System.getProperty("os.name");
		String path = "c:\\tmp\\";
		
		if(!os.toLowerCase().contains("windows"))
			path = System.getProperty("user.home") + File.separator + "tmp" + File.separator;
		
		return path;
	}
	
	
	// output HTML element tags to a given output
	private void setForm() {
		formMarkup = new String[] {
			String.format("<form class='row g-3' action='%s' method='POST'>", path),
			"	<div class='col-12'>",
			"	    <div class='form-text'>To start a log for a new day, toggle the checbox below.</div>",
			"    	<label class='form-label'>Food Item</label>",
			"	    <input type='text' class='form-control' name='foodName'>",
			"	</div>",
			"	<div class='col-12'>",
			"	    <label class='form-label'>Description</label>",
			"	    <input type='text' class='form-control' name='foodInfo'>",
			"	</div>",
			"	<div class='col-sm-12 col-md-6'>",
			"	    <label class='form-label'>Count</label>",
			"	    <input type='number' class='form-control' name='foodCount'>",
			"	</div>",
			"	<div class='form-check col-12'>",
			"	    <input type='checkbox' class='form-check-input' name='isNewDay'>",
			"	    <label class='form-check-label'>New Day</label>",
			"	</div>",
			"	<button type='submit' class='btn btn-primary col-6 col-sm-12'>Submit</button>", 
			"</form>"
		};
	}
	
	
	// serve this markup on Get request
	private void printFormPage(PrintWriter output) {
		
		for(String htmlOpen : startingMarkup)
			output.println(htmlOpen);
		
        output.println(greeting);
		
		for(String htmlForm : formMarkup)
			output.println(htmlForm);
		
		for(String htmlEnd : endingMarkup)
			output.println(htmlEnd);
	}

	
	// store form data to file
	private void writeToFile(RandomAccessFile file) {
		int bufferLength = 20;
		StringBuffer buffer = new StringBuffer(); // Buffer: mutable string
		
		buffer.setLength(bufferLength);
		buffer.setCharAt(bufferLength - 1, '\n');
		
		// append form data to Buffer
		data.forEach((key, value) -> {
			buffer.append(String.format("%s: %s\n", key, value));
		});

		try {
			file.seek(file.length());
			
			// write to file
			file.writeBytes(buffer.toString());
			
			// reset file pointer
			file.seek(0);
		
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	// output file data to html page
	private void readFile(RandomAccessFile file, PrintWriter output) {
		String tempData;
		
		for(String htmlOpen : startingMarkup)
			output.println(htmlOpen);
		
		output.println("<h1>Submission Successful</h1>");
		
		output.println("<table class='table'>");
		output.println("<thead>");
		
		// Fill Table Heading
		output.println("<tr>");
		output.println("<th scope='col'>Previous Logs</th>");
		output.println("</tr>");
		output.println("</thead>");
		output.println("<tbody>");
		
		try {
			// loop through file and output data
			while((tempData = file.readLine()) != null) {
				System.out.println(tempData);
				
				output.println("<tr>");
				output.println(String.format("<td>%s</td>", tempData));
				output.println("</tr>");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		output.println("</tbody>");
		output.println("</table>");
		
		for(String htmlEnd : endingMarkup)
			output.println(htmlEnd);
				
	}

}
