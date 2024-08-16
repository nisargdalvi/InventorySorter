import java.awt.BorderLayout;
import java.awt.Color;






import javax.swing.*;

import org.uncommons.swing.SpringUtilities;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

/**CIGUIUpdate Class
 * Extends JPanel, Lets user Update a previously added Chemical
 * @author Nisarg Dalvi
 * @since Decmeber 2013
 */
public class CIGUIUpdate extends JPanel
{
	//Search Field
	private JLabel updateLabel = new JLabel("Update", JLabel.TRAILING);	//Label for JTextField (Search Field)		
	private JComboBox searchField;				//Search Field 	

	//Update Field
	private JButton updateButton = new JButton("Update");					//Update Button, writes all information to File
	private JButton cancelButton = new JButton("Cancel");					//Cancel Button, for when user wishes to cancel updating
	private JComboBox nameField;						//JTextfield for name of the Chemical
	private JTextField compoundField = new JTextField();					//JTextfield for compound of the Chemical
	private JTextField containerSizeField = new JTextField();				//JTextfield for size of the container of the Chemical
	private JTextField amountLeftField = new JTextField();					//JTextfield for amount left in current container of the Chemical
	private JTextField quantity = new JTextField();							//JTextfield for quantity of extra containers of the Chemical
	private JTextField purchasedDate = new JTextField();					//JTextfield for purchase date of the current container of the Chemical 
	private JTextField msdsDateField = new JTextField();					//JTextfield for MSDS Date of the Chemical

	//Labels for JTextFields (With TRAILING property for easy pairing with JTextFields)
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);						//Label for name field
	private JLabel compoundLabel = new JLabel("Compound", JLabel.TRAILING);				//Label for compound field
	private JLabel containerSizeLabel = new JLabel("Container Size", JLabel.TRAILING);	//Label for container size field
	private JLabel amountLeftLabel = new JLabel("Amount Left", JLabel.TRAILING);		//Label for amount left field
	private JLabel quantityLabel = new JLabel("Quantity", JLabel.TRAILING);				//Label for quantity field
	private JLabel purchasedDateLabel = new JLabel("Date Purchased", JLabel.TRAILING);	//Label for purchased date field
	private JLabel msdsDateLabel = new JLabel("MSDS Date", JLabel.TRAILING);	//JButton for updating MSDS Date and Compound

	//JPanels
	private JPanel finishPanel;	//contains "Update" and "Cancel" buttons
	private JPanel fieldPanel;	//Contains JTextfields and JLabels
	
	//Instances of other classes
	private CIGUIView view; 	//Instance of CIGUIView class, to update view
	private ErrorChecker er;		//Instance of CIGUIAdd class, to use error-checking method
	
	private Chemical tempChem; 	//Temporary chemical for use in replacing original chemical

	/**CIGUIUpdate Constructor
	 * Constructor for CIGUIUpdate, runs searchLayout and registerControllers methods
	 * @param CIGUIView view - Instance of CIGUIView class, used for updating view
	 * @param CIGUIAdd add - Instance of CIGUIAdd class, used for adding the updated chemical
	 */
	public CIGUIUpdate(CIGUIView view, ErrorChecker er)
	{
		searchLayout();
		this.view = view;
		this.er = er;

	}
	
	
	/**Search Layout method
	 * Lays out JComponents on JPanel
	 */
	public void searchLayout()
	{
		removeAll();
		setLayout(new BorderLayout()); 				//Sets layout to BorderLayout
		
		fieldPanel = new JPanel();
		finishPanel = new JPanel();
		
		fieldPanel.setLayout(new SpringLayout());	//Sets layout of fieldPanel (JTextFields+JLabels) to Spring Layout
		
		//Set borders for all JTextFields and disable all JTF's other than searchField 
		searchField = new JComboBox();
		searchField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		AutoCompleteSupport.install(searchField, GlazedLists.eventListOf(CModel.addMSDSName())); //the actual auto complete
		searchField.setEditable(true);
		
		nameField = new JComboBox();
		nameField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));				//Name Field
		AutoCompleteSupport.install(nameField, GlazedLists.eventListOf(CModel.getAllMSDSNames())); //the actual auto complete
		nameField.setEditable(true); 									//must have this otherwise the field is a little messed up
		nameField.setEnabled(false);
		
		compoundField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));			//Compound Field
		compoundField.setEditable(false);

		containerSizeField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));		//Container Size Field
		containerSizeField.setEditable(false);

		amountLeftField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));		//Amount Left Field
		amountLeftField.setEditable(false);

		quantity.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));				//Quantity Field
		quantity.setEditable(false);

		purchasedDate.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));			//Purchase Date Field
		purchasedDate.setEditable(false);

		msdsDateField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));			//MSDS Date Field
		msdsDateField.setEditable(false);

		//Add all Field Panel components
		fieldPanel.add(updateLabel);		//Update label + Search field
		fieldPanel.add(searchField);

		fieldPanel.add(nameLabel);			//Name label + Name field
		fieldPanel.add(nameField);

		fieldPanel.add(compoundLabel);		//Compound label + Compound field
		fieldPanel.add(compoundField);

		fieldPanel.add(containerSizeLabel);	//Container size label + Container size field
		fieldPanel.add(containerSizeField);

		fieldPanel.add(amountLeftLabel);	//Amount left label + Amount left field
		fieldPanel.add(amountLeftField);

		fieldPanel.add(quantityLabel);		//Quantity label + Quantity field
		fieldPanel.add(quantity);

		fieldPanel.add(purchasedDateLabel);	//Purchased date label + Purchased date field
		fieldPanel.add(purchasedDate);

		fieldPanel.add(msdsDateLabel);		//MSDS Date (+Compound) button + MSDS date field
		fieldPanel.add(msdsDateField);
		
		//Separate all JTextFields and JLabels into pairs
		SpringUtilities.makeCompactGrid(fieldPanel, 8, 2, 6, 6, 6, 6);
		
		//Add all Finish Panel components
		finishPanel.add(cancelButton); 
		finishPanel.add(updateButton);
		
		//Make "Update" button invisible
		updateButton.setVisible(false);

		//Add both panels to this panel
		add(fieldPanel, BorderLayout.CENTER);
		add(finishPanel, BorderLayout.PAGE_END);

		registerControllers();
	}
	
	
	/**Set Fields method
	 * Sets all the JTextFields using details of chemical
	 * @param String name - Name of Chemical 
	 */
	public void setFields(JComboBox box)
	{
		System.out.print("started setting fields ");
		
		box.setFocusable(false);
		
		try
		{
			tempChem = CModel.searchChemical(box.getSelectedItem().toString()); 	//Finds chemical - Returns Chemical or null (if not found)
		}
		catch(NullPointerException e)
		{}

		if(tempChem != null)						//If chemical is found
		{
			//Set all fields with details
			String tempChemStr = tempChem.getName().replaceAll("_", " ");
			nameField.setSelectedItem(tempChemStr);											//Name
			compoundField.setText(tempChem.getCompound().replaceAll("_", " "));				//Compound
			containerSizeField.setText(tempChem.getContainerSize().replaceAll("_", " "));	//Container Size
			amountLeftField.setText(tempChem.getAmountLeft().replaceAll("_", " "));			//Amount Left
			quantity.setText(Short.toString(tempChem.getQuantity()));						//Quantity
			purchasedDate.setText(tempChem.getPurchaseDate().replaceAll("_", " "));			//Purchase Date
			msdsDateField.setText(tempChem.getMSDate().replaceAll("_", " "));				//MSDS Date

			updateButton.setVisible(true);		//Make "Update" Button visible
			
			//Make all TextFields Editable (Except MSDS Date)
			nameField.setEnabled(true);			
			compoundField.setEditable(true);
			containerSizeField.setEditable(true);
			amountLeftField.setEditable(true);
			purchasedDate.setEditable(true);
			quantity.setEditable(true);	
		}
		
		//If Chemical Not found
		else
		{
			JOptionPane.showMessageDialog(null, "Chemical Not Found"); 	//Inform user "Chemical Not Found"
			System.out.print("this ran ");
		}
		
		System.out.print("ended setting fields ");
	}

	
	/**Update method
	 * After Checking if all the fields are filled out properly, updates the chemical and runs the Empty Fields method
	 */
	public void update()
	{
		//check if all the Fields are filled out properly
		boolean check = er.checkFields(nameField, compoundField, containerSizeField, amountLeftField, quantity, purchasedDate, msdsDateField);
		
		//if they are, replace the chemical
		if(check)
		{
			//replace all spaces with a "_" for all entries 
			String name = nameField.getSelectedItem().toString().replaceAll(" ", "_");	
			String compound = compoundField.getText().replaceAll(" ", "_");
			String containerSize = containerSizeField.getText().replaceAll(" ", "_");
			String amountLeft = amountLeftField.getText().replaceAll(" ", "_");
			short quant = Short.parseShort(quantity.getText());
			String pd = purchasedDate.getText().replaceAll(" ", "_");
			String msdsDate = msdsDateField.getText().replaceAll(" ", "_");
			
			//replace chemical
			Chemical newChemical = new Chemical (name, compound, containerSize, amountLeft, quant, pd, msdsDate); //Turn details into chemical

			CModel.replaceChemical(tempChem, newChemical);	//replace original chemical with updated one
			
			//reset layout
			emptyFields();	//empty fields and disable text fields

			view.updateLayout(); //Update View
			
		}

	}

	
	/**Empty Fields method
	 * Empties all TextFields and disables all but the Search Field
	 */
	public void emptyFields()
	{
		searchField.setSelectedItem(null);
		nameField.setSelectedItem(null);
		
		searchField.setEditable(true);
		
		nameField.setEnabled(false);
		compoundField.setText("");
		compoundField.setEditable(false);
		containerSizeField.setText("");
		containerSizeField.setEditable(false);
		amountLeftField.setText("");
		amountLeftField.setEditable(false);
		quantity.setText("");
		quantity.setEditable(false);
		purchasedDate.setText("");
		purchasedDate.setEditable(false);
		msdsDateField.setText("");

		updateButton.setVisible(false); //Make update button invisible
		
		System.out.print("empty finished ");
	}

	
	/**Register Controllers method
	 * Registers all the controllers
	 */
	public void registerControllers()
	{
		new UpdateFieldListener(searchField, this);											//Update Field ActionListener
		new UpdateButtonListener(updateButton, this);										//Update Button ActionListener
		new CancelButtonListener(cancelButton, this);										//Cancel Button ActionListener
		new MSDSCompoundListener(compoundField, msdsDateField, nameField);	//MSDSButton (+ Compound) Button ActionListener
	}

}
