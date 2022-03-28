package next.xadmin.jass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;

import next.xadmin.login.bean.LoginBean;
import next.xadmin.login.database.LoginDao;

public class LoginModule implements javax.security.auth.spi.LoginModule {
  private CallbackHandler handler;
  private Subject subject;
  private UserPrincipal userPrincipal;
  private RolePrincipal rolePrincipal;
  private String login;
  private List<String> userGroups;
	
	public static final String TEST_USERNAME = "test";
	public static final String TEST_PASSWORD = "test";
	private CallbackHandler callbackHandler = null;
	private boolean authenticationSuccessFlag = false;
	private LoginDao loginDao = null; 	
	
	@Override
	public boolean commit() throws LoginException {
		System.out.println("In commit of LM");
	    userPrincipal = new UserPrincipal(login);
	    subject.getPrincipals().add(userPrincipal);
	
	    if (userGroups != null && userGroups.size() > 0) {
	      for (String groupName : userGroups) {
	        rolePrincipal = new RolePrincipal(groupName);
	        subject.getPrincipals().add(rolePrincipal);
	      }
	    }
		System.out.println("End commit of LM");
	    return true;
		//return authenticationSuccessFlag;
	}

	@Override
	public boolean abort() throws LoginException {
		// TODO Auto-generated method stub
		System.out.println("In abort of LM");
		return false;
	}
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,Map<String, ?> options) {
		System.out.println("In initilize of LM");
		handler = callbackHandler;
	    this.subject = subject;
		this.callbackHandler = callbackHandler;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean login() throws LoginException {
		System.out.println("Login Module Started");
		// TODO Auto-generated method stub
		Callback[] callbackArray = new Callback[2];
		callbackArray[0] = new NameCallback("User Name:");
		callbackArray[1] = new PasswordCallback("password:", true);
		try {
			callbackHandler.handle(callbackArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("here 1");
			e.printStackTrace();
		} catch (UnsupportedCallbackException e) {
			// TODO Auto-generated catch block
			System.out.println("here 2");
			e.printStackTrace();
		}
		String username = ((NameCallback) callbackArray[0]).getName();
		String password =  new String(((PasswordCallback) callbackArray[1]).getPassword());
		/*
		if((username.equals("admin"))&& password.equals("2000")) {
			return true;
		}
		*/

		if (loginDao==null){
			loginDao = new LoginDao();
		}
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);


		
		
		
		if(loginDao.validate(loginBean)) {
			System.out.println("LM 1");
			System.out.println(username + " " + password);
			System.out.println("Authentication success......................");
			authenticationSuccessFlag = true;
	    	login = username;
	        userGroups = new ArrayList<String>();
	        userGroups.add("admin");
			return authenticationSuccessFlag;

		}
		else {
			System.out.println("LM 2");
			authenticationSuccessFlag = false;
			System.out.println("Authentication Failed......................");
			throw new FailedLoginException("authentication failure .........");
		}
	
	}

	@Override
	public boolean logout() throws LoginException {
		System.out.println("In logout of LM");
		// TODO Auto-generated method stub
		if (loginDao!=null){
			boolean result = loginDao.CloseConnection();

			//String infoMessage = "Successfull Logout";
			//String titleBar = Boolean.toString(result);
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);

		}
	    subject.getPrincipals().remove(userPrincipal);
	    subject.getPrincipals().remove(rolePrincipal);
		System.out.println("out logout of LM");
	    return true;
		//return false;
	}

}