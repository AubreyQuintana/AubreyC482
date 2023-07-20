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

/** This class is your Main Screen. It is the starting point for the user to interact with the software.
 * @author Aubrey Quintana
 */
public class MainScreen implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TableView<Product> prodTableView;
    @FXML
    private TableColumn<Product, Integer> prodIdCol;
    @FXML
    private TableColumn<Product, String> prodNameCol;
    @FXML
    private TableColumn<Product, Integer> prodInvLvlCol;
    @FXML
    private TableColumn<Product, Double> prodPriceCol;
    @FXML
    private TextField searchPartIdTxt;
    @FXML
    private TextField searchProductIdTxt;

    /**This method is used to initialize any data that needs to be run first. */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        partsTableView.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTableView.setItems(Inventory.getAllProducts());

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * User can add a part. This method takes the user to a new window where they can add a part into the system.
     * @param event Event raised by clicking add part button.
     * @throws IOException Throws when problems loading the add part page.
     */
    @FXML
    protected void OnActionAddPartMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * User can modify a part. This method allows a user to select a part, go to the modify screen, and change any part details.
     * @param event Event raised when clicking the modify part button.
     * @throws IOException Throws when problems loading the modify part page.
     */
    @FXML
    protected void OnActionModPartMain(ActionEvent event) throws IOException {

            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPart.fxml"));

            if (selectedPart == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Please select a part from the list to modify");
                alert.showAndWait();
            } else {
                Parent parent = loader.load();
                ModifyPart modifyPart = loader.getController();
                modifyPart.sendPartData(selectedPart);
                Scene scene = new Scene(parent, 500, 600);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
    }

    /**
     * User can delete a part. This method allows a user to select a part and delete it from the table/list.
     * @param event Event occurs when clicking on delete part button.
     */
    @FXML
    protected void OnActionDelPartMain(ActionEvent event){

        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a part from the list to delete");
            alert.showAndWait();
        }
        else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm deletion");
                alert.setContentText("Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                }
        }
    }

    /**
     * User can add a product. This method takes the user to a new window where they can add a product into the system.
     * @param event Event raised by clicking add product button
     * @throws IOException Thrown when problems loading the add product form
     */
    @FXML
    protected void OnActionAddProdMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * User can modify a product. This method allows a user to select a product, go to the modify screen, and change any product details.
     * @param event Event occurs when clicking modify product button.
     * @throws IOException Throws when problems loading modify product page.
     */
    @FXML
    protected void OnActionModProdMain(ActionEvent event) throws IOException {

        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a product from the list to modify");
            alert.showAndWait();
        } else {
            Parent parent = loader.load();
            ModifyProduct modifyProduct = loader.getController();
            modifyProduct.sendProductData(selectedProduct);
            Scene scene  = new Scene(parent, 1000, 800);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * User can delete a product. This method allows a user to select a product and delete it from the table/list.
     * Runtime Error : I was deleting product with associated parts.
     * I resolved it by adding a check to confirm whether a product had associated parts.
     * Also added a check to confirm that the user wanted to delete the product.
     * @param event Event occurs when clicking delete product button.
     */
    @FXML
    protected void OnActionDelProdMain(ActionEvent event){
        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Please select a product from the list to delete");
            alert.showAndWait();
        }
        else {
            if(selectedProduct.getAllAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm deletion");
                alert.setContentText("Are you sure you want to delete this product?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                   Inventory.deleteProduct(selectedProduct);
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("This product has associated parts and cannot be deleted.");
                alert.showAndWait();
            }
        }
    }

    /**
     * User can search for a part. This method allows a user to search for a part either by name or part id by using the search box.
     * @param event Event occurs when user enters data into the search box and presses enter.
     */
    @FXML
    protected void OnActionSearchPart(ActionEvent event) {

        String partName = searchPartIdTxt.getText();
        if (partName.isBlank()) {
            partsTableView.setItems(Inventory.getAllParts());
            return;
        }

        try {
            Part part = Inventory.lookupPart(Integer.parseInt(partName));
            if(part != null){
                partsTableView.getSelectionModel().select(part);
            }
            else{
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Part> searchPartList = Inventory.lookupPart(partName);
            if (!searchPartList.isEmpty()) {
                partsTableView.setItems(searchPartList);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error dialog");
                alert.setContentText("Not found! Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * User can search for a product. This method allows a user to search for a product either by name or product id by using the search box.
     * @param event Event occurs when user enters data into the search box and presses enter.
     */
    @FXML
    protected void OnActionSearchProduct(ActionEvent event) {

        String productName = searchProductIdTxt.getText();
        if (productName.isBlank()){
            prodTableView.setItems(Inventory.getAllProducts());
            return;
        }
        try {
            Product product = Inventory.lookupProduct(Integer.parseInt(productName));
            if(product != null){
                prodTableView.getSelectionModel().select(product);
            }
            else{
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            ObservableList<Product> searchProductList = Inventory.lookupProduct(productName);
            if (!searchProductList.isEmpty()) {
            prodTableView.setItems(searchProductList);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setContentText("Not found! Please try again.");
            alert.showAndWait();
            }
        }
    }

    /**
     * User can exit the program. This method allows the user to click the exit button and close the program.
     * @param event Event occurs when clicking exit button.
     */
    @FXML
    protected void OnActionExit(ActionEvent event){

            System.exit(0);

    }



}