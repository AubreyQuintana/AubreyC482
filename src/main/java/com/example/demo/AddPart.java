package com.example.demo;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


/**This class allows a user to add a new part into the system.
 * @author Aubrey Quintana
 */
public class AddPart {

    Stage stage;
    Parent scene;

    @FXML
    private Label partIdOrName;
    @FXML
    private RadioButton addPartInRBtn;
    @FXML
    private RadioButton addPartOutRBtn;
    @FXML
    private TextField addPartIdTxt;
    @FXML
    private TextField addPartNameTxt;
    @FXML
    private TextField addPartInvTxt;
    @FXML
    private TextField addPartPriceTxt;
    @FXML
    private TextField addPartMaxTxt;
    @FXML
    private TextField addPartMinTxt;
    @FXML
    private TextField addPartMachTxt;

    /**
     * User can save info for adding a new part. This method allows a user to input part details into text fields and save the data as a new object.
     * @param event Event occurs when clicking the save button.
     * @throws IOException Throws when problems saving the data or loading the Main Screen page.
     */
    @FXML
    protected void OnActionSavePart(ActionEvent event) throws IOException {

        try {
            int id = Inventory.partCounter++;
            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addPartInvTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());

            if (stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Your inventory must be equal or less than the maximum and it must be greater than or equal to your minimum value.");
                alert.showAndWait();
                return;
            }

            if(min < 1 || max < min){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Maximum must be greater than minimum, and minimum must be greater than 0.");
                alert.showAndWait();
                return;
            }

            if (addPartInRBtn.isSelected()) {
                int machineId = Integer.parseInt(addPartMachTxt.getText());
                Inventory.addPart(new InHouse(id, name, stock, price, min, max, machineId));
            } else if (addPartOutRBtn.isSelected()) {
                String companyName = addPartMachTxt.getText();
                Inventory.addPart(new OutSourced(id, name, stock, price, min, max, companyName));
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        }

    }

    /**
     * User can go back to main screen.
     * @param event Event occurs when clicking the cancel button.
     * @throws IOException Throws when problems loading the Main Screen page.
     */
    @FXML
    protected void OnActionCancelPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * User can choose In House radio button. This changes the text field label to Machine ID.
     * @param event Event occurs when choosing the In House radio button.
     */
    @FXML
    protected void onActionInHouseRBtn(ActionEvent event) {
        partIdOrName.setText("Machine Id");
    }

    /**
     * User can choose Outsourced radio button. This changes the text field label to Company name.
     * @param event Event occurs when choosing the Outsourced radio button.
     */
    @FXML
    protected void onActionOutsourcedRBtn(ActionEvent event) {
        partIdOrName.setText("Company Name");
    }
}
