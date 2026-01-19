package ci553.happyshop.client.login;

// 1. Abstract Base Class
public abstract class Account {
    protected String usernameHash;
    protected String email;
    protected String passwordHash;

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

    public boolean checkLogin(String inputUser, String inputPassword) {
        //check if the user hash and password hash are the same as the inputted ones
        return AccountUtils.checkHash(inputUser, this.usernameHash ) && AccountUtils.checkHash(inputPassword, this.passwordHash);
    }

    //abstract function so the subclasses can write their own logic.
    public abstract String getAccountType();

    //format account data into a simple string.
    public String toFileString() {
        return usernameHash + "," + email + "," + passwordHash + "," + getAccountType();
    }

}

