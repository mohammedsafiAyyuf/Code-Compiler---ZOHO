package next.xadmin.login.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUpload")
@MultipartConfig(
		  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
		  maxFileSize = 1024 * 1024 * 10,      // 10 MB
		  maxRequestSize = 1024 * 1024 * 100   // 100 MB
		)
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public String getDateAndTIme() {
        LocalDateTime myDateObj = LocalDateTime.now();
        System.out.println("Before formatting: " + myDateObj);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH.mm.ss");

        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println("After formatting: " + formattedDate);
        return(formattedDate);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		System.out.println("File Upload Servlet doGet method");
		String language = request.getParameter("language");
		String Code = request.getParameter("code");		
		String fileName = request.getParameter("fileName");
		PrintWriter out = response.getWriter();

		
		String cpp_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.txt";
		 //for writing data to file
        // Open the file.
        PrintWriter writeToFile = new PrintWriter(cpp_file); // Step 2

        // Write the name of four oceans to the file
        writeToFile.println(Code);   

        // Close the file.
        writeToFile.close();  

        //to store the code to local directory
        if (fileName.isBlank()) {
        	fileName = getDateAndTIme();
        }
        String fileLocation = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\Uploaded_Files\\"+fileName+"."+language;
	    File new_file = new File(fileLocation);		
        PrintWriter new_writeToFile = new PrintWriter(fileLocation);
        new_writeToFile.println(Code);   
        new_writeToFile.close();  
        
		out.println("Code Stored Successfully");			
	
	}
    //####################################################################
    private final static Logger LOGGER = Logger.getLogger(FileUploadServlet.class.getCanonicalName());
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    /* Receive file uploaded to the Servlet from the HTML5 form */
	    System.out.println("here");

		Part filePart = request.getPart("file");
	    //String fileLocation = filePart.getSubmittedFileName();

	    // Create path components to save the file
	    String path = request.getParameter("destination");
	    System.out.println("here2.1");

	    String FileLocation = getFileName(filePart);
	    System.out.println("here2.2");
	    System.out.println(FileLocation);
	    String[] temp = FileLocation.replace("\\", "\\\\").split("\\\\");
	    System.out.println(FileLocation);

	    System.out.println("here2.3");
	    
	    String fileName = temp[temp.length-1];
	    System.out.println(fileName);
	    //for storing copy of file
	    File new_file = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\Uploaded_Files\\"+fileName);
	    for (Part part : request.getParts()) {
		    part.write("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\" + "sample.txt");
	    	part.write("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\Uploaded_Files\\"+fileName);
	    }
	    System.out.println("here3");
	    
	    /*
	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("JavaScript");
	    // read script file
	    try {
			engine.eval(Files.newBufferedReader(Paths.get("C:/Scripts/Jsfunctions.js"), StandardCharsets.UTF_8));
		} catch (ScriptException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Invocable inv = (Invocable) engine;
	    // call function from script file
	    try {
			inv.invokeFunction("yourFunction", "param");
		} catch (NoSuchMethodException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	    
	    response.sendRedirect("CodeEditor.html");
	  }

}
