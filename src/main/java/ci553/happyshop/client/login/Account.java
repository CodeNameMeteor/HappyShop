package ci553.happyshop.client.login;

// 1. Abstract Base Class
public abstract class Account {
    protected String username;
    protected String email;
    protected String passwordHash;

    public Account(String username, String email, String password, boolean isHashed) {
        this.username = username;
        this.email = email;
        this.passwordHash = isHashed ? password : hashPassword(password);
    }

    // Common functionality for all accounts
    public boolean checkLogin(String inputUser, String inputPass) {
        return this.username.equals(inputUser) && this.passwordHash.equals(hashPassword(inputPass));
    }

    // Abstract method: Every subclass MUST define its own type identifier
    public abstract String getAccountType();

    public String toFileString() {
        // We save the type string so we know which class to load later
        return username + "," + email + "," + passwordHash + "," + getAccountType();
    }

    // Mock hash function (keep your existing one)
    protected String hashPassword(String pass) {
        return pass; // Replace with your actual hashing logic
    }
}

