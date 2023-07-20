package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class creates the blueprint for the products.
 * @author Aubrey Quintana
 */

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    /**
     *
     * @param id The product ID
     * @param name The product name
     * @param stock The inventory of the product
     * @param price The price of the product
     * @param min The minimum amount of that product
     * @param max The maximum amount of that product
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part add a part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart remove a part from list
     * @return Returns updated list with removed part
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
