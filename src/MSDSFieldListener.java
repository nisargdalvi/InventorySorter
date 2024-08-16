import javax.swing.*;

import java.awt.event.*;

/**MSDS Field Listener
 * Action Listener for MSDS Search field in View MSDS Tab
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class MSDSFieldListener implements KeyListener
{
	JComboBox field;	//MSDS Search Field
	CIGUIMSDS msds;		//Instance of CIGUIMSDS
	
	
	/**MSDS Field Listener Constructor
	 * Adds Action Listener to JTextField
	 * @param JTextField field - Search Field where name of MSDS file is input
	 * @param CIGUIMSDS msds - Used for running a method in CIGUIMSDS class file
	 */
	public MSDSFieldListener(JComboBox field, CIGUIMSDS msds)
	{
		this.field = field;
		this.msds = msds;
		field.getEditor().getEditorComponent().addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent evt)
	{}


	@Override
	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		{
			try
			{
				msds.openFile(field.getSelectedItem().toString());		//Opens pdf file using method in CIGUIMSDS
			}
			catch (NullPointerException npe)
			{
				msds.openFile("");
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent evt)
	{}
}
