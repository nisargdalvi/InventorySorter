import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.*;

import javax.swing.*;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

/**CIGUIMSDS Class
 * Extends JPanel, Lets user open a PDF (MSDS) file
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class CIGUIMSDS extends JPanel
{
	private JComboBox msdsField = new JComboBox(); 	//MSDS Text Field
	private JLabel label = new JLabel("View MSDS"); 	//Label for Text Field
	
	
	/**CIGUIMSDS Constructor
	 * Constructor for CIGUIMSDS, runs setLayout and registerControllers methods
	 */
	public CIGUIMSDS()
	{
		setLayout();
		registerControllers();
	}
	
	
	/**Set Layout method
	 * Sets the GUI of the JPanel
	 */
	private void setLayout()
	{
		msdsField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));	//Border for Text Field
		msdsField.setPreferredSize(new Dimension(330, 30));
		AutoCompleteSupport.install(msdsField, GlazedLists.eventListOf(CModel.getAllMSDSNames())); //the actual auto complete
		msdsField.setVisible(true);
		
		add(label);																		//Add Label for Text Field
		add(msdsField);																	//Add Text Field
	}
	
	
	/**openFile Method
	 * Opens a file
	 * @param String fileName - Name of the file (If not in same folder, file path)
	 */
	public void openFile(String fileName)
	{
		fileName = fileName.replaceAll(" ", "_");				//Replace all spaces with "_"
		
		File file = new File(System.getProperty("user.dir") + "/English MSDS/" + fileName + ".pdf");	//Create File that is the folder in which the program is running from
		
			try 
			{
				Desktop.getDesktop().open(file);
			} 
			catch (IOException e) 
			{
				
			} //Open the file onto the Desktop
			catch (IllegalArgumentException iae)
			{
				
			}

		
		msdsField.setSelectedItem(null);
	}
	
	
	/**Register Controllers
	 * Registers controller
	 */
	private void registerControllers()
	{
		new MSDSFieldListener(msdsField, this); //MSDSFieldListener
	}
}
