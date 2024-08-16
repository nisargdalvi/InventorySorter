import java.awt.event.*;

import javax.swing.*;

/**MSDS Button Listener
 * Action Listener for MSDS (+Compound) Button 
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class MSDSCompoundListener implements KeyListener
{
	private JTextField msdsButtonField; 	//MSDS Date Field
	private JComboBox nameBox;
	private JTextField nameField;			//Name of chemical
	private JTextField compoundField; 		//Compound Field (Chemical)
	private boolean choice;
	
	
	/**MSDS Button Listener Constructor
	 * Adds Action Listener to button
	 * @param msdsButton
	 * @param compoundField
	 * @param msdsButtonField
	 * @param nameField
	 */
	public MSDSCompoundListener(JTextField compoundField, JTextField msdsButtonField, JComboBox nameBox)
	{
		this.msdsButtonField = msdsButtonField;
		this.nameBox = nameBox;
		this.compoundField = compoundField;
		nameBox.getEditor().getEditorComponent().addKeyListener(this);
	}
		
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			CModel.getMSDSDateCompound(nameBox, msdsButtonField, compoundField);	//Runs static method in CModel class
			nameBox.setFocusable(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
