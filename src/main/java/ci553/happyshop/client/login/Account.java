package ci553.happyshop.client.login;

public class Account {
    private String username;
    private String email;
    private String password; //hashing used in the future
    private int accountType = 0; //0 is customer, 1 is picker, 2 is admin
    private boolean isAdmin = false;

    public Account(String pUsername, String pEmail, String pPassword, int pAccType)
    {
        this.username = pUsername;
        this.email = pEmail;
        this.password = pPassword;
        this.accountType = pAccType;
    }
    public boolean checkAccountLogin(String InputtedUserName, String InputtedPassword)
    {
        if(this.username.equals(InputtedUserName) || this.email.equals(InputtedUserName))
        {
            return this.password.equals(InputtedPassword);
        }
        return false;
    }

}
