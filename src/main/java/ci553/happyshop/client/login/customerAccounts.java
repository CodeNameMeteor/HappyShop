package ci553.happyshop.client.login;
import java.util.ArrayList;
public class customerAccounts {
    Account account = null;
    ArrayList<Account> accounts = new ArrayList<Account>();

    public customerAccounts()
    {}


    public Account createCustomerAccount(String username, String password, String emailAddress, int accType)
    {
        return new Account(username,password,emailAddress,accType);
    }

    public boolean login(String AccName, String AccPassword)
    {
        for (Account Account : accounts){
            if(Account.checkAccountLogin(AccName,AccPassword))
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
        return account != null;
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

    public void addCustomerAccount(String pUserName, String pPassword, String pEmail, int pAcctype)
    {
        addAccount(createCustomerAccount(pUserName,pPassword,pEmail,pAcctype));

    }

    public int getAcctype()
    {
        if(loggedIn()) {
            return account.accountType;
        }
        return 5;
    }
}
