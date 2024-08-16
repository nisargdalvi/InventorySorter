import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**TIGUIView Class
 * Extends JPanel, lets user View all the Textbooks that are stored in the program
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class TIGUIView extends JPanel
{
	JTable table;				//Table in which information will be displayed
	DefaultTableModel model;	//Table Model - The model which the table is based off of (Default)
	JScrollPane scrollPane;		//JScrollPane - Enables user to scroll once View extends farther than the JFrame
	
	
	/**TIGUIView Constructor
	 * Runs View Layout method
	 */
	public TIGUIView()
	{
		viewLayout();
	}
	
	
	/**View Layout method
	 * Lays out components on the Panel
	 */
	public void viewLayout()
	{
		setLayout(new BoxLayout(this, 0));				//Makes the Panel BoxLayout (To cover the whole screen)
		String [] titles = {"Name", "Grade", "Publisher", "ID"};	//Titles for headers
		
		model = new DefaultTableModel(titles, 0)		//Makes a DefaultTableModel based off of headers
		{
			//Overriding Methods from DefaultTableModel
			
			//Overriding Get Column Class to make the 2nd Column a Byte Class (To sort by Bytes not Strings)
            public Class getColumnClass(int column)
            {
            	//If column = 1 (2nd Column), return Byte.class
                if(column == 1)
                {
                	return Byte.class;
                }
                
                
                //For all the others, return String.class
                else
                {
                	return String.class;
                }
            }
            
            //Overriding isCellEditable method
            
            //Determines whether or not a cell is editable or not
            public boolean isCellEditable(int row, int column)
            {
              return false;		//Makes all cells un-editable
            }
        };
		
        //Table
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();		//Table Cell Renderer
        dtcr.setHorizontalAlignment(JLabel.LEFT);							//Set Table Cell Renderer to align to the left
        
		table = new JTable(model);											//Make table based off of model
		table.getColumnModel().getColumn(1).setCellRenderer(dtcr);			//Make 2nd column left aligned using Table Cell Renderer
		table.setCellSelectionEnabled(true);								//Make cells selectable (So they can click on them)
		table.setAutoCreateRowSorter(true);									//Make tables auto sort 
		table.getRowSorter().toggleSortOrder(0);							//Set the sorting based off of name as default
		
		//Scroll Pane
		scrollPane = new JScrollPane(table);								//Add Scroll Pane to Table
		add(scrollPane);													//Add Scroll Pane to Panel
		
		updateLayout(); //Run Update Layout method
	}

	
	/**Update Layout
	 * Updates Information of Table
	 */
	public void updateLayout()
	{
		model.setRowCount(0);											//Delete All Rows
		
		List <Textbook> text = TModel.getTextbook();					//Make a list of all the Textbooks

		//For every textbook in the List
		for(Textbook textbook: text)
		{		
			//Get the Details
			String name = textbook.getName().replaceAll("_", " ");	//Name
			byte grade = textbook.getGrade();						//Grade
			String publisher = textbook.getPublisher();
			String code = textbook.getCode().replaceAll("_", " ");	//ID
			
			//Make a row with that information
			Object [] textInfo = {name, grade, publisher, code};		//Make an Object array holding information
			
			model.addRow(textInfo);							//Add Object to a new row
		}
		
		repaint(); //Refresh
	}
}
