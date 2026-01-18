package ci553.happyshop.client.login;

public class Account {
    private final String username;
    private final String email;
    private final String passwordHash;
    public int accountType; // 0=Customer, 1=Picker, 2=Admin

    //  accepts a raw password and hashes it immediately
    public Account(String pUsername, String pEmail, String pRawPassword, int pAccType, boolean isAlreadyHashed) {
        this.username = pUsername;
        this.email = pEmail;
        this.accountType = pAccType;

        if (isAlreadyHashed) {
            this.passwordHash = pRawPassword;
        } else {
            this.passwordHash = PasswordUtils.hashPassword(pRawPassword);
        }
    }

    public boolean checkAccountLogin(String inputName, String inputPassword) {
        if (this.username.equals(inputName) || this.email.equals(inputName)) {
            // compare input hash to stored hash
            return PasswordUtils.checkPassword(inputPassword, this.passwordHash);
        }
        return false;
    }

    // format data
    public String toFileString() {
        return username + "," + email + "," + passwordHash + "," + accountType;
    }

    //public String getUsername() { return username; }
}