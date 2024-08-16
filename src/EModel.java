import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**EModel class
 * model for all the equipment, handles add, remove, update, and view
 * @author Mohammed Khan
 * @since December 2013
 */
public class EModel
{
	//Instance variables
	private static List <Equipment> equipment = new ArrayList<Equipment>(); //arraylist to store all equipments
	
	/**Update List method
	 * runs when the programs starts to update the view tabe with any previously stored iformation
	 * @param file - to get the information from
	 * @throws IOException
	 */
	public static void updateList(String file) throws IOException
	{
		Scanner in = new Scanner(new File(file)); //scanner to read file
		
		//input name, type, quantity, and location from resource file and add to the current list
		while(in.hasNext())
		{
			String name = in.next();
			String type = in.next();
			short quantity = Short.parseShort(in.next());
			String location = in.next();
			
			equipment.add(new Equipment(name, type, quantity, location));
		}
		
		in.close(); //close input file
	}
	
	/**Get Equipmnet method
	 * returns equipment to the desired place
	 * @return
	 */
	public static List <Equipment> getEquipment()
	{
		return equipment; //returns the instance variable of array lists
	}

	/**Add Equipment method
	 * adds an equipment to the list with the given characteristics
	 * @param name - name of equipment
	 * @param type - type of equipment
	 * @param quantity - number of that equipment
	 * @param location - location of storage of that equipment
	 */
	public static void addEquipment(String name, String type, short quantity, String location)
	{
		//take the given values and repace all spaces with "_"
		name = name.replaceAll(" ", "_");
        type = type.replaceAll(" ", "_");
        location = location.replaceAll(" ", "_");
 
    	equipment.add(new Equipment(name, type, quantity, location)); //add the equipment to the array list 
	}
	
	/**Remove equipment method
	 * removes the equipment with the given name
	 * @param name - name of equipment to remove
	 */
	public static void removeEquipment(String name)
    {
        Equipment equip = searchEquipment(name); //search for the equipment with the given name and return it to this method
        
        //if there is no equipment then dont run
        if(equip != null)
        {
        	//create a string to store the formatted equipment information to be outputted to the resource file
        	String line = equip.getName() + "\t" + equip.getType() + "\t" + Short.toString(equip.getQuantity()) + "\t" + equip.getLocation();
            try
			{
				TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Equipment.NIMO"), line, ""); //location of the save file
			}
			catch (IOException e)
			{}
            
            //remove the said equipment
            equipment.remove(equip);
            JOptionPane.showMessageDialog(null, equip.getName().replaceAll("_", " ") + " Has Been Removed");	//Inform user that the equipment
            																									//has been removed
        }
        
        //if there is no equipment display a message
        else
        {
            JOptionPane.showMessageDialog(null, "Equipment Not Found");
        }
    }
	
/**Search Equipment method
 * search for a given equipment by name and returns that equipment as an obejct
 * @param name - name to search by
 * @return rqipment - that has been found
 */
	public static Equipment searchEquipment(String name)
	{
		for(Equipment equip : equipment)								//For every equipment in the list
		{
			String ename = equip.getName().replaceAll("_", " ");		//Name of equipment without spaces

			//If equipment name matches String input
			if(ename.equalsIgnoreCase(name))										
			{	
				return equip;	//return equipment									
			}
		}

		return null;		//If equipment not found, return null
	}

	/**Replace Equipment method
	 * takes an old equipment, removes it, and creates a new equipment with the updated information
	 * @param old
	 * @param updated
	 */
	public static void replaceEquipment(Equipment old, Equipment updated)
    {
		//change the name formatting of both the sets of information to match the resource file format (seperated by tabs)
		String oldEquip = old.getName() + "\t" + old.getType() + "\t" + Short.toString(old.getQuantity()) + "\t" + old.getLocation();
		String newEquip = updated.getName() + "\t" + updated.getType() + "\t" + Short.toString(updated.getQuantity()) + "\t" + updated.getLocation();		
        
		//try write to the resource file (remove old one, add new one)
		try
		{
			TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Equipment.NIMO"), oldEquip, newEquip);
		}
		catch (IOException e)
		{}
        
        equipment.remove(old); //remove old equipment from the array list
        equipment.add(updated); //add new equipment to the array list
    }

	/**Contains Equipment method
	 * checks to see if the equipment given is already existent in the list 
	 * @param name - equipment found
	 * @return boolean - if the equipment is in the list or not
	 */
	public static boolean containsEquipment(String name)
	{
		name = name.replaceAll(" ", "_"); //replace name formatting from resource format to normal format
		
		for(Equipment equip : equipment)			//for every equipment in the list
		{
			String equipmName = equip.getName();	//get the name of the equipment
			if(equipmName.equalsIgnoreCase(name))	//if the names are the same then return true saying that given equipment already
			{										//exists	
				return true;
			}
		}
		
		return false; //if its not there then return false
	}
}
