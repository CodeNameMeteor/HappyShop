package ci553.happyshop.client.login;

//Customer Subclass
public class CustomerAccount extends Account {
    public CustomerAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "CUSTOMER"; // Defined Constant
    }

}
