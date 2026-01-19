package ci553.happyshop.client.login;

/**
 * Constructor for the Customer account subclass, inherits from the Account class.
 */
public class CustomerAccount extends Account {
    public CustomerAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "CUSTOMER"; // Defined Constant
    }

}
