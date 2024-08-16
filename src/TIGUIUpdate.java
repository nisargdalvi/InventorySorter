import javax.swing.*;

import org.uncommons.swing.SpringUtilities;

import java.awt.BorderLayout;
import java.awt.Color;

/**TIGUIUpdate class
 * Extends JPanel, GUI for updating a Textbook
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class TIGUIUpdate extends JPanel
{
	//Before Entry of Update Textbook - Text field and Label
	private JLabel updateLabel = new JLabel("Update", JLabel.TRAILING);	
	private JTextField searchField = new JTextField(20);	
	
	//After entry of Update Textbook
	private JTextField nameField = new JTextField();	//Name Field
	private JComboBox<Byte> gradeBox;					//Grade drop-down menu
	private JTextField publisherField = new JTextField();					//Publisher field
	private JTextField codeField = new JTextField();	//ID Field
	
	//Labels
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);
	private JLabel gradeLabel = new JLabel("Grade", JLabel.TRAILING);
	private JLabel publisherLabel = new JLabel("Publisher", JLabel.TRAILING);
	private JLabel codeLabel = new JLabel("ID Code(s)", JLabel.TRAILING);
	
	//Finish Panel
	private JButton updateButton = new JButton("Update");	//Update Button
	private JButton cancelButton = new JButton("Cancel");	//Cancel Button
	
	//Panels
	private JPanel finishPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	
	
	private TIGUIView view;		//Instance of View
	
	private Textbook tempText;	//Temporary text book for replacing

	
	/**TIGUIUpdate Constructor
	 * Runs Search Layout and Register Controllers methods
	 * @param TIGUIView view - Used for updating View
	 */
	public TIGUIUpdate(TIGUIView view)
	{
		searchLayout();
		registerControllers();
		this.view = view;
	}

	
	/**Search Layout methods
	 * Layout for Panel 
	 */
	public void searchLayout()
	{
		setLayout(new BorderLayout());				//Set layout to BorderLayout
		
		//Drop down box
		Byte [] grades = {9, 10, 11, 12};			//Make an array of Byte values (Grade)
		gradeBox = new JComboBox <Byte> (grades);	////Make a Drop-Down menu with the four grades
		gradeBox.setSelectedItem(null);				//Set default item to null
		gradeBox.setEnabled(false);					//Disable it
		
		//Field Panel
		fieldPanel.setLayout(new SpringLayout());	//Set Field Panel to SpringLayout
		
		//Set border for Update Field
		searchField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		//Set border and disable Name and ID Field
		nameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		nameField.setEditable(false);
		
		publisherField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		publisherField.setEditable(false);
		
		codeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		codeField.setEditable(false);
		
		//Add components to panel
		fieldPanel.add(updateLabel);	//Search field + Label
		fieldPanel.add(searchField);
		
		fieldPanel.add(nameLabel);		//Name Field + Label
		fieldPanel.add(nameField);
		
		fieldPanel.add(gradeLabel);		//Grade Drop-Down menu + Label
		fieldPanel.add(gradeBox);
		
		fieldPanel.add(publisherLabel);
		fieldPanel.add(publisherField);
		
		fieldPanel.add(codeLabel);		//ID Field + Label
		fieldPanel.add(codeField);
		
		SpringUtilities.makeCompactGrid(fieldPanel, 5, 2, 6, 6, 6, 6);	//Pair up all components
		
		//Add components to Finish Panel
		finishPanel.add(cancelButton);
		finishPanel.add(updateButton);
		
		//Make Update Button invisible
		updateButton.setVisible(false);
		
		//Add Panels to this Panel
		add(fieldPanel, BorderLayout.CENTER);
		add(finishPanel, BorderLayout.PAGE_END);
	}

	
	/**Set Fields method
	 * Enables both the JTextFields and JComboBox if Textbook to be updated is found
	 * @param String name - Name of textbook
	 */
	public void setFields(String name)
	{
		tempText = TModel.searchTextbook(name);		//Finds the Textbook 
		
		//If textbook is found
		if(tempText!= null)							
		{
			nameField.setText(tempText.getName().replaceAll("_", " "));		//Replaces "_" of name with spaces and sets that to Name Field
			gradeBox.setSelectedItem(tempText.getGrade());					//Gets the grade
			publisherField.setText(tempText.getName().replaceAll("_", " "));
			codeField.setText(tempText.getCode().replaceAll("_", " "));		//Replaces "_" of ID with spaces and sets that to ID Field
			
			//Sets all components enabled
			updateButton.setVisible(true);		//Makes "Update" Button visible	
			nameField.setEditable(true);
			gradeBox.setEnabled(true);
			publisherField.setEditable(true);
			codeField.setEditable(true);
		}
		
		//If textbook is not found, inform user
		else
		{
			JOptionPane.showMessageDialog(null, "Textbook Not Found");
			emptyFields();	//reset fields
		}
	}

	
	/**Update Method
	 * Updates the entry if all fields are filled in properly
	 */
	public void update()
	{
		//Replace all spaces with "_" + Get selected grade from drop-down menu
		String name = nameField.getText().replaceAll(" ", "_");	
		byte grade = (byte) gradeBox.getSelectedItem();
		String publisher = publisherField.getText().replaceAll(" ", "_");
		String code = codeField.getText().replaceAll(" ", "_");
		
		//If all parameters are filled in correctly
		if(name != null && code != null && publisher != null && gradeBox.getSelectedItem() != null)
		{
			Textbook newTextbook = new Textbook(name, grade, publisher, code);	//Make a new textbook
			
			TModel.replaceTextbook(tempText, newTextbook);			//replace old textbook with new textbook
			
			emptyFields();											//Reset the JTextFields and JComboBox
			
			view.updateLayout();									//Update View
		}
		
		//If parameters are not filled in correctly, inform user
		else
		{
			JOptionPane.showMessageDialog(null, "Please fill all arguments");
		}
	}

	
	/**Empty Fields method
	 * Resets both the JTextFields and the JCombobox
	 */
	public void emptyFields()
	{
		//Set all text to null, and disable everything except the Search Field
		searchField.setText("");
		nameField.setText("");
		nameField.setEditable(false);
		gradeBox.setSelectedItem(null);
		gradeBox.setEnabled(false);
		publisherField.setText("");
		publisherField.setEditable(false);
		codeField.setText("");
		codeField.setEditable(false);
	}

	
	/**Register Controllers method
	 * Registers the controllers
	 */
	public void registerControllers()
	{
		new UpdateFieldListener(searchField, this);			//Search Field
		new UpdateButtonListener(updateButton, this);		//Update Button
		new CancelButtonListener(cancelButton, this);		//Cancel Button
	}
}
