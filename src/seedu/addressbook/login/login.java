package seedu.addressbook.login;

//import seedu.addressbook.data.exception.IllegalValueException;

public class login {
//    private boolean loggedin;
    private static final String stdUser = "Username";
    private static final String stdPass = "Password";
    private static String usernameF;
    private static int accesslevelF;

    public static boolean main(String username, String password){
            Credentials credentials = new Credentials(username, password, 999);
            if(credentials.getUsername().equals(stdUser) && credentials.getPassword().equals(stdPass)){
//                System.out.println("Welcome to AddressBook3");
                usernameF = "Administrator";
                accesslevelF = 0;
                return true;
            }else if(credentials.validateCredentials()){
//                System.out.println("2Welcome to AddressBook3");
                usernameF = username;
                credentials.validateAccessLevel();
                accesslevelF = credentials.getAccessLevel();
//                System.out.println("accesslevel is " + accesslevelF );
                return true;
            }else{
//                credentials = null;
//                System.out.println("Incorrect Username/Password! Please try again.");
            }
//        System.out.println("Login failed. Account is now locked.");
        return false;
    }

    public static String getUsernameF(){
        return usernameF;
    }
    public static int getAccesslevelF(){
        return accesslevelF;
    }
}
