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

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage4</CODE> class.<P>
 *
 * @author  Chris Weaver & Group 6
 * @version %I%, %G%
 */
public final class Stage6
{

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
		Recipes	urecipes = new Fridge(); //creates the recipes database
		Fridge	ugroceries = new Fridge(); //creates the groceries database
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


		/* //NOTE this will be done in constructors now
		//creates the content of the fridge category panel
		String[] colName = new String[] {"☆", "Name" ,"Amount", "Days Left", "Leftovers?"};
		Object[][] products = new Object[][] {
                { "☆", "Apples", "15 (Apples)", "3" , ""},
                { "★", "Eggs", "6 (Eggs)", "12" , ""},
                { "☆", "Chili", "--", "3", "Yes"},
                { "★", "Oranges" ,"20 (Oranges)", "4", ""},
                { "☆", "Peaches" ,"10 (Peaches)", "1", ""},
                { "☆", "Tacos", "--", "2", "Yes"},
                { "★", "Bread", "2 (Slices)", "7", ""},
                { "☆", "Potato Chips", "1 (Bags)", "3" , ""}

							};
		//creates the content of the groceries category panel
		String[] colName1 = new String[] {"Name" ,"Amount", "Delete"};
		Object[][] products1 = new Object[][] {
			 	{ "Apples" ,"15", "[x]" },
				{ "Oranges" ,"20", "[x]"},
				{ "Peaches" ,"10", "[x]"},
							};


		//creates the content of the recipes category panel
		String[] colName2 = new String[] {"Name","Delete"};
		Object[][] products2 = new Object[][] {
				{ "Grilled Cheese", "[x]" },
				{ "Pizza", "[x]" },
				{ "Mac & Cheese", "[x]" },
							 };

							*/

		// ----- MENU BAR ----- //  //TODO write file chooser code
		JMenuBar menuBar = fileMenu.render(); //create the menu bar
		frame.setJMenuBar(menuBar); //add the menu bar to the main frame

		// ----- TOOL BAR ----- //
		JToolBar toolBar = iconBar.render(); //create the tool bar
		//add the tool bar to the main frame
		frame.getContentPane().add(toolBar, BorderLayout.PAGE_END);

		//sets the base frame to visible & to end on exit
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
				public void windowClosing(WindowEvent e)
				{
					quitItem.doClick();
					System.exit(0);
				}
			});
	}
}
//******************************************************************************
