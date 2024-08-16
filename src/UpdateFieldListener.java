import javax.swing.*;

import java.awt.event.*;

/**Update Field Listener
 * Action Listener for JTextField in "Update" tab
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class UpdateFieldListener implements KeyListener
{
	JTextField field;
	JComboBox box;			//Update field
	CIGUIUpdate ciu;		//Instance of Update classes
	EIGUIUpdate eiu;
	TIGUIUpdate tiu;
	int choice = 0;			//Differentiate between different constructors 

	/**Update Field Listener Constructor
	 * Adds ActionListener to JTextField 
	 * @param JTextField field - JTextField to which this ActionListener is added to
	 * @param CIGUIUpdate ciu - Used for running a method in CIGUIUpdate
	 */
	public UpdateFieldListener(JComboBox box, CIGUIUpdate ciu)
	{
		this.box = box;
		this.ciu = ciu;
		box.getEditor().getEditorComponent().addKeyListener(this);
		choice = 1;
	}

	/**Update Field Listener Constructor
	 * Adds ActionListener to JTextField 
	 * @param JTextField field - JTextField to which this ActionListener is added to
	 * @param EIGUIUpdate eiu - Used for running a method in EIGUIUpdate
	 */
	public UpdateFieldListener(JTextField field, EIGUIUpdate eiu)
	{
		this.field = field;
		this.eiu = eiu;
		field.addKeyListener(this);
		choice = 2;
	}

	/**Update Field Listener Constructor
	 * Adds ActionListener to JTextField 
	 * @param JTextField field - JTextField to which this ActionListener is added to
	 * @param TIGUIUpdate tiu - Used for running a method in TIGUIUpdate
	 */
	public UpdateFieldListener(JTextField field, TIGUIUpdate tiu)
	{
		this.field = field;
		this.tiu = tiu;
		field.addKeyListener(this);
		choice = 3;
	}

	public void keyPressed(KeyEvent evt)
	{}

	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		{
			switch(choice)
			{
			case 1:
				//System.out.print("first ran ");
				ciu.setFields(box);					//runs setFields method in CIGUIUpdate
				box.setFocusable(true);
				break;
			case 2:
				eiu.setFields(field.getText()); 	//runs setFields method in EIGUIUpdate
				break;
			case 3:
				tiu.setFields(field.getText()); 	//runs setFields method in EIGUIUpdate
				break;
			default:
				break;
			}
		}
	}

	public void keyTyped(KeyEvent evt)
	{}
}
