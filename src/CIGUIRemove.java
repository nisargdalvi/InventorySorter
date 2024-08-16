import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;

/** CIGUIRemove Class
 *  Extends JPanel, Lets user Remove a Chemical
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class CIGUIRemove extends JPanel
{
	private JComboBox removeField;							//ComboBox where Chemical to be removed will be input
	private JLabel label = new JLabel("Remove");			//Label for ComboBox
	private CIGUIView view;											//Instance of CIGUIView class (To update View after removal)
	private CIGUIUpdate ciu;
	
	/**CIGUIRemove Constructor
	 * Constructor for class, runs methods: setLayout and registerControllers
	 * @param view - Instance of CIGUIView class, used for updating view
	 */
	public CIGUIRemove(CIGUIView view, CIGUIUpdate ciu)
	{
		setLayout();
		this.view = view;
		this.ciu = ciu;
	}
	
	
	/**Set Layout method
	 * Sets the layout of the JPanel
	 */
	public void setLayout()
	{
		removeAll();
		removeField = new JComboBox();
		removeField.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK)); 	//Border for JTextField
		removeField.setPreferredSize(new Dimension(330, 30));
		AutoCompleteSupport.install(removeField, GlazedLists.eventListOf(CModel.addMSDSName())); //the actual auto complete
		
		add(label);																			
		add(removeField);
		
		registerControllers();
	}
	
	
	/**Register Controllers method
	 * Sets controller to JTextfield
	 */
	private void registerControllers()
	{
		new RemoveFieldListener(removeField, this);		//Makes a new RemoveFieldListener (ActionListener) for the JTextField
	}
	
	
	/**Update Method
	 * Updates the view
	 */
	public void update()
	{
		view.updateLayout(); 	//runs Update Layout method in CIGUIView
		this.setLayout();
		ciu.searchLayout();
	}
}
