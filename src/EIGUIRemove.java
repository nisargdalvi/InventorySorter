import java.awt.Color;
import javax.swing.*;

/**EIGUIRemove Class
 * extends panel to display the "Remove" panel in the Tabbed Pane
 * @author Mohammed Khan
 * @since December 2013
 */
public class EIGUIRemove extends JPanel
{
	//instance variables
	private JTextField removeField = new JTextField(30);	//create a JTextField of 30 characters length
	private JLabel label = new JLabel("Remove");			//create a JLabel to label the JTextField
	EIGUIView view;											//create and instance of EIGUIView, to update the layout when
															//a change has been made
	/**EIGUIRemobe Constructor
	 * default constructor
	 * @param view - creates instance of EIGUIView so that it's updatelLayout method can be called
	 */
	public EIGUIRemove(EIGUIView view)
	{
		setLayout();
		registerControllers();
		this.view = view;
	}

	/**SetLayout method
	 * sets the panel's layout
	 */
	private void setLayout()
	{
		removeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));	//give the remove filed a border
        
		//add the specific components to the panel
		add(label);
        add(removeField);	
	}
	
	/**Register Controllers method
	 * registers all the current controllers
	 */
	private void registerControllers()
	{
        new RemoveFieldListener(removeField, this);
	}

	/**Update method
	 * runs the updateLayout method from EIGUIView
	 */
	public void update()
	{
		view.updateLayout();
	}	
}