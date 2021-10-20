package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    private Inventory inv;
    private Product product;

    public AddProductController(Inventory inv) {
        this.inv = inv;
    }


    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField productInv;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;

    @FXML private TextField partSearch;
    @FXML private TableView<Part> partsTable;
    @FXML private TableView<Part> partsAssociatedTable;

    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInvLevelCol;
    @FXML private TableColumn<Part, Double> partPriceCol;

    @FXML private TableColumn<Part, Integer> partAssociatedIdCol;
    @FXML private TableColumn<Part, String> partAssociatedNameCol;
    @FXML private TableColumn<Part, Integer> partAssociatedInvLevelCol;
    @FXML private TableColumn<Part, Double> partAssociatedPriceCol;


    private ObservableList<Part> partInventory = FXCollections.observableArrayList();

    static int numOfProducts = 1;

    /**
     * Initializes the class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        product = new Product(numOfProducts, "test", 1,1,1,1);
        generatePartsTable();
        generatePartsAssociatedTable();

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
     * Assigns associated parts of the product to the parts associated table view
     */
    private void generatePartsAssociatedTable() {
        partAssociatedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partAssociatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partAssociatedInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsAssociatedTable.setItems(product.getAllAssociatedParts());
        partsAssociatedTable.refresh();

    }

    /**
     * Adds associated parts to the associated parts table view and removes the part from the parts table view
     */
    public void AddAssociatedPart(ActionEvent event){
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        product.addAssociatedPart(selectedPart);
        partsTable.getItems().remove(selectedPart);

    }

    /**
     * Removes associated parts from the associated parts table view and adds the part to the parts table view
     */
    public void RemoveAssociatedPart(ActionEvent event){
        Part selectedPart = partsAssociatedTable.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(selectedPart);
        partsTable.getItems().add(selectedPart);
        AlertBox.displayAlert("Removal Confirmation", "Part has been removed.");
    }
    /**
     * Locates a part with a String or Integer and selects the part within the table view
     */
    public void searchHandler(ActionEvent event){
        String searchField = partSearch.getText();
        for(Part p: partInventory){
            if(p.getName().toLowerCase().contains(searchField.toLowerCase()) || (Integer.toString(p.getId()).equals(searchField))){
                partsTable.getSelectionModel().select(p);
            }
        }

    }

    /**
     * Creates a product from the input of the user and adds the product to the inventory
     */
    public void SaveButton(ActionEvent event) throws IOException {
        int min = Integer.parseInt(productMin.getText());
        int max = Integer.parseInt(productMax.getText());
        int stock = Integer.parseInt(productInv.getText());

        product.setName(productName.getText());
        product.setStock(Integer.parseInt(productInv.getText()));
        product.setPrice(Double.parseDouble(productPrice.getText()));
        product.setMin(Integer.parseInt(productMin.getText()));
        product.setMax(Integer.parseInt(productMax.getText()));

        if(min < max && stock >= min && stock <= max) {
            inv.addProduct(product);
            numOfProducts++;
            ChangeScene(event);
        }else{
            AlertBox.displayAlert("Invalid input", "Min should be less than Max; and Inv should be between those two values.");
        }
    }

    /**
     * Returns to main menu of application
     */
    public void ChangeScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        View_Controller.MainScreenController controller = new View_Controller.MainScreenController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

}
