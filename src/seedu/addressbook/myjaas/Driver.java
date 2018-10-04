package seedu.addressbook.myjaas;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class Driver {
    public static void main(String[] args) throws LoginException {
        System.setProperty("java.security.auth.login.config", "jaas2.config");
        LoginContext loginContext = new LoginContext("MyJaasTest", new MyCallbackHandler());
        loginContext.login();
    }
}
