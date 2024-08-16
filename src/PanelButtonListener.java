import java.awt.event.*;
import javax.swing.*;

/**Chemical Button Listener
 * Action Listener class for the categories in the Main Menu
 * @author Nisarg Dalvi
 * @since January 2014
 */
public class PanelButtonListener implements ActionListener
{
	
	JFrame frame; 	//The Main Menu JFrame
	
	
	/**Panel Button Listener Constructor
	 * Constructor of the class
	 * @param JButton button - Button onto which the ActionListener is implemented on
	 * @param JFrame frame -  Frame to be opened
	 */
	public PanelButtonListener(JButton button, JFrame frame)
	{
		this.frame = frame;
		button.addActionListener(this);
	}
	
	
	/** Action Performed method
	 *  Method that runs after button is clicked, makes the JFrame visible
	 */
	public void actionPerformed(ActionEvent e)
	{
		frame.setVisible(true);
	}
}
