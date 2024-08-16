import java.io.*;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.pdmodel.*;
import java.util.regex.*;

/**PDFBox class
 * Scans PDF's for MSDS Date and Compound 
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class PDFBox
{
	
	/**Find MSDS Date method
	 * Finds the MSDS Date in a .pdf file
	 * @param File file - File to be checked (.pdf)
	 * @return String - MSDS Date
	 */
	public static String findMSDSDate(File file) throws IOException
	{
		PDDocument doc = PDDocument.load(file);			//loads pdf file
		PDFTextStripper ts = new PDFTextStripper();		//text stripper to get text
		
		StringBuilder page = new StringBuilder();		//String Builder
		page.append(ts.getText(doc));					//append pdf file into StringBuilder
		
		Pattern pattern = Pattern.compile("Date.+");	//Create Pattern for date (Line with "Date" and everything after in that line)

		Matcher matcher = pattern.matcher(page);		//Get match from StringBuilder

		String date = null;

		if(matcher.find())								//If it finds something
		{
			date = matcher.group();						//date = match found
		}
		else											//If not, date = "Date not found"
		{
			date = "Date Not Found";
		}
		
		doc.close();									//close document
		
		String [] split = date.split("Date: ");			//Split date into two parts: One before "Date: " and one after
		
		date = split[1];								//Take second part (Contains actual date)
		
		return date;
	}
	
	
	/**Find Formula method
	 * Finds Compound of Chemical in .pdf files
	 * @param File file - File to be checked (.pdf)
	 * @return String - Compound of Chemical
	 */
	public static String findFormula(File file) throws IOException
	{
		PDDocument doc = PDDocument.load(file);						//loads pdf file
		PDFTextStripper ts = new PDFTextStripper();					//text stripper for getting the text from the file
		
		StringBuilder page = new StringBuilder();					//String Builder
		page.append(ts.getText(doc));								//Append the pdf into the StringBuilder
		
		Pattern pattern = Pattern.compile("Molecular formula.+");	//Make Pattern ("Molecular Formula: ") and the whole line afterward
		Matcher matcher = pattern.matcher(page);					//Match pattern on StringBuilder
		
		String compound = "";
		
		if(matcher.find())											//If match found
		{
			compound = matcher.group();								//Make compound = match
		}
		
		else														//If match not found
		{
			compound = file.getName() + ": Not found";				//Print: "Name of Chemical" + Not found
		}
		
		doc.close();		//close document
		
		String [] split = compound.split("Molecular formula");		//Split line into parts separated by "Molecular Formula"
		String [] splat = split[1].split("Appearance");				//Take 2nd part of previous split, and split that with "Appearance"
		
		try
		{
			int period = splat[0].lastIndexOf(".");						//Take 1st part from previous split and get the last index of "." 
			compound = new StringBuilder(splat[0]).replace(period, period+1, "").toString();	//Take out the period at the end
		}
		
		catch(StringIndexOutOfBoundsException sioobe)
		{
			compound = splat[0];
		}
		
		
		//return compound with a specific dot replaced with hydrate sign (Some hydrates have this sign instead of the normal one)
		return compound.replaceAll("·", "•");												
	}
}
