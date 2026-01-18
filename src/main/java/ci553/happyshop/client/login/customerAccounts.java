package ci553.happyshop.client.login;

import java.io.*;
import java.util.ArrayList;

public class customerAccounts {
    private Account currentAccount = null;
    private ArrayList<Account> accounts = new ArrayList<>();
    private final String FILE_NAME = "users.txt";

    public customerAccounts() {
        loadAccounts();

        // If file is empty/missing, add default admin for testing
        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Creating default admin.");
            addCustomerAccount("admin", "admin123", "admin@shop.com", 2);
            addCustomerAccount("dylan", "silby", "dylanpsilby@gmail.com", 0);
        }
    }

    public boolean login(String inputName, String inputPassword) {
        for (Account acc : accounts) {
            if (acc.checkAccountLogin(inputName, inputPassword)) {
                currentAccount = acc;
                return true;
            }
        }
        currentAccount = null;
        return false;
    }

    public void addCustomerAccount(String name, String pass, String email, int type) {
        // Create new account (false = password is NOT yet hashed)
        Account newAcc = new Account(name, email, pass, type, false);
        accounts.add(newAcc);
        saveAccounts(); // Save immediately to file
    }

    public int getCurrentAccountType() {
        return (currentAccount != null) ? currentAccount.accountType : -1;
    }

    public boolean checkNewCustomerDetails(String name, String pass, String email)
    {
        if(!email.contains("@"))
        {
            return false;
        }
        else if(pass.length() < 5)
        {
            return false;
        }

        return true;
    }

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

    private void loadAccounts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    // Load account (true = password IS already hashed in file)
                    accounts.add(new Account(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), true));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}