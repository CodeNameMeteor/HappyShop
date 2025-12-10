package ci553.happyshop.client.login;
import java.util.ArrayList;
public class customerAccounts {
    Account account;
    ArrayList<Account> accounts = new ArrayList<Account>();

    public Account createCustomerAccount(String username, String password, String emailAddress, int acctype)
    {
        return new Account(username,password,emailAddress,acctype);
    }

    public boolean login(String AccName, String AccPassword)
    {
        for (Account Account : accounts){
            if(account.checkAccountLogin(AccName,AccPassword))
            {
                account = Account;
                return true;
            }
        }
        account = null;
        return false;
    }
    public boolean loggedIn()
    {
        return account == null;
    }
    public void logOut()
    {
        if(loggedIn())
        {
            account = null;
        }
    }
    public void addAccount(Account a)
    {
        accounts.add(a);
    }
}
