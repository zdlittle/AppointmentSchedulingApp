package Model;

public class InHouse extends Part{

    private int machineId;

    /**
     * Method passes the parameter values to the Part superclass
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Sets the machine Id of the InHouse Part
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Gets the machine Id of the InHouse Part
     */
    public int getMachineId(){
        return machineId;
    }
}
