package ci553.happyshop.client.login;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The class EmergencyExit used to immediately shut down the entire application.
 * It is a singleton with static access, instantiation is restricted.
 */
public class Login {
    private static Login login;
    TextField tfUserName; //for user input on the search page. Made accessible so it can be accessed or modified by CustomerModel
    TextField tfPassword;
    VBox mainLayout;
    //used by Main class to get the single instance
    public static Login getLogin(customerAccounts Accounts) {
        if (login == null)
            login = new Login(Accounts);
        return login;
    }

    //Private constructor creates a shutdown window.
    //The window displays a single button with a shutdown image,positioned via `WinPosManager`,
    private Login(customerAccounts Accounts) {
        // --- Image Setup ---
        //ImageView ivLogin = new ImageView("ShutDown.jpg");
        //ivLogin.setFitWidth(WIDTH - 100);
        //ivLogin.setFitHeight(WIDTH - 100); // Assuming you want square aspect based on width
        //ivLogin.setPreserveRatio(true);

        // --- Username Row (HBox) ---
        Label laUserName = new Label("Username:");
        // laUserName.setStyle(UIStyle.labelStyle); // Uncomment if style exists

        tfUserName = new TextField();
        tfUserName.setPromptText("Enter Your Username");
        tfUserName.setStyle(UIStyle.textFiledStyle);

        // Create HBox for Username
        HBox hbName = new HBox(10); // 10 is the spacing between Label and TextField
        hbName.setAlignment(Pos.CENTER); // Center items within the HBox
        hbName.getChildren().addAll(laUserName, tfUserName);

        // --- Password Row (HBox) ---
        Label laPassword = new Label("Password:");
        // laPassword.setStyle(UIStyle.labelStyle); // Uncomment if style exists

        tfPassword = new TextField(); // Use PasswordField to hide characters
        tfPassword.setPromptText("Enter Your Password");
        tfPassword.setStyle(UIStyle.textFiledStyle);

        // Create HBox for Password
        HBox hbPassword = new HBox(10);
        hbPassword.setAlignment(Pos.CENTER);
        hbPassword.getChildren().addAll(laPassword, tfPassword);

        // --- Login Button ---
        Button btnLogin = new Button("Login"); // Added text so button is visible
        btnLogin.setOnAction(event -> {
            System.out.println("Login Clicked: " + tfUserName.getText());
            if(Accounts.login(tfUserName.getText(),tfPassword.getText()))
            {
                System.out.println("Logged into account: " + tfUserName.getText());
            }
        });

        // --- Main Layout (VBox) ---
        // VBox holds the Image, Name Row, Password Row, and Button vertically
        VBox mainLayout = new VBox(20); // 20 is vertical spacing between rows
        mainLayout.setAlignment(Pos.CENTER); // Center everything in the window
        mainLayout.getChildren().addAll(hbName, hbPassword, btnLogin);

        // --- Root Pane ---
        BorderPane borderPane = new BorderPane();
        // Set the VBox as the center of the BorderPane
        borderPane.setCenter(mainLayout);

        // Use your existing style
        // borderPane.setStyle(UIStyle.rootStyle);

        int WIDTH = UIStyle.EmergencyExitWinWidth;
        int HEIGHT = UIStyle.EmergencyExitWinHeight;
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        Stage window = new Stage();
        window.setScene(scene);
        window.setTitle("🛒 LOGIN");

        WinPosManager.registerWindow(window, WIDTH, HEIGHT);
        window.show();
    }

}
