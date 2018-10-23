package seedu.addressbook.login;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.login.hashing;

import java.io.IOException;
import java.util.Scanner;

public class Credentials {
    private static final String USERNAME_VALIDATION_REGEX = "[stST]\\d{7}\\w";
    private static final String PASSWORD_VALIDATION_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private static final String MESSAGE_PASSWORD_CONSTRAINTS = "Invalid Password! Passwords must: \n\thave both Uppercase and Lowercase letters\n\thave at least 1 digit\n\tbe at least 8 characters long ";
    private static final String MESSAGE_USERNAME_CONSTRAINTS = "Invalid Username! Username is NRIC";

    private String PASSWORD;
    private String USERNAME;
//    private String salt;
//    private  int authLevel;

    public Credentials(String username, String password) throws IllegalValueException {
        if(isValidUsername(username)) setUsername(username);
        else{
            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
        }
        if(isValidPassword(password)) setPassword(password);
        else{
            throw new IllegalValueException(MESSAGE_PASSWORD_CONSTRAINTS);
        }
        WorkWithLoginStorage.addLogin(this);
    }

    public void editPassword() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter current password: ");
        PASSWORD = sc.next();
        if(WorkWithLoginStorage.compareCredentials(this)){
            System.out.println("Enter new password: ");
            String password1 = sc.next();
            System.out.println("Re-enter new password: ");
            String password2 = sc.next();
            if(password1.equals(password2)){
                WorkWithLoginStorage.editLogin(this);
            }
        }
        deletePassword();
        sc.close();
    }

    private boolean isValidUsername(String username){
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    private boolean isValidPassword(String password){
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    public void setUsername(String username){
            USERNAME = username;
    }

    public String getUsername(){
        return USERNAME;
    }

    public void setPassword(String password) {
            PASSWORD = hashPassword(password);
    }

    private String hashPassword(String toBeHashed){
        return hashing.hashIt(toBeHashed);
    }

    public String getPassword(){
        return PASSWORD;
    }

    private void deletePassword(){
        PASSWORD = null;
    }
}
