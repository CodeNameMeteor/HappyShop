package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Product;
import ci553.happyshop.utility.StorageLocation;
import ci553.happyshop.utility.UIStyle;
import ci553.happyshop.utility.WinPosManager;
import ci553.happyshop.utility.WindowBounds;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerView  {
    public CustomerController cusController;

    private final int WIDTH = UIStyle.customerWinWidth;
    private final int HEIGHT = UIStyle.customerWinHeight;
    private final int COLUMN_WIDTH = WIDTH / 2 - 10;

    private HBox hbRoot;
    private VBox vbTrolleyPage;
    private VBox vbReceiptPage;

    // --- CHANGED: Single Search Field ---
    public TextField tfSearch;

    // List Components
    ListView<Product> lvProducts;
    ObservableList<Product> obsProList;

    private TextArea taTrolley;
    private TextArea taReceipt;

    private Stage viewWindow;

    public void start(Stage window) {
        VBox vbSearchPage = createSearchPage();
        vbTrolleyPage = CreateTrolleyPage();
        vbReceiptPage = createReceiptPage();

        Line line = new Line(0, 0, 0, HEIGHT);
        line.setStrokeWidth(4);
        line.setStroke(Color.PINK);
        VBox lineContainer = new VBox(line);
        lineContainer.setPrefWidth(4);
        lineContainer.setAlignment(Pos.CENTER);

        hbRoot = new HBox(10, vbSearchPage, lineContainer, vbTrolleyPage);
        hbRoot.setAlignment(Pos.CENTER);
        hbRoot.setStyle(UIStyle.rootStyle);

        Scene scene = new Scene(hbRoot, WIDTH, HEIGHT);
        window.setScene(scene);
        window.setTitle("🛒 HappyShop Customer Client");
        WinPosManager.registerWindow(window, WIDTH, HEIGHT);
        window.show();
        viewWindow = window;
    }

    private VBox createSearchPage() {
        Label laPageTitle = new Label("Search Products");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        // --- NEW: Single Search Bar Layout ---
        tfSearch = new TextField();
        tfSearch.setPromptText("Enter Product Name or ID...");
        tfSearch.setStyle(UIStyle.textFiledStyle);
        // Allow pressing "Enter" to trigger search
        tfSearch.setOnAction(e -> {
            try { cusController.doAction("Search"); }
            catch (Exception ex) { ex.printStackTrace(); }
        });

        Button btnSearch = new Button("\uD83D\uDD0D");
        btnSearch.setDefaultButton(true);
        btnSearch.setStyle(UIStyle.buttonStyle);
        btnSearch.setOnAction(this::buttonClicked);

        HBox hbSearchBar = new HBox(10, tfSearch, btnSearch);
        hbSearchBar.setAlignment(Pos.CENTER);

        // Add to Trolley Button
        Button btnAddToTrolley = new Button("Add to Trolley");
        btnAddToTrolley.setStyle(UIStyle.buttonStyle);
        btnAddToTrolley.setOnAction(this::buttonClicked);

        // --- LIST VIEW SETUP ---
        obsProList = FXCollections.observableArrayList();
        lvProducts = new ListView<>(obsProList);

        // CHANGED: Increased height significantly as we removed previous input rows
        lvProducts.setPrefHeight(HEIGHT - 150);
        lvProducts.setFixedCellSize(60);
        lvProducts.setStyle(UIStyle.listViewStyle);

        lvProducts.setCellFactory(param -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);

                if (empty || product == null) {
                    setGraphic(null);
                } else {
                    String imageName = product.getProductImageName();
                    String relativeImageUrl = StorageLocation.imageFolder + imageName;
                    Path imageFullPath = Paths.get(relativeImageUrl).toAbsolutePath();
                    String imageFullUri = imageFullPath.toUri().toString();

                    ImageView ivPro;
                    try {
                        ivPro = new ImageView(new Image(imageFullUri, 60, 55, true, true));
                    } catch (Exception e) {
                        ivPro = new ImageView(new Image("imageHolder.jpg", 60, 55, true, true));
                    }

                    String stockStatus = product.getStockQuantity() > 0
                            ? "Stock: " + product.getStockQuantity()
                            : "OUT OF STOCK";

                    String info = String.format("%s - £%.2f\n%s",
                            product.getProductDescription(),
                            product.getUnitPrice(),
                            stockStatus);

                    Label laInfo = new Label(info);
                    laInfo.setStyle("-fx-font-weight: bold;");

                    HBox hbox = new HBox(10, ivPro, laInfo);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    setGraphic(hbox);
                }
            }
        });

        // Simplified VBox
        VBox vbSearchPage = new VBox(15, laPageTitle, hbSearchBar, btnAddToTrolley, lvProducts);
        vbSearchPage.setPrefWidth(COLUMN_WIDTH);
        vbSearchPage.setAlignment(Pos.TOP_CENTER);
        vbSearchPage.setStyle("-fx-padding: 15px;");

        return vbSearchPage;
    }

    private VBox CreateTrolleyPage() {
        Label laPageTitle = new Label("🛒🛒  Trolley 🛒🛒");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        taTrolley = new TextArea();
        taTrolley.setEditable(false);
        taTrolley.setPrefSize((double) WIDTH /2, HEIGHT-50);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(this::buttonClicked);
        btnCancel.setStyle(UIStyle.buttonStyle);

        Button btnCheckout = new Button("Check Out");
        btnCheckout.setOnAction(this::buttonClicked);
        btnCheckout.setStyle(UIStyle.buttonStyle);

        HBox hbBtns = new HBox(10, btnCancel, btnCheckout);
        hbBtns.setStyle("-fx-padding: 15px;");
        hbBtns.setAlignment(Pos.CENTER);

        vbTrolleyPage = new VBox(15, laPageTitle, taTrolley, hbBtns);
        vbTrolleyPage.setPrefWidth(COLUMN_WIDTH);
        vbTrolleyPage.setAlignment(Pos.TOP_CENTER);
        vbTrolleyPage.setStyle("-fx-padding: 15px;");
        return vbTrolleyPage;
    }

    private VBox createReceiptPage() {
        Label laPageTitle = new Label("Receipt");
        laPageTitle.setStyle(UIStyle.labelTitleStyle);

        taReceipt = new TextArea();
        taReceipt.setEditable(false);
        taReceipt.setPrefSize((double) WIDTH /2, HEIGHT-50);

        Button btnCloseReceipt = new Button("OK & Close");
        btnCloseReceipt.setStyle(UIStyle.buttonStyle);
        btnCloseReceipt.setOnAction(this::buttonClicked);

        vbReceiptPage = new VBox(15, laPageTitle, taReceipt, btnCloseReceipt);
        vbReceiptPage.setPrefWidth(COLUMN_WIDTH);
        vbReceiptPage.setAlignment(Pos.TOP_CENTER);
        vbReceiptPage.setStyle(UIStyle.rootStyleYellow);
        return vbReceiptPage;
    }

    private void buttonClicked(ActionEvent event) {
        try{
            Button btn = (Button)event.getSource();
            String action = btn.getText();
            // Handle slight text variations
            if(action.contains("Add to Trolley")){
                action = "Add to Trolley";
                showTrolleyOrReceiptPage(vbTrolleyPage);
            }
            if(action.equals("OK & Close")){
                showTrolleyOrReceiptPage(vbTrolleyPage);
            }
            cusController.doAction(action);
        }
        catch(SQLException | IOException e){
            e.printStackTrace();
        }
    }

    public void updateProductList(ArrayList<Product> searchResults) {
        obsProList.clear();
        obsProList.addAll(searchResults);
    }

    public void updateTrolleyAndReceipt(String trolley, String receipt) {
        taTrolley.setText(trolley);
        if (!receipt.equals("")) {
            showTrolleyOrReceiptPage(vbReceiptPage);
            taReceipt.setText(receipt);
        }
    }

    private void showTrolleyOrReceiptPage(Node pageToShow) {
        int lastIndex = hbRoot.getChildren().size() - 1;
        if (lastIndex >= 0) {
            hbRoot.getChildren().set(lastIndex, pageToShow);
        }
    }

    WindowBounds getWindowBounds() {
        return new WindowBounds(viewWindow.getX(), viewWindow.getY(),
                viewWindow.getWidth(), viewWindow.getHeight());
    }
}