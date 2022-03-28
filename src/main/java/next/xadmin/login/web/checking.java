package next.xadmin.login.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class checking
 */
@WebServlet("/checking")
public class checking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//used to display code in code editor
		System.out.println("Checking GET Method");
		response.setContentType("text/plain");
		String input_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.txt";
		PrintWriter out = response.getWriter();
		
		
		BufferedReader br = new BufferedReader(new FileReader(input_file));

 
		String st;
        while ((st = br.readLine()) != null)
        	out.println(st);	
        br.close();		
        
		/*
		try {
            File myObj = new File(input_file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              out.println(data);
            	}
            myReader.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        */
	out.close();
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/plain");
		System.out.println("Checking file Post Method");
		PrintWriter out = response.getWriter();

		String Action = request.getParameter("Action");	
		System.out.println(Action);			

		if (Action.equals("getFileNameFromDrive")==true) {
			File[] files = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\Uploaded_Files\\").listFiles();
			//If this pathname does not denote a directory, then listFiles() returns null. 
			System.out.println("here 1");			
			for (File file : files) {
			    if (file.isFile()) {
			    	out.println(file.getName());
			        System.out.println(file.getName());
			    }   
			}
			System.out.println("here 2");			
		}
		else if (Action.equals("GetSelectedItem")==true) {
			String fileName = request.getParameter("value");			
			System.out.println(fileName);
			
			String input_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\Uploaded_Files\\"+fileName;
			String output_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.txt";


	        // File path is passed as parameter
	        File file = new File(input_file);
	        BufferedReader br = new BufferedReader(new FileReader(file));
	 
	        String st;
			String Code = "";
	        while ((st = br.readLine()) != null)
	        	Code+=st+"\n";
	        br.close();
			System.out.println("Data Successfully retrived from file");

			//for writing data to file
	        // Open the file.

			
			PrintWriter writeToFile = null;
			try {
				writeToFile = new PrintWriter(output_file);
			} catch (FileNotFoundException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			} // Step 2

	        // Write the name of four oceans to the file
	        writeToFile.println(Code);   

	        // Close the file.
	        writeToFile.close();  
			System.out.println("Data Successfully stored to sample.txt file");
			
		}
		out.close();
	}

}
