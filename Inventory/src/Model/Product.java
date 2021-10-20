package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the Id of the Product
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the Name of the Product
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the Price of the Product
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Sets the Stock of the Product
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Sets the Min of the Product
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Sets the Max of the Product
     */
    public void setMax(int max){
        this.max = max;
    }

    /**
     * Gets the Id of the Product
     */
    public int getId(){

        return id;
    }

    /**
     * Returns the Name of the Product
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the Price of the Product
     */
    public double getPrice(){
        return price;
    }

    /**
     * Returns the Stock of the Product
     */
    public int getStock(){
        return stock;
    }

    /**
     * Returns the Min of the Product
     */
    public int getMin(){
        return min;
    }

    /**
     * Returns the Max of the Product
     */
    public int getMax(){
        return max;
    }

    /**
     * Adds the associated part to the associatedParts observable list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deletes the associated part from the associatedParts observable list
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns the associatedPart observable list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
