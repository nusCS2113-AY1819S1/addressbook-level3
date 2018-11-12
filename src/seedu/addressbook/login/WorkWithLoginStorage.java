package seedu.addressbook.login;

import java.io.*;
import java.util.Scanner;

public class WorkWithLoginStorage {
    private static File logins = new File("loginstorage.txt");
    private static Scanner sc;
    private static String USERNAME;
    private static String PASSWORD;

    private static void openScanner()   {
        try {
            logins.createNewFile();
            sc = new Scanner(logins);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean compareCredentials(String username, String password)   {
        openScanner();
        if(retrieveUsername(username)){
            retrieveStoredHash();
            return (hashing.hashIt(password)).equals(PASSWORD);
        }else {
            return false;
        }
    }

    public static void editLogin(Credentials username) {
        deleteLogin(username);
        addLogin(username);
    }

    public static boolean addLogin(Credentials username) {
        openScanner();
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(logins, true));
            pw.print("\n" + username.getUsername() + " " + hashing.hashIt(username.getPassword()) + " " + username.getAccessLevel());
            pw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteLogin(Credentials username){
        try{
            File file1 = new File ("temp.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file1, true));
            openScanner();
            while(sc.hasNext()){
                USERNAME = sc.next();
                if(matchUsername(username.getUsername())){
                    sc.nextLine();
                }else{
                    pw.print(USERNAME + sc.nextLine());
                    if(sc.hasNextLine()){
                        pw.println();
                    }
                }
            }
            pw.close();
            logins.delete();
            file1.renameTo(logins);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
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

    public static int retrieveAccessLevel(String username){
        openScanner();
        retrieveUsername(username);
        sc.next();
        return sc.nextInt();
    }
}