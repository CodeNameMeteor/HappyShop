package ci553.happyshop.client.login;

//Admin Subclass
public class AdminAccount extends Account {
    public AdminAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "ADMIN";
    }

}
