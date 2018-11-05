package seedu.addressbook.login;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.login.Credentials.*;

import java.util.Scanner;

public class login {
//    private boolean loggedin;
//    private boolean debug = true;
    private static int tries = 0;
    private static final String stdUser = "Username";
    private static final String stdPass = "Password";
    private static String usernameF;
    private static String password;
    private static Scanner in = new Scanner(System.in);

//    private static Credentials credentials = new Credentials();
//
//    static {
//        try {
//            credentials = new Credentials("S1234567T", "123abcABC!@#");
//        } catch (IllegalValueException e) {
//            e.printStackTrace();
//        }
//    }


//    public static void setUsername(String user) {
//        username = user;
//    }
//    public static void setPassword(String pass){
//        password = pass;
//    }
//
//    private static void inputLogin(){
//        System.out.print("Username: ");
//        username = in.nextLine();
//        System.out.print("Password: ");
//        password = in.nextLine();
//    }

    public static boolean main(String username, String password){
            Credentials credentials = new Credentials(username, password);
            if(credentials.getUsername().equals(stdUser) && credentials.getPassword().equals(stdPass)){
                System.out.println("Welcome to AddressBook3");
                usernameF = username;
                return true;
            }else if(credentials.validateCredentials()){
//            }else if(WorkWithLoginStorage.compareCredentials(username, password)){
                System.out.println("2Welcome to AddressBook3");
                usernameF = username;
                return true;
            }else{
//                credentials = null;
                System.out.println("Incorrect Username/Password! Please try again.");
            }
//        }
        System.out.println("Login failed. Account is now locked.");
        return false;
    }

    public static String getUsernameF(){
        return usernameF;
    }
}
