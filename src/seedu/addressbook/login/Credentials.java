package seedu.addressbook.login;

import seedu.addressbook.data.exception.IllegalValueException;
import java.io.IOException;
import java.util.Scanner;

public class Credentials {
    private static final String USERNAME_VALIDATION_REGEX = "[stST]\\d{7}\\w";
    private static final String PASSWORD_VALIDATION_REGEX = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private static final String MESSAGE_PASSWORD_CONSTRAINTS = "Invalid Password! Passwords must: \n\thave both Uppercase and Lowercase letters\n\thave at least 1 digit and 1 special character\n\tbe at least 8 characters long ";
    private static final String MESSAGE_USERNAME_CONSTRAINTS = "Invalid Username! Username is NRIC";

    private String PASSWORD;
    private String USERNAME;
    private int ACCESSLEVEL;
//    private String salt;
//    private  int authLevel;

    public Credentials(String username, String password, int accesslevel){
//        if(isValidUsername(username)){
            USERNAME = username;
//            if(isValidPassword(password)){
                PASSWORD = password;
                ACCESSLEVEL = accesslevel;
//            }
//            WorkWithLoginStorage.addLogin(this);
//        }
    }

//    public Credentials(String username, String password) throws IllegalValueException {
//        if(isValidUsername(username)) setUsername(username);
//        else{
//            throw new IllegalValueException(MESSAGE_USERNAME_CONSTRAINTS);
//        }
//        if(isValidPassword(password)) setPassword(password);
//        else{
//            throw new IllegalValueException(MESSAGE_PASSWORD_CONSTRAINTS);
//        }
//        WorkWithLoginStorage.addLogin(this);
//    }

    public void addCredentials(String username, String password) throws IllegalValueException {
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

    public boolean validateCredentials(){
        return WorkWithLoginStorage.compareCredentials(getUsername(), getPassword());
    }

    public void changePassword(){
        WorkWithLoginStorage.editLogin(this);
    }

    public void newLogin(){
        WorkWithLoginStorage.addLogin(this);
        System.out.println("newLogin called");
    }

    private String hashPassword(String toBeHashed){
        return hashing.hashIt(toBeHashed);
    }

    public void validateAccessLevel(){
        setAccessLevel(WorkWithLoginStorage.retrieveAccessLevel(this.getUsername()));
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
        PASSWORD=password;
    }
    public String getPassword(){
        return PASSWORD;
    }

    public int getAccessLevel(){
        return ACCESSLEVEL;
    }
    public void setAccessLevel(int accesslevel){
        ACCESSLEVEL = accesslevel;
    }


    private void deletePassword(){
        PASSWORD = null;
    }
}
