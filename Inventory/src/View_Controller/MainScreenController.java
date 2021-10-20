package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class MainScreenController implements Initializable{


    Inventory inv;

    @FXML private TextField partSearch;
    @FXML private TextField productSearch;
    @FXML private TableView<Part> partsTable;
    @FXML private TableView<Product> productsTable;

    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLevelCol;
    @FXML private TableColumn<Part, Double> partPriceCol;

    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInvLevelCol;
    @FXML private TableColumn<Product, Integer> productPriceCol;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();

    private Part selectedPart;
    private Product selectedProduct;

    public MainScreenController(Inventory inv){
        this.inv = inv;
    }

    /**
     * Initializes the class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generatePartsTable();
        generateProductsTable();
    }

    /**
     * Assigns products to the product table view
     */
    private void generateProductsTable() {
        productInventory.setAll(inv.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(productInventory);
        productsTable.refresh();
    }

    /**
     * Assigns parts to the parts table view
     */
    private void generatePartsTable() {
        partInventory.setAll(inv.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTable.setItems(partInventory);
        partsTable.refresh();
    }


    /**
     * Changes scene to allow user to add a part
     */
    public void ChangeWindowAddPart(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddPart.fxml"));
        /**
         * RUNTIME ERROR
         * As I was beginning to figure out scene changes,
         * I was not providing the newly created controllers any access to the initial inventory.
         * Once, I passed in the inventory, the runtime error was resolved.
         */
        View_Controller.AddPartController controller = new View_Controller.AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     * Changes scene to allow user to modify a part
     */
    public void ChangeWindowModifyPart(ActionEvent event) throws IOException{
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPart.fxml"));
        View_Controller.ModifyPartController controller = new View_Controller.ModifyPartController(inv, selectedPart);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     * Changes scene to allow user to add a product
     */
    public void ChangeWindowAddProduct(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/AddProduct.fxml"));
        View_Controller.AddProductController controller = new View_Controller.AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     * Changes scene to allow user to modify a part
     */
    public void ChangeWindowModifyProduct(ActionEvent event) throws IOException{
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
        View_Controller.ModifyProductController controller = new View_Controller.ModifyProductController(inv, selectedProduct);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     * Deletes a part within the part table view
     */
    public void DeletePartButton(ActionEvent event){
        selectedPart = partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        generatePartsTable();
        AlertBox.displayAlert("Deletion Confirmation", "Part has been deleted.");

    }

    /**
     * Deletes a product within the product table view
     */
    public void DeleteProductButton(ActionEvent event){

        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct.getAllAssociatedParts().size() == 0) {
            Inventory.deleteProduct(selectedProduct);
            generateProductsTable();
            AlertBox.displayAlert("Deletion Confirmation", "Product has been deleted.");
        }else{
            AlertBox.displayAlert("Can't Delete Product", "Parts are associated to this product. Remove associated parts to remove product.");
        }
    }

    /**
     * Terminates the application
     */
    //Exit Button
    public void ExitButton(ActionEvent event) throws IOException {
        Platform.exit();

    }

    /**
     * Locates a part with a String or Integer and selects the part within the table view
     */
    public void PartSearchHandler(ActionEvent event){
        String searchField = partSearch.getText();
        for(Part p: partInventory){
            if(p.getName().toLowerCase().contains(searchField.toLowerCase()) || (Integer.toString(p.getId()).equals(searchField))){
                partsTable.getSelectionModel().select(p);
            }
        }
    }
    /**
     * Locates a product with a String or Integer and selects the product within the table view
     */
    public void ProductSearchHandler(ActionEvent event){
        String searchField = productSearch.getText();
        for(Product p: productInventory){
            if(p.getName().toLowerCase().contains(searchField.toLowerCase()) || (Integer.toString(p.getId()).equals(searchField))){
                productsTable.getSelectionModel().select(p);
            }
        }
    }

}
