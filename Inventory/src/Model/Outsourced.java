package Model;

public class Outsourced extends Part{

    private String companyName;

    /**
     * Method passes the values to the superclass Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Sets the Company Name of the Outsourced Part
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Gets the Company Name of the Outsourced Part
     */
    public String getCompanyName(){
        return companyName;
    }

}
