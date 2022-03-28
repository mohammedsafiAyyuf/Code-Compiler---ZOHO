package next.xadmin.jass;

import java.io.IOException;
import java.io.PrintWriter;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.xadmin.login.bean.LoginBean;
import next.xadmin.login.database.LoginDao;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet("/Authentication")
public class AuthenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static LoginContext loginContext = null;
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Logout Started");
		if (loginContext!=null)
		{	try {
				loginContext.logout();
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.invalidate();
		response.sendRedirect("Login.jsp");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.setContentType("text/html");
		//PrintWriter printWriter = response.getWriter();
		//printWriter.println("<html><head><title>JASS WEB Authentication</title></head><body>");
		System.out.println("Authentication started");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("Check 1");

		if((username!=null) && (password!=null)) {
			CallbackHandler callbackHandler = new CallbackHandler(username,password);
			boolean authenticatedFlag =true;
			System.out.println("Check 2");

			try {
				loginContext = new LoginContext("LoginModule",callbackHandler);
				loginContext.login();
				System.out.println("Login Context created successfully");
				
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				authenticatedFlag =false;
			}
			System.out.println("Check 3");

			if (authenticatedFlag) {
				//printWriter.println("Authentication Success..............");
				HttpSession session = request.getSession();
				session.setAttribute("username",username);;
				response.sendRedirect("HomePage.jsp");
				System.out.println("Check 4");
				
			}
			else {
				//printWriter.println("Authentication Failure..............");				
				//String infoMessage = "Your User Name or Password is invalid, Please Sign Up for New account";
				//String titleBar = "Failure!";
				//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
				
				response.sendRedirect("Login.jsp");
				System.out.println("Check 5");

			}
		}
		else {
			response.sendRedirect("Login.jsp");
			//printWriter.println("Invalid Authentication...............");
			System.out.println("Check 6");

		}
		//printWriter.println("</body></html>");
		System.out.println("Check 7");
	}

}