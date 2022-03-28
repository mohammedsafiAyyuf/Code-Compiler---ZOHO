//1. import a package
package next.xadmin.login.database;
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
/*
JDBC - Java Data Base connection system.
to connect java to Db we need to use driver there are 4 types of driver. to connect java to DB we need to follow 7 steps

 1.import package
 2.load or register a driver
 3.establish a connection
 4.create the statement
 5.execute the query
 6.process result
 7. close the connection

 */

public class LoginDao {
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private String dbUname = "zohoUser";
	private String dbPassword = "S@fi26052000";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//2. download sql jar file put the file in project folder ,load it here
	public void loadDriver(String dbDriver)
	{
		try {
			//to call the static block without creating the object of it **IMPORTANT**
			//there will be two block static and instance block, static block is called when class loaded and instance block is called when object created
			Class.forName(dbDriver);
			//DriverManager.registerDriver(new com.mysl.jdbc.Driver()); 
			//to crate a new instance use .newInstance() at end of it.
			//this is same as DriverManager.registerDriver(new com.mysl.jdbc.Driver()); 
			//if we go into the driver class of mysql connector there we can see the static block there contains this same code there
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//3. establishing the connectoin .
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
		
		//4. creating statement
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
		CloseConnection();  //7. close connectoin here
		return status;
	}
	
	public boolean CloseConnection(){
	    try { rs.close(); } catch (Exception e) { /* Ignored */ }
	    try { ps.close(); } catch (Exception e) { /* Ignored */ }
	    try { con.close(); } catch (Exception e) { /* Ignored */ }
		return true;
		
	}
}
