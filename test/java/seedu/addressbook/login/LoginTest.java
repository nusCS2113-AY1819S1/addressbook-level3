package seedu.addressbook.login;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.login.hashing;
import seedu.addressbook.login.Credentials;
import seedu.addressbook.login.WorkWithLoginStorage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class LoginTest {

    private Credentials credentials;

    @Before
    public void setup() {
        credentials = new Credentials("S0000000A", "S0000000a", 2);
    }

    @Test
    public void testLogin(){
        assertTrue(login.main(credentials.getUsername(), credentials.getPassword()));
        assertTrue(login.main("Username", "Password"));
        assertFalse(login.main("fakeUser", "fakePass"));
    }

    @Test
    public void testGetAndSetUsername() {
        String user = "S1234567A";
        credentials.setUsername(user);
        assertEquals(user, credentials.getUsername() );
    }

    @Test
    public void testAddCredentials() throws IllegalValueException {
        credentials.addCredentials("S1239991A", "S1239991a!");
        assertEquals("S1239991A", credentials.getUsername());
        assertEquals("S1239991a!", credentials.getPassword());
    }

    @Test
    public void testGetAndSetPassword() {
        String password = "qwERty!@#123>;'.[,';'.p";
        credentials.setPassword(password);
        assertEquals(password, credentials.getPassword() );
    }

    @Test
    public void testValidateCredentials() {
        assertTrue(credentials.validateCredentials());
        credentials.setPassword("fakePass123!@#");
        assertFalse(credentials.validateCredentials());
        credentials.setUsername("fakeUser123!@#");
        assertFalse(credentials.validateCredentials());
    }

//    @Test
//    public void testDeleteAndAddLogin(){
//        assertTrue(WorkWithLoginStorage.deleteLogin(credentials));
//        assertFalse(login.main(credentials.getUsername(), credentials.getPassword()));
//        assertTrue(WorkWithLoginStorage.addLogin(credentials));
//        assertTrue(login.main(credentials.getUsername(), credentials.getPassword()));
//    }

    @Test
    public void testEditLogin(){
        assertTrue(WorkWithLoginStorage.addLogin(credentials));
        assertTrue(login.main(credentials.getUsername(), credentials.getPassword()));
    }
//    @Test
//    public void testHashIt(){
//        String hashedPassword = "20B0855B805188A131444C4B0C7B4BEB1F1C2C7D72EAC683AE42B6A439F9F4D9796454836148972BDB2F02E0487195BB620FAD6E168AC76F7C0BFE4743871C18";
//        assertEquals(hashedPassword, hashing.hashIt(credentials.getPassword()));
//    }
}
