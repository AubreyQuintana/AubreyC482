package Model;

/** This is a subclass of Part holding objects that are stored in house.
 * @author Aubrey Quintana
 */
public class InHouse extends Part{
    private int machineId;

    /**
     *
     * @param id The part ID
     * @param name the part name
     * @param stock The inventory of the part
     * @param price The price of the part
     * @param min The minimum amount of that part
     * @param max The maximum amount of that part
     * @param machineId The machine ID associated with that part
     */
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineId) {
        super(id, name, stock, price, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId set the machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
