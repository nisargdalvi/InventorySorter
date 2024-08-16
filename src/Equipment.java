/**Equipment Class
 * equipment object for storing equipments
 * @author Mohammed Khan
 * @since December 2013
 */
public class Equipment
{
	//instance variables
	private String name, type, location; //String values to store the object's properties
	private short quantity; //short value to store the quantity of equipments

	/**Equipment Constructor
	 * creates an equipment based on the parameters delivered
	 * @param name - name of equipment
	 * @param type - type of equipment
	 * @param quantity - number of equipments
	 * @param location - storage of equipments
	 */
    public Equipment(String name, String type, short quantity, String location)
    {
    	//make instance variable equal delivered values
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.location = location;
    }

    /**Get Name method
     * returns the name of the equipment
     * @return name - equipment's name
     */
    public String getName()
    {
        return name; //returns the equipment name
    }

    /**Get Type method
     * returns the equipment's type
     * @return type - equipment's type
     */
    public String getType()
    {
    	return type; //returns the equipment's type
    }
    
    /**Get Quantity method
     * returns the equipment's quantity
     * @return quantity - equipment's quantity
     */
    public short getQuantity()
    {
    	return quantity; //returns the equipment's quantity
    }
    
    /**Get Location method
     * returns the equipment's location
     * @return location - equipment's location
     */
    public String getLocation()
    {
    	return location; //returns the equipment's quantity
    }
}
