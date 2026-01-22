package ci553.happyshop.client.login;

import java.io.*;
import java.util.ArrayList;

/**
 * CustomerAccounts is a class that interfaces with the account logic and the user
 * creates default accounts if they are not created already
 * writes account to data to files and loads them too
 */
public class CustomerAccounts {
    private Account currentAccount = null; //store current account
    private final ArrayList<Account> accounts = new ArrayList<>(); //arraylist to store accounts
    private final String FILE_NAME = "users.txt"; //default file name

    /**
     * CustomerAccount constructor, initializes the users.txt file if no users are present.
     */
    public CustomerAccounts() {
        loadAccounts();

        if (accounts.isEmpty()) {
            //if there are no accounts create the default accounts for each account types
            System.out.println("No accounts found. Creating default admin.");
            addAccount(new AdminAccount("admin", "admin@shop.com", "admin123", false));
            addAccount(new CustomerAccount("frank", "frankscott@gmail.com", "scott", false));
            addAccount(new PickerAccount("picker", "picker@pick.com", "picker123", false));
        }
    }

    /**
     * login class iterates through the stored accounts to see if the inputted data matches any
     * of the stored ones
     * @param inputName inputted user name
     * @param inputPassword inputted password
     * @return returns true if an account was found, false if not.
     */
    public boolean login(String inputName, String inputPassword) {
        //iterates through the account list and checks the inputted logins to the stored ones.
        for (Account acc : accounts) {
            if (acc.checkLogin(inputName, inputPassword)) {
                currentAccount = acc;
                return true;
            }
        }
        currentAccount = null;
        return false;
    }

    /**
     * addAccount generic account that works with any account and adds them to the account array list
     * and saves them to the file
     * @param newAcc New account to be added.
     */
    public void addAccount(Account newAcc) {
        accounts.add(newAcc);
        saveAccounts();
    }

    /**
     * getCurrentAccountType will return a string if the account is none
     * as we expect the return to be a string if it's null it wouldn't be able to
     * this prevents those errors from occuring
     * @return returns the account type string
     */
    public String getCurrentAccountType() {
        if (currentAccount == null) return "NONE";
        return currentAccount.getAccountType();
    }
    //write accounts to the file.
    //writes text to a character output stream, buffering characters to efficiently write single characters arrays and strings.
    /**
     * saveAccounts writes new accounts to the stored user file
     */
    private void saveAccounts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accounts) {
                writer.write(acc.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads stored accounts from the users.txt file
     */
    private void loadAccounts() {
        //load accounts from the file
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    //get each individual section of the account
                    String user = parts[0];
                    String email = parts[1];
                    String pass = parts[2];
                    String type = parts[3];

                    // factory logic: gets accounts from the file and creates new accounts for them.
                    if (type.equals("ADMIN")) {
                        accounts.add(new AdminAccount(user, email, pass, true));
                    } else if (type.equals("PICKER")) {
                        accounts.add(new PickerAccount(user, email, pass, true));
                    } else {
                        accounts.add(new CustomerAccount(user, email, pass, true));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * checkNewCustomerDetails, checks the inputted details against a set of requirements
     * for example password must be longer then 5 characters, and an email must contain '@'
     * @param name inputted username
     * @param pass inputted password
     * @param email inputted email
     * @return returns true if it meets the requirements, false if not.
     */
    public boolean checkNewCustomerDetails(String name, String pass, String email) {
        return email.contains("@") && pass.length() >= 5;
    }
}