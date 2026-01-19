package ci553.happyshop.client.login;

// 3. Admin Subclass
public class PickerAccount extends Account {
    public PickerAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "PICKER";
    }

}
