package ci553.happyshop.client.login;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * the class login is used to show the login and registration window
 * enabling users to login into their accounts.
 */
public class Login {

    //tracks if a login was a success
    public interface LoginListener {
        void onLoginSuccess(String accountType);
    }

    //Store the instance of the window
    private static Login loginInstance;
    private Stage window;
    private final CustomerAccounts accounts; //store the accounts

    //
    public static void showLogin(CustomerAccounts accounts, LoginListener listener) {
        if (loginInstance == null) {
            //starts the login instance
            loginInstance = new Login(accounts, listener);
        } else {
            //shows the window if there is an existing login instance.
            loginInstance.window.show();
        }
    }

    private Login(CustomerAccounts accounts, LoginListener listener) {
        this.accounts = accounts;//store the accounts

        //Create a header text for the login in window
        Label lblHeader = new Label("Welcome Back");
        //get the styling from the css file
        lblHeader.getStyleClass().add("header-label");

        //creates a new text field for the username
        TextField tfUserName = new TextField();
        tfUserName.setPromptText("Username");
        tfUserName.getStyleClass().add("input-field");

        //creates a new text field for the password.
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");
        pfPassword.getStyleClass().add("input-field");

        //creates a login button.
        Button btnLogin = new Button("LOG IN");
        btnLogin.getStyleClass().add("primary-btn");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        //set it to the default so you a user can press enter and it activate
        btnLogin.setDefaultButton(true);

        //add a register account button
        Button btnRegister = new Button("Don't have an account? Sign up");
        btnRegister.getStyleClass().add("link-btn");

        //an empty label so we can provide error messages.
        Label statusLabel = new Label("");
        statusLabel.getStyleClass().add("error-label");

        btnLogin.setOnAction(event -> {
            //when login is pressed, pass the username and password to accounts
            if (accounts.login(tfUserName.getText(), pfPassword.getText())) {
                //close window
                window.close();
                //update the listener that login was successful
                listener.onLoginSuccess(accounts.getCurrentAccountType());
            } else {
                //Update label if username/password are invalid.
                statusLabel.setText("Invalid username or password");
            }
        });
        //open registration window when register button is pressed.
        btnRegister.setOnAction(event -> showRegistrationWindow());

        //create a vbox for the white input section
        VBox card = new VBox(15); // 15px spacing
        card.setAlignment(Pos.CENTER);
        card.getStyleClass().add("card-pane");
        card.getChildren().addAll(lblHeader, tfUserName, pfPassword, btnLogin, btnRegister, statusLabel);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-padding: 20;"); // Padding around the outside

        //create the root size
        Scene scene = new Scene(root, 400, 500);

        //load the css file
        try {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        } catch (Exception e) {
            System.out.println("Could not load CSS file. Make sure it is in the src folder.");
        }
        //initialize the window
        window = new Stage();
        window.setScene(scene);
        window.setTitle("HappyShop Login");
        window.show();
    }

    private void showRegistrationWindow() {
        //create the stage for the registration window
        Stage regStage = new Stage();
        regStage.initModality(Modality.APPLICATION_MODAL);
        //set the title
        regStage.setTitle("HappyShop Create Account");
        //set the header label to create account
        Label lblHeader = new Label("Create Account");
        lblHeader.getStyleClass().add("header-label");
        //create a text field to input usernames
        TextField regUser = new TextField();
        regUser.setPromptText("Username");
        regUser.getStyleClass().add("input-field");
        //create a text field to input email addresses
        TextField regEmail = new TextField();
        regEmail.setPromptText("Email");
        regEmail.getStyleClass().add("input-field");
        //create a text field to input password
        PasswordField regPass = new PasswordField();
        regPass.setPromptText("Password");
        regPass.getStyleClass().add("input-field");
        //register button
        Button btnConfirm = new Button("REGISTER");
        btnConfirm.getStyleClass().add("primary-btn");
        btnConfirm.setMaxWidth(Double.MAX_VALUE);
        //error message label
        Label regStatus = new Label("");
        regStatus.getStyleClass().add("error-label");

        btnConfirm.setOnAction(e -> {
            if (regUser.getText().isEmpty() || regPass.getText().isEmpty()) {
                regStatus.setText("Fields cannot be empty");
            } else if (!accounts.checkNewCustomerDetails(regUser.getText(), regPass.getText(), regEmail.getText())) {
                //checks if the inputted fields meet our requirements
                //e.g. email must have @
                //password must be above a set length.
                regStatus.setText("Fields don't meet requirements");
            } else {
                //if the inputted details are fine, create an account with these details
                Account newCustomer = new CustomerAccount(
                        regUser.getText(),
                        regEmail.getText(),
                        regPass.getText(),
                        false
                );
                //adds the new account to the account list
                accounts.addAccount(newCustomer);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        regStage.setScene(scene);
        regStage.showAndWait();
    }
}