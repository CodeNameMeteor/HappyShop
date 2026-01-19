package ci553.happyshop.client.login;

import java.util.Objects;

/**
 * The account class provides a base class for our account classes
 * it provides basic login and hashing functionality.
 */
public abstract class Account {
    protected String usernameHash;
    protected String email;
    protected String passwordHash;

    /**
     * Account Constructor Creates an account object
     * @param username the specified username for the account
     * @param email the specified email for the account
     * @param pRawPassword the inputted password for the account
     * @param isAlreadyHashed tells the constructor if the password is already hashed or not
     */
    public Account(String username, String email, String pRawPassword, boolean isAlreadyHashed) {
        this.email = email;
        //if the already hashed boolean is true use the inputted password and username as the hash
        if (isAlreadyHashed) {
            this.passwordHash = pRawPassword;
            this.usernameHash = username;
        } else {
            //if not hash the inputted password and username and store them
            this.passwordHash = AccountUtils.hashString(pRawPassword);
            this.usernameHash = AccountUtils.hashString(username);
        }
    }

    /**
     * checkLogin checks if the stored details for an account are the same as the inputted ones
     * @param inputUser the inputted username
     * @param inputPassword the inputted password
     * @return returns true if the login is successful, false if not
     */
    public boolean checkLogin(String inputUser, String inputPassword) {
        //check if the user hash and password hash are the same as the inputted ones
        return (AccountUtils.checkHash(inputUser, this.usernameHash) || Objects.equals(inputUser, this.email)) && AccountUtils.checkHash(inputPassword, this.passwordHash);
    }

    /**
     * getAccountType() is an abstract function that doesn't do anything but is a base for subclasses
     * @return returns the account type as a string
     */
    public abstract String getAccountType();

    /**
     * toFileString formats the account data into a string to be written into the user file.
     * @return returns the user data in this format "usernameHash", "email", "passwordHash", "accountType"
     */
    public String toFileString() {
        return usernameHash + "," + email + "," + passwordHash + "," + getAccountType();
    }

}

