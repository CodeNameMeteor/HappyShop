package ci553.happyshop.client.login;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * AccountUtils provides hashing functionality
 * allows for a given string to be hashed and to check hashs against hash's
 */
public class AccountUtils {
    /**
     * hashString returns a hashed version of a given string
     * @param data inputted String to be hashed
     * @return returns the hashed hex string of the inputted data
     */
    public static String hashString(String data) {
        try {
            //Message Digest is a class inside of Java.Security
            //that provides the use of various cryptographic functions.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //create a byte array of the hash digest bytes.
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
            //use big integer to convert the byte array into it's sign-magnitude representation
            BigInteger number = new BigInteger(1, hash);
            //convert the hex representation into a string.
            StringBuilder hexString = new StringBuilder(number.toString(16));
            //pad with leading zeros
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            //return hex
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            //catch if we specify a non-existent algorithm
            throw new RuntimeException(e);
        }
    }

    /**
     * checkHash hash's an inputted string and compares it to a another
     * @param inputPassword the password the user inputted
     * @param storedHash the hash that is stored in the file
     * @return returns true if the password hash is the same as stored one, false if not.
     */
    public static boolean checkHash(String inputPassword, String storedHash) {
        //converts the inputted password into a hash and compare it to the stored hash.
        String inputHash = hashString(inputPassword);
        return inputHash.equals(storedHash);
    }
}