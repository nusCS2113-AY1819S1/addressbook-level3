package seedu.addressbook.login;

import java.io.*;
import java.util.Scanner;
import seedu.addressbook.login.hashing;

public class WorkWithLoginStorage {
    private static boolean debug = false;
    private static File logins = new File("src/seedu/addressbook/login/loginstorage.txt");
    private static Scanner sc;
    private static String USERNAME;
    private static String PASSWORD;

    private static void openScanner(){
        try{
            sc = new Scanner(logins);
        } catch (FileNotFoundException f){
            System.out.println("file not found");
        }
    }

    public static boolean compareCredentials(String user, String pass) {
        openScanner();

        if(debug) System.out.println(logins.getAbsolutePath());
        if(debug) System.out.println("user = " + user +"; pass = " + pass);

        if(retrieveUsername(user)){
            if(debug) System.out.println("user correct");
            retrieveStoredHash();
            if(debug)System.out.println("password = " + PASSWORD + "2");
            return hashing.main(pass).equals(PASSWORD);
        }else {
            return false;
        }
    }

//    private static void editLogin(String username){
//        //to be implemented
//    }

    public static void deleteLogin(String username){
        try{
            File file1 = new File ("temp.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file1, true));
            openScanner();
            while(sc.hasNext()){
                USERNAME = sc.next();
                System.out.println(USERNAME);
                if(matchUsername(username)){
                    System.out.println("matches");
                    sc.nextLine();
                    pw.println(sc.nextLine());
                }else{
                    pw.print(USERNAME);
                    pw.println(" " + sc.nextLine());
                }
            }
            pw.close();
            logins.delete();
            file1.renameTo(logins);
        }catch (IOException e){
            System.out.println("cannot create file");
        }
    }

    private static boolean retrieveUsername(String username) {
        while (sc.hasNextLine()) {
            USERNAME = sc.next();
            if(matchUsername(username)){
                return true;
            }
            sc.nextLine();
        }
        return false;
    }

    private static boolean matchUsername(String username){
        return USERNAME.equals(username);
    }

    private static void retrieveStoredHash() {
        PASSWORD = sc.next();
    }

//    public static void retrieveSalt(){
//        //to be implemented
//    }

}