import javax.swing.*;

import java.awt.event.*;

/**Update Button Listener
 * ActionListener class for the Update Button (CIGUIAdd) 
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class UpdateButtonListener implements ActionListener
{
	CIGUIUpdate ciu;	//instance of Update classes
	EIGUIUpdate eiu;
	TIGUIUpdate tiu;
	int choice = 0;		////Differentiate between different constructors 
	
	/**Update Button Listener Constructor
	 * Sets this ActionListener to button
	 * @param JButton update - JButton to which this ActionListener is added to
	 * @param CIGUIUpdate ciu - Used for running a method in CIGUIUpdate
	 */
	public UpdateButtonListener(JButton update, CIGUIUpdate ciu)
	{
		this.ciu = ciu;
		update.addActionListener(this);
		choice = 1;
	}
	
	/**Update Button Listener Constructor
	 * Sets this ActionListener to button
	 * @param JButton update - JButton to which this ActionListener is added to
	 * @param EIGUIUpdate eiu - Used for running a method in EIGUIUpdate
	 */
	public UpdateButtonListener(JButton update, EIGUIUpdate eiu)
	{
		this.eiu = eiu;
		update.addActionListener(this);
		choice = 2;
	}
	
	/**Update Button Listener Constructor
	 * Sets this ActionListener to button
	 * @param JButton update - JButton to which this ActionListener is added to
	 * @param TIGUIUpdate tiu - Used for running a method in TIGUIUpdate
	 */
	public UpdateButtonListener(JButton update, TIGUIUpdate tiu)
	{
		this.tiu = tiu;
		update.addActionListener(this);
		choice = 3;
	}


	/**Action Performed method
	 * Method invoked after Button is clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		switch(choice)
		{
		case 1:
			ciu.update();		//runs update method in CIGUIUpdate
			break;
		case 2:
			eiu.update();		//runs update method in CIGUIUpdate
			break;
		case 3:
			tiu.update();		//runs update method in CIGUIUpdate
			break;
		default:
			break;
		}
	}
}
