package ci553.happyshop.client.login;

import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The class EmergencyExit used to immediately shut down the entire application.
 * It is a singleton with static access, instantiation is restricted.
 */
public class Login {
    private final int WIDTH = UIStyle.EmergencyExitWinWidth;
    private final int HEIGHT = UIStyle.EmergencyExitWinHeight;
    private static Login login;

    //used by Main class to get the single instance
    public static Login getLogin() {
        if (login == null)
            login = new Login();
        return login;
    }

    //Private constructor creates a shutdown window.
    //The window displays a single button with a shutdown image,positioned via `WinPosManager`,
    private Login() {
        ImageView ivLogin = new ImageView("ShutDown.jpg");
        ivLogin.setFitWidth(WIDTH-100);
        ivLogin.setFitHeight(WIDTH-100);
        ivLogin.setPreserveRatio(true);

        Button btnLogin = new Button();
        //btnExit.setGraphic(ivExit);
        btnLogin.setOnAction(event -> {
            System.out.println("Hiii");
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(btnLogin);

        borderPane.setStyle(UIStyle.rootStyle);
        Scene scene = new Scene(borderPane, WIDTH, HEIGHT);
        Stage window = new Stage();
        window.setScene(scene);
        window.setTitle("🛒 LOGIN");
        WinPosManager.registerWindow(window,WIDTH,HEIGHT); //calculate position x and y for this window
        window.show();
    }

}
