import java.io.*;
import java.util.*;

/**Text File Writer Class
 * Writes text to file 
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class TextFileWriter
{
	/**Write to File method (Chemical)
	 * Writes a Chemical to file
	 * @param String file - Name of File
	 * @param Chemical chemical - Chemical to be written
	 */	
	public static void writeToFile(String file, Chemical chemical) throws IOException
	{
		File f = new File(file);	//Loads file
		PrintWriter pw = null;		//PrintWriter for printing text
		
		f.setWritable(true);		//Make file writable
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));	//Prints text to file (the "true" at the end is for appending the text)
		
		//Get details of Chemical
		String name = chemical.getName();
		String compound = chemical.getCompound();
		String containerSize = chemical.getContainerSize();
		String amountLeft = chemical.getAmountLeft();
		short quantity = chemical.getQuantity();
		String purchaseDate = chemical.getPurchaseDate();
		String msdsDate = chemical.getMSDate();
		
		//Print details with a tab between each entry
		pw.println(name + "\t" + compound + "\t" + containerSize + "\t" + amountLeft + "\t" + quantity + "\t" + purchaseDate + "\t" + msdsDate);	
		
		f.setWritable(false);       //Make file read-on
		
		pw.close();
	}
	
	/**Write to File method (Textbook)
	 * Writes a Textbook to file
	 * @param String file - Name of File
	 * @param Textbook textbook - Textbook to be written
	 */	
	public static void writeToFile(String file, Textbook textbook) throws IOException
	{
		File f = new File(file);	//Loads file
		PrintWriter pw = null;		//PrintWriter for printing text
		
		f.setWritable(true);		//Make file writable
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));	//Prints text to file (the "true" at the end is for appending the text)
		
		//Get details of Textbook
		String name = textbook.getName();
		byte grade = textbook.getGrade();
		String publisher = textbook.getPublisher();
		String code = textbook.getCode();
				
		//Print details with a tab between each entry
		pw.println(name + "\t" + grade + "\t" + publisher + "\t" + code);	
		
		f.setWritable(false);       //Make file read-on
		
		pw.close();
	}

	
	/**Write to File method (Equipment)
	 * Writes a Equipment to file
	 * @param String file - Name of File
	 * @param Equipment equipment - Equipment to be written
	 */	
	public static void writeToFile(String file, Equipment equipment) throws IOException
	{
		File f = new File(file);
		
		PrintWriter pw = null;		//PrintWriter for printing text
		
		f.setWritable(true);		//Make file writable
		
		pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));	//Prints text to file (the "true" at the end is for appending the text)
		
		//Get details of Equipment
		String name = equipment.getName();
		String type = equipment.getType();
		short quantity = equipment.getQuantity();
		String location = equipment.getLocation();
				
		//Print details with a tab between each entry
		pw.println(name + "\t" + type + "\t" + quantity + "\t" + location);	
		
		f.setWritable(false);       //Make file read-on
		
		pw.close();
	}
	
	
	/**Write to File method (Any 2 Strings)
	 * Replaces a String with another String in a file
	 * @param String filePath - Name of File
	 * @param String original - Original String
	 * @param String replace - String that is replacing the original
	 */
	public static void writeToFile(String filePath, String original, String replace) throws IOException
	{
		File file = new File (filePath);				//load file
		BufferedReader in = null;						//BufferedReader to read the file
		
		file.setWritable(true);							//Make file writable
		
		in = new BufferedReader(new FileReader(file));	//Set file to BufferedReader

		List <String> lines = new ArrayList <String>();	//Make a List of Strings

		String line = null;

		line = in.readLine();							//Read line


		while(line != null)								//For every line
		{
			if(line.equals(original))					//If line is same as original
			{
				lines.add(replace);						//add replacement to List 
			}
			else
			{
				lines.add(line);						//Else, add the same line to List
			}


			line = in.readLine();						

		}


		in.close();		

		PrintWriter out = null;							//PrintWriter to print text

		out = new PrintWriter(new FileWriter(file));	//Set same file to PrintWriter


		while(lines.isEmpty() != true)					//Print every line from List of Strings onto file
		{
			out.println(lines.remove(0));				
		}

		out.close();	//close PrintWriter
			
		file.setWritable(false);	//Make file read-only
		
	}

	
	/**Remove Null Lines method
	 * Removes blank lines from text files
	 * @param File file - The text file from which null lines are removed
	 */
	public static void removeNullLines(String file) throws IOException
	{
		File f = new File(file);
		f.setWritable(true);											//Make file writable
		
		BufferedReader in = new BufferedReader(new FileReader(f));		//Read the file using Buffered Reader
		
		List <String> lines = new ArrayList<String>();					//List of Strings
		
		String line = in.readLine();									//Read the first line
		
		//For all the lines in the text file
		while(line != null)										
		{
			
			//If line is not ""
			if(!line.equals(""))
			{
				lines.add(line);	//Add the file to the list
			}
			
			//read the next line
			line = in.readLine();
		}
		
		in.close();	//close Buffered Reader
		
		//Run a PrintWriter on the file
		PrintWriter out = new PrintWriter(new FileWriter(f));
		
		//Until the List of lines is empty
		while(lines.isEmpty() != true)
		{
			out.println(lines.remove(0));	//Remove the first line, and print that out into the file
		}
		
		//Close PrintWriter
		out.close();
		
		f.setWritable(false);       //Make file read-on
	}
	
	public static void writeToFile(String filePath, String line) throws IOException
	{
		File file = new File(filePath);
		
		PrintWriter out = new PrintWriter(new FileWriter(file, true));
		
		out.println(line);
	}
}
