package seedu.addressbook.login;

import java.sql.SQLOutput;
import java.util.Scanner;

public class login {
    private boolean loggedin;
//    private boolean debug = true;
    private static int tries = 0;

//    public static String[] getLogin(){
//        String stdUser;
//        String stdPass;
//        stdUser = "Username";
//        stdPass = "Password";
//
//        String username;
//        String password;
//
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Username: ");
//        username = sc.next();
//        System.out.print("Password: ");
//        password = sc.next();
//    }

    public static boolean main(){
        String stdUser;
        String stdPass;
        stdUser = "Username";
        stdPass = "Password";

        String username;
        String password;

        Scanner sc = new Scanner(System.in);
        for(tries=0; tries<3; tries++){
            System.out.print("Username: ");
            username = sc.next();
            System.out.print("Password: ");
            password = sc.next();

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
//        System.out.println(username + "   " + password);
    }
}
