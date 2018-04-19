//******************************************************************************
// Copyright (C) 2016-2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  9 20:33:16 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file (for CS 4053/5053 homeworks).
// 20180123 [weaver]:	Modified for use in CS 3053 team projects.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.stages;

import java.io.FileWriter;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage4</CODE> class.<P>
 *
 * @author  Chris Weaver & Group 6
 * @version %I%, %G%
 */
public final class Stage8
{
	//TODO currently no way to add new values to any database. The databases have add methods
	// but they are not called by anything, and require a food object. Something must get user input,
	// create a food, and then call the right add methods.

	//TODO currently database panels render before we can be sure the readr
	//has loaded a file. This still neeeds to be changed, perhaps by drawing a
	//defualt panel, and then re-rendering after data has been loaded? Is that feasible?

	//TODO refine the HTML fine & make sure it works in Java

	//TODO Hook the ingredients to recipes somehow

	//TODO make sure everything displays correctly inside of the tables

	//TODO ensure that Gradle builds this version correctly

	//TODO get MyTable working right

	//main
	public static void main(String[] args)
	{
		//MAIN WINDOW creates the base JFrame on which everything will be displayed
		JFrame frame = new JFrame("FridgTrackr");

		//top will hold everything that's to go on the PAGE_START slot of main frame
		JPanel top = new JPanel(new BorderLayout());
		//welcome label
		JLabel welcome = new JLabel("Welcome to FridgTrackr!");
		welcome.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		welcome.setBorder(new EmptyBorder(5, 115, 5, 100));
		//back icon
		Icon backIcon = Resources.getImage("icons/back.png");
		JButton back = new JButton();
		back.setIcon(backIcon);
		back.setPreferredSize(new Dimension(100, 50));
		back.setFont(new Font("Arial", Font.PLAIN, 25));
		//add icon
		Icon addIcon = Resources.getImage("icons/add.png");
		JButton add = new JButton();
		add.setIcon(addIcon);
		add.setFont(new Font("Arial", Font.PLAIN, 25));
		add.setPreferredSize(new Dimension(100, 50));
		//adds the buttons & the welcome message to top
		top.add(back, BorderLayout.LINE_START);
		top.add(welcome, BorderLayout.CENTER);
		top.add(add, BorderLayout.LINE_END);

		//sets the defualt size of the main window
		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(top, BorderLayout.PAGE_START);

		// --- Build --- //
		Fridge	ufridge = new Fridge(); //creates the fridge database
		Recipes	urecipes = new Recipes(); //creates the recipes database
		Groceries	ugroceries = new Groceries(); //creates the groceries database

		// --- Render --- //
		JPanel	fridge = ufridge.render(); //creates the fridge UI
		JPanel	recipes = urecipes.render(); //creates the recipes UI
		JPanel	groceries = ugroceries.render(); //creates the groceries UI

		//creates the file reader and passes it the databases
		readr in = new readr(ufridge, urecipes, ugroceries);

		//creates the pane that will store the category tabs
		JTabbedPane tabs = new JTabbedPane();
		// Could use Weaver's getImage() for this now but this code works
		Icon fridgeIcon = Resources.getImage("icons/refrigerator.png");
		Icon recipesIcon = Resources.getImage("icons/contract.png");
		Icon groceriesIcon = Resources.getImage("icons/groceries.png");
		//adds tabs to JTabbedPane
		tabs.addTab("Fridge", fridgeIcon, fridge);
		tabs.addTab("Recipes", recipesIcon, recipes);
		tabs.addTab("Groceries", groceriesIcon, groceries);
		//adds the JTabbedPane to the base pane
		frame.getContentPane().add(tabs, BorderLayout.CENTER);

		// ----- MENU BAR ----- //
		JMenuBar menuBar = fileMenu.render(in); //create the menu bar
		frame.setJMenuBar(menuBar); //add the menu bar to the main frame

		// ----- TOOL BAR ----- //
		JToolBar toolBar = iconBar.render(); //create the tool bar
		//add the tool bar to the main frame
		frame.getContentPane().add(toolBar, BorderLayout.PAGE_END);

		//sets the base frame to visible & to end on exit
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//if changes have been made prompt user to save
		frame.addWindowListener(new WindowAdapter()
		{public void windowClosing(WindowEvent e)
				{
					if(in.changeMade()) //if changes have been made
					{
						//creates the prompt message
						String message = "You have made changes. Would you like to save?";
						//prompts the user
						int choice = JOptionPane.showConfirmDialog(null, message);
						//if they choose to save
						if(choice == JOptionPane.YES_OPTION)
						{
							//file chooser can throw a FileNotFoundException. Check for it
							try
							{
								//creates the fileChooser
								JFileChooser fileBox = new JFileChooser();
								//stores the return of the file chooser after close
								int result = fileBox.showSaveDialog(null);
								//if a valid path for saving was choosen
								if(result == JFileChooser.APPROVE_OPTION)
								{
									//create a file writer for the new file
									FileWriter writer = new FileWriter(fileBox.getSelectedFile()+".txt");
									//tell the readr to create a CSV string
									writer.write(in.out());
									writer.close(); //closes the writer
								}

								//quits after saving
								System.exit(0);
							} //end of try
							catch(Exception f)
							{
								f.printStackTrace(); //print the stack trace on an error
							} //end of catch
						}

						//if they choose not to save
						if(choice == JOptionPane.NO_OPTION)
						{
							//quits
							System.exit(0);
						}
					}
				}
			}); //end of action listener
	} //end of main method
} //end of class
//******************************************************************************
