package ci553.happyshop.client.login;

// 2. Customer Subclass
public class CustomerAccount extends Account {
    public CustomerAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "CUSTOMER"; // Defined Constant
    }

    // You can now add Customer-only methods here!
    // public void addToBasket(...) {}
}
