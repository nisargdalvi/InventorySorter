import java.awt.event.*;

import javax.swing.*;

/**Cancel Button Listener Class
 * Action Listener for cancel button used in C/E/TIGUIUpdate and C/E/TIGUIAdd
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class CancelButtonListener implements ActionListener
{
	CIGUIUpdate ciu; 	//instance of Update classes
	EIGUIUpdate eiu; 
	TIGUIUpdate tiu; 
	
	CIGUIAdd cia;		//instance of Add classes
	EIGUIAdd eia;
	TIGUIAdd tia;
	
	boolean choice;		//boolean variable to determine whether to pick Update or Add
	int choose; 		//Differentiate between different classes

/** CancelButtonListener Constructor
 *  Constructor for CIGUIUpdate Cancel Button 
 * @param JButton button - The button to which the listener is being added on
 * @param CIGUIUpdate ciu - The CIGUIUpdate object, used for accessing methods in class
 */
	public CancelButtonListener(JButton button, CIGUIUpdate ciu)
	{
		this.ciu = ciu;
		button.addActionListener(this);
		choice = true;
		choose = 1;
	}
	
	
	/**CancelButtonListener Constructor(2)
	 * Constructor for CIGUIAdd Cancel Button
	 * @param JButton button - The button to which the listener is being added on
	 * @param CIGUIAdd cia - The CIGUIAdd object, used for accessing methods in class
	 */
	public CancelButtonListener(JButton button, CIGUIAdd cia)
	{
		this.cia = cia;
		button.addActionListener(this);
		choice = false;
		choose = 1;
	}
	
	
	/** CancelButtonListener Constructor
	 *  Constructor for EIGUIUpdate Cancel Button 
	 * @param JButton button - The button to which the listener is being added on
	 * @param EIGUIUpdate eiu - The EIGUIUpdate object, used for accessing methods in class
	 */
		public CancelButtonListener(JButton button, EIGUIUpdate eiu)
		{
			this.eiu = eiu;
			button.addActionListener(this);
			choice = true;
			choose = 2;
		}
		
		
		/**CancelButtonListener Constructor(2)
		 * Constructor for EIGUIAdd Cancel Button
		 * @param JButton button - The button to which the listener is being added on
		 * @param EIGUIAdd eia - The EIGUIAdd object, used for accessing methods in class
		 */
		public CancelButtonListener(JButton button, EIGUIAdd eia)
		{
			this.eia = eia;
			button.addActionListener(this);
			choice = false;
			choose = 2;
		}
		
		/** CancelButtonListener Constructor
		 *  Constructor for TIGUIUpdate Cancel Button 
		 * @param JButton button - The button to which the listener is being added on
		 * @param TIGUIUpdate tiu - The TIGUIUpdate object, used for accessing methods in class
		 */
			public CancelButtonListener(JButton button, TIGUIUpdate tiu)
			{
				this.tiu = tiu;
				button.addActionListener(this);
				choice = true;
				choose = 3;
			}
			
			
			/**CancelButtonListener Constructor(2)
			 * Constructor for TIGUIAdd Cancel Button
			 * @param JButton button - The button to which the listener is being added on
			 * @param TIGUIAdd tia - The TIGUIAdd object, used for accessing methods in class
			 */
			public CancelButtonListener(JButton button, TIGUIAdd tia)
			{
				this.tia = tia;
				button.addActionListener(this);
				choice = false;
				choose = 3;
			}


	/**Action Performed Method
	 * Method that runs after button is clicked
	 */
	public void actionPerformed(ActionEvent e)
	{
		switch(choose)
		{
		case 1:
			if(choice)
			{
				ciu.emptyFields();	//If the CIGUIUpdate constructor was selected, empty CIGUIUpdate text fields
			}
			
			else
			{
				cia.emptyFields();	//else empty CIGUIAdd fields
			}
			break;
		case 2:
			if(choice)
			{
				eiu.emptyFields();	//If the EIGUIUpdate constructor was selected, empty EIGUIUpdate text fields
			}
			
			else
			{
				eia.emptyFields();	//else empty EIGUIAdd fields
			}
			break;
		case 3:
			if(choice)
			{
				tiu.emptyFields();	//If the TIGUIUpdate constructor was selected, empty TIGUIUpdate text fields
			}
			
			else
			{
				tia.emptyFields();	//else empty TIGUIAdd fields
			}
			break;
		default:
			break;
		}
	}
}
