import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
	
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private String dbUname = "zohoUser";
	private String dbPassword = "S@fi26052000";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	
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
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	
	
	public String insert(Member member)
	{
		loadDriver(dbDriver);
		Connection con = getConnection();
		String result = "Data entered successfully";
		String sql = "insert into member values(?,?,?,?)";
		
		PreparedStatement ps;
		try {
		ps = con.prepareStatement(sql);
		ps.setString(1, member.getUname());
		ps.setString(2, member.getPassword());
		ps.setString(3, member.getEmail());
		ps.setString(4, member.getPhone());
		ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Data not entered";
		}
		return result;
	}
}



/*
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDao {
	private String dbUrl = "jdbc:mysql://localhost:3306/userdb";
	private String dbUname = "root";
	private String dbPassword = "rootpasswordgiven";
	private String dbDriver = "com.mysql.cj.jdbc.Driver";

	public void loadDriver(String dbDriver)
	{
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//con is our connection object
	public Connection getConnection()
	{
		Connection con = null;
		try {
			con = DriverManager.getConnection(dbUrl, dbUname, dbPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}	
	
	public String insert(Member member) {
		//check flag
		String result = "Data Entered Successfully";
		
		// to insert data to database we need to use jdbc driver for that we need 
		// to load the jdbc driver and load the connections 

		//loading Driver
		loadDriver(dbDriver);
		
		//getting Connection
		Connection con = getConnection();
		
		//Code to insert Data , we will write query and store it inside string
		String sql = "insert into member values(?,?,?,?)";
		
		//we have to create an prepared statement and insert the data using prepared statement
		PreparedStatement ps;
		try {
		ps = con.prepareStatement(sql);
		ps.setString(1, member.getUname());
		ps.setString(2, member.getPassword());
		ps.setString(3, member.getEmail());
		ps.setString(4, member.getPhone());
		ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Data not entered";
		}
		return result;
	}
}
*/