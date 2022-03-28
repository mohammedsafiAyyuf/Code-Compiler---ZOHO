package next.xadmin.jass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import next.xadmin.login.bean.LoginBean;

public class LoginDao {
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private String dbUname = "zohoUser";
	private String dbPassword = "S@fi26052000";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public void loadDriver(String dbDriver)
	{
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		//Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	public boolean validate(LoginBean loginBean) {
		boolean status = false;
		loadDriver(dbDriver);
		
		//Connection con = getConnection();
		getConnection();
		
		String sql = "select * from member where uname=? and password=?";

		//PreparedStatement ps;
		try {
		ps = con.prepareStatement(sql);
		ps.setString(1, loginBean.getUsername());
		ps.setString(2, loginBean.getPassword());
		//ResultSet rs = ps.executeQuery();
		rs = ps.executeQuery();
		status = rs.next();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//response.getWriter().print("Found YOu");
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean CloseConnection(){
	    try { rs.close(); } catch (Exception e) { /* Ignored */ }
	    try { ps.close(); } catch (Exception e) { /* Ignored */ }
	    try { con.close(); } catch (Exception e) { /* Ignored */ }
		return true;
		
	}
}