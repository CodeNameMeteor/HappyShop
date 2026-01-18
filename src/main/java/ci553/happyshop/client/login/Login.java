package ci553.happyshop.client.login;

import ci553.happyshop.utility.UIStyle;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {

    // Functional interface to send data back to Main
    public interface LoginListener {
        void onLoginSuccess(int accountType);
    }

    private static Login loginInstance;
    private Stage window;
    private customerAccounts accounts; // Store reference to accounts

    public static void showLogin(customerAccounts accounts, LoginListener listener) {
        if (loginInstance == null) {
            loginInstance = new Login(accounts, listener);
        } else {
            loginInstance.window.show();
        }
    }

    private Login(customerAccounts accounts, LoginListener listener) {
        this.accounts = accounts;

        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username or Email");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        btnLogin.setDefaultButton(true); //Allows for it to be hti by enter

        Button btnCreate = new Button("Create Account");
        //btnCreate.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-underline: true;");

        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: red;");

        // --- Login Logic ---
        btnLogin.setOnAction(event -> {
            boolean success = accounts.login(tfUserName.getText(), pfPassword.getText());
            if (success) {
                statusLabel.setText("Login Successful!");
                int type = accounts.getCurrentAccountType();
                window.close();
                listener.onLoginSuccess(type);
            } else {
                statusLabel.setText("Invalid credentials.");
            }
        });

        // --- Register Logic ---
        btnCreate.setOnAction(event -> showRegistrationWindow());

        // --- Layout ---
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        layout.getChildren().addAll(
                new Label("HappyShop Login"),
                tfUserName,
                pfPassword,
                btnLogin,
                btnCreate, // Add the new button here
                statusLabel
        );

        window = new Stage();
        window.setScene(new Scene(layout, 300, 300));
        window.setTitle("Login");
        window.show();
    }

    /**
     * Opens a modal window for new user registration.
     */
    private void showRegistrationWindow() {
        Stage regStage = new Stage();
        regStage.initModality(Modality.APPLICATION_MODAL); // Block interaction with Login window
        regStage.setTitle("Create New Account");

        TextField regUser = new TextField();
        regUser.setPromptText("Choose Username");

        TextField regEmail = new TextField();
        regEmail.setPromptText("Email Address");

        PasswordField regPass = new PasswordField();
        regPass.setPromptText("Choose Password");

        Button btnConfirm = new Button("Register Now");
        Label regStatus = new Label("");

        btnConfirm.setOnAction(e -> {
            String u = regUser.getText().trim();
            String mail = regEmail.getText().trim();
            String p = regPass.getText();

            // Basic Validation
            if (u.isEmpty() || p.isEmpty() || mail.isEmpty()) {
                regStatus.setText("All fields are required.");
                regStatus.setStyle("-fx-text-fill: red;");
            } else {
                // 0 represents 'Customer' account type
                accounts.addCustomerAccount(u, p, mail, 0);

                // Show success and close
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully! Please log in.");
                alert.showAndWait();

                regStage.close();
            }
        });

        VBox regLayout = new VBox(15);
        regLayout.setAlignment(Pos.CENTER);
        regLayout.setStyle("-fx-padding: 20;");
        regLayout.getChildren().addAll(
                new Label("New Customer Registration"),
                regUser,
                regEmail,
                regPass,
                btnConfirm,
                regStatus
        );

        Scene regScene = new Scene(regLayout, 300, 300);
        regStage.setScene(regScene);
        regStage.showAndWait();
    }
}