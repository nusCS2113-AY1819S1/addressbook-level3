package seedu.addressbook.login;


public class login {
    private static final String stdUser = "Username";
    private static final String stdPass = "Password";
    private static String usernameF;
    private static int accesslevelF;

    public static boolean main(String username, String password){
            Credentials credentials = new Credentials(username, password, 999);
            if(credentials.getUsername().equals(stdUser) && credentials.getPassword().equals(stdPass)){
                usernameF = "Administrator";
                accesslevelF = 0;
                credentials.validateCredentials();
                return true;
            }else if(credentials.validateCredentials()) {
                usernameF = username;
                credentials.validateAccessLevel();
                accesslevelF = credentials.getAccessLevel();
                return true;
            }
        return false;
    }

    public static String getUsernameF(){
        return usernameF;
    }
    public static int getAccesslevelF(){
        return accesslevelF;
    }
}
