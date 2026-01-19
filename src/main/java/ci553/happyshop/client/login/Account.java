package ci553.happyshop.client.login;

// 1. Abstract Base Class
public abstract class Account {
    protected String username;
    protected String email;
    protected String passwordHash;

    public Account(String username, String email, String pRawPassword, boolean isAlreadyHashed) {
        this.username = username;
        this.email = email;
        if (isAlreadyHashed) {
            this.passwordHash = pRawPassword;
        } else {
            this.passwordHash = PasswordUtils.hashPassword(pRawPassword);
        }
    }

    public boolean checkLogin(String inputUser, String inputPassword) {
        return this.username.equals(inputUser) && PasswordUtils.checkPassword(inputPassword, this.passwordHash);
    }

    public abstract String getAccountType();

    public String toFileString() {
        return username + "," + email + "," + passwordHash + "," + getAccountType();
    }

}

