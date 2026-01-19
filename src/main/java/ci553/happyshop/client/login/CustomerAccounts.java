package ci553.happyshop.client.login;

import java.io.*;
import java.util.ArrayList;

public class CustomerAccounts { // Renamed to CamelCase convention
    private Account currentAccount = null;
    private ArrayList<Account> accounts = new ArrayList<>();
    private final String FILE_NAME = "users.txt";

    public CustomerAccounts() {
        loadAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Creating default admin.");
            // Note specific AdminAccount creation
            addAccount(new AdminAccount("admin", "admin@shop.com", "admin123", false));
            addAccount(new CustomerAccount("dylan", "dylanpsilby@gmail.com", "silby", false));
        }
    }

    public boolean login(String inputName, String inputPassword) {
        for (Account acc : accounts) {
            if (acc.checkLogin(inputName, inputPassword)) {
                currentAccount = acc;
                return true;
            }
        }
        currentAccount = null;
        return false;
    }

    // Generic add method that accepts any child of Account
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
                    String user = parts[0];
                    String email = parts[1];
                    String pass = parts[2];
                    String type = parts[3];

                    // FACTORY LOGIC: Decide which class to create based on file data
                    if (type.equals("ADMIN")) {
                        accounts.add(new AdminAccount(user, email, pass, true));
                    } else {
                        accounts.add(new CustomerAccount(user, email, pass, true));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Validation logic remains similar
    public boolean checkNewCustomerDetails(String name, String pass, String email) {
        return email.contains("@") && pass.length() >= 5;
    }
}