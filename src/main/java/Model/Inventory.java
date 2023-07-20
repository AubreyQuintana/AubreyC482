package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

/**This class holds all of our inventory lists of parts and products.
 * @author Aubrey Quintana
 */
public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Used to automate Part ID's for new parts added into the list.
     */
    public static int partCounter = 4;

    /**
     * Adds a new part into the list.
     * @param part New part using Part class constructor.
     */
    public static void addPart(Part part)
    {
        allParts.add(part);
    }

    /**
     * Adds a new product into the list.
     * @param product New product using Product class constructor.
     */
    public static void addProduct(Product product)
    {
        allProducts.add(product);
    }

    /**
     * Look up a part ID.
     * @param partId partId parameter from Part class.
     * @return Returns the part that matches the ID or nothing.
     */
    public static Part lookupPart(int partId){
        for(Part part : getAllParts()){
            if(part.getId() == partId)
                return part;
        }
        return null;
    }

    /**
     * Look up a product by ID.
     * @param productId productId parameter from the Product class.
     * @return Returns the product that matches the ID or nothing.
     */
    public static Product lookupProduct(int productId){
        for(Product product : getAllProducts()){
            if(product.getId() == productId)
                return product;
        }
        return null;
    }

    /**
     * Look up a part by name.
     * @param partName partName parameter from the Part class.
     * @return Returns the created list that matches characters of the string input.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> list = FXCollections.observableArrayList();
        for(Part part : getAllParts()){
            if(part.getName().toLowerCase().contains(partName.toLowerCase()))
                list.add(part);
        }
        return list;
    }

    /**
     * Look up a product by name.
     * @param productName productName parameter from the Product class.
     * @return Returns the created list that matches characters of the string input.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> list = FXCollections.observableArrayList();
        for(Product product : getAllProducts()){
            if(product.getName().toLowerCase().contains(productName.toLowerCase()))
                list.add(product);
        }
        return list;
    }

    /**
     * Update a part in the list.
     * @param index Index through the Parts list.
     * @param selectedPart User selects a part from the Parts table.
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * Update a product in the list.
     * @param index Index through the Products list.
     * @param newProduct User selects a product from the Product table.
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Removes a part from the list.
     * @param selectedPart User selects a part from the Parts table.
     * @return N/A
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * Removes a product from the list.
     * @param selectedProduct User selects a product from the Products table.
     * @return N/A
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * Shows all parts in the Part list.
     * @return Returns allParts list.
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Shows all products in the Product list.
     * @return Returns allProducts list.
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

}