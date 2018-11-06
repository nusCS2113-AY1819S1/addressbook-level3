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

    public void editPassword() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter current password: ");
        PASSWORD = sc.next();
        if(WorkWithLoginStorage.compareCredentials(this.getUsername(), this.getPassword())){
            System.out.println("Enter new password: ");
            String password1 = sc.next();
            System.out.println("Re-enter new password: ");
            String password2 = sc.next();
            if(password1.equals(password2)){
                System.out.println("This action cannot be undone! Are you sure you want to change your password? \n EnterY/N to confirm.");
                String confirm = sc.next();
                if(confirm.equals("Y")){
                    WorkWithLoginStorage.editLogin(this);
                }else if(confirm.equals("N")){
                    System.out.println("Then why waste my time trying to change your password?");
                }else{
                    System.out.println("Invalid input, please try again");
                }
            }
        }
        deletePassword();
//        sc.close();
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
