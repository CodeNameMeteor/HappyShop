package ci553.happyshop.client.customer;

import ci553.happyshop.catalogue.Order;
import ci553.happyshop.catalogue.Product;
import ci553.happyshop.orderManagement.OrderHub;
import ci553.happyshop.storageAccess.DatabaseRW;
import ci553.happyshop.utility.ProductListFormatter;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerModel {
    public CustomerView cusView;
    public DatabaseRW databaseRW;

    private ArrayList<Product> searchResults = new ArrayList<>();
    private final ArrayList<Product> trolley = new ArrayList<>();

    private String displayTaTrolley = "";
    private String displayTaReceipt = "";

    void search() throws SQLException {
        String keyword = cusView.tfSearch.getText().trim();
        searchResults.clear();
        if (!keyword.isEmpty()) {
            ArrayList<Product> nameMatches = databaseRW.searchProduct(keyword);
            for (Product nameMatch : nameMatches) {
                // Avoid duplicates if ID search and Name search returned the same object
                boolean alreadyInList = false;
                for (Product existing : searchResults) {
                    if (existing.getProductId().equals(nameMatch.getProductId())) {
                        alreadyInList = true;
                        break;
                    }
                }
                if (!alreadyInList) {
                    searchResults.add(nameMatch);
                }
            }
            if (searchResults.isEmpty()) {
                System.out.println("No products found for: " + keyword);
            }
        } else {
            System.out.println("Please type a keyword.");
        }
        cusView.updateProductList(searchResults);
    }

    void addToTrolley() {
        Product selectedProduct = cusView.lvProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            if (selectedProduct.getStockQuantity() > 0) {
                if (trolley.contains(selectedProduct)) {
                    selectedProduct.incrementOrderedQuantity();
                    var productIndex = trolley.indexOf(selectedProduct);
                    trolley.set(productIndex, selectedProduct);
                } else {
                    trolley.add(selectedProduct);
                }
                trolley.sort(null);

                displayTaTrolley = ProductListFormatter.buildString(trolley);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Out of Stock");
                alert.setHeaderText("Item unavailable");
                alert.setContentText("Sorry, " + selectedProduct.getProductDescription() + " is currently out of stock.");
                alert.showAndWait();
            }
        } else {
            // Optional: User didn't select anything
        }

        displayTaReceipt = "";
        updateTrolleyAndReceiptView();
    }

    void checkOut() throws IOException, SQLException {
        if (!trolley.isEmpty()) {
            ArrayList<Product> groupedTrolley = groupProductsById(trolley);
            ArrayList<Product> insufficientProducts = databaseRW.purchaseStocks(groupedTrolley);

            if (insufficientProducts.isEmpty()) {
                OrderHub orderHub = OrderHub.getOrderHub();
                Order theOrder = orderHub.newOrder(trolley);
                trolley.clear();
                displayTaTrolley = "";
                displayTaReceipt = String.format(
                        "Order_ID: %s\nOrdered_Date_Time: %s\n%s",
                        theOrder.getOrderId(),
                        theOrder.getOrderedDateTime(),
                        ProductListFormatter.buildString(theOrder.getProductList())
                );
            } else {
                StringBuilder errorMsg = new StringBuilder();
                for (Product p : insufficientProducts) {
                    errorMsg.append(p.getProductId()).append(", ")
                            .append(p.getProductDescription()).append(" (Only ")
                            .append(p.getStockQuantity()).append(" available)\n");
                }

                for (Product p : insufficientProducts) {
                    trolley.removeIf(pt -> Objects.equals(pt.getProductId(), p.getProductId()));
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Insufficient Stock");
                alert.setContentText(errorMsg.toString());
                alert.showAndWait();

                if (trolley.isEmpty()) {
                    displayTaTrolley = "Your trolley is empty";
                } else {
                    displayTaTrolley = ProductListFormatter.buildString(trolley);
                }
            }
        } else {
            displayTaTrolley = "Your trolley is empty";
        }

        search();
        updateTrolleyAndReceiptView();
    }

    private ArrayList<Product> groupProductsById(ArrayList<Product> proList) {
        Map<String, Product> grouped = new HashMap<>();
        for (Product p : proList) {
            String id = p.getProductId();
            if (grouped.containsKey(id)) {
                Product existing = grouped.get(id);
                existing.setOrderedQuantity(existing.getOrderedQuantity() + p.getOrderedQuantity());
            } else {
                grouped.put(id, new Product(p.getProductId(), p.getProductDescription(),
                        p.getProductImageName(), p.getUnitPrice(), p.getStockQuantity()));
                Product existing = grouped.get(id);
                existing.setOrderedQuantity(p.getOrderedQuantity());
            }
        }
        return new ArrayList<>(grouped.values());
    }

    void cancel() {
        trolley.clear();
        displayTaTrolley = "";
        updateTrolleyAndReceiptView();
    }

    void closeReceipt() {
        displayTaReceipt = "";
        updateTrolleyAndReceiptView();
    }

    void updateTrolleyAndReceiptView() {
        cusView.updateTrolleyAndReceipt(displayTaTrolley, displayTaReceipt);
    }
}