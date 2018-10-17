package seedu.addressbook.login;

import java.io.*;
import java.util.Scanner;
import seedu.addressbook.login.hashing;

public class WorkWithLoginStorage {
    private static boolean debug = false;
    private static File logins = new File("src/seedu/addressbook/login/loginstorage.txt");

    public static boolean compareCredentials(String user, String pass) {
        try {
            String username;
            String password;
            String hashedPassword;

            Scanner sc = new Scanner(logins);
            if(debug) System.out.println(logins.getAbsolutePath());
            if(debug) System.out.println("user = " + user +"; pass = " + pass);
            while (sc.hasNextLine()) {
                username = sc.next();
                if(debug) System.out.println("username = " + username + "1");
                if(username.equals(user)){
                    if(debug)System.out.println("user correct");
                    password = sc.next();
                    if(debug)System.out.println("password = " + password + "2");
                    if(hashing.main(pass).equals(password)){
                        return true;
                    }else{
                        return false;
                    }
                }else{
//                    System.out.println(sc.next());
                    sc.next();
                    sc.next();
                }
            }
        } catch (FileNotFoundException f){
            System.out.println("file not found");
        }
        return false;
    }

    public static void editLoginStorage(){
        try{
            File file1 = new File ("temp.txt");
            PrintWriter pw = new PrintWriter(new FileWriter(file1, true));
            Scanner sc = new Scanner(logins);
            while(sc.hasNextLine()){
                pw.println(sc.nextLine());
            }
//            pw.println("hello123");
            pw.close();
            logins.delete();
            file1.renameTo(logins);
        }catch (IOException e){
            System.out.println("cannot create file");
        }
    }
}