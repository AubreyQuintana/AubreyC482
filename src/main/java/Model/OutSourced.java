package Model;

/** This is a subclass of Part holding objects being outsourced from other companies.
 * @author Aubrey Quintana
 */
public class OutSourced extends Part{
    private String companyName;

    /**
     *
     * @param id The part ID
     * @param name The part name
     * @param stock The inventory of the part
     * @param price The price of the part
     * @param min The minimum amount of that part
     * @param max The maximum amount of that part
     * @param companyName The company name associated with that part
     */
    public OutSourced(int id, String name, int stock, double price, int min, int max, String companyName) {
        super(id, name, stock, price, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName set the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
