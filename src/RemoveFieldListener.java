import javax.swing.*;

import java.awt.event.*;

/**Remove Field Listener
 * ActionListener for Remove Field in "Remove" tab
 * @author Nisarg Dalvi
 * @since December 2013
 */
public class RemoveFieldListener implements KeyListener
{
	JTextField field;	//Remove field
	JComboBox box;
	CIGUIRemove cir;	//Instance of Remove classes
	EIGUIRemove eir;
	TIGUIRemove tir;
	int choice = 0;		//Choice of Remove (Chemical, Equipment, Textbook)
	
	/**Remove Field Listener Constructor
	 * Adds ActionListener to JTextField
	 * @param JTextField field - JTextField to which this ActionListener is added to
	 * @param CIGUIRemove cir - Used for running a method in CIGUIRemove
	 */
	public RemoveFieldListener(final JComboBox box, final CIGUIRemove cir)
	{
		this.box = box;
		this.cir = cir;
		box.getEditor().getEditorComponent().addKeyListener(this); 
		choice = 1;
	}
	
	
	public RemoveFieldListener(JTextField field, EIGUIRemove eir)
	{
		this.field = field;
		this.eir = eir;
		field.addKeyListener(this);
		choice = 2;
	}
	
	public RemoveFieldListener(JTextField field, TIGUIRemove tir)
	{
		this.field = field;
		this.tir = tir;
		field.addKeyListener(this);
		choice = 3;
	}
	

	@Override
	public void keyPressed(KeyEvent evt)
	{}


	@Override
	public void keyReleased(KeyEvent evt)
	{
		if(evt.getKeyCode() == KeyEvent.VK_ENTER)
		{
			switch(choice)
			{
			case 1:
				CModel.removeChemical(box);					//Runs Remove Chemical method in CModel
				cir.update();								//Runs "Update" method in CIGUIRemove
				box.getEditor().selectAll();
				box.setFocusable(true);
				break;
			case 2:
				EModel.removeEquipment(field.getText());	//Runs Remove Equipment method in EModel
				field.selectAll();							//Empties text field
				eir.update();								//Runs "Update" method in EIGUIRemove
				break;
			case 3:
				TModel.removeTextbook(field.getText());		//Runs Remove Textbook method in TModel
				field.selectAll();							//Empties text field
				tir.update();								//Runs "Update" method in TIGUIRemove
				break;
			default:
				break;				
			}
		}
		
		
		
	}


	@Override
	public void keyTyped(KeyEvent evt)
	{}
}
