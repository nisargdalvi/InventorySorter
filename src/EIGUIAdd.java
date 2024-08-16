import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import javax.swing.*;
import org.uncommons.swing.SpringUtilities;

/**EIGUIAdd
 * extends panel to display the "Add" panel 
 * @author Mohammed Khan
 * @since December 2013
 */
public class EIGUIAdd extends JPanel
{
	private JButton confirmButton = new JButton("Confirm"); //confirm button
	private JButton cancelButton = new JButton("Cancel"); //cancel button
	
	private JTextField nameField = new JTextField(); //name textbox
	private JTextField typeField = new JTextField(); //type textbox
	private JTextField quantityField = new JTextField(); //quantity textbox
	private JTextField locationField = new JTextField(); //location textbox
	
	private JPanel fieldPanel = new JPanel(); //panel to house the fields
	
	//labels for the text boxes
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);
	private JLabel typeLabel = new JLabel("Type", JLabel.TRAILING);
	private JLabel quantityLabel = new JLabel("Quantity", JLabel.TRAILING);
	private JLabel locationLabel = new JLabel("Location", JLabel.TRAILING);
	
	private JPanel finishPanel = new JPanel(); //panel for the confirm and cancel buttons
	
	private EIGUIView view; //instance of the view to access the updateLayout method to update the view

	/**EIGUIAdd constructor
	 * default constructor
	 * @param view
	 */
	public EIGUIAdd(EIGUIView view)
	{
		super();
		setLayout();
		registerControllers();
		this.view = view;
	}

	/**Set Layout method
	 * sets the layout of the Add tab panel
	 */
	private void setLayout()
	{
		setLayout(new BorderLayout()); //create a border layout for the main panel
		
		fieldPanel.setLayout(new SpringLayout()); //create Spring layout for the textboxes and their labels
		
		//create borders for the textboxes
		nameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		typeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		quantityField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		locationField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		//add the textfields and labels to the field panel
		fieldPanel.add(nameLabel);
		fieldPanel.add(nameField);
		
		fieldPanel.add(typeLabel);
		fieldPanel.add(typeField);
		
		fieldPanel.add(quantityLabel);
		fieldPanel.add(quantityField);
		
		fieldPanel.add(locationLabel);
		fieldPanel.add(locationField);
		
		//make the field panel a spring utilities style compact grid to line up the fields and labels
		SpringUtilities.makeCompactGrid(fieldPanel, 4, 2, 6, 6, 6, 6);
		
		//add the buttons
		finishPanel.add(confirmButton);
		finishPanel.add(cancelButton);
		
		//add the panels to the main panel
		add(fieldPanel, "Center");
		add(finishPanel, "Last");
		
		validate(); //repaint everything
	}

	/**Update method
	 * runs after Confirm Button is hit. Saves all data to temporary array and updates View 
	 */
	public void update()
	{
		//Replace all spaces with "_" for all JTextFields
		String name = nameField.getText().replaceAll(" ", "_");
		String type = typeField.getText().replaceAll(" ", "_");
		String quantity = quantityField.getText().replaceAll(" ", "_");
		String location = locationField.getText().replaceAll(" ", "_");
		
		//Check for errors
		boolean check = checkFields(nameField,  typeField,  quantityField, locationField);
		
		//if there were no errors
		if(check)
		{
			if(!(EModel.containsEquipment(nameField.getText())))
			{
				//Add the equipment
				EModel.addEquipment(name, type, Short.parseShort(quantity), location);
				
				try
				{
					TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Equipment.NIMO"), new Equipment(name, type, Short.parseShort(quantity), location));
				}
				catch (NumberFormatException e)
				{}
				catch (IOException e)
				{}
				
				//Clear Text Fields
				nameField.setText(null);
				typeField.setText(null);
				quantityField.setText(null);
				locationField.setText(null);
				
				view.updateLayout(); //Update view 
				
			}
			
			//If Chemical exists, tell user
			else
			{
				JOptionPane.showMessageDialog(null, "This Equipment Has Already Been Entered");
			}
		}
	}
	
	/** Check Fields method
	 *  Checks whether all JTextFields are properly filled out
	 * @param JTextField nameField - JTextField for Name of Equipment
	 * @param JTextField typeField - JTextField for type of Equipment
	 * @param JTextField quantityField - JTextField for Quantity of Containers in storage of Equipment
	 * @param JTextField locationField - JTextField for location of current Container for Equipment
	 * @return Boolean - True if everything is properly filled out, false if not
	 */
	public boolean checkFields(JTextField nameField, JTextField typeField, JTextField quantityField, JTextField locationField)
	{		
		//Get information of whats inside JTextFields
		String quantityNum = quantityField.getText();
		
		//Perform check
		//If any of the fields are empty, open up a message dialog asking user to fill all spaces to add chemical
		if(nameField.getText().isEmpty() || typeField.getText().isEmpty() || quantityField.getText().isEmpty() || locationField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Please Fill All Spaces to Add Equipment");
		}
		
		//Else, check if the Quantity Field has only digits
		else
		{
			if(quantityNum.matches("\\d+")) //If everything is a digit, returns true
			{
				return true;	
			}
			
			//If Quantity Field doesn't contain only digits, ask user to only input digits
			else
			{
				JOptionPane.showMessageDialog(null, "Only Digits are Valid in Quantity (No spaces either)");
			}
		}
		
		return false;
	}

	/**Register Controllers method
	 * registers controllers
	 */
	private void registerControllers()
	{
		new ConfirmButtonListener(confirmButton, this);
		new CancelButtonListener(cancelButton, this);
	}

	/**Empty Fields method
	 * empties all the textboxes
	 */
	public void emptyFields()
	{
		nameField.setText("");
		nameField.setEditable(true);
		typeField.setText("");
		typeField.setEditable(true);
		quantityField.setText("");
		quantityField.setEditable(true);
		locationField.setText("");
		locationField.setEditable(true);
	}
}