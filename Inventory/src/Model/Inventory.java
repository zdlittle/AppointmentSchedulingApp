package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){ allParts.add(newPart); }

    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public Part lookupPart(int partId){
        int i;
        for(i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getId() == partId){
                break;
            }
        }
        return allParts.get(i);
    }

    public Product lookupProduct(int productId){
        int i;
        for(i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getId() == productId){
                break;
            }
        }
        return allProducts.get(i);
    }

    public ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> lookedUpPart = null;
        int i;
        for(i = 0; i < allParts.size(); i++){
            if(allParts.get(i).getName() == partName){
                break;
            }
        }
        lookedUpPart.add(allParts.get(i));
        return lookedUpPart;
    }

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> lookedUpProduct = null;
        int i;
        for(i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getName() == productName){
                break;
            }
        }
        lookedUpProduct.add(allProducts.get(i));
        return lookedUpProduct;
    }

    public static void updatePart(int index, Part selectedPart){
        allParts.remove(index);
        allParts.add(index, selectedPart);

    }

    public static void updateProduct(int index, Product newProduct){

        allProducts.remove(index);
        allProducts.add(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart){

        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct){

        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts(){

        return allParts;
    }

    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }


}
