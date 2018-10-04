/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

package seedu.addressbook.myjaas;

import java.io.Serializable;
import java.security.Principal;

public class MyPrincipal implements Principal, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
    public String getName(){
	    //TODO
        return null;
    }

//	public static void main(String[] args) {
//		System.setProperty("java.security.auth.login.config", "jaas.config");
//
//		String name = "myName";
//		String password = "myPassword";
//
//		try {
//			LoginContext lc = new LoginContext("Test", new TestCallbackHandler(name, password));
//			lc.login();
//		} catch (LoginException e) {
//			e.printStackTrace();
//		}
//	}
}
