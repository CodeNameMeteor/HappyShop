package ci553.happyshop.client.login;

import java.io.*;
import java.util.ArrayList;

public class CustomerAccounts {
    private Account currentAccount = null; //store current account
    private final ArrayList<Account> accounts = new ArrayList<>(); //arraylist to store accounts
    private final String FILE_NAME = "users.txt"; //default file name

    public CustomerAccounts() {
        loadAccounts();

        if (accounts.isEmpty()) {
            //if there are no accounts create the default accounts for each account types
            System.out.println("No accounts found. Creating default admin.");
            addAccount(new AdminAccount("admin", "admin@shop.com", "admin123", false));
            addAccount(new CustomerAccount("frank", "frankscott@gmail.com", "scott", false));
            addAccount(new PickerAccount("picker", "picker@pick.com", "picker", false));
        }
    }

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

    // generic add method that accepts any child of Account
    public void addAccount(Account newAcc) {
        accounts.add(newAcc);
        saveAccounts();
    }

    // Helper to return the type as a String or Int based on the instance class
    public String getCurrentAccountType() {
        if (currentAccount == null) return "NONE";
        return currentAccount.getAccountType();
    }

    private void saveAccounts() {
        //write accounts to the file.
        //writes text to a character output stream, buffering characters to efficiently write single characters arrays and strings.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Account acc : accounts) {
                writer.write(acc.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    // Validation Logic.
    public boolean checkNewCustomerDetails(String name, String pass, String email) {
        return email.contains("@") && pass.length() >= 5;
    }
}