import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

/**Model Class
 * Model for Textbook
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class TModel
{
	private static List <Textbook> textbook = new ArrayList<Textbook>();	//List of Textbooks
	
	
	/**Get Textbook method
	 * Static method, Returns List of textbooks
	 * @return List of Textbooks
	 */
	public static List <Textbook> getTextbook()
	{
		return textbook;
	}

	
	/**Add Textbook method
	 * Static method, adds a textbook to the list
	 * @param String name - Name of Textbook
	 * @param Byte grade - Grade to which textbook belongs to
	 * @param String code - ID Code of the textbook
	 */
	public static void addTextbook(String name, byte grade, String publisher, String code)
	{		
		//Replace all spaces with "_" in both the name and the code
		name = name.replaceAll(" ", "_");
        code = code.replaceAll(" ", "_");
        publisher = publisher.replaceAll(" ", "_");
        
    	textbook.add(new Textbook(name, grade, publisher, code));	//Add the text book to the List
	}
	
	
	/**Update List method
	 * Static method, updates the list using file
	 * @param String file - Name of the File from which to update from
	 */
	public static void updateList(String file) throws IOException
	{
		Scanner in = new Scanner(new File(file));	//Make scanner scan the file
		
		//while it has another word
		while(in.hasNext())
		{
			String name = in.next();					//Name is the first word
			byte grade = Byte.parseByte(in.next());		//Grade is the second word
			String publisher = in.next();
			String code = in.next();					//ID is the third
			
			textbook.add(new Textbook(name, grade, publisher, code));	//Add a textbook object using given information
		}
		
		in.close();	//Close scanner
	}
	
	
	/**Remove Textbook method
	 * Static method, removes a textbook from the list
	 * @param String name - Name of the Textbook to be removed
	 */
	public static void removeTextbook(String name)
    {
        Textbook text = searchTextbook(name);		//Searches for the Textbook
        
        //If textbook is found
        if(text != null)
        {
        	String line = text.getName() + "\t" + Short.toString(text.getGrade()) + "\t" + text.getCode();	//Make a String Line similar to that in the storage File
            try
			{
				TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Textbooks.NIMO"), line, ""); //Remove textbook from file
			}
			catch (IOException e)
			{}
            
            textbook.remove(text);	//Remove textbook from List
            JOptionPane.showMessageDialog(null, text.getName().replaceAll("_", " ") + " Has Been Removed");	//Inform user Textbook Name "Has Been Removed"
        }
        
        //If text was not found, inform user
        else
        {
            JOptionPane.showMessageDialog(null, "Textbook Not Found");
        }
    }

	
	/**Search Textbook method
	 * Searches for the Textbook entered
	 * @param String name - Name of the textbook to be searched
	 * @return Textbook or null
	 */
	public static Textbook searchTextbook(String name)
	{
		for(Textbook text : textbook)								//For every textbook in the List
		{
			String tname = text.getName().replaceAll("_", " ");		//Name of textbook without spaces

			//If textbook name matches String input
			if(tname.equals(name))										
			{	
				return text;	//return textbook									
			}
		}

		return null;		//If textbook not found, return null
	}

	
	/**Replace Textbook method
	 * Replaces old textbook with updated textbook
	 * @param Textbook old - Old textbook
	 * @param Textbook updated - Updated Textbook
	 */
	public static void replaceTextbook(Textbook old, Textbook updated)
    {
		//Make a String with the same format as that in the file (of the Old and Update textbook)
		
		String oldText = old.getName() + "\t" + Short.toString(old.getGrade()) + "\t" + old.getCode();				//Old			
		String newText = updated.getName() + "\t" + Short.toString(updated.getGrade()) + "\t" + updated.getCode();	//Updated
        
		//Replace textbook in File
		try
		{
			TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Textbooks.NIMO"), oldText, newText);
		}
		catch (IOException e)
		{}
        
		
        textbook.remove(old);		//Remove old textbook from List
        textbook.add(updated);		//Add new textbook into List
    }

	
	/**Contains Textbook method
	 * Checks if Textbook is in the List
	 * @param String name - Name of the Textbook
	 * @return True if textbook is there, false if not
	 */
	public static boolean containsTextbook(String name)
	{
		name = name.replaceAll(" ", "_");	//Replace all spaces with a "_" 
		
		//For every textbook in the List
		for(Textbook text : textbook)
		{
			String TextName = text.getName();	//Get the name of that textbook
			
			if(TextName.equals(name))			//If the name of that textbook and the one entered match
			{
				return true;					//Return true
			}
		}
		
		return false; 	//If none match, return false
	}
}
