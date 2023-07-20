package com.example.demo;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**This class allows a user to modify a part already in the system.
 * @author Aubrey Quintana
 */
public class ModifyPart {

    Stage stage;
    Parent scene;

    @FXML
    private Label modPartIdOrName;
    @FXML
    private RadioButton modPartInRBtn;
    @FXML
    private RadioButton modPartOutRBtn;
    @FXML
    private TextField modPartIdTxt;
    @FXML
    private TextField modPartNameTxt;
    @FXML
    private TextField modPartInvTxt;
    @FXML
    private TextField modPartPriceTxt;
    @FXML
    private TextField modPartMaxTxt;
    @FXML
    private TextField modPartMinTxt;
    @FXML
    private TextField modPartMachTxt;
    private static int selectedPartIndex;

    /**
     * User can save info for modifying an existing part. This method allows a user to edit any part details and override the data as a new object.
     * @param event Event occurs when clicking the Save button.
     * @throws IOException Throws when problems saving the data or loading the Main Screen page.
     */
    @FXML
    protected void OnActionSaveModPart(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modPartIdTxt.getText());
            String name = modPartNameTxt.getText();
            int stock = Integer.parseInt(modPartInvTxt.getText());
            double price = Double.parseDouble(modPartPriceTxt.getText());
            int min = Integer.parseInt(modPartMinTxt.getText());
            int max = Integer.parseInt(modPartMaxTxt.getText());

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


            if (modPartInRBtn.isSelected()) {
                int machineId = Integer.parseInt(modPartMachTxt.getText());
                Inventory.updatePart(selectedPartIndex, new InHouse(id, name, stock, price, min, max, machineId));
            } else if (modPartOutRBtn.isSelected()) {
                String companyName = modPartMachTxt.getText();
                Inventory.updatePart(selectedPartIndex, new OutSourced(id, name, stock, price, min, max, companyName));
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for each text field");
            alert.showAndWait();
        }

    }

    /**
     * User can go back to main screen.
     * @param event Event occurs when clicking the Cancel button.
     * @throws IOException Throws when problems loading the Main Screen page.
     */
    @FXML
    protected void OnActionCancelModPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * User can choose In House radio button. This changes the text field label to Machine Id.
     * @param event Event occurs when choosing the In House radio button.
     */
    @FXML
    protected void onActionInHouseRBtn(ActionEvent event) {
        modPartIdOrName.setText("Machine Id");
    }

    /**
     * User can choose Outsourced radio button. This changes the text field label to Company name.
     * @param event Event occurs when choosing the Outsourced radio button.
     */
    @FXML
    protected void onActionOutsourcedRBtn(ActionEvent event) {
        modPartIdOrName.setText("Company Name");
    }

    /**
     * This method gets the part details of the part the user selected to populate in the text fields on the modify part page so the user can modify as need be.
     * @param selectedPart Users selects a part from the table on the Main Screen.
     */
    @FXML
    protected void sendPartData(Part selectedPart) {
            selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);
            if (selectedPart instanceof OutSourced) {
                modPartOutRBtn.setSelected(true);
                modPartIdOrName.setText("Company Name");
                modPartMachTxt.setText(String.valueOf(((OutSourced)selectedPart).getCompanyName()));

            } else {
                modPartInRBtn.setSelected(true);
                modPartIdOrName.setText("Machine ID");
                modPartMachTxt.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
            }
        modPartIdTxt.setText(String.valueOf(selectedPart.getId()));
        modPartNameTxt.setText(selectedPart.getName());
        modPartInvTxt.setText(String.valueOf(selectedPart.getStock()));
        modPartPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        modPartMinTxt.setText(String.valueOf(selectedPart.getMin()));
        modPartMaxTxt.setText(String.valueOf(selectedPart.getMax()));
    }


}
