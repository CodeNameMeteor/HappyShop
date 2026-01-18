package ci553.happyshop.client.login;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login {

    // Functional interface to send data back to Main
    public interface LoginListener {
        void onLoginSuccess(int accountType);
    }

    private static Login loginInstance;
    private Stage window;

    // Modified to accept the Listener
    public static void showLogin(customerAccounts accounts, LoginListener listener) {
        if (loginInstance == null) {
            loginInstance = new Login(accounts, listener);
        } else {
            loginInstance.window.show();
        }
    }

    private Login(customerAccounts accounts, LoginListener listener) {
        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username");

        PasswordField pfPassword = new PasswordField(); // Use PasswordField!
        pfPassword.setPromptText("Password");

        Button btnLogin = new Button("Login");
        Button btnCreate = new Button("Create Account");
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: red;");
        btnCreate.setOnAction(event -> {
            //create logic goes here
        });
        btnLogin.setOnAction(event -> {
            boolean success = accounts.login(tfUserName.getText(), pfPassword.getText());

            if (success) {
                statusLabel.setText("Login Successful!");
                int type = accounts.getCurrentAccountType();
                window.close(); // Close login window
                listener.onLoginSuccess(type); // Trigger Main to open apps
            } else {
                statusLabel.setText("Invalid credentials.");
            }
        });

        // Layout
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                new Label("HappyShop Login"),
                tfUserName,
                pfPassword,
                btnLogin,
                btnCreate,
                statusLabel
        );
        layout.setStyle("-fx-padding: 20;");

        window = new Stage();
        window.setScene(new Scene(layout, 300, 250));
        window.setTitle("Login");
        window.show();
    }
}