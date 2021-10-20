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

public class ModifyPartController implements Initializable {

    private Inventory inv;
    private Part part;

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



    public ModifyPartController(Inventory inv, Part selectedPart) {
        this.inv = inv;
        part = selectedPart;
    }

    /**
     * Initializes the class
     * Determines the part type and inputs part data into the text fields on screen
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioButtonToggleGroup = new ToggleGroup();
        inHouse.setToggleGroup(radioButtonToggleGroup);
        outsourced.setToggleGroup(radioButtonToggleGroup);

        if (part instanceof InHouse) {
            InHouse inHouseSelectedPart = (InHouse) part;

            partMachineId.setText(String.valueOf(inHouseSelectedPart.getMachineId()));
            label.setText("Machine Id");
            inHouse.setSelected(true);

            partId.setText(String.valueOf(inHouseSelectedPart.getId()));
            partName.setText(inHouseSelectedPart.getName());
            partInv.setText(String.valueOf(inHouseSelectedPart.getStock()));
            partPrice.setText(String.valueOf(inHouseSelectedPart.getPrice()));
            partMax.setText(String.valueOf(inHouseSelectedPart.getMax()));
            partMin.setText(String.valueOf(inHouseSelectedPart.getMin()));
        }

        if(part instanceof Outsourced){
            Outsourced outsourcedSelectedPart = (Outsourced) part;

            partMachineId.setText(outsourcedSelectedPart.getCompanyName());
            label.setText("Company Name");
            outsourced.setSelected(true);

            partId.setText(String.valueOf(outsourcedSelectedPart.getId()));
            partName.setText(outsourcedSelectedPart.getName());
            partInv.setText(String.valueOf(outsourcedSelectedPart.getStock()));
            partPrice.setText(String.valueOf(outsourcedSelectedPart.getPrice()));
            partMax.setText(String.valueOf(outsourcedSelectedPart.getMax()));
            partMin.setText(String.valueOf(outsourcedSelectedPart.getMin()));
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
    }

    /**
     * Creates a InHouse or Outsourced part depending on the selected radio button
     * Part within inventory is updated and reflected within the tableview
     */
    public void SaveButton(ActionEvent event) throws IOException {
        InHouse newPartInHouse;
        Outsourced newPartOutsourced;

        Integer id = Integer.valueOf(partId.getText());
        String name = partName.getText().toString();
        Double price = Double.parseDouble(partPrice.getText());
        Integer stock = Integer.parseInt(partInv.getText());
        Integer min = Integer.parseInt(partMin.getText());
        Integer max = Integer.parseInt(partMax.getText());
        Integer machineId;
        String companyName;
        if(min < max && stock >= min && stock <= max) {
            if (this.radioButtonToggleGroup.getSelectedToggle().equals(this.inHouse)) {
                machineId = Integer.parseInt(partMachineId.getText());
                newPartInHouse = new InHouse(id, name, price, stock, min, max, machineId);
                newPartInHouse.setMachineId(machineId);
                inv.updatePart(part.getId() - 1, newPartInHouse);
            }
            if (this.radioButtonToggleGroup.getSelectedToggle().equals(this.outsourced)) {
                companyName = partMachineId.getText().toString();
                newPartOutsourced = new Outsourced(id, name, price, stock, min, max, companyName);
                newPartOutsourced.setCompanyName(companyName);
                inv.updatePart(part.getId() - 1, newPartOutsourced);
            }
            ChangeScene(event);
        }else{
            AlertBox.displayAlert("Invalid input", "Min should be less than Max; and Inv should be between those two values.");
        }
    }
}
