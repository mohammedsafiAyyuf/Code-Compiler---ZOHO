package next.xadmin.login.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.JsonArray;

/**
 * Servlet implementation class CodeCompiler
 */
@WebServlet("/CodeCompiler")
public class CodeCompiler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String ValuesGivenToVariable = null;
	private ArrayList<ArrayList<String>> preProcessedInputArray=new ArrayList<ArrayList<String>>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeCompiler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void cpp_compilatoin(String Code, PrintWriter out){
		System.out.println("Compiling C++ code");
		
		String cpp_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.cpp";
		String output_exe = "CPP_CompileOutput";		
		
	 //for writing data to file
        // Open the file.
        PrintWriter writeToFile;
		try {
			writeToFile = new PrintWriter(cpp_file);
	        writeToFile.println(Code);   
	        writeToFile.close();  

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // Step 2

        // Write the name of four oceans to the file

        // Close the file.
		  
		
		/*
		 //for Appending Data to file
        BufferedWriter appendToFile = new BufferedWriter(new FileWriter(cpp_file, true));
 
        // Writing on output stream
        appendToFile.write(Code);
        // Closing the connection
        appendToFile.close();			
		*/

        
		//code compiling
		ProcessBuilder compiler = new ProcessBuilder("g++", "-o"+ output_exe, cpp_file);

        // take all commands as input in a text file
        File commands = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\commands.txt");

        // File where error logs should be written
        File error = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\error.txt");

        // File where output should be written
        File output = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\output.txt");
		
		// redirect all the files
		compiler.redirectInput(commands);
		compiler.redirectOutput(output);
		compiler.redirectError(error);
		
		Process compiler_process = null;
		try {
			compiler_process = compiler.start();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		
        int compiler_returnCode=-1;
        try {
        	compiler_returnCode = compiler_process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BufferedReader compiler_reader = new BufferedReader(new InputStreamReader(compiler_process.getInputStream()));
        StringBuilder compiler_strBld = new  StringBuilder();
        String compiler_line;
        
        try {
			while((compiler_line = compiler_reader.readLine())!=null) {
				compiler_strBld.append(compiler_line + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        if (compiler_returnCode ==-1){
        	System.out.println("Compiler Not successfull, code failed");
        }
        else if (compiler_returnCode ==0){
        	System.out.println(" Compiler successfull");
        	System.out.println(compiler_strBld);
    		out.println(compiler_strBld);			

			//code Executing
			ProcessBuilder pb_new = new ProcessBuilder("./"+output_exe);	
	        Process process = null;
			try {
				process = pb_new.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        StringBuilder strBld = new  StringBuilder();
	        String line;
	        
	        try {
				while((line = reader.readLine())!=null) {
					strBld.append(line + "\n");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        int returnCode=-1;
	        try {
				returnCode = process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if (returnCode ==-1){
	        	System.out.println("Not successfull Execution, code failed");
	        }
	        else if (returnCode ==0){
	        	System.out.println("successfull Exeution");
	        	System.out.println(strBld);
	    		out.println(strBld);
		
	        	
	        }
	        else {
	        	System.out.println("successfull Execution, Code has error");
	        }	
    		
    		
        }
        else {
        	System.out.println("compiler successfull, but no output");

            // Creating an object of BufferedReader class
            BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(error));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            // Declaring a string variable
            String st;
            // Consition holds true till
            // there is character in a string
            try {
				while ((st = br.readLine()) != null)
					{	
						System.out.println(st);
						out.println(st);
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }	
							
	}
	public void c_compilatoin(String Code, PrintWriter out){

		System.out.println("Compiling C code");
		
		String cpp_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.c";
		String output_exe = "C_CompileOutput";		
		
	 //for writing data to file
        // Open the file.
        PrintWriter writeToFile = null;
		try {
			writeToFile = new PrintWriter(cpp_file);
		} catch (FileNotFoundException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		} // Step 2

        // Write the name of four oceans to the file
        writeToFile.println(Code);   

        // Close the file.
        writeToFile.close();  
		  
		
		/*
		 //for Appending Data to file
        BufferedWriter appendToFile = new BufferedWriter(new FileWriter(cpp_file, true));
 
        // Writing on output stream
        appendToFile.write(Code);
        // Closing the connection
        appendToFile.close();			
		*/

        
		//code compiling
		ProcessBuilder compiler = new ProcessBuilder("gcc", "-o"+ output_exe, cpp_file);

        // take all commands as input in a text file
        File commands = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\commands.txt");

        // File where error logs should be written
        File error = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\error.txt");

        // File where output should be written
        File output = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\output.txt");
		
		// redirect all the files
		compiler.redirectInput(commands);
		compiler.redirectOutput(output);
		compiler.redirectError(error);
		
		Process compiler_process = null;
		try {
			compiler_process = compiler.start();
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}

		
		
        int compiler_returnCode=-1;
        try {
        	compiler_returnCode = compiler_process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BufferedReader compiler_reader = new BufferedReader(new InputStreamReader(compiler_process.getInputStream()));
        StringBuilder compiler_strBld = new  StringBuilder();
        String compiler_line;
        
        try {
			while((compiler_line = compiler_reader.readLine())!=null) {
				compiler_strBld.append(compiler_line + "\n");
			}
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        
        
        if (compiler_returnCode ==-1){
        	System.out.println("Compiler Not successfull, code failed");
        }
        else if (compiler_returnCode ==0){
        	System.out.println(" Compiler successfull");
        	System.out.println(compiler_strBld);
    		out.println(compiler_strBld);			

			//code Executing
			ProcessBuilder pb_new = new ProcessBuilder("./"+output_exe);	
	        Process process = null;
			try {
				process = pb_new.start();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        StringBuilder strBld = new  StringBuilder();
	        String line;
	        
	        try {
				while((line = reader.readLine())!=null) {
					strBld.append(line + "\n");
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	        int returnCode=-1;
	        try {
				returnCode = process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if (returnCode ==-1){
	        	System.out.println("Not successfull Execution, code failed");
	        }
	        else if (returnCode ==0){
	        	System.out.println("successfull Exeution");
	        	System.out.println(strBld);
	    		out.println(strBld);
		
	        	
	        }
	        else {
	        	System.out.println("successfull Execution, Code has error");
	        }	
    		
    		
        }
        else {
        	System.out.println("compiler successfull, but no output");

            // Creating an object of BufferedReader class
            BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(error));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            // Declaring a string variable
            String st;
            // Condition holds true till
            // there is character in a string
            try {
				while ((st = br.readLine()) != null)
					{	
						System.out.println(st);
						out.println(st);
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }			
		
	}
	public void python_compilatoin(String Code, PrintWriter out){
		System.out.println("Compiling Python code");
		
		String python_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.py";
		
		//for writing data to file
        // Open the file.
        PrintWriter writeToFile = null;
		try {
			writeToFile = new PrintWriter(python_file);
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} // Step 2

        // Write the name of four oceans to the file
        writeToFile.println(Code);   

        // Close the file.
        writeToFile.close();  
		  
		
		/*
		 //for Appending Data to file
        BufferedWriter appendToFile = new BufferedWriter(new FileWriter(cpp_file, true));
 
        // Writing on output stream
        appendToFile.write(Code);
        // Closing the connection
        appendToFile.close();			
		*/

        
		//code compiling
		ProcessBuilder compiler = new ProcessBuilder ("C:/Python27/python", python_file);

        // take all commands as input in a text file
        File commands = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\commands.txt");

        // File where error logs should be written
        File error = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\error.txt");

        // File where output should be written
        File output = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\output.txt");
		
		// redirect all the files
		compiler.redirectInput(commands);
		compiler.redirectOutput(output);
		compiler.redirectError(error);
		
		Process compiler_process = null;
		try {
			compiler_process = compiler.start();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		
		
        int compiler_returnCode=-1;
        try {
        	compiler_returnCode = compiler_process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        BufferedReader compiler_reader = new BufferedReader(new InputStreamReader(compiler_process.getInputStream()));
        StringBuilder compiler_strBld = new  StringBuilder();
        String compiler_line;
        
        try {
			while((compiler_line = compiler_reader.readLine())!=null) {
				compiler_strBld.append(compiler_line + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
        if (compiler_returnCode ==-1){
        	System.out.println("Compiler Not successfull, code failed");
        }
        else if (compiler_returnCode ==0){
        	System.out.println(" Compiler successfull");
        	System.out.println(compiler_strBld);
    		out.println(compiler_strBld);			
		
    		
        }
        else {
        	System.out.println("compiler successfull, but no output");

            // Creating an object of BufferedReader class
            BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(error));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     
            // Declaring a string variable
            String st;
            // Consition holds true till
            // there is character in a string
            try {
				while ((st = br.readLine()) != null)
					{	
						System.out.println(st);
						out.println(st);

					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }	
		
	}

	//finding integer variable names
	public ArrayList<String> int_variableNameFinder(String Code) {
		String[] temp = Code.split("\n");
		ArrayList<String> int_variable = new ArrayList<String>();
		
        for (int idx = 0; idx < temp.length; idx++) {
        	if (temp[idx].contains("int") && (temp[idx].contains(";"))){
        		String[] temp2 = temp[idx].stripLeading().split(";");
                //System.out.print("level 1 :");
                //System.out.println(temp[idx]);
        		
        		for(int idx2=0; idx2<temp2.length; idx2++){
        			if (temp2[idx2].contains("int")) {
        				while(temp2[idx2].contains("{")==true) {
            				int start = temp2[idx2].indexOf("{");
            				int end = temp2[idx2].indexOf("}");
            				String tempA = temp2[idx2].substring(start,end+1);
            				tempA = tempA.replace(",", "@#@");
            				tempA = tempA.replace("{", "&*");
            				tempA = tempA.replace("}","*&");
            				temp2[idx2] = temp2[idx2].substring(0,start)+tempA+temp2[idx2].substring(end+1);
            				//System.out.println(tempA);
            			}
        				while(temp2[idx2].contains("&*")==true){
        					temp2[idx2]=temp2[idx2].replace("&*","{");
        					temp2[idx2]=temp2[idx2].replace("*&","}");

        				}


        				if (temp2[idx2].contains(",")) {
	        				String[] temp3 = temp2[idx2].trim().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        int flag=0;
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].trim();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                if (flag==1) {
	        	                    try {
	            	                	Integer.parseInt(temp3[idx3]);        	                        
	        	                     } catch (NumberFormatException e) {
	        	                    	 
	            						int_variable.add(temp3[idx3]);        	                	
	        	                    }
	        	                }
	        	                else if (temp3[idx3].equals("int")) {
	        						flag=1;
	        					}
	    	                    //System.out.println(int_variable);        	                    
	
	        				}	                       
        				}
        				else {
	        				String[] temp3 = temp2[idx2].stripLeading().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].stripLeading();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                
	        	                if (temp3[idx3].equals("int")) {
	        	                	int_variable.add(temp3[idx3+1]);
	        	                	break;
	        	                }
	    	                    //System.out.println(int_variable);        	                    
        					
	                        }
        				}
        				
        			}
        		}
        	}
        }
		return int_variable;		
	}

	//finding float variable names
	public ArrayList<String> float_variableNameFinder(String Code) {
		String[] temp = Code.split("\n");
		ArrayList<String> float_variable = new ArrayList<String>();
		
        for (int idx = 0; idx < temp.length; idx++) {
        	if (temp[idx].contains("float") && (temp[idx].contains(";"))){
        		String[] temp2 = temp[idx].stripLeading().split(";");
                //System.out.print("level 1 :");
                //System.out.println(temp[idx]);

        		
        		for(int idx2=0; idx2<temp2.length; idx2++){
        			if (temp2[idx2].contains("float")) {

        				while(temp2[idx2].contains("{")==true) {
            				int start = temp2[idx2].indexOf("{");
            				int end = temp2[idx2].indexOf("}");
            				String tempA = temp2[idx2].substring(start,end+1);
            				tempA = tempA.replace(",", "@#@");
            				tempA = tempA.replace("{", "&*");
            				tempA = tempA.replace("}","*&");
            				temp2[idx2] = temp2[idx2].substring(0,start)+tempA+temp2[idx2].substring(end+1);
            				//System.out.println(tempA);
            			}        		
        				while(temp2[idx2].contains("&*")==true){
        					temp2[idx2]=temp2[idx2].replace("&*","{");
        					temp2[idx2]=temp2[idx2].replace("*&","}");

        				}

        				if (temp2[idx2].contains(",")) {
	        				String[] temp3 = temp2[idx2].stripLeading().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        int flag=0;
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].stripLeading();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                if (flag==1) {
	        	                    try {
	            	                	Double.parseDouble(temp3[idx3]);        	                        
	        	                     } catch (NumberFormatException e) {
	        	                    	 
	            						float_variable.add(temp3[idx3]);        	                	
	        	                    }
	        	                }
	        	                else if (temp3[idx3].equals("float")) {
	        						flag=1;
	        					}
	    	                    //System.out.println(int_variable);        	                    
	
	        				}	                       
        				}
        				else {
	        				String[] temp3 = temp2[idx2].stripLeading().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].stripLeading();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                
	        	                if (temp3[idx3].equals("float")) {
	        	                	float_variable.add(temp3[idx3+1]);
	        	                	break;
	        	                }
	    	                    //System.out.println(int_variable);        	                    
        					
	                        }
        				}
        				
        			}
        		}
        	}
        }
		return float_variable;		
	}


	//finding Char variable names
	public ArrayList<String> char_variableNameFinder(String Code) {
		String[] temp = Code.split("\n");
		ArrayList<String> char_variable = new ArrayList<String>();
		
        for (int idx = 0; idx < temp.length; idx++) {
        	if (temp[idx].contains("char") && (temp[idx].contains(";"))){
        		String[] temp2 = temp[idx].stripLeading().split(";");
                //System.out.print("level 1 :");
                //System.out.println(temp[idx]);
        		
        		for(int idx2=0; idx2<temp2.length; idx2++){
        			if (temp2[idx2].contains("char")) {
        				while(temp2[idx2].contains("{")==true) {
            				int start = temp2[idx2].indexOf("{");
            				int end = temp2[idx2].indexOf("}");
            				String tempA = temp2[idx2].substring(start,end+1);
            				tempA = tempA.replace(",", "@#@");
            				tempA = tempA.replace("{", "&*");
            				tempA = tempA.replace("}","*&");
            				temp2[idx2] = temp2[idx2].substring(0,start)+tempA+temp2[idx2].substring(end+1);
            				//System.out.println(tempA);
            			}        		
        				while(temp2[idx2].contains("&*")==true){
        					temp2[idx2]=temp2[idx2].replace("&*","{");
        					temp2[idx2]=temp2[idx2].replace("*&","}");

        				}

        				if (temp2[idx2].contains(",")) {
	        				String[] temp3 = temp2[idx2].stripLeading().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        int flag=0;
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].stripLeading();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                if (flag==1) {
	        	                	if (temp3[idx3].contains("\"")!=true && temp3[idx3].contains("'")!=true) {	
	        	                		char_variable.add(temp3[idx3]);
	        	                	}
	        	                }
	        	                else if (temp3[idx3].equals("char")) {
	        						flag=1;
	        					}
	    	                    //System.out.println(int_variable);        	                    
	
	        				}	                       
        				}
        				else {
	        				String[] temp3 = temp2[idx2].stripLeading().split(" |=|,");
	                        //System.out.print("level 2 :");
	                        //System.out.println(temp2[idx2]);
	                        for(int idx3=0; idx3<temp3.length; idx3++) {
	                        	temp3[idx3]=temp3[idx3].stripLeading();
	        	                //System.out.print("level 3 :");
	        	                //System.out.println(temp3[idx3]);
	        	                
	        	                if (temp3[idx3].equals("char")) {
	        	                	char_variable.add(temp3[idx3+1]);
	        	                	break;
	        	                }
	    	                    //System.out.println(int_variable);        	                    
        					
	                        }
        				}
        				
        			}
        		}
        	}
        }
		return char_variable;		
	}

	public String add_VarNameTo_EndofCode(String Code) {
		System.out.println("Add var name to end of code stared");
		ArrayList<String> int_variable = int_variableNameFinder(Code);
		ArrayList<String> char_variable = char_variableNameFinder(Code);
		ArrayList<String> float_variable = float_variableNameFinder(Code);
		
        System.out.println("Found Variable Names");
        System.out.println(int_variable);
        System.out.println(char_variable);
        System.out.println(float_variable);

        int idx;
        //adding variable names to End of code
        if (Code.contains("return")==true) {
            idx = Code.lastIndexOf("return");
        }
        else {
            idx = Code.lastIndexOf("}");        	
        }
        
        String temp ="std::cout<<std::endl<<std::endl; \n std::cout<<\"------------------------Final Variable Values------------------------\"<<std::endl<<std::endl; \n ";
        for(int i=0; i<int_variable.size(); i++) {
        	if (int_variable.get(i)!=" " && int_variable.get(i)!="" ) {
        		//System.out.print("Here -1 :");
        		//System.out.println(int_variable.get(i));
        		if((int_variable.get(i).contains("[")==true) || (int_variable.get(i).contains("]")==true)){
        			int start = int_variable.get(i).indexOf("[");
        			int end = int_variable.get(i).indexOf("]");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		int_variable.set(i, int_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -2 :");
            		//System.out.println(int_variable.get(i));        			
        		}
        		if((int_variable.get(i).contains("{")==true) || (int_variable.get(i).contains("}")==true)){
        			int start = int_variable.get(i).indexOf("{");
        			int end = int_variable.get(i).indexOf("}");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		int_variable.set(i, int_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -3 :");
            		//System.out.println(int_variable.get(i));        			
        		}
           	if (int_variable.get(i)!=" " && int_variable.get(i)!="" )
        		temp=temp.concat("std::cout<<"+"\"Integer : "+int_variable.get(i)+"\""+"<<"+" \"\\t = \\t\" "+"<<"+int_variable.get(i)+"<<std::endl; \n");
        	}
        }
        for(int i=0; i<char_variable.size(); i++) {
        	if (char_variable.get(i)!=" " && char_variable.get(i)!="") {
        		if((char_variable.get(i).contains("[")==true) || (char_variable.get(i).contains("]")==true)){
        			int start = char_variable.get(i).indexOf("[");
        			int end = char_variable.get(i).indexOf("]");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		char_variable.set(i, char_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -2 :");
            		//System.out.println(int_variable.get(i));        			
        		}
        		if((char_variable.get(i).contains("{")==true) || (char_variable.get(i).contains("}")==true)){
        			int start = char_variable.get(i).indexOf("{");
        			int end = char_variable.get(i).indexOf("}");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		char_variable.set(i, char_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -3 :");
            		//System.out.println(int_variable.get(i));        			
        		}
           	if (char_variable.get(i)!=" " && char_variable.get(i)!="" )
        		temp=temp.concat("std::cout<<"+"\" Char : "+char_variable.get(i)+"\""+"<<"+" \"\\t = \\t\" "+"<<"+char_variable.get(i)+"<<std::endl; \n");
        	}
        }
        for(int i=0; i<float_variable.size(); i++) {
        	if (float_variable.get(i)!=" " && float_variable.get(i)!="") {
        		if((float_variable.get(i).contains("[")==true) || (float_variable.get(i).contains("]")==true)){
        			int start = float_variable.get(i).indexOf("[");
        			int end = float_variable.get(i).indexOf("]");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		float_variable.set(i, float_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -2 :");
            		//System.out.println(int_variable.get(i));        			
        		}
        		if((float_variable.get(i).contains("{")==true) || (float_variable.get(i).contains("}")==true)){
        			int start = float_variable.get(i).indexOf("{");
        			int end = float_variable.get(i).indexOf("}");
        			int val = Math.min(start, end);
        			if (val==-1) {
        				val= Math.max(start, end);
        			}
            		float_variable.set(i, float_variable.get(i).substring(0,val)); 
            		//System.out.print("Here -3 :");
            		//System.out.println(int_variable.get(i));        			
        		}
           	if (float_variable.get(i)!=" " && float_variable.get(i)!="" )
        		temp=temp.concat("std::cout<<"+"\" Float : "+float_variable.get(i)+"\""+"<<"+" \"\\t = \\t\" "+"<<"+float_variable.get(i)+"<<std::endl; \n");
        	}
       	}

        temp=temp.concat("return 0; }");
        Code = Code.substring(0, idx)+temp;
        //System.out.println(temp);       	
        
		return(Code);
	}

	public int randomInt_Generator() {
        Random rand = new Random();
        
        // Generate random integers in range 0 to 999
        int rand_int = rand.nextInt(1000);

        return rand_int;
	}

	public float randomFloat_Generator() {		
        Random rand = new Random();
        
        // Generate random integers in range 0 to 999
        float rand_float = rand.nextFloat();
        return rand_float;
	}

	public char randomChar_Generator() {		
        Random rand = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789`~!@#$%^&*()_+}{|:>?</.,;][=-";
        
        // Generate random integers in range 0 to 999
        char rand_char = alphabet.charAt(rand.nextInt(alphabet.length())) ;
        return rand_char;
	}
	
	
	public String changeVarValue_in_Code(String Code, String[] varNameArray, String randRequired) {
		int idx=0;
		ValuesGivenToVariable="\n------------------------Provided Input Variable Values------------------------\n";
		String[] CodeArray = Code.split("\n");
		System.out.print("VarName Array : ");
		System.out.println(varNameArray.length);
		System.out.println(Arrays.toString(varNameArray));

		while(varNameArray.length>idx) {
			String[] temp = varNameArray[idx].split(",");

			//System.out.print("varNameArray : ");
			//System.out.println(varNameArray.length);
			//System.out.println(Arrays.toString(varNameArray));
			System.out.println(varNameArray[idx]);
			System.out.println(Arrays.toString(temp));
			
			int lineNo = Integer.parseInt(temp[0])-1;
			String varName = temp[1];
			String varType = temp[2];
			String varValue = "";
			System.out.print("randRequired : ");
			System.out.println(randRequired);
			if (varType.indexOf("Array_")!=-1) {
				System.out.print("Array INput :  ");
				System.out.println(temp[3]);

				if (temp.length>3) {
					while(temp[3].contains("#@#")==true)
						temp[3] = temp[3].replace("#@#", ",");
					while(temp[3].contains("*&")==true) {
						temp[3] = temp[3].replace("*&", "}");
						temp[3] = temp[3].replace("&*", "{");
					}
					varValue = temp[3];
				}
				else {
					varValue="{}";
				}
				System.out.println(varValue);

			}
			else if (temp.length>3) {
				varValue=temp[3];
			}
			else if(randRequired.equals("true")==true) {
				if (varType.equals("int")) {
					varValue = String.valueOf(randomInt_Generator());
				}
				else if (varType.equals("float")) {
					varValue = String.valueOf(randomInt_Generator()+randomFloat_Generator());
				} 
				else if(varType.equals("char")) {
					varValue="'"+randomChar_Generator()+"'";
				}
				System.out.println("rand ok");
			}
			
			
			ValuesGivenToVariable+=	varType+" : "+varName+"     =     "+varValue;
			String line = CodeArray[lineNo];
			//to change array input seperator , in Code
			System.out.println(line);
			while(line.contains("{")==true) {
				int start = line.indexOf("{");
				int end = line.indexOf("}");
				String tempA = line.substring(start,end+1);
				tempA = tempA.replace(",", "@#@");
				tempA = tempA.replace("{", "&*");
				tempA = tempA.replace("}","*&");
				line = line.substring(0,start)+tempA+line.substring(end+1);
				System.out.println(tempA);
			}
			while(line.contains("&*")==true){
				line=line.replace("&*","{");
				line=line.replace("*&","}");
			}
			System.out.println(line);			
			int varIdx = line.indexOf(varName);
			int equal_SignIdx = line.indexOf("=", varIdx);
			int semiColon_SignIdx = line.indexOf(";", varIdx);
			int comma_SignIdx = line.indexOf(",",varIdx);
			
			if((comma_SignIdx==-1) || (comma_SignIdx>semiColon_SignIdx)) {
				if (varValue.isBlank()!=true)
					CodeArray[lineNo] = line.substring(0, equal_SignIdx)+" = "+ varValue +line.substring(semiColon_SignIdx,line.length());
				else
					ValuesGivenToVariable+=line.substring(equal_SignIdx+1, semiColon_SignIdx);
			}
			else {
				if (varValue.isBlank()!=true)
					CodeArray[lineNo] = line.substring(0, equal_SignIdx)+" = "+ varValue +line.substring(comma_SignIdx,line.length());
				else
					ValuesGivenToVariable+=line.substring(equal_SignIdx+1, comma_SignIdx);
			}
			
			System.out.println("line : ->");
			System.out.println(line);
			//System.out.println(tempLine);
			System.out.println(line.substring(0, equal_SignIdx));
			System.out.println(varIdx);
			System.out.println(equal_SignIdx);
			System.out.println(semiColon_SignIdx);
			System.out.println(comma_SignIdx);
			ValuesGivenToVariable+='\n';	
			idx++;
		}
		Code = String.join("\n", CodeArray);
		ValuesGivenToVariable+="\n------------------------Program Output------------------------\n";
		return Code;
	}
	
	public void preProcessing_InputData(String[] varNameArray, int main_idx) {

		if (varNameArray.length<=main_idx) {
			//An input is prepared , we can put it in code and execute it now
			ArrayList<String> temp = new ArrayList<String>();
			for(int i=0; i<varNameArray.length; i++) {
				temp.add(varNameArray[i]);
				temp.set(i, temp.get(i).replace("$#$",","));
			}
			preProcessedInputArray.add(temp);
			//System.out.print("Reached End : ");
			//System.out.println(Arrays.toString(varNameArray));
			return;
		}
		String[] newVarNameArray=Arrays.copyOfRange(varNameArray,0,varNameArray.length);
		
		String[] curEle = varNameArray[main_idx].split("\\$#\\$");
		//System.out.print("curEle : ");
		//System.out.println(Arrays.toString(curEle));
		//System.out.println(curEle.length);
		
		if(curEle[curEle.length-1].contains(",") || curEle[curEle.length-1].contains("-") ) {
			String[] temp = curEle[curEle.length-1].split(",");
			for(int i=0;i<temp.length;i++) {

				if(temp[i].contains("-")) {
					String[] temp_ = temp[i].split("-");
					if (curEle[curEle.length-2].equals("int")) {
						int start = Integer.valueOf(temp_[0]), end=Integer.valueOf(temp_[1]);
						int increment =1;

						if(temp_.length==3) {
							increment=Integer.valueOf(temp_[2]);
						}
						for(    ; start<=end ;start+=increment) {
							String newEle= String.join("$#$",Arrays.copyOf(curEle,curEle.length-1,String[].class))+"$#$"+start;
							newVarNameArray[main_idx] = newEle;
							preProcessing_InputData(newVarNameArray, main_idx+1);
									
						}
					}
					else if (curEle[curEle.length-2].equals("float")) {
						float start = Float.valueOf(temp_[0]), end=Float.valueOf(temp_[1]);
						float increment =1;
						
						if(temp_.length==3) {
							increment=Float.valueOf(temp_[2]);
						}
						for(    ; start<=end ;start+=increment) {
							String newEle= String.join("$#$",Arrays.copyOf(curEle,curEle.length-1,String[].class))+"$#$"+start;
							newVarNameArray[main_idx] = newEle;
							preProcessing_InputData(newVarNameArray, main_idx+1);
									
						}
					}

				}
				else {
					String newEle= String.join("$#$",Arrays.copyOf(curEle,curEle.length-1,String[].class))+"$#$"+temp[i];
					newVarNameArray[main_idx] = newEle;
	
					preProcessing_InputData(newVarNameArray, main_idx+1);
				}

			}
			
		}
		else {
			preProcessing_InputData(varNameArray, main_idx+1);	
		}
	}		
	
	public void changeInput_And_runCode(String Code,String language,String[] varNameArray, String randRequired, PrintWriter out) {

		//to incorporate "," of char input
		while(Code.contains("','")) {
			Code = Code.replace("','", "'%&%'");
		}
		
		//Changing Input in Code
		System.out.println("Change val in code started");
		Code = changeVarValue_in_Code(Code, varNameArray,randRequired);
		
		while(ValuesGivenToVariable.contains("'%&%'")) {
			ValuesGivenToVariable=ValuesGivenToVariable.replace("'%&%'", "','");
		}
		out.println(ValuesGivenToVariable);
		
		//System.out.println("Var value Given");
		//System.out.println(ValuesGivenToVariable);

		System.out.println("Var Value Changed in code successfully");
		//System.out.println(Code);
		
		//Add variable name in print statement at bottom of Code
		Code = add_VarNameTo_EndofCode(Code);		
		System.out.println("Var Name added to end of code successfully");

		System.out.println(Code);

		//to incorporate "," of char input
		while(Code.contains("'%&%'")) {
			Code = Code.replace("'%&%'","','");
		}
		
		
        //To compile the code
		if (language.equals("cpp")) {
			cpp_compilatoin(Code, out);  		
		}

		else if (language.equals("c")) {
			c_compilatoin(Code, out);  			  		
		}
		else if (language.equals("python")) {
			python_compilatoin(Code, out);  			  		 					
		}
		out.println("-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-\n");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");
		System.out.println("Code Compail Servlet : GET Method");
		String language = request.getParameter("language");
		String Code = request.getParameter("code");												//[lineNo $#$ varName $#$ varType $#$ varVal]
		String[] varNameArray = request.getParameterValues("varArray")[0].split("\\|");   //varArray format : ["7$#$x$#$int$#$45 | 8$#$x$#$int$#$458 | 9$#$x$#$int$#$64 | 10$#$x$#$int$#$913"]
																						  //varNameArray format : ["7$#$x$#$int$#$45", "8$#$x$#$int$#$458", "9$#$x$#$int$#$64", "10$#$x$#$int$#$913"]
		String randRequired = request.getParameter("randRequired") ;
		int randCount;
		try {
			randCount = Integer.parseInt(request.getParameter("randCount"));
		}
		catch(Exception e){
			randCount = 1;
		}
		PrintWriter out = response.getWriter();

		System.out.println("PreProcessing Input Array");
		preProcessedInputArray.clear();   //clear all previous stored values
		preProcessing_InputData(varNameArray, 0);		
		System.out.println(preProcessedInputArray);
		System.out.println("Preprocessing Input data successfully");
		
		for(int j=0; j<randCount;j++) {
			for(int i=0; i<preProcessedInputArray.size(); i++) {  //for running for all the input
				ValuesGivenToVariable="";
				System.out.println(preProcessedInputArray.get(i));
				changeInput_And_runCode(Code,language,preProcessedInputArray.get(i).toArray(new String[preProcessedInputArray.get(i).size()]),randRequired,out);
			}
		}
		//out.println(language);
		//System.out.println(language);
		
	}
		

	
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		System.out.println("no problem ");

			String cpp_file = "C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.cpp";
			String output_exe = "CompileOutput";		

			try {
				new ProcessBuilder("g++", "-o"+ output_exe, cpp_file).start().waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} 
	        System.out.println("no problem 1");

	        ProcessBuilder pb_new = new ProcessBuilder("./"+output_exe);
	        //execute.redirectErrorStream(true);

	        Process process = pb_new.start();
	        
	        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	        StringBuilder strBld = new  StringBuilder();
	        String line;
	        System.out.println("no problem 2");
	        
	        while((line = reader.readLine())!=null) {
	        	strBld.append(line + "\n");
	        }
	        
	        int returnCode=-1;
	        try {
				returnCode = process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if (returnCode ==-1){
	        	System.out.println("Not successfull, code failed");
	        }
	        else if (returnCode ==0){
	        	System.out.println("successfull");
	        	System.out.println(strBld);

				
				String titleBar = "Code Compiled";
				JOptionPane.showMessageDialog(null, strBld, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
				response.sendRedirect("HomePage.jsp");	        	
	        	
	        }
	        else {
	        	System.out.println("successfull, but no output");
	        	
	        }
	    //rest of coding to provide input using OutputStream of 'runProcess' and to close the stream.

		//Runtime.getRuntime().exec("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\sample.exe", null, new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\"));
	}
	*/

}

class StreamReader implements Runnable {

    private InputStream reader;

   public StreamReader(InputStream inStream) {
      reader = inStream;

   }

   @Override
   public void run() {

      byte[] buf = new byte[1024];

        int size = 0;
        try {

            while ((size = reader.read(buf)) != -1) {

                System.out.println(new String(buf));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
   		}
	}
}