import java.io.*;
import java.util.*;

import javax.swing.*;

/**CModel class 
 * Model for the Chemical class, contains static methods and variables 
 * @author Nisarg Dalvi
 * @Since December 2013
 */
public class CModel
{
	private static List <Chemical> chemicals = new ArrayList <Chemical>();			//All chemicals
	private static Object [] allMSDSNames;
	//private static Object [] addedMSDSNames;

	/**Get Chemicals method
	 * @return <List> Chemicals - All the chemicals
	 */
	public static List <Chemical> getChemicals()									
	{
		return chemicals;
	}


	/**Update List method
	 * Runs at the beginning of the program, updates Chemicals List from a file
	 * @param String file - Name of file
	 */
	public static void updateList(String file) throws IOException
	{
		Scanner in = new Scanner(new File(file));

		while(in.hasNext())
		{
			String name = in.next();
			String compound = in.next();
			String containerSize = in.next();
			String amountLeft = in.next();
			short quantity = Short.parseShort(in.next());
			String purchaseDate = in.next();
			String msdsDate = in.next();

			chemicals.add(new Chemical(name, compound, containerSize, amountLeft, quantity, purchaseDate, msdsDate));
		}

		in.close();		
	}


	/**Add Chemical method
	 * Adds a Chemical to the List
	 * @param String name - Name of Chemical
	 * @param String compound - Compound of Chemical
	 * @param String containerSize - Container Size of Chemical
	 * @param String amountLeft - Amount left in Container of Chemical
	 * @param short quantity - Quantity of containers left (Of the Chemical)
	 * @param String purchaseDate - Purchase Date of current Container of Chemical
	 * @param String msdsDate - MSDS Date of Chemical
	 */
	public static void addChemical(String name, String compound, String containerSize, String amountLeft, short quantity, String purchaseDate, String msdsDate)
	{
		name = name.replaceAll(" ", "_");
		compound = compound.replaceAll(" ", "_");
		containerSize = containerSize.replaceAll(" ", "_");
		amountLeft = amountLeft.replaceAll(" ", "_");
		purchaseDate = purchaseDate.replaceAll(" ", "_");
		msdsDate = msdsDate.replaceAll(" ", "_");

		chemicals.add(new Chemical(name, compound, containerSize, amountLeft, quantity, purchaseDate, msdsDate));
		addMSDSName();
	}


	/**Remove Chemical
	 * Removes the specified Chemical from the list
	 * @param String name - Name of Chemical to be Removed
	 */
	public static void removeChemical(JComboBox box)
	{
		Chemical chemical = null;

		try
		{
			chemical = searchChemical(box.getSelectedItem().toString());	//Finds the Chemical
		}
		catch(NullPointerException e)
		{}

		box.setFocusable(false);

		if(chemical != null)						//If Chemical is found
		{
			String line = chemical.getName() + "\t" + chemical.getCompound() + "\t" + chemical.getContainerSize() + "\t" 
					+ chemical.getAmountLeft() + "\t" + Short.toString(chemical.getQuantity()) + "\t" + chemical.getPurchaseDate()
					+ "\t" + chemical.getMSDate(); // Make a String representing what it would look like in the file

			try
			{
				TextFileWriter.writeToFile(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO", line, "");	//Replace that part of the file with nothing
			}
			catch (IOException e)
			{} 	

			chemicals.remove(chemical);							//Remove Chemical from List
			addMSDSName();
			JOptionPane.showMessageDialog(null, chemical.getName().replaceAll("_", " ") + " Has Been Removed");	//Inform user "Chemical Not Found"
			box.setSelectedItem(null);			
		}

		//If Chemical is not found
		else
		{
			JOptionPane.showMessageDialog(null, "Chemical Not Found");	//Inform user "Chemical Not Found"
			box.setSelectedItem(null);
		}
	}


	/**Get MSDS Date Compound Method
	 * Gets the MSDS Date and Compound of the Chemical from a PDF File
	 * @param JTextField nameField - Field containing name of the Chemical
	 * @param JTextField msdsButtonField - Field into which the MSDS Date is going to be printed out on
	 * @param JTextField compoundField - Field into which the Compound is going to be printed out on
	 */
	public static void getMSDSDateCompound(JComboBox nameField, JTextField msdsButtonField, JTextField compoundField)
	{
		nameField.setFocusable(false);

		if(nameField.getSelectedItem() != null)
		{
			String fileName = nameField.getSelectedItem().toString().replaceAll("\\s", "_");	//Gets the name and replaces all spaces with a "_"
			File file = new File(System.getProperty("user.dir") + "/English MSDS/" + fileName + ".pdf");		//Make File which will be the same folder that this program will be in
			String date = null;											//MSDS Date
			String compound = null;										//Compound
			try 
			{
				date = PDFBox.findMSDSDate(file);		//Get MSDS Date
				compound = PDFBox.findFormula(file);	//Get Compound
			} catch (IOException e) 
			{}		

			msdsButtonField.setText(date);		//Print out MSDS Date onto Text field
			compoundField.setText(compound);	//Print out Compound onto Text field

		}


		else
		{
			JOptionPane.showMessageDialog(null, "Please Enter A Name for Chemical");
		}
	}

	/**Search Chemical method
	 * Searches for given chemical
	 * @param String name - Name of Chemical
	 * @return Chemical - Returns Chemical if found. If not, returns null
	 */
	public static Chemical searchChemical(String name)
	{
		for(Chemical chemical : chemicals)								//For every chemical in the Lit
		{
			String cname = chemical.getName().replaceAll("_", " ");		//Name of chemical without spaces

			//If chemical name matches String input
			if(cname.equalsIgnoreCase(name))										
			{	
				return chemical;	//return Chemical									
			}

		}

		return null;		//If chemical not found, return null
	}


	/**Replace Chemical method
	 * Replaces old chemical with new Chemical
	 * @param Chemical old - Old Chemical (To be replaced)
	 * @param Chemical updated - New Chemical (Replacing the old)
	 */
	public static void replaceChemical(Chemical old, Chemical updated)
	{
		//Make Strings representing old and new Chemical in text file
		String oldChem = old.getName() + "\t" + old.getCompound() + "\t" + old.getContainerSize() + "\t" + old.getAmountLeft() + "\t" + Short.toString(old.getQuantity())
				+ "\t" + old.getPurchaseDate() + "\t" + old.getMSDate();
		String newChem = updated.getName() + "\t" + updated.getCompound() + "\t" + updated.getContainerSize() + "\t" + updated.getAmountLeft() + "\t" + Short.toString(updated.getQuantity())
				+ "\t" + updated.getPurchaseDate() + "\t" + updated.getMSDate();
		try
		{
			TextFileWriter.writeToFile(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO", oldChem, newChem);
		}
		catch (IOException e)
		{} //Replace old Chemical with new Chemical in text file

		chemicals.remove(old);		//Remove old chemical	
		chemicals.add(updated);		//Add new chemical

		addMSDSName();
	}


	/**Contains Chemical method
	 * Checks if specified chemical exists or not
	 * @param String name - Name of Chemical to be checked
	 * @return Boolean - True if it does contain, false if not
	 */
	public static boolean containsChemical(String name)
	{
		name = name.replaceAll(" ", "_");	//Get name of Chemical

		for(Chemical chemical : chemicals)	//For every Chemical in List
		{
			//Compare String to Chemical name
			String chemName = chemical.getName();
			if(chemName.equalsIgnoreCase(name))
			{
				return true;		//If they match, return true
			}
		}

		return false;				//If no matches, return false
	}

	public static void updateAllMSDSNames(String folder) throws IOException
	{
		List <String> names = new ArrayList<String>();

		File [] listofFiles = new File(System.getProperty("user.dir") + "/English MSDS").listFiles();

		for(File file : listofFiles)
		{
			names.add(file.getName());
		}

		allMSDSNames = new Object [names.size()];
		int counter = 0;

		while(names.isEmpty() != true)
		{
			String fileName = names.remove(0);
			fileName = fileName.replaceAll("_", " ");
			String [] split = fileName.split(".pdf");
			fileName = split[0];
			allMSDSNames[counter] = fileName;
			counter++;
		}
	}

	public static void updateAddedMSDSNames(String file) throws FileNotFoundException
	{
		File f = new File(file);

		Scanner in = new Scanner(f);
		List <String> names = new ArrayList<String>();

		while(in.hasNextLine())
		{
			names.add(in.nextLine().replaceAll("_", " "));
		}

		Object [] tempAddedMSDSNames = new Object[names.size()];

		int counter = 0;

		while(names.isEmpty() != true)
		{
			tempAddedMSDSNames[counter] = names.remove(0);
			counter++;
		}

		in.close();
	}


	public static Object[] addMSDSName()
	{
		Object[] addedMSDSNames = new Object[chemicals.size() + 1];

		for(int i = 0; i < chemicals.size(); i++)
		{
			addedMSDSNames[i] = chemicals.get(i).getName().replaceAll("_", " ");
		}
		return addedMSDSNames;
	}

	public static Object[] getAllMSDSNames()
	{
		return allMSDSNames;
	}

	public static String checkLowQuantity(int x)
	{
		String lowChems = "";
		
		for(int i = 0; i < chemicals.size(); i++)
		{
			if(chemicals.get(i).getQuantity() <= x)
			{
				lowChems = lowChems + chemicals.get(i).getName().replaceAll("_", " ");
				
				if(i < chemicals.size())
				{
					lowChems += ", ";
				}
				
				else
				{
					lowChems+= ".";
				}
			}
		}
		return lowChems;
	}
}
