import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.List;
import java.util.Scanner;

/**Main Class
 * startup class for the Inventory sorter program that will display 3 button to choose from: "Chemical", "Equipment", and "Textbook".
 * @author Mohammed Khan
 * @since January 2014
 */
public class Main
{
	/**Main method
	 * displays a the password enter panel, will not let the use continue unless correct pw is entered
	 */
	public static void main (String [] args)
	{
		//create frame
		JFrame login = new JFrame("Please Enter The Password");
		login.setSize(300, 100);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setLocationRelativeTo(null);
		login.setResizable(true);
		
		//create pw field
		final JPasswordField pField = new JPasswordField(30);
		
		login.add(pField); //add pw field
		
		//import password from external file
		Scanner in = null;
		try
		{
			in = new Scanner(new File(System.getProperty("user.dir") + "/Resources/Password.NIMO"));
		}
		catch (FileNotFoundException e)
		{}
		
		String password = in.nextLine(); //get the text
		
		login.setVisible(true); //set the frame to visable
		
		pField.addActionListener(new PasswordListener(pField, password, login)); //set action listener on the password field
	} //end main
	
	/**Run Main method
	 * Runs the main program (If password is correct)
	 */
	public static void runMain()
	{
		JFrame frame = new JFrame("Inventory Sorter");			//Creates the new JFrame
		frame.setSize(500, 520);								//Sets the size of the JFrame
		frame.setLocationRelativeTo(null);						//sets the JFrame to occur anywhere
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//tells the JFrame to exit when closed
		frame.setResizable(false);								//restricts resizing of the JFrame
		
		//Import image located in the same folder as the program
		BufferedImage bi = null;
		try
		{
			bi = ImageIO.read(new File("Logo.jpg"));
		}
		catch (IOException e)
		{
		}
		
		final Image img = bi.getScaledInstance(494, 491, Image.SCALE_SMOOTH); //scale the image to size 494 x 491
		
		// create panel and draw the image on to the panel
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
			     super.paintComponent(g);
			     g.drawImage(img, 0, 0, this);
			}
		};
		
		panel.setLayout(null); //set panel layout to null, so we can set the bounds of the buttons specifically
		
		//create the 3 choice buttons + change pw button
		JButton cButton = new JButton("Chemicals");
		JButton eButton = new JButton("Equipment");
		JButton tButton = new JButton("Textbooks");
		JButton pButton = new JButton("Change Password");
		
		JLabel credit = new JLabel();								//create a label that says the names of the program developers
		credit.setText("Made by Nisarg Dalvi and Mohammed Khan");	//text of the label
		credit.setForeground(Color.WHITE);							//colour of the text in the label
		credit.setBounds(0, 0, 300, 20);							//postion and size of the label
		
		//set the position and the size of the buttons
		cButton.setBounds(50, 410, 100, 35);
		eButton.setBounds(190, 410, 100, 35);
		tButton.setBounds(330, 410, 100, 35);
		
		//add the buttons and the label
		panel.add(cButton);
		panel.add(eButton);
		panel.add(tButton);
		panel.add(credit);

		frame.add(panel); //add panel to frame
		
		//create the contents of each frame an load them here to wait for the listener
		JFrame chemical = loadChemical();
		JFrame equipment = loadEquipment();
		JFrame textbook = loadTextbook();
		
		/*String lowChems = CModel.checkLowQuantity(2);
		
		if(!(lowChems.equals("")))
		{
			JOptionPane.showMessageDialog(null, ("The Following Chemicals Have Less Than 2pcs Left in Reserve, Consider Ordering New Ones: " + lowChems));
		}*/
		
		//add action listeners to each button
		new PanelButtonListener(cButton, chemical);
		new PanelButtonListener(eButton, equipment);
		new PanelButtonListener(tButton, textbook);		

		frame.setVisible(true); //set frame to visble

	}
	
	/**Load Chemical method
	 * loads the chemical frame but leaves it invisible and lets the listener handle the creation of it
	 * @return JFrame - that was created
	 */
	private static JFrame loadChemical()
	{
		JFrame frame = new JFrame("Chemicals");	//creates the "Chemicals" JFrame
		frame.setSize(800, 320); 				//set JFrame size
		frame.setLocationRelativeTo(null);		//set the location of the JFrame to anywhere on the screen
		frame.setResizable(false);				//stops the frame from being resized 
		
		//remove null lines from the resource file
		try
		{
			TextFileWriter.removeNullLines(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO");
		}
		catch (IOException e)
		{}
		
		//try to update the current list from the "Chemicals.NIMO" file
		try
		{
			CModel.updateList(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO");
		}
		catch (IOException e)
		{}
		
		try
		{
			CModel.updateAllMSDSNames(System.getProperty("user.dir") + "/English MSDS");
			// CModel.updateAddedMSDSNames(System.getProperty("user.dir") + "/Resources/UpdateMSDS.NIMO");
		}
		catch (IOException e)
		{}
		
		//create the different panel classes for each of the tabs
		ErrorChecker er = new ErrorChecker();
		
		CIGUIView guiView = new CIGUIView();
		CIGUIUpdate guiUpdate = new CIGUIUpdate(guiView, er);
		CIGUIRemove guiRemove = new CIGUIRemove(guiView, guiUpdate);
		CIGUIAdd guiAdd = new CIGUIAdd(guiView, guiRemove, guiUpdate, er);
		CIGUIMSDS guiMSDS = new CIGUIMSDS();
		
		//Create the tabbed panes, give them titles, and add the panel classes to them.
		JTabbedPane tb = new JTabbedPane();
		tb.addTab("View", guiView);
		tb.addTab("Add", guiAdd);
		tb.addTab("Update", guiUpdate);
		tb.addTab("Remove", guiRemove);
		tb.addTab("View MSDS", guiMSDS);
		
		frame.add(tb); //add tabbed pane to frame
		
		return frame; //return created frame
	}
	
	/**Load Equipment method
	 * loads the equipment frame but leaves it invisible and lets the listener handle the creation of it
	 * @return JFrame - that was created
	 */
	private static JFrame loadEquipment()
	{
		//create new frame for the equipment inventories
		JFrame frame = new JFrame("Equipment");	//name
		frame.setSize(800, 250);				//size
		frame.setLocationRelativeTo(null);		//allows it to appear anywhere
		frame.setResizable(false);				//stops it from being resizeable

		//remove null lines from the resource file
		try
		{
			TextFileWriter.removeNullLines(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO");
		}
		catch (IOException e)
		{}
		
		//try to load the Eqipment.NIMO resource file
		try
		{
			EModel.updateList(System.getProperty("user.dir") + "/Resources/Equipment.NIMO");
		}
		catch (IOException e)
		{}
		
		//create the different panel classes for each of the tabbed panes
		EIGUIView guiView = new EIGUIView();
		EIGUIAdd guiAdd = new EIGUIAdd(guiView);
		EIGUIUpdate guiUpdate = new EIGUIUpdate(guiView, guiAdd);
		EIGUIRemove guiRemove = new EIGUIRemove(guiView);
		
		//create tabbed panes, give them titles, and add the panel classes to them
		JTabbedPane tb = new JTabbedPane();
		tb.addTab("Add", guiAdd);
		tb.addTab("Update", guiUpdate);
		tb.addTab("Remove", guiRemove);
		tb.addTab("View", guiView);
		
		//add tabbed panes to the frame
		frame.add(tb);
		
		return frame; //return created frame
	}
	
	/**Load Textbook method
	 * loads the textbook frame but leaves it invisible and lets the listener handle the creation of it
	 * @return
	 */
	private static JFrame loadTextbook()
	{
		//create a frame for the Textbook inventory
		JFrame frame = new JFrame("Textbooks");	//name
		frame.setSize(800, 230);				//size
		frame.setLocationRelativeTo(null);		//allow window to appear anywhere on the screen
		frame.setResizable(false);				//stop it from being resized

		//remove null lines from the resource file
		try
		{
			TextFileWriter.removeNullLines(System.getProperty("user.dir") + "/Resources/Chemicals.NIMO");
		}
		catch (IOException e)
		{}
		
		//try to load the Resource.NIMO file
		try
		{
			TModel.updateList(System.getProperty("user.dir") + "/Resources/Textbooks.NIMO");
		}
		catch (IOException e)
		{}
		
		//create the panel classes for each tab
		TIGUIView guiView = new TIGUIView();
		TIGUIAdd guiAdd = new TIGUIAdd(guiView);
		TIGUIUpdate guiUpdate = new TIGUIUpdate(guiView);
		TIGUIRemove guiRemove = new TIGUIRemove(guiView);
		
		//create a tabbed pane, add tittles, add panel classes
		JTabbedPane tb = new JTabbedPane();
		tb.addTab("Add", guiAdd);
		tb.addTab("Update", guiUpdate);
		tb.addTab("Remove", guiRemove);
		tb.addTab("View", guiView);
		
		frame.add(tb); //add tabbed pane to frame
		
		return frame; //return created frame
	}
}
