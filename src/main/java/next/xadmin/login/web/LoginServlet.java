package next.xadmin.login.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import next.xadmin.login.bean.LoginBean;
import next.xadmin.login.database.LoginDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private LoginDao loginDao = null;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//procedure for LogOut
		if (loginDao!=null){
			boolean result = loginDao.CloseConnection();
			HttpSession session = request.getSession();
			session.removeAttribute("username");
			session.invalidate();
			response.sendRedirect("Login.jsp");

			//String infoMessage = "Successfull Logout";
			//String titleBar = Boolean.toString(result);
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//procedure for login
		//LoginDao loginDao = new LoginDao();
		if (loginDao==null){
			loginDao = new LoginDao();
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);

		if (loginDao.validate(loginBean)){
			HttpSession session = request.getSession();
			session.setAttribute("username",username);;
			response.sendRedirect("HomePage.jsp");
		}
		else {
			//String infoMessage = "Your User Name or Password is invalid, Please Sign Up for New account";
			//String titleBar = "Failure!";
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
			
			response.sendRedirect("Login.jsp");
		}
	}

}
