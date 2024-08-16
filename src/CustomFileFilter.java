import java.io.File;
import java.util.regex.*;

import javax.swing.filechooser.*;

/**Custom File Filter Class
 * Extends FileFilter, used in JFileChooser to show files with a specific name only
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class CustomFileFilter extends FileFilter
{
	private Pattern pattern; 	//Pattern to be matched
	private Matcher matcher;	//Matcher - Matches pattern to String
	
	
	/**Custom File Filter Constructor
	 * Makes a pattern using given filename
	 * @param String fileName - Name of files to be searched
	 */
	public CustomFileFilter(String fileName)
	{
		//Makes the pattern
		pattern = Pattern.compile("^" + fileName);	//"^" - Metacharacter, used for searching only the beginning of Strings 
		
	}
	
	
	/**Accept Method
	 * Whether the given file is accepted by the filter or not
	 */
	public boolean accept(File file)
	{
		//If its a directory (folder), display it
		if(file.isDirectory())
		{
			return true;
		}
		
		matcher = pattern.matcher(file.getName()); 	//Get all the matches
		return matcher.find();						//find - returns boolean value on whether or not pattern was found
	}


	/**Get Description method
	 * Required method, gives Description of the Filter
	 */
	public String getDescription()
	{
		return null;
	}

}
