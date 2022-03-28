package next.xadmin.HomePage.web;
import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/HomePage")
public class HomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //to highlight code received
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//HashTable to store the location of variable
		//(LineNo, variable), Value
        Hashtable<String, String> variableTable = new Hashtable<>();
        //How to put value into the table
        //variableTable.put("4$four","four");
		//System.out.println(variableTable.get("4$four"));
		
		response.setContentType("text/plain");
		System.out.println("Home Page Servlet GET method");
		String language = request.getParameter("language");
		String Code = request.getParameter("code")+"   ";		
		PrintWriter out = response.getWriter();

		System.out.println("highlight code started");
		//for getting comma as a char input
		while(Code.contains("','")==true) {
			Code=Code.replace("','", "'%&%'");    // " '%&%' " -> " ',' "  **IMPORTANT**
		}
		//System.out.println(Code);
		
		//Invocable invocable = (Invocable) engine;
		String[] string = Code.stripLeading().stripTrailing().split("\n");
		String line;
		
		
		/*
		out.println("<!DOCTYPE html>\r\n"
		     		+ "<html>\r\n"
		     		+ "<head>\r\n"
		     		+ "<meta charset='ISO-8859-1'>\r\n"
		     		+ "<title>Code Editor</title>\r\n"
		     		+ "<link rel=\"stylesheet\" href=\"CodeEditor.css\" type=\"text/css\">"
		     		+ "</head>\r\n"
		     		+ "<body>" 
		     		+ "<script type=\"text/javascript\" src=\"CodeEditor.js\"></script>\r\n" );
		*/
		/*     
		out.println("<div class=\"editor-holder\">\r\n"
		     		+ "	<ul class=\"toolbar\">\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "	</ul>"
		     		+ "	<div class=\"scroller\">\r\n");
		  */
		
		for(int idx_=0; idx_<string.length; idx_++) {
			line = string[idx_].stripTrailing();

			//header file changer
			if (line.contains("#include") ){
				String[] strArray =line.stripLeading().stripTrailing().split(" ");					
				out.println(strArray[0]+ " &lt" + strArray[1].substring(1,strArray[1].length()-1) +"&gt");
			}
			
			 
			//else if ((line.contains("int") || line.contains("char") || line.contains("float")) && line.contains("=") && line.contains(";")) {
			else if ( line.contains("=") && line.contains(";")) {

				//changing <,> symbol
				if(line.contains("<")){
					line=line.replace("<", " &lt ");
				}
				if(line.contains(">")){
					line=line.replace(">", " &gt ");
				}								
				
				//processing the string
				String[] manyInitilization =line.stripTrailing().split(";");
				int idx=0;
				while(manyInitilization.length>idx) {
					
					//if (manyInitilization[idx].contains("="))
					if ((manyInitilization[idx].contains("int") || manyInitilization[idx].contains("char") || manyInitilization[idx].contains("float")) && manyInitilization[idx].contains("=")) 
					 {	
						//to work on array and highlight array values also
						String varType = "";
						
						if(manyInitilization[idx].contains("{") && manyInitilization[idx].contains("}")) {
							String curLine = manyInitilization[idx];
							while ((curLine.indexOf("{")!=-1) && (curLine.indexOf("}")!=-1)) {								
								int start = curLine.indexOf("{");
								int end = curLine.indexOf("}");
								String temp = "&*"+curLine.substring(start+1,end)+"*&";
								temp = temp.replace(" ", "");
								temp = String.join("#@#", temp.split(","));
								curLine = curLine.substring(0,start)+temp+curLine.substring(end+1);
							}
							
							while(curLine.contains("&*")) {
								curLine=curLine.replace("&*", "{");
								curLine=curLine.replace("*&", "}");
							}
							manyInitilization[idx]=curLine;
							//varType+="Array_";
						}
						
						
						int count = (manyInitilization[idx].split(",", -1).length-1);
						String[] strArray_ = manyInitilization[idx].stripLeading().stripTrailing().split(",");
						System.out.print("Here1 : ");
						System.out.println(manyInitilization[idx]);
						int flag =0;
						int temp_idx =0;
						while(strArray_.length>temp_idx) {
							String[] strArray = strArray_[temp_idx].stripLeading().stripTrailing().split(" +|=");
							System.out.print("Here2 : ");
							System.out.println(Arrays.toString(strArray));																					
							String temp = "" ;
							
							int i=0;
							String varName ="";
							if (flag==0) {
								flag=1;
								varType+=strArray[0];								
							}
							
							System.out.println(Arrays.toString(strArray));
							while(strArray.length>i+1 ) {
								temp+=strArray[i]+" ";
								strArray[i]=strArray[i].stripLeading().stripTrailing();
								if (strArray[i].equals("")!=true && strArray[i].equals(" ")!=true) {
									if(strArray[i].equals("[")==true || strArray[i].equals("]")==true)  {
										varName += strArray[i];
										}
									else {
										varName = strArray[i];

									}
									}
								i++;
							}
	
							
							//temp+=" = ";
							if(strArray[i].contains("{") || strArray[i].contains("}")){
								String temp2 = strArray[i].replace("#@#", ",");
								temp +=" = <span class='highlight' >"+temp2+"</span>";		
						        variableTable.put(Integer.toString(idx_)+"$"+varName+"$"+"Array_"+varType,strArray[i]);
								System.out.println("Here 2");
								System.out.println(strArray[i]);
								System.out.println(temp2);
								System.out.println(varName);
								System.out.println(varType);

							}							
							else if (isNumeric(strArray[i])){
								temp +=" = <span class='highlight' >"+strArray[i]+"</span>";								
						        variableTable.put(Integer.toString(idx_)+"$"+varName+"$"+varType,strArray[i]);

							}
							else if(strArray[i].contains("'") || strArray[i].contains("\"")){
								
								temp +=" = <span class='highlight' >"+strArray[i].replace("'%&%'", "','")+"</span>";								
						        variableTable.put(Integer.toString(idx_)+"$"+varName+"$"+varType,strArray[i]);
							}

							else {
								temp +=strArray[i];										
							}
							//temp+="<input type='text' value='"+strArray[i]+"' >";					
							if(count>0) {
								temp+=",";
								count-=1;
							}
							//temp+="<input type='text' value='"+strArray[i]+"' >";
							out.println(temp);
							temp=null;
							temp_idx+=1;
						}
					}
					else {
						//System.out.println(manyInitilization[idx]);
						out.println(manyInitilization[idx]);							

					}
					//System.out.println(";");
					out.println(";");
					idx++;										
				}
				manyInitilization=null;	
			}

			else {
				//System.out.println(line);
				out.println(line);
			}
			line = null;
			System.gc();			 
			out.println("<br>");

		}
		
		//out.println("</body>\r\n"+ "</html>");
		
		//out.println("Code Highlited Successfully!!");			
		
		
		/*
		 //old code
		 
		File output = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\ide.html");
	    FileWriter myWriter = new FileWriter(output);
	 
	   // ProcessBuilder launcher = new ProcessBuilder("g++", myObj, "-o", "HelloWorld");
	     myWriter.write("<!DOCTYPE html>\r\n"
	     		+ "<html>\r\n"
	     		+ "<head>\r\n"
	     		+ "<meta charset='ISO-8859-1'>\r\n"
	     		+ "<title>Code Editor</title>\r\n"
	     		+ "<link rel=\"stylesheet\" href=\"CodeEditor.css\" type=\"text/css\">"
	     		+ "</head>\r\n"
	     		+ "<body>" 
	     		+ "<script type=\"text/javascript\" src=\"CodeEditor.js\"></script>\r\n" );
	     
	     myWriter.write("<div class=\"editor-holder\">\r\n"
		     		+ "	<ul class=\"toolbar\">\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "	</ul>"
		     		+ "	<div class=\"scroller\">\r\n"
	     		+ "		<textarea class=\"editor allow-tabs\">"
	     		+ " Select Your Program file!!\n");

	     myWriter.write("</textarea>\r\n"
	     		+ "		<pre><code class=\"syntax-highight html\"></code></pre>\r\n"
	     		+ "	</div>\r\n"
	     		+ "</div>\r\n"
	     		+ ""
	     		+ "</body>\r\n"
					+ "</html>");
			
		    myWriter.close();			
			response.sendRedirect("HomePage.jsp");
	     */
	System.out.println(variableTable);
	out.println("$#$");
	out.println(variableTable);
	

	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public static boolean isNumeric(String string) {
			
			
	    if(string == null || string.equals("")) {
	        //System.out.println("String cannot be parsed, it is null or empty.");
	        return false;
	    }
	    
	    try {
		    double intValue = Double.parseDouble(string);
	        return true;
	    } catch (NumberFormatException e) {
	        //System.out.println("Input String cannot be parsed to Integer.");
	    }
	    return false;
	}
	
	//To highlight code inside file
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		/*
		System.out.println("highlight code started");
		//Invocable invocable = (Invocable) engine;
		BufferedReader in = new BufferedReader(new 	FileReader(("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\CodeFile\\sample.txt")));
		String line;
		
		File output = new File("C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\ide.html");
		
	    FileWriter myWriter = new FileWriter(output);

	     myWriter.write("<!DOCTYPE html>\r\n"
		     		+ "<html>\r\n"
		     		+ "<head>\r\n"
		     		+ "<meta charset='ISO-8859-1'>\r\n"
		     		+ "<title>Code Editor</title>\r\n"
		     		+ "<link rel=\"stylesheet\" href=\"CodeEditor.css\" type=\"text/css\">"
		     		+ "</head>\r\n"
		     		+ "<body>" 
		     		+ "<script type=\"text/javascript\" src=\"CodeEditor.js\"></script>\r\n" );
		     
		     myWriter.write("<div class=\"editor-holder\">\r\n"
		     		+ "	<ul class=\"toolbar\">\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "		<li></li>\r\n"
		     		+ "	</ul>"
		     		+ "	<div class=\"scroller\">\r\n");
		    
		    //previous logic 
		    /*
			while((line = in.readLine()) != null)
			{	
				myWriter.write("\n");

				//header file changer
				if (line.contains("#include") ){
					String[] strArray =line.split(" ");					
					myWriter.write(strArray[0]+ " &lt" + strArray[1].substring(1,strArray[1].length()-1) +"&gt");
				}
				
				 
				else if ((line.contains("int") || line.contains("char") || line.contains("float")) && line.contains("=") && line.contains(";")) {
					//changing <,> symbol
					if(line.contains("<")){
						line=line.replace("<", " &lt ");
					}
					if(line.contains(">")){
						line=line.replace(">", " &gt ");
					}					
					
					//processing the string
					String[] manyInitilization =line.split(";");
					int idx=0;
					while(manyInitilization.length>idx) {
						if ((manyInitilization[idx].contains("int") || manyInitilization[idx].contains("char") || manyInitilization[idx].contains("float")) && manyInitilization[idx].contains("=")) {
							String[] strArray = manyInitilization[idx].split(" |=|;");
							String temp = "" ;
							
							int i=0;
							while(strArray.length>i+1) {

								temp+=strArray[i]+" ";
								i++;
							}


							temp+=" = ";
							//temp ="<span class='highlight' >"+strArray[i]+"</span>";
							//temp+="<input type='text' value='"+strArray[i]+"' >";					
							myWriter.write(temp);
							temp=null;
						}
						else {

							myWriter.write(manyInitilization[idx]);							
						}
						myWriter.write(";<b>");
						idx++;
											
					}
					manyInitilization=null;	
					}

				else {
					myWriter.write(line);
					myWriter.write("<br>");					}
				line = null;
				System.gc();			 
			}
			
		     myWriter.write( "		<pre><code class=\"syntax-highight html\"></code></pre>\r\n"
			     		+ "	</div>\r\n"
			     		+ "</div>\r\n"
			     		+ ""
			     		+ "</body>\r\n"
							+ "</html>");
			
			in.close();
		    myWriter.close();	
		
		response.sendRedirect("HomePage.jsp");
	     */
		/*
	     
	     //old input finding trick
		while((line = in.readLine()) != null)
		{	
			//header file changer
			if (line.contains("#include") ){
				String[] strArray =line.stripLeading().stripTrailing().split(" ");					
				myWriter.write(strArray[0]+ " &lt" + strArray[1].substring(1,strArray[1].length()-1) +"&gt");
			}
			
			 
			//else if ((line.contains("int") || line.contains("char") || line.contains("float")) && line.contains("=") && line.contains(";")) {
			else if (line.contains("=") && line.contains(";")) {
				//changing <,> symbol
				if(line.contains("<")){
					line=line.replace("<", " &lt ");
				}
				if(line.contains(">")){
					line=line.replace(">", " &gt ");
				}					
			
				//processing the string
				String[] manyInitilization =line.stripLeading().stripTrailing().split(";");
				int idx=0;
				while(manyInitilization.length>idx) {
					//if ((manyInitilization[idx].contains("int") || manyInitilization[idx].contains("char") || manyInitilization[idx].contains("float")) && manyInitilization[idx].contains("=")) {
					if (manyInitilization[idx].contains("=")) {
						String[] strArray_ = manyInitilization[idx].stripLeading().stripTrailing().split(";");														
						int temp_idx =0;							
						
						while(strArray_.length>temp_idx) {
							int count = (strArray_[temp_idx].split(",", -1).length-1);
							String[] splittingForComma = strArray_[temp_idx].stripLeading().stripTrailing().split(",");
							int idx_comma =0;

							while(splittingForComma.length>idx_comma) {	
								String[] strArray = splittingForComma[idx_comma].stripLeading().stripTrailing().split(" |=|");
	
								String temp = "" ;
								
								int i=0;
								while(strArray.length>i+1) {
	
									temp+=strArray[i]+" ";
									i++;
								}
	
	
								temp+=" = ";
								if (isNumeric(strArray[i])){
									temp +="<span class='highlight' >"+strArray[i]+"</span>";								
								}
								else if(strArray[i].contains("'") || strArray[i].contains("\"")){
									temp +="<span class='highlight' >"+strArray[i]+"</span>";								
								}
								else {
									temp +=strArray[i];										
								}

								if(count>0) {
									temp+=",";
									count-=1;
								}
								//temp+="<input type='text' value='"+strArray[i]+"' >";
								myWriter.write(temp);
								temp=null;
								idx_comma+=1;
							}
							temp_idx+=1;
								
						}
					}
					
					else {
						myWriter.write(manyInitilization[idx]);							
					}
					myWriter.write(";");
					idx++;											
				}
				manyInitilization=null;	
				}

			else {
				myWriter.write(line);
			}
			line = null;
			System.gc();			 
			myWriter.write("<br>");

		}
		
		myWriter.write("</body>\r\n"
				+ "</html>");
		
		in.close();
	    myWriter.close();	
	
	response.sendRedirect("HomePage.jsp");
	*/
	}
	
}
