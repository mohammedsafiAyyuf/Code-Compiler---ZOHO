
import javax.swing.JOptionPane;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

/**
 * Servlet implementation class Register
 */
//@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Getting the Members
		String uname = request.getParameter("uname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		//Inserting the member to member class variables
		Member member = new Member(uname, password, email, phone);
		
		//Inserting the members to Data Base
		RegisterDao rDao= new RegisterDao();
		String result = rDao.insert(member);
		
		//we gonna show response to webpage saying if data entered successfully to db
		if (result == "Data not entered"){ 
			//String infoMessage = " Registration Failed";
			//String titleBar = "Failer";
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);

				//response.getWriter().print(result);
				response.sendRedirect("memberRegistration.jsp");
		}
		else {
			//String redirectURL ="C:\\Users\\MR_MECHANICAL\\eclipse-workspace\\LoginPage\\src\\main\\webapp\\HomePage.jsp" ;
			//String infoMessage = "Success Fully Registration";
			//String titleBar = "Success";
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);

			response.sendRedirect("Login.jsp");
			//response.getWriter().print(result);

			//Response response1 = new Response();
			//response1.sendRedirect(redirectURL);
		}
		
	}
}
