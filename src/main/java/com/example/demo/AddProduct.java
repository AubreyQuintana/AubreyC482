package com.example.demo;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class allows a user to add a new product into the system
 * @author Aubrey Quintana
 */
public class AddProduct implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTableView1;
    @FXML
    private TableColumn<Part, Integer> addIdCol1;
    @FXML
    private TableColumn<Part, String> addNameCol1;
    @FXML
    private TableColumn<Part, Integer> addInvCol1;
    @FXML
    private TableColumn<Part, Double> addPriceCol1;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> addIdCol2;
    @FXML
    private TableColumn<Part, String> addNameCol2;
    @FXML
    private TableColumn<Part, Integer> addInvCol2;
    @FXML
    private TableColumn<Part, Double> addPriceCol2;
    @FXML
    private TextField addProdNameTxt;
    @FXML
    private TextField addProdInvTxt;
    @FXML
    private TextField addProdPriceTxt;
    @FXML
    private TextField addProdMaxTxt;
    @FXML
    private TextField addProdMinTxt;
    @FXML
    private TextField addProdSearchTxt;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This method is used to initialize any data that needs to be run first.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partsTableView1.setItems(Inventory.getAllParts());

        addIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        addNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedParts);

        addIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        addNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * User can save info for adding a new product. This method allows a user to input product details into text fields and save the data as a new object.
     *
     * @param event Event occurs when clicking the Save button.
     * @throws IOException Throws when problems saving data or loading the Main Screen page.
     */
    @FXML
    protected void onActionSaveProd(ActionEvent event) throws IOException {
        try {
            int id = Inventory.partCounter++;
            String name = addProdNameTxt.getText();
            int stock = Integer.parseInt(addProdInvTxt.getText());
            double price = Double.parseDouble(addProdPriceTxt.getText());
            int min = Integer.parseInt(addProdMinTxt.getText());
            int max = Integer.parseInt(addProdMaxTxt.getText());

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Your inventory must be equal or less than the maximum and it must be greater than or equal to your minimum value.");
                alert.showAndWait();
                return;
            }

            if (min < 1 || max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Maximum must be greater than minimum, and minimum must be greater than 0.");
                alert.showAndWait();
                return;
            }

            Product newP = new Product(id, name, stock, price, min, max);
            for (Part part : associatedParts) {
                newP.addAssociatedPart(part);
            }

            Inventory.addProduct(newP);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please enter valid value for text field");
            alert.showAndWait();
        }
    }

    /**
     * User can go back to main screen.
     *
     * @param event Event occurs when clicking the Cancel button.
     * @throws IOException Throws when problems loading the Main Screen.
     */
    @FXML
    protected void OnActionCancelProd(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * User can search for a part. This method allows a user to search for a part either by name or part id by using the search box.
     *
     * @param event Event occurs when user enters data into the search box and presses enter.
     */
    @FXML
    protected void OnActionProdSearch(ActionEvent event) {
        String partName = addProdSearchTxt.getText();
        if (partName.isBlank()) {
            partsTableView1.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Part part = Inventory.lookupPart(Integer.parseInt(partName));
            if (part != null) {
                partsTableView1.getSelectionModel().select(part);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> searchPartList = Inventory.lookupPart(partName);
            if (!searchPartList.isEmpty()) {
                partsTableView1.setItems(searchPartList);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Not found! Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * User can add an associated part to the product. This method allows a user to select a part from the parts list and add it to an associated parts list referencing that product.
     *
     * @param event Event occurs when clicking the Add button.
     */
    @FXML
    protected void onActionAddProd(ActionEvent event) {
        Part selectedPart = partsTableView1.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a part from the list");
            alert.showAndWait();
        } else {
            associatedParts.add(selectedPart);
        }

    }

    /**
     * User can delete an associated part. This method allows a user to delete an associated part from the list.
     *
     * @param event Event occurs when clicking the Remove Associated Parts button.
     */
    @FXML
    protected void onActionRemoveProd(ActionEvent event) {
        Part selectedAssociatedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();

        if (selectedAssociatedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a part from the list");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm deletion");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedAssociatedPart);
            }
        }
    }
}
