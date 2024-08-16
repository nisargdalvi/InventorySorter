
/** Chemical Class
 * 	Creates a Chemical object
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class Chemical
{
	private String name;			//Name of the chemical
	private String compound;		//Compound of the chemical
	private String mSDate; 			//The MSDS Date of the chemical
	private String purchaseDate; 	//Purchase Date of the chemical being used
	private String containerSize; 	//Size of the container in which the chemical is in
	private String amountLeft;		//Amount left inside the container
	private short quantity;			//Number of extra bottles of this chemical
	
	/**Chemical Constructor
	 * Constructor for Chemical
	 * @param name - Name of the chemical
	 * @param compound - Compound of the chemical
	 * @param containerSize - Size of the container in which the chemical is in
	 * @param amountLeft - Amount left inside the container
	 * @param quantity - Number of extra bottles of this chemical
	 * @param purchaseDate - Purchase Date of the chemical being used
	 * @param mSDate - The MSDS Date of the chemical
	 */
	public Chemical(String name, String compound, String containerSize, String amountLeft, short quantity, String purchaseDate, String mSDate)
	{
		this.name= name;
		this.compound = compound;
		this.containerSize = containerSize;
		this.amountLeft = amountLeft;
		this.quantity = quantity;
		this.mSDate = mSDate;
		this.purchaseDate = purchaseDate;
	}
	
	
	/**Get Name method
	 * Gets the name of the chemical
	 * @return String name
	 */
	public String getName()
	{
		return name;
	}
	
	
	/**Get Compound method
	 * Gets the compound of the chemical
	 * @return String compound
	 */
	public String getCompound()
	{
		return compound;
	}
	
	
	/**Get Container Size Method
	 * Gets the size of the container that the chemical is in
	 * @return String containerSize
	 */
	public String getContainerSize()
	{
		return containerSize;
	}
	
	
	/**Get Amount Left method
	 * Gets the amount of chemical left in current container
	 * @return String amountLeft
	 */
	public String getAmountLeft()
	{
		return amountLeft;
	}
	
	
	/**Get Quantity method
	 * Gets the number of bottles left 
	 * @return short quantity
	 */
	public short getQuantity()
	{
		return quantity;
	}
	
	
	/**Get Purchase Date method
	 * Gets the purchase date of the chemical 
	 * @return String purchaseDate
	 */
	public String getPurchaseDate()
	{
		return purchaseDate;
	}
	
	
	/**Get MSDS Date method
	 * Gets the MSDS Date of the MSDS sheet linked to the chemical
	 * @return String mSDate
	 */
	public String getMSDate()
	{
		return mSDate;
	}	
	
	
}
