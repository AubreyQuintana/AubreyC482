package com.example.demo;

import Model.InHouse;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class runs the start of my software.
 * Future Enhancement : In the future it could be beneficial to the user to add pictures of the individual parts and products for comparison in verifying the correct orders and for taking inventory.
 * Javadoc folder is located in IdeaProjects folder next to my IntelliJ folder containing my project.
 * @author Aubrey Quintana
 */
public class HelloApplication extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 600);
        stage.setTitle("Aubrey Quintana PA");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the main method. This is the first method that gets called when you first run your Java program.
     * @param args N/A
     */
    public static void main(String[] args)
    {
        Product product1 = new Product(1, "Giant Bicycle",  15, 299.99,1, 100);
        Product product2 = new Product(2, "Scott Bicycle", 15, 199.99, 1, 100);
        Product product3 = new Product(3, "GT Bike", 15, 99.99, 1, 20);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        InHouse part1 = new InHouse(1, "Brakes", 15, 12.99, 1, 20, 2563);
        InHouse part2 = new InHouse(2, "Tire", 15, 14.99, 1, 20, 5224);
        InHouse part3 = new InHouse(3, "Rim", 15, 56.99, 1, 20, 6335);

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);


        launch();
    }

}