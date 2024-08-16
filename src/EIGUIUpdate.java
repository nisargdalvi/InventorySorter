import javax.swing.*;
import org.uncommons.swing.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Color;

/**EIGUIUpdate
 * extends panel to attach to the tab labeled "Update", allow equipments to be updated
 * @author Mohammed Khan
 * @since December 2013
 */
public class EIGUIUpdate extends JPanel
{
	//Instance Variables
	private JLabel updateLabel = new JLabel("Update", JLabel.TRAILING);		//Label for searchField
	private JTextField searchField = new JTextField(20);					//Actually search file to fins chemical from
	
	private JButton updateButton = new JButton("Update");					//Button to update any current changes made
	private JButton cancelButton = new JButton("Cancel");					//Button to cancel any changes made
	
	private JTextField nameField = new JTextField();						//JTextField for name
	private JTextField typeField = new JTextField();						//JTextField for type
	private JTextField quantityField = new JTextField();					//JTextField for quantity
	private JTextField locationField = new JTextField();					//JTextField for location

	private JPanel fieldPanel = new JPanel();								//Panel that houses all the fields
	
	//labels for the fields
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);;
	private JLabel typeLabel = new JLabel("Type", JLabel.TRAILING);;		
	private JLabel quantityLabel = new JLabel("Quantity", JLabel.TRAILING);;
	private JLabel locationLabel = new JLabel("Location", JLabel.TRAILING);;
	
	private JPanel finishPanel = new JPanel(); //panel for the update and cancel buttons
	
	private EIGUIView view;			//Instance of EIGUIView to access the updateLayout method
	private EIGUIAdd eia;			//Instance of EIGUIAdd to access the checkField method
	private Equipment tempEquip;	//To store a temporary equipment to set in the fields later
	
	/**EIGUIUpdate Constructor
	 * default constructor
	 * @param view
	 * @param eia
	 */
	public EIGUIUpdate(EIGUIView view, EIGUIAdd eia)
	{
		searchLayout();
		registerControllers();
		this.view = view;
		this.eia = eia;
	}
	
	/**Search Layout
	 * sets the layout for the update panel
	 */
	public void searchLayout()
	{
		setLayout(new BorderLayout()); //sets a border layout
		
		fieldPanel.setLayout(new SpringLayout()); //creates the layout that allows you to connect labels to textfield
		
		searchField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK)); //creates border layout for the search field
		
		//make a border for the nameField and set it as non editable
		nameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		nameField.setEditable(false);
		
		//make a border for the typeField and set it as non editable
		typeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		typeField.setEditable(false);
		
		//make a border for the quantityField and set it as non editable
		quantityField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		quantityField.setEditable(false);
		
		//make a border for the locationField and set it as non editable
		locationField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		locationField.setEditable(false);
		
		//addall the compnents to the fieldPanel
		fieldPanel.add(updateLabel);
		fieldPanel.add(searchField);
		fieldPanel.add(nameLabel);
		fieldPanel.add(nameField);
		fieldPanel.add(typeLabel);
		fieldPanel.add(typeField);
		fieldPanel.add(quantityLabel);
		fieldPanel.add(quantityField);
		fieldPanel.add(locationLabel);
		fieldPanel.add(locationField);
		
		SpringUtilities.makeCompactGrid(fieldPanel, 5, 2, 6, 6, 6, 6); //creates a grid that will fit the textboxes and their labels
		
		//add the buttons
		finishPanel.add(cancelButton);
		finishPanel.add(updateButton);
		
		updateButton.setVisible(false); //hide the update button until the equipment is found
		
		//add the panes to the borderlayout of the main panel
		add(fieldPanel, BorderLayout.CENTER);
		add(finishPanel, BorderLayout.PAGE_END);
	}

	/**Set Fields method
	 * takes the given equipment and fills the fields with the stored information
	 * @param name
	 */
	public void setFields(String name)
	{
		tempEquip = EModel.searchEquipment(name); //copy of the equipment wanting to be changed
		
		//if there actually is an equipment
		if(tempEquip != null)
		{
			//change the text format from resource format to text format and set it to the fields
			nameField.setText(tempEquip.getName().replaceAll("_", " "));
			typeField.setText(tempEquip.getType().replaceAll("_", " "));
			quantityField.setText(Short.toString(tempEquip.getQuantity()));
			locationField.setText(tempEquip.getLocation().replaceAll("_", " "));
			
			//make all the text boxes editable and makes the update button visable
			updateButton.setVisible(true);
			nameField.setEditable(true);
			typeField.setEditable(true);
			quantityField.setEditable(true);
			locationField.setEditable(true);
		}
		
		//inform the user if there is no equipment by that name
		else
		{
			JOptionPane.showMessageDialog(null, "Equipment Not Found");
			emptyFields();
		}
	}

	/**Update method
	 * if the controller approves then it updates the inpormation that was changed
	 */
	public void update()
	{
		boolean check = eia.checkFields(nameField, typeField, quantityField, locationField); //checks if all the fields are filled
		
		//if all fields are filled
		if(check)
		{
			//change the text formatting from resource to text
			String name = nameField.getText().replaceAll(" ", "_");
			String type = typeField.getText().replaceAll(" ", "_");
			short quantity = Short.parseShort(quantityField.getText());
			String location = locationField.getText().replaceAll(" ", "_");
			
			Equipment newEquipment = new Equipment(name, type, quantity, location); //create new (changed equipment)
			
			EModel.replaceEquipment(tempEquip, newEquipment); //replace old equipment with new one
			
			emptyFields(); //empty all the textFields
			
			view.updateLayout(); //update the view page
		}
	}

	/**Empty Fields method
	 * epties the fields, sets all but the update textbox to non editable
	 */
	public void emptyFields()
	{
		searchField.setText("");
		nameField.setText("");
		nameField.setEditable(false);
		typeField.setText("");
		typeField.setEditable(false);
		quantityField.setText("");
		quantityField.setEditable(false);
		locationField.setText("");
		locationField.setEditable(false);
	}

	/**Register controllers method
	 * registers the controllers
	 */
	public void registerControllers()
	{
		new UpdateFieldListener(searchField, this);
		new UpdateButtonListener(updateButton, this);
		new CancelButtonListener(cancelButton, this);
	}
}
