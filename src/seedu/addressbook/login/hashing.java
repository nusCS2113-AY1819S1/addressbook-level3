package seedu.addressbook.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class hashing {

    private static String hashAlgo = "SHA-512";

    public static String main(String password){
        try {
            return getHash(password, hashAlgo);
        }catch (Exception b){
            System.out.println("sometihing");
            return "gg";
        }
    }

    private static String getHash(String password,String hashAlgo) {
        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgo);
            digest.reset();
            byte[] hash = digest.digest(password.getBytes());
            return bytesToStringHex(hash);
        } catch (NoSuchAlgorithmException a) {
            System.out.println("nosuchalgo found");
        }
        return "abc";
    }

    private static byte[] getSalt(){
        byte[] bytes = new byte[20];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for(int j=0; j<bytes.length; j++){
            int v = bytes[j] & 0xFF;
            hexChars[j*2] = hexArray[v >>> 4];
            hexChars[j*2+1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

