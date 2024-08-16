import java.util.Date;
import javax.swing.*;


public class ErrorChecker 
{
	/** Check Fields method
	 *  Checks whether all JTextFields are properly filled out
	 * @param JTextField nameField - JTextField for Name of Chemical
	 * @param JTextField compoundField - JTextField for Compound of Chemical
	 * @param JTextField containerSizeField - JTextField for Size of Container of Chemical
	 * @param JTextField amountLeftField - JTextField for Amount Left in current Container of Chemical
	 * @param JTextField quantity - JTextField for Quantity of Containers in storage of Chemical
	 * @param JTextField purchasedDate - JTextField for Purchase Date of current Container for Chemical
	 * @param JTextField msdsDateField - JTextField for MSDS Date of Chemical
	 * @return Boolean - True if everything is properly filled out, false if not
	 */
	public boolean checkFields(JComboBox<String> nameField, JTextField compoundField, JTextField containerSizeField, JTextField amountLeftField, 
			JTextField quantity, JTextField purchasedDate, JTextField msdsDateField)
	{
		//Get information of whats inside JTextFields
		//Get purchase Date and Quantity (Things to be checked)
		String pd = purchasedDate.getText();
		String quantityNum = quantity.getText();	

		//boolean value to check for date
		boolean dateCheck = true;

		//try to convert purchase date to Date format. If can't be converted, make dateCheck false (used later in error checking)
		try
		{
			Date date = new Date(pd);
		}
		catch(IllegalArgumentException e)
		{
			dateCheck = false;
		}
		
		
		//Perform check
		//If any of the fields are empty, open up a message dialog asking user to fill all spaces to add chemical
		if(nameField.getSelectedItem() == null || compoundField.getText().isEmpty() || containerSizeField.getText().isEmpty()
				|| amountLeftField.getText().isEmpty() || quantity.getText().isEmpty() || purchasedDate.getText().isEmpty()
				|| msdsDateField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Please Fill All Spaces to Add Chemical");
		}

		//Else, check if the Quantity Field has only digits 
		else
		{
			if(quantityNum.matches("\\d+")) 		//If everything is a digit, returns true
			{
				//If date is valid
				if(dateCheck)
				{
					return true;
				}

				//Else, tell them to put in a valid date format
				else
				{
					JOptionPane.showMessageDialog(null, "Date not Valid");
				}
			}

			//If Quantity Field doesn't contain only digits, ask user to only input digits
			else
			{
				JOptionPane.showMessageDialog(null, "Only Digits are Valid in Quantity (No spaces either)");
			}
		}

		return false;
	}
}
