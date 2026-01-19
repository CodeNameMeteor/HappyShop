package ci553.happyshop.client.login;

/**
 * Constructor for the Picker account subclass, inherits from the Account class.
 */
public class PickerAccount extends Account {
    public PickerAccount(String user, String email, String pass, boolean isHashed) {
        super(user, email, pass, isHashed);
    }

    @Override
    public String getAccountType() {
        return "PICKER";
    }

}
