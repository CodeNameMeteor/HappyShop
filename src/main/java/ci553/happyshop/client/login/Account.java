package ci553.happyshop.client.login;

// 1. Abstract Base Class
public abstract class Account {
    protected String usernameHash;
    protected String email;
    protected String passwordHash;

    public Account(String username, String email, String pRawPassword, boolean isAlreadyHashed) {
        this.email = email;
        if (isAlreadyHashed) {
            this.passwordHash = pRawPassword;
            this.usernameHash = username;
        } else {
            this.passwordHash = AccountUtils.hashString(pRawPassword);
            this.usernameHash = AccountUtils.hashString(username);
        }
    }

    public boolean checkLogin(String inputUser, String inputPassword) {
        return AccountUtils.checkHash(inputUser, this.usernameHash ) && AccountUtils.checkHash(inputPassword, this.passwordHash);
    }

    public abstract String getAccountType();

    public String toFileString() {
        return usernameHash + "," + email + "," + passwordHash + "," + getAccountType();
    }

}

