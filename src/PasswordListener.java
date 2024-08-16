import java.awt.event.*;

import javax.swing.*;

/**Password Listener Controller
 * listens for a password entered in the password field 
 * @author Mohammed Khan
 * @since January 2014
 */
public class PasswordListener implements ActionListener
{
	//instance variables
	private String password; //actual password
	private JPasswordField pField; //password field
	private JFrame login; //login frame which houses the password field
	
	/**Password Listener Constructor
	 * general consturctor
	 * @param pField
	 * @param password
	 * @param login
	 */
	public PasswordListener(JPasswordField pField, String password, JFrame login)
	{
		this.password = password;
		this.pField = pField;
		this.login = login;
	}
	
	/**Action performed
	 * what to do when the action is performed
	 */
	public void actionPerformed(ActionEvent arg0)
	{		
		//if the password is correct then open main and kill login panel
		if(new String(pField.getPassword()).equals(password))
		{
			Main.runMain();
			login.dispose();
		}
		
		//if pw is wrong tell them they are wrong and reset field
		else
		{
			JOptionPane.showMessageDialog(null, "Incorrect Password, Try Again (Password is Case Sensitive)");
			pField.selectAll();
		}
	}
}
