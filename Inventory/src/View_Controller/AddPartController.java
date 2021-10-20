package View_Controller;


import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    private Inventory inv;
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;
    private ToggleGroup radioButtonToggleGroup;


    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;

    @FXML private Label label;
    @FXML private TextField partMachineId;

    static int numOfParts = 1;

    public AddPartController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Initializes the class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonToggleGroup = new ToggleGroup();
        this.inHouse.setToggleGroup((radioButtonToggleGroup));
        this.outsourced.setToggleGroup(radioButtonToggleGroup);
    }

    /**
     * Determines if part is InHouse or Outsourced
     * Creates the part with the text input from user
     * Adds part to the inventory
     */
    public void SaveButton(ActionEvent event) throws IOException {
        String name = partName.getText();

        Double price = Double.parseDouble(partPrice.getText());
        Integer stock = Integer.parseInt(partInv.getText());
        Integer min = Integer.parseInt(partMin.getText());
        Integer max = Integer.parseInt(partMax.getText());
        Integer machineId;
        String companyName;

        if(min < max && stock >= min && stock <= max) {
            if (this.radioButtonToggleGroup.getSelectedToggle().equals(this.inHouse)) {
                machineId = Integer.parseInt(partMachineId.getText());
                InHouse newPart = new InHouse(numOfParts, name, price, stock, min, max, machineId);
                newPart.setMachineId(machineId);
                inv.addPart(newPart);
            }
            if (this.radioButtonToggleGroup.getSelectedToggle().equals(this.outsourced)) {
                companyName = partMachineId.getText().toString();
                Outsourced newPart = new Outsourced(numOfParts, name, price, stock, min, max, companyName);
                newPart.setCompanyName(companyName);
                inv.addPart(newPart);
            }
            numOfParts++;
            ChangeScene(event);
        }else{
            AlertBox.displayAlert("Invalid input", "Min should be less than Max; and Inv should be between those two values.");
        }
    }

    /**
     * Changes a label dependent on which radio button is selected
     */
    public void radioButtonChanged(){
        if(this.radioButtonToggleGroup.getSelectedToggle().equals(this.inHouse)){
            label.setText("Machine ID");
        }
        if(this.radioButtonToggleGroup.getSelectedToggle().equals(this.outsourced)){
            label.setText("Company Name");
        }
        partId.setText(String.valueOf(numOfParts));
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
