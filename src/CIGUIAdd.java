import javax.swing.*;
import org.uncommons.swing.SpringUtilities;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**CIGUIAdd Class
 * Extends JPanel, Lets user Add a Chemical
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class CIGUIAdd extends JPanel
{
	//Finish Panel
	private JButton confirmButton = new JButton("Confirm");		//Confirm Button, when clicked, saves information to temporary array
	private JButton cancelButton = new JButton("Cancel");		//Cancel Button, used for when user wants the cancel adding a chemical

	//Field Panel
	private JComboBox nameField = new JComboBox();				//JComboBox for name of chemical
	private JTextField compoundField = new JTextField();		//JTextField for compound of chemical
	private JTextField containerSizeField = new JTextField();	//JTextField for size of container of the chemical
	private JTextField amountLeftField = new JTextField();		//JTextField for amount left of chemical
	private JTextField quantity = new JTextField();				//JTextField for quantity of chemicals in storage
	private JTextField purchasedDate = new JTextField();		//JTextField for purchase date of chemical
	private JTextField msdsDateField = new JTextField();		//JTextField for MSDS date of MSDS sheet of chemical

	//Labels for TextFields
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);						//Label for Name Field
	private JLabel compoundLabel = new JLabel("Compound", JLabel.TRAILING);				//Label for Compound Field
	private JLabel containerSizeLabel = new JLabel("Container Size", JLabel.TRAILING);	//Label for Container Size Field
	private JLabel amountLeftLabel = new JLabel("Amount Left", JLabel.TRAILING);		//Label for Amount Left Field
	private JLabel quantityLabel = new JLabel("Quantity", JLabel.TRAILING);				//Label for Quantity Field
	private JLabel purchasedDateLabel = new JLabel("Date Purchased", JLabel.TRAILING);	//Label for Purchase Date Field
	private JLabel msdsDateLabel = new JLabel("MSDS Date", JLabel.TRAILING);	//JButton which, when clicked, automatically fills in Compound and MSDS Date 

	//JPanels
	private JPanel finishPanel = new JPanel();	//JPanel containing Cancel, Confirm, and Save Buttons
	private JPanel fieldPanel = new JPanel();	//JPanel for adding information (JTextFields + JLabels)

	private CIGUIView view; //Instance of CIGUIView for automatic updating
	private CIGUIRemove cir;
	private CIGUIUpdate ciu;
	private ErrorChecker ec;
	/**CIGUIAdd Constructor
	 * Constructor for class, runs setLayout and registerControllers methods
	 * @param CIGUIView view - Used for access to methods in CIGUIView class (For automatic updating)
	 */
	public CIGUIAdd(CIGUIView view, CIGUIRemove cir, CIGUIUpdate ciu, ErrorChecker ec)
	{
		super();
		setLayout();
		registerControllers();
		this.view = view;
		this.cir = cir;
		this.ciu = ciu;
		this.ec = ec;
	}


	/**Set Layout method
	 * Sets GUI of JPanel 
	 */
	private void setLayout()
	{
		setLayout(new BorderLayout()); //Set the layout as BorderLayout

		fieldPanel.setLayout(new SpringLayout()); //Set JPanel with JTextFields as SpringLayout

		List <String> names = new ArrayList<String>();

		File [] listofFiles = new File(System.getProperty("user.dir") + "/English MSDS").listFiles();

		for(File file : listofFiles)
		{
			names.add(file.getName());
		}

		Object [] elements = new Object[names.size()];
		int counter = 0;

		while(names.isEmpty() != true)
		{
			String fileName = names.remove(0);
			fileName = fileName.replaceAll("_", " ");
			String [] split = fileName.split(".pdf");
			fileName = split[0];
			elements[counter] = fileName;
			counter++;
		}				
		
		//Give each JTextField a border
		nameField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		AutoCompleteSupport.install(nameField, GlazedLists.eventListOf(elements)); //the actual auto complete
		nameField.setEditable(true); //must have this otherwise the field is a little messed up

		compoundField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		containerSizeField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		amountLeftField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		quantity.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		purchasedDate.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));
		msdsDateField.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.BLACK));

		msdsDateField.setEditable(false); //MSDS Date Field cannot be edited 

		fieldPanel.add(nameLabel); //Add Name Field + Label
		fieldPanel.add(nameField);

		fieldPanel.add(compoundLabel); //Add Compound Field + Label
		fieldPanel.add(compoundField);

		fieldPanel.add(containerSizeLabel); //Add Container Size Field + Label
		fieldPanel.add(containerSizeField);

		fieldPanel.add(amountLeftLabel); //Add Amount Left Field + Label
		fieldPanel.add(amountLeftField);

		fieldPanel.add(quantityLabel); //Add Quantity Field + Label
		fieldPanel.add(quantity);

		fieldPanel.add(purchasedDateLabel); //Add Purchased Date Field + Label
		fieldPanel.add(purchasedDate);

		fieldPanel.add(msdsDateLabel); //Add MSDS Date Field + MSDS & Compound Button
		fieldPanel.add(msdsDateField);

		SpringUtilities.makeCompactGrid(fieldPanel, 7, 2, 6, 6, 6, 6); ///Separate all JTextFields and JLabels into pairs

		//Add buttons to Finish Panel
		finishPanel.add(confirmButton); 
		finishPanel.add(cancelButton);

		//Add panels to JPanel
		add(fieldPanel, BorderLayout.CENTER); 
		add(finishPanel, BorderLayout.PAGE_END);

		validate(); //Refresh
	}

	

	/**Update Method
	 * Method that runs after Confirm Button is hit. Saves all data to temporary array and updates View 
	 */
	public void update()
	{
		//Replace all spaces with "_" for all JTextFields
		String name = "";
		try
		{
			name = nameField.getSelectedItem().toString().replaceAll(" ", "_");
		}
		catch (NullPointerException npe)
		{}
		
		String compound = compoundField.getText().replaceAll(" ", "_");
		String containerSize = containerSizeField.getText().replaceAll(" ", "_");
		String amountLeft = amountLeftField.getText().replaceAll(" ", "_");
		String pd = purchasedDate.getText().replaceAll(" ", "_");
		String msdsDate = msdsDateField.getText().replaceAll(" ", "_");
		String quantityNum = quantity.getText();

		//Check for errors
		boolean check = ec.checkFields(nameField, compoundField, containerSizeField, amountLeftField, quantity, purchasedDate, msdsDateField); 
		boolean repeat = CModel.containsChemical(name);

		if(check) 	//if there were no errors
		{
			if(!(repeat)) //if the chemical was not repeater
			{
				//Add the chemical
				CModel.addChemical(name, compound, containerSize, amountLeft, Short.parseShort(quantityNum), pd, msdsDate); 
				try
				{
					TextFileWriter.writeToFile(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO", new Chemical(name, compound, containerSize, amountLeft, Short.parseShort(quantityNum), pd, msdsDate));
				}
				catch (NumberFormatException e)
				{}
				catch (IOException e)
				{}

				String nulled = null;

				//Clear Text Fields 
				nameField.setSelectedItem(nulled);
				compoundField.setText(null);
				containerSizeField.setText(null);
				amountLeftField.setText(null);
				quantity.setText(null);
				purchasedDate.setText(null);
				msdsDateField.setText(null);

				view.updateLayout(); //Update view 
				cir.setLayout();
				ciu.searchLayout();
			}

			//If Chemical exists, tell user
			else
			{
				JOptionPane.showMessageDialog(null, "This Chemical Already Exists");
			}
		}
	}


	/**Empty Fields method
	 * Empties all fields (Used after Cancelling)
	 */
	public void emptyFields()
	{

		nameField.setSelectedItem(null);
		compoundField.setText("");
		containerSizeField.setText("");
		amountLeftField.setText("");
		quantity.setText("");
		purchasedDate.setText("");
		msdsDateField.setText("");
	}


	/**Register Controllers 
	 * Registers all the controllers for the buttons in this JPanel
	 */
	private void registerControllers()
	{	
		new MSDSCompoundListener(compoundField, msdsDateField, nameField); 	//MSDS Button
		new ConfirmButtonListener(confirmButton, this);										//Confirm Button
		new CancelButtonListener(cancelButton, this);										//Cancel Button
	}

}
