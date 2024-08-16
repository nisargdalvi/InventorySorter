import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.*;

import org.uncommons.swing.SpringUtilities;

/**TIGUI Add class
 * Extends JPanel, GUI for adding a Textbook
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class TIGUIAdd extends JPanel
{
	//Finish Panel Components
	private JButton confirmButton = new JButton("Confirm");	//Confirm Button
	private JButton cancelButton = new JButton("Cancel");	//Cancel Button
	
	//Field Panel Components
	private JTextField nameField = new JTextField();	//Name Field
	private JComboBox<Byte> gradeBox;					//Drop-down menu for grade
	private JTextField publisherField = new JTextField();					//Publisher Field
	private JTextField codeField = new JTextField();	//ID Field
	
	//Labels
	private JLabel nameLabel = new JLabel("Name", JLabel.TRAILING);
	private JLabel gradeLabel = new JLabel("Grade", JLabel.TRAILING);
	private JLabel publisherLabel = new JLabel("Publisher", JLabel.TRAILING);
	private JLabel codeLabel = new JLabel("ID Code(s)", JLabel.TRAILING);
	
	//Panels
	private JPanel finishPanel = new JPanel();
	private JPanel fieldPanel = new JPanel();
	
	//Instance of View
	private TIGUIView view;

	
	/**TIGUIAdd Constructor
	 * Sets layout and Registers controllers
	 * @param TIGUIVIew view - To update View
	 */
	public TIGUIAdd(TIGUIView view)
	{
		super();
		setLayout();
		registerControllers();
		this.view = view;
	}

	/** Set Layout method
	 *  Sets the layout of the Panel
	 */
	private void setLayout()
	{
		setLayout(new BorderLayout());				//Set layout to BorderLayout
			
		Byte [] grades = {9, 10, 11, 12};			//Make an array of Byte values (Grade)
		
		gradeBox = new JComboBox<Byte>(grades);		//Make a Drop-Down menu with the four grades
		gradeBox.setSelectedItem(null);				//Make default selected item null
		
		//Field Panel
		fieldPanel.setLayout(new SpringLayout());	//Set Field Panel layout to SpringLayout
		
		//Make borders for Text fields
		nameField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));	
		publisherField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		codeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		//Add Components to Field Panel
		fieldPanel.add(nameLabel);		//Name Field + Label
		fieldPanel.add(nameField);
		
		fieldPanel.add(gradeLabel);		//Drop-down menu + Label
		fieldPanel.add(gradeBox);
		
		fieldPanel.add(publisherLabel);
		fieldPanel.add(publisherField);
		
		fieldPanel.add(codeLabel);		//ID Field + Label
		fieldPanel.add(codeField);
		
		SpringUtilities.makeCompactGrid(fieldPanel, 4, 2, 6, 6, 6, 6);	//Pair them all up
		
		//Finish Panel
		finishPanel.add(confirmButton);	//Add Confirm and Cancel Buttons
		finishPanel.add(cancelButton);
		
		//Add Panels to this Panel
		add(fieldPanel, BorderLayout.CENTER);
		add(finishPanel, BorderLayout.PAGE_END);
		
		validate();		//Refresh
	}

	
	/**Update method
	 * Updates the GUI after Confirm button is clicked
	 */
	public void update()
	{
		//Replace all spaces with a "_" and get selection from drop-down menu
		String name = nameField.getText().replaceAll(" ", "_");
		byte grade = (byte)gradeBox.getSelectedItem();
		String publisher = publisherField.getText().replaceAll(" ", "_");
		String code = codeField.getText().replaceAll(" ", "_");
		
		//If they entered all information necessary
		if(name != null || code != null || publisher != null || gradeBox.getSelectedItem() != null)
		{
			//If this textbook doesn't already exist
			if(!(TModel.containsTextbook(nameField.getText())))
			{
				TModel.addTextbook(name, grade, publisher, code);	//Add Textbook
				try
				{
					//Write info to file
					TextFileWriter.writeToFile((System.getProperty("user.dir") + "/Resources/Textbooks.NIMO"), new Textbook(name, grade, publisher, code));
				}
				catch (NumberFormatException e)
				{}
				catch (IOException e)
				{}
				
				//Run Empty Fields method
				emptyFields();
				
				//Update View
				view.updateLayout();
			}
			
			//If text book has already been added, inform the user
			else
			{
				JOptionPane.showMessageDialog(null, "This(These) Textbook(s) Has Already Been Entered");
			}
		}
		
		//If all the information is not given, ask user to fill everything in
		else
		{
			JOptionPane.showMessageDialog(null, "Please Fill All Spaces to Add Textbook(s)");
		}
	}
	
	/**Register Controllers method
	 * Registers all the controllers
	 */
	private void registerControllers()
	{
		new ConfirmButtonListener(confirmButton, this);	//Confirm Button
		new CancelButtonListener(cancelButton, this);	//Cancel Button
	}

	
	/**	Empty Fields method
	 *  Empties the TextFields and sets drop-down menu selected item to null
	 */
	public void emptyFields()
	{
		nameField.setText("");
		gradeBox.setSelectedItem(null);
		publisherField.setText("");
		codeField.setText("");
	}
}