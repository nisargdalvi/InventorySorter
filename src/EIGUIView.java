import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**EIGUIView Class
 * extends panel to display the "View" panel in the Tabbed Pane
 * @author Mohammed Khan
 * @since December 2013
 */
public class EIGUIView extends JPanel
{
	//Instance variables
	JTable table;				//table
	DefaultTableModel model;	//table sorting model
	JScrollPane scrollPane;		//Scroll bar for the table
	
	/**EIGUIView Constructor
	 * default constructor
	 */
	public EIGUIView()
	{
		viewLayout();
	}
	
	/**View Layout method
	 * lays out the view class upon initial starup
	 */
	public void viewLayout()
	{
		setLayout(new BoxLayout(this, 0));								//set panel layout
		String [] titles = {"Name", "Type", "Quantity", "Location"};	//create an array of strings for the table's titles
		
		//create a new DefaultTableModel and overrid 2 of its methods
		model = new DefaultTableModel(titles, 0)
		{
			//override method for getting the column class
            public Class getColumnClass(int column)
            {
            	//for column 2 (quantity) return Short class so that the table sorts that column like numbers
                if(column == 2)
                {
                	return Short.class;
                }
                
                else
                {
                	return String.class;
                }
            }
            
            //override method to seeing which cells are editable, make then all non editable therefore allowing copy paste but not
            //change of information
            public boolean isCellEditable(int row, int column)
            {
              return false;
            }
        };
		
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); //create a default table cell renderee r to change the way
        																//cells are formatted
        dtcr.setHorizontalAlignment(JLabel.LEFT);						//align cell text to the left
        
		table = new JTable(model);										//set modified model to the table
		table.getColumnModel().getColumn(2).setCellRenderer(dtcr);		//setCellRenderer for the columns
		table.setCellSelectionEnabled(true);							//allow cells to be selected
		table.setAutoCreateRowSorter(true);								//sort the rows with default(auto) sorter
		table.getRowSorter().toggleSortOrder(0);						//sort all cells by name
		scrollPane = new JScrollPane(table);							//add scroll bar to table
		add(scrollPane);												//add scroll bar to panel
		updateLayout();													//update the layout
	}

	/**Update Layout method
	 * updates all the cells in the view table
	 */
	public void updateLayout()
	{
		model.setRowCount(0);												//deletes all rows
		
		List <Equipment> equip = EModel.getEquipment();						//get the array list from the EModel

		for(Equipment equipment: equip)										//for ever stored equipment in the array list
		{	
			//Convert all the names to normal format from resource format
			String name = equipment.getName().replaceAll("_", " ");			
			String type = equipment.getType().replaceAll("_", " ");			
			short quantity = equipment.getQuantity();						
			String location = equipment.getLocation().replaceAll("_", " ");	
			
			Object [] equipInfo = {name, type, quantity, location};			//create an object of the sname catagories
			
			model.addRow(equipInfo);										//add that creted object to a row in the table
		}
		
		repaint();															//repaint the whole panel
	}
}
