package seedu.addressbook.login;

import java.util.Scanner;

public class Login {
//    private boolean loggedin;
//    private boolean debug = true;

    public static boolean main(){
        String stdUser;
        String stdPass;
        stdUser = "Username";
        stdPass = "Password";

        String username;
        String password;

        Scanner sc = new Scanner(System.in);
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
            System.out.println("Login failed");
            return false;
        }

//        System.out.println(username + "   " + password);
    }
}