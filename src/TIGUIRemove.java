import java.awt.Color;

import javax.swing.*;

/**TIGUIRemove class
 * Extends JPanel, GUI for removing a Textbook
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class TIGUIRemove extends JPanel
{
	private JTextField removeField = new JTextField(30);	//TextField for removing
	private JLabel label = new JLabel("Remove");			//Label
	
	TIGUIView view;	//Instance of View class to update View	
	
	
	/**TIGUIRemove Constructor
	 * Runs set layout and registers controllers methods
	 * @param view
	 */
	public TIGUIRemove(TIGUIView view)
	{
		setLayout();
		registerControllers();
		this.view = view;
	}

	/**Set Layout method
	 * Sets the layout of the Panel
	 */
	private void setLayout()
	{
		//Border for text field
		removeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
       
		//Add label and text field
		add(label);
        add(removeField);	
	}
	
	
	/**Register Controllers method
	 * Registers the controller
	 */
	private void registerControllers()
	{
        new RemoveFieldListener(removeField, this);	//Text field
	}

	/**Update method
	 * Updates the View
	 */
	public void update()
	{
		view.updateLayout();	//Runs update layout method in TIGUIView class
	}	
}
