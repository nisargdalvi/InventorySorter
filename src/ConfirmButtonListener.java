import java.awt.event.*;

import javax.swing.*;

/**Confirm Button Listener
 * Action Listener for Confirm Button 
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class ConfirmButtonListener implements ActionListener
{
	CIGUIAdd cia;		//Instance of Add classes
	EIGUIAdd eia;
	TIGUIAdd tia;
	int choice = 0;		//choice
	
	/**Confirm Button Listener Constructor(s)
	 * Constructors for this class, adds ActionListener to button
	 * @param JButton confirm - Button to which ActionListener is added to
	 * @param CIGUIAdd cia - CIGUIAdd class, for using methods in class
	 */
	public ConfirmButtonListener(JButton confirm, CIGUIAdd cia)
	{
		this.cia = cia;						
		confirm.addActionListener(this);
		choice = 1;
	}
	
	public ConfirmButtonListener(JButton confirm, EIGUIAdd eia)
	{
		this.eia = eia;
		confirm.addActionListener(this);
		choice = 2;
	}
	
	public ConfirmButtonListener(JButton confirm, TIGUIAdd tia)
	{
		this.tia = tia;
		confirm.addActionListener(this);
		choice = 3;
	}


	/**Action Performed method
	 * Method invoked after button is clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		////Runs update method in selected add classes
		switch (choice)
		{
		case 1:
			cia.update();
			break;
		case 2:
			eia.update();
			break;
		case 3:
			tia.update();
			break;
		default:
			break;
		}
	}
}
