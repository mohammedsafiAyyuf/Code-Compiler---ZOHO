
package next.xadmin.jass;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Driver {
	public static void main(String[] args) {
		System.out.println("Driver Started");
		
		System.setProperty("java.security.auth.login.config","C:\\Program Files\\Apache Software Foundation\\Tomcat 8.5\\conf\\jaas_loginPage.config");
		/*
		LoginContext loginContext = null;
	
		try {
			loginContext = new LoginContext("JassLoginModule", new CallbackHandler());
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Driver 1");

			System.out.println(e.getMessage());
			System.exit(0);
		}
		while(true) {
			try {
				loginContext.login();
				System.exit(0);
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Driver 2");
				System.out.println(e.getMessage());
			}
		}
		*/	
	}
	
}
