import javax.swing.*;
import java.util.Date;
import java.util.List;

import javax.swing.table.*;

/**CIGUIView Class
 * Extends JPanel, shows a Table with all the chemicals added 
 * @author Nisarg Daliv
 * @since December 2013
 */
public class CIGUIView extends JPanel
{
	JTable table;						//JTable onto which information is displayed
	DefaultTableModel defaultTable;		//Model for the Table to follow
	JScrollPane scrollPane;				//JScrollPane for when inventory exceeds beyond the Frame

	/**CIGUIView Constructor
	 * Constructor for CIGUIView, runs the viewLayout method
	 */
	public CIGUIView()
	{
		viewLayout();
	}


	/**View Layout method
	 * Lays out JTable and updates it
	 */
	public void viewLayout()
	{
		setLayout(new BoxLayout(this, 0));	//BoxLayout is used so that JTable takes up the entire JPanel
		
		//Table Column Headers
		String [] titles = {"Name", "Compound", "Container Size", "Amount Left", "Quantity", "Purchase Date", "MSDS Date"}; 
		
		//Table model
		defaultTable = new DefaultTableModel(titles, 0)
		{
			//Override getColumnClass method to sort different columns using different classes
			public Class getColumnClass(int column)	
			{
				if(column == 4)						//Quantity Class is sorted using Short class
				{
					return Short.class;
				}

				else if(column == 5 || column == 6)	//Purchase and MSDS Date are sorted using Date class
				{
					return Date.class;
				}

				else
				{
					return String.class;			//All others are sorted using String class
				}
			}

			//Override isCellEditable method to disable cell editing but still keep them click-able (For copy-pasting)
			public boolean isCellEditable(int rows, int column)
			{
				return false;	//Cells are not editable
			}
		};

		
		DefaultTableCellRenderer dtrc = new DefaultTableCellRenderer(); //Table Cell renderer to change how different cells are rendered
		dtrc.setHorizontalAlignment(JLabel.LEFT);						//Render selected cells to place text on the Left side
		
		//Table
		table = new JTable(defaultTable);								//Create a new table 
		table.getColumnModel().getColumn(4).setCellRenderer(dtrc);		//Set cell renderer to column 4 (Quantity) so they're left aligned
		table.setAutoCreateRowSorter(true);								//Allow auto-sorting
		table.setCellSelectionEnabled(true);							//Allow cell-selection (So they're click-able)
		table.getRowSorter().toggleSortOrder(0);						//Sort all cells by Name 
		
		//JPanel
		scrollPane = new JScrollPane(table);							//Add Table to ScrollPane
		add(scrollPane);												//Add ScrollPane to Panel

		updateLayout();													//Update Layout
	}
	
	
	/**Update Layout method
	 * Updates information of the table
	 */
	public void updateLayout()
	{		
		defaultTable.setRowCount(0);					//Take out all previous rows

		List <Chemical> chem = CModel.getChemicals();	//Make a list of all the Chemicals

		for(Chemical chemical : chem)					//For every Chemical in the List
		{
			//Replace all "_" with spaces
			String name = chemical.getName().replaceAll("_", " ");
			String compound = chemical.getCompound().replaceAll("_", " ");
			String containerSize = chemical.getContainerSize().replaceAll("_", " ");
			String amountLeft = chemical.getAmountLeft().replaceAll("_", " ");
			short quantity = chemical.getQuantity();
			String purchaseDate = chemical.getPurchaseDate().replaceAll("_", " ");
			String msdsDate = chemical.getMSDate().replaceAll("_", " ");

			Date purchaseDate2 = new Date(purchaseDate); 	//Turn Purchase Date String to a Date object
			Date msdsDate2 = new Date(msdsDate);			//Turn MSDS Date String to a Date object
			
			compound = numToSubscript(compound);			//Subscript all necessary numbers in compound
			
			Object [] chemInfo = {name, compound, containerSize, amountLeft, quantity, purchaseDate2, msdsDate2}; //Make object from details

			table.setRowHeight(25);	//Set row height to 25 (To fit in subscripts in Compound)

			defaultTable.addRow(chemInfo);	//Add row containing all info
		}

		repaint();	
	}

	
	/**NumtoSubscript method
	 * Converts all numbers (with exceptions) to subscripts
	 * @param String compound - The compound whose numbers need to be turned to subscripts
	 * @return String - HTML line with subscripts for numbers
	 */
	public String numToSubscript(String compound)
	{
		char [] characters = compound.toCharArray(); //Turn String into an array of characters

		int numCheck = 0; 					//Variable to which number is assigned to
		String subCompound = "<html>";		//String (in HTML) to which numbers letters are added normally, and numbers with subscripts
		boolean hydrate = false;			//Checks for a hydrate (Number after hydrate symbol is not subscripted)
		boolean num = false;				//Used to stop repeating digits after hydrate
		
		//For every character in array
		for(int i = 0; i < characters.length; i++) 
		{
			try
			{
				//If hydrate is true and the current character is not an H
				if(hydrate && (!(Character.toString(characters[i]).equals("H")))) 
				{
					subCompound = subCompound + Character.toString(characters[i]);	//just add digit
				}
				
				//else check if its a number
				else
				{
					numCheck = Integer.parseInt(Character.toString(characters[i]));
					num = true; //if this stays true later, it will be counted as a number
				}
			}
			
			//if its not a number
			catch(Exception e)
			{

				subCompound = subCompound + Character.toString(characters[i]);  //just add the letter
				
				//hydrate exception
				if(Character.toString(characters[i]).equals("•"))				//if its a hydrate character
				{
					hydrate = true;		//This is a hydrate
				}
				
				if(Character.toString(characters[i]).equals("H"))				//If "H" comes up, make Hydrate false
				{
					hydrate = false;
				}
				
				//Digit repeat prevention
				num = false;			//Not a number
				
				continue;				//Continue (To stop it from stopping)
			}
			
			//If numcheck is not zero
			if(numCheck != 0)
			{
				//If number is a number (Stops repeating numbers in hydrate)
				if(num)
				{
					subCompound = subCompound + "<sub>" + numCheck + "</sub>";  //Add number with subscript properties to HTML String
				}
			}

		}

		subCompound = subCompound + "</html>"; 	//after everything is added, close off HTML String

		return subCompound;		//Return String
	}

}
