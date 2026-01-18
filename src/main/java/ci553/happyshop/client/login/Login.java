package ci553.happyshop.client.login;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login {

    public interface LoginListener {
        void onLoginSuccess(int accountType);
    }

    private static Login loginInstance;
    private Stage window;
    private customerAccounts accounts;

    public static void showLogin(customerAccounts accounts, LoginListener listener) {
        if (loginInstance == null) {
            loginInstance = new Login(accounts, listener);
        } else {
            loginInstance.window.show();
        }
    }

    private Login(customerAccounts accounts, LoginListener listener) {
        this.accounts = accounts;

        Label lblHeader = new Label("Welcome Back");
        lblHeader.getStyleClass().add("header-label");

        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username");
        tfUserName.getStyleClass().add("input-field");

        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");
        pfPassword.getStyleClass().add("input-field");

        Button btnLogin = new Button("LOG IN");
        btnLogin.getStyleClass().add("primary-btn");
        btnLogin.setMaxWidth(Double.MAX_VALUE); // Stretch button
        btnLogin.setDefaultButton(true);

        Button btnRegister = new Button("Don't have an account? Sign up");
        btnRegister.getStyleClass().add("link-btn");

        Label statusLabel = new Label("");
        statusLabel.getStyleClass().add("error-label");

        btnLogin.setOnAction(event -> {
            if (accounts.login(tfUserName.getText(), pfPassword.getText())) {
                window.close();
                listener.onLoginSuccess(accounts.getCurrentAccountType());
            } else {
                statusLabel.setText("Invalid username or password");
            }
        });

        btnRegister.setOnAction(event -> showRegistrationWindow());

        VBox card = new VBox(15); // 15px spacing
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card-pane");
        card.getChildren().addAll(lblHeader, tfUserName, pfPassword, btnLogin, btnRegister, statusLabel);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-padding: 20;"); // Padding around the outside

        Scene scene = new Scene(root, 400, 500);

        try {
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("Could not load CSS file. Make sure it is in the src folder.");
        }

        window = new Stage();
        window.setScene(scene);
        window.setTitle("HappyShop Login");
        window.show();
    }

    private void showRegistrationWindow() {
        Stage regStage = new Stage();
        regStage.initModality(Modality.APPLICATION_MODAL);
        regStage.setTitle("Create Account");

        Label lblHeader = new Label("Create Account");
        lblHeader.getStyleClass().add("header-label");

        TextField regUser = new TextField();
        regUser.setPromptText("Username");
        regUser.getStyleClass().add("input-field");

        TextField regEmail = new TextField();
        regEmail.setPromptText("Email");
        regEmail.getStyleClass().add("input-field");

        PasswordField regPass = new PasswordField();
        regPass.setPromptText("Password");
        regPass.getStyleClass().add("input-field");

        Button btnConfirm = new Button("REGISTER");
        btnConfirm.getStyleClass().add("primary-btn");
        btnConfirm.setMaxWidth(Double.MAX_VALUE);

        Label regStatus = new Label("");
        regStatus.getStyleClass().add("error-label");

        btnConfirm.setOnAction(e -> {
            if (regUser.getText().isEmpty() || regPass.getText().isEmpty()) {
                regStatus.setText("Fields cannot be empty");
            } else if(!accounts.checkNewCustomerDetails(regUser.getText(), regPass.getText(), regEmail.getText()))
            {
                regStatus.setText("Fields don't meet requirements");
            }
            else {
                accounts.addCustomerAccount(regUser.getText(), regPass.getText(), regEmail.getText(), 0);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Account created!");
                alert.showAndWait();
                regStage.close();
            }
        });

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card-pane");
        card.getChildren().addAll(lblHeader, regUser, regEmail, regPass, btnConfirm, regStatus);

        StackPane root = new StackPane(card);
        Scene scene = new Scene(root, 400, 500);

        try {
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        } catch (Exception e) { e.printStackTrace(); }

        regStage.setScene(scene);
        regStage.showAndWait();
    }
}