/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

package seedu.addressbook.myjaas;

//import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;
//import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
//import javax.security.auth.callback.NameCallback;
//import javax.security.auth.callback.PasswordCallback;
//import javax.security.auth.callback.UnsupportedCallbackException;
//import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class MyLoginModule implements LoginModule {
	public void initialize(Subject arg0, CallbackHandler arg1, Map<String, ?> arg2, Map<String,?> arg3){
        System.out.println("LM.initialize...");
	}
    public boolean login() throws LoginException {
        System.out.println("LM.login...");
        return false;
    }
    public boolean commit() throws LoginException {
        System.out.println("LM.commit...");
        return false;
    }
    public boolean abort() throws LoginException {
        System.out.println("LM.abort...");
        return false;
    }
    public boolean logout() throws LoginException {
        System.out.println("LM.logout...");
        return false;
    }
}