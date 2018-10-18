package seedu.addressbook.login;

import java.util.Scanner;

public class login {
//    private boolean loggedin;
//    private boolean debug = true;
    private static int tries = 0;
    private static String stdUser = "Username";
    private static String stdPass = "Password";
    private static String username;
    private static String password;
    private static Scanner sc = new Scanner(System.in);

    public static void getLogin(){
        System.out.print("Username: ");
        username = sc.next();
        System.out.print("Password: ");
        password = sc.next();
    }

    public static boolean main(){
        for(tries=0; tries<3; tries++){
            getLogin();
            if(username.equals(stdUser) && password.equals(stdPass)){
                System.out.println("Welcome to AddressBook3");
                return true;
            }else if(WorkWithLoginStorage.compareCredentials(username, password)){
                System.out.println("2Welcome to AddressBook3");
                return true;
            }else{
                System.out.println("Incorrect Username/Password! Please try again.");
            }
        }
        System.out.println("Login failed. Account is now locked.");
        return false;
    }
}
