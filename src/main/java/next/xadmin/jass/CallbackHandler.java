package next.xadmin.jass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class CallbackHandler implements javax.security.auth.callback.CallbackHandler {
	private String username = null;
	private String password = null;
	public CallbackHandler(String user, String password){
		this.username = user;
		this.password=password;
	}
	@Override
	public void handle(Callback[] callbackArray) throws IOException, UnsupportedCallbackException{
		System.out.println("CallBack handler started");
		NameCallback nameCallback = null;
		PasswordCallback passwordCallback = null;
		int counter =0;
		while(counter<callbackArray.length) {
			if(callbackArray[counter] instanceof NameCallback) {
				nameCallback = (NameCallback) callbackArray[counter++];
				System.out.println("CB handler 1");
				//System.out.println(nameCallback.getPrompt());
				//nameCallback.setName((new BufferedReader(new InputStreamReader(System.in))).readLine());
				nameCallback.setName(username);
			}else if (callbackArray[counter] instanceof PasswordCallback) {
				passwordCallback = (PasswordCallback) callbackArray[counter++];
				System.out.println("CB handler 2");

				//System.out.println(passwordCallback.getPrompt());
				//passwordCallback.setPassword((new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray());
				passwordCallback.setPassword(password.toCharArray());
			}
		}
	}
	

}