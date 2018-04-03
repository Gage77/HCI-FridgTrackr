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

//import java.lang.*;
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

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage4</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage5
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 36);
	private static final Color	FILL_COLOR = Color.YELLOW;
	private static final Color	EDGE_COLOR = Color.RED;

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private static String		message;

	//**********************************************************************
	// Main
	//**********************************************************************

	//main
	public static void main(String[] args)
	{
		//MAIN WINDOW creates the base JFrame on which everything will be displayed
		JFrame			frame = new JFrame("FridgTrackr");

		// Top panel
		JPanel top = new JPanel(new BorderLayout());
		JLabel welcome = new JLabel("Welcome to FridgTrackr!");
		welcome.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		welcome.setBorder(new EmptyBorder(5, 215, 5, 200));
		//top.add(back, BorderLayout.LINE_START);
		top.add(welcome, BorderLayout.CENTER);
		//top.add(add, BorderLayout.LINE_END);
		
		// Disabled action listeners for back and add button
		/*ActionListener backListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("back button pressed. value: N/A");
			}
		};     back.addActionListener(backListener);
		
		ActionListener addListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("add item button pressed. value: N/A");
			}
		};     add.addActionListener(addListener);*/
		
		//creates the 3 category panels
		JPanel			recipes = new JPanel(new BorderLayout());
		JPanel			fridge = new JPanel(new BorderLayout());
		JPanel			groceries = new JPanel(new BorderLayout());

		//adds a title to each category panel
		recipes.setBorder(BorderFactory.createTitledBorder("Recipes"));
		fridge.setBorder(BorderFactory.createTitledBorder("Fridge"));
		groceries.setBorder(BorderFactory.createTitledBorder("Groceries"));

		//sets the defualt size of the main window
		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(top, BorderLayout.PAGE_START);

		//creates the pane that will store the category tabs
		JTabbedPane tabs = new JTabbedPane();
		// Could use Weaver's getImage() for this now but this code works
		Icon fridgeIcon = new ImageIcon(Stage5.class.getResource("refrigerator.png"));
		Icon recipesIcon = new ImageIcon(Stage5.class.getResource("contract.png"));
		Icon groceriesIcon = new ImageIcon(Stage5.class.getResource("groceries.png"));
		//adds tabs to JTabbedPane
		tabs.addTab("Fridge", fridgeIcon, fridge);
		tabs.addTab("Recipes", recipesIcon, recipes);
		tabs.addTab("Groceries", groceriesIcon, groceries);
		//adds the JTabbedPane to the base pane
		frame.getContentPane().add(tabs, BorderLayout.CENTER);

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
		//creates a table to hold the fridge panel data
		JTable fridgeTable = new JTable( products, colName );
		fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
		fridgeTable.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.getTableHeader().setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.setRowHeight(20);
		//adds the data panel to the fridge category panel
		fridge.add(new JScrollPane(fridgeTable));

		//creates the content of the groceries category panel
		String[] colName1 = new String[] {"Name" ,"Amount", "Delete"};
		Object[][] products1 = new Object[][] {
							 { "Apples" ,"15", "[x]" },
							 { "Oranges" ,"20", "[x]"},
							 { "Peaches" ,"10", "[x]"},
						 };
		//creates a table to hold the groceries panel data
		JTable table1 = new JTable( products1, colName1 );
		//adds the data panel to the fridge category panel
		groceries.add(new JScrollPane(table1) );

		//creates the content of the recipes category panel
		String[] colName2 = new String[] {"Name","Delete"};
    		Object[][] products2 = new Object[][] {
                { "Grilled Cheese", "[x]" },
                { "Pizza", "[x]" },
                { "Mac & Cheese", "[x]" },
            };
		//creates a table to hold the recipes panel data
		JTable table2 = new JTable( products2, colName2);
		//adds the data panel to the recipes category panel
		recipes.add(new JScrollPane(table2), BorderLayout.CENTER);
		// ----- FRIDGE TAB FILTER CHECKBOX PANEL ----- 
		JPanel			mid = new JPanel(new BorderLayout());
		JButton back = new JButton("←");
		back.setPreferredSize(new Dimension(100, 50));
		back.setFont(new Font("Arial", Font.PLAIN, 25));
		JButton add = new JButton("+");
		add.setFont(new Font("Arial", Font.PLAIN, 25));
		add.setPreferredSize(new Dimension(100, 50));
		mid.add(back, BorderLayout.WEST);
		mid.add(add, BorderLayout.EAST);
		JPanel			filterPanel = new JPanel(new GridLayout(2, 4));
		filterPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
		JCheckBox		favoritesBox = new JCheckBox();
		favoritesBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			favoritesLabel = new JLabel("Favorites");
		JCheckBox		expiredBox = new JCheckBox();
		expiredBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			expiredLabel = new JLabel("Expired");
		JCheckBox		lowBox = new JCheckBox();
		lowBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			lowLabel = new JLabel("Low Stock");
		JCheckBox		leftoversBox = new JCheckBox();
		leftoversBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			leftoversLabel = new JLabel("Leftovers");
		filterPanel.add(favoritesBox);
		filterPanel.add(favoritesLabel);
		filterPanel.add(expiredBox);
		filterPanel.add(expiredLabel);
		filterPanel.add(lowBox);
		filterPanel.add(lowLabel);
		filterPanel.add(leftoversBox);
		filterPanel.add(leftoversLabel);
		mid.add(filterPanel, BorderLayout.CENTER);
		fridge.add(mid, BorderLayout.NORTH);
		// ----- RECIPES TAB FILTER CHECKBOX PANEL ----- 
		JPanel			mid2 = new JPanel(new BorderLayout());
		JButton back2 = new JButton("←");
		back2.setPreferredSize(new Dimension(100, 50));
		back2.setFont(new Font("Arial", Font.PLAIN, 25));
		JButton add2 = new JButton("+");
		add2.setFont(new Font("Arial", Font.PLAIN, 25));
		add2.setPreferredSize(new Dimension(100, 50));
		mid2.add(back2, BorderLayout.WEST);
		mid2.add(add2, BorderLayout.EAST);
		JPanel			filterPanel2 = new JPanel(new GridLayout(1, 4));
		filterPanel2.setBorder(new EmptyBorder(5, 50, 5, 50));
		JCheckBox		favoritesBox2 = new JCheckBox(); // Items marked as favorite by user
		favoritesBox2.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			favoritesLabel2 = new JLabel("Favorites");
		JCheckBox		inStockBox = new JCheckBox(); // Recipes for which ingredients are in stock
		inStockBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			inStockLabel = new JLabel("In Stock");
		filterPanel2.add(favoritesBox2);
		filterPanel2.add(favoritesLabel2);
		filterPanel2.add(inStockBox);
		filterPanel2.add(inStockLabel);
		mid2.add(filterPanel2, BorderLayout.CENTER);
		recipes.add(mid2, BorderLayout.NORTH);
		
		// ----- MENU BAR -----
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		// File menu
		JMenuItem openItem = new JMenuItem(new AbstractAction("Open (CTRL + O)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Open (CTRL + O). Opens a FridgTrackr file."); 
		    }
		});
		JMenuItem saveItem = new JMenuItem(new AbstractAction("Save (CTRL + S)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Save (CTRL + S). Saves the current FridgTrackr file."); 
		    }
		});
		JMenuItem printAllItem = new JMenuItem(new AbstractAction("All (CTRL + P)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Print -> All (CTRL + P). Prints the full FridgTrackr file."); 
		    }
		});
		//JMenuItem printSelectedItem = new JMenuItem("Currently Shown");
		JMenuItem printFridgeItem = new JMenuItem(new AbstractAction("Fridge Stock")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Print -> Fridge Stock. Prints the current fridge stock."); 
		    }
		});
		JMenuItem printRecipesItem = new JMenuItem(new AbstractAction("Recipes")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Print -> Recipes. Prints the currently stored recipes."); 
		    }
		});
		JMenuItem printGroceriesItem = new JMenuItem(new AbstractAction("Groceries")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("File -> Print -> Groceries. Prints the current grocery list."); 
		    }
		});
		JMenu printSubmenu = new JMenu("Print");
		JMenuItem quitItem = new JMenuItem("Quit	(CTRL + Q)"); // ActionListener added later
		printSubmenu.add(printAllItem);
		//printSubmenu.add(printSelectedItem);
		printSubmenu.add(printFridgeItem);
		printSubmenu.add(printRecipesItem);
		printSubmenu.add(printGroceriesItem);
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(printSubmenu);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);
		// Edit menu
		JMenuItem copy = new JMenuItem(new AbstractAction("Copy (CTRL + C)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Copy (CTRL + C). Copies the selection."); 
		    }
		});
		JMenuItem cut = new JMenuItem(new AbstractAction("Cut (CTRL + X)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Cut (CTRL + X). Cuts the selection."); 
		    }
		});
		JMenuItem paste = new JMenuItem(new AbstractAction("Paste (CTRL + V)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Paste (CTRL + V). Pastes the selection."); 
		    }
		});
		JMenuItem search = new JMenuItem(new AbstractAction("Search (CTRL + F)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Search (CTRL + F). Allows the user to search for a string."); 
		    }
		});
		JMenuItem restore = new JMenuItem(new AbstractAction("Restore (CTRL + Z)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Restore (CTRL + Z). Restores the last deleted item to a limit."); 
		    }
		});
		JMenuItem favorites = new JMenuItem(new AbstractAction("Favorites")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Filter By: --> Favorites. Filters by favorited items."); 
		    }
		});
		JMenuItem expiration = new JMenuItem(new AbstractAction("Expired")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Filter By: --> Expired. Filters by expired items."); 
		    }
		});
		JMenuItem lowStock = new JMenuItem(new AbstractAction("Low Stock")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Filter By: --> Low Stock. Filters by low stock."); 
		    }
		});
		JMenuItem leftovers = new JMenuItem(new AbstractAction("Leftovers")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Edit -> Filter By: --> Leftovers. Filters by items marked as leftovers."); 
		    }
		});
		JMenu filterBySubmenu = new JMenu("Filter By:");
		filterBySubmenu.add(favorites);
		filterBySubmenu.add(expiration);
		filterBySubmenu.add(lowStock);
		filterBySubmenu.add(leftovers);
		JMenu editMenu = new JMenu("Edit");
		editMenu.add(copy);
		editMenu.add(cut);
		editMenu.add(paste);
		editMenu.addSeparator();
		editMenu.add(search);
		editMenu.add(restore);
		editMenu.add(filterBySubmenu);
		menuBar.add(editMenu);
		// Window menu
		JMenu theme = new JMenu("Theme");
		JMenuItem fontStyle = new JMenuItem(new AbstractAction("Font Style")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Font Style. Allows the user to select a font style."); 
		    }
		});
		JMenuItem fontColour = new JMenuItem(new AbstractAction("Font Colour")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Font Colour. Allows the user to select a font colour."); 
		    }
		});
		JMenu background = new JMenu("Background");
		JMenuItem primary = new JMenuItem(new AbstractAction("Primary Colour")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Background -> Primary Colour. "
		        		+ "Allows the user to select the primary background colour."); 
		    }
		});
		JMenuItem secondary = new JMenuItem(new AbstractAction("Secondary Colour")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Background -> Secondary Colour. "
		        		+ "Allows the user to select the secondary background colour."); 
		    }
		});
		JMenuItem swap = new JMenuItem(new AbstractAction("Swap")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Background -> Swap. "
		        		+ "Allows the user to swap the primary and secondary background colours."); 
		    }
		});
		background.add(primary);
		background.add(secondary);
		background.add(swap);
		JMenuItem importTheme = new JMenuItem(new AbstractAction("Import")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Import. Allows the user to import a theme."); 
		    }
		});
		JMenuItem saveTheme = new JMenuItem(new AbstractAction("Save")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Theme -> Save. Allows the user to save the current theme."); 
		    }
		});
		theme.add(fontStyle);
		theme.add(fontColour);
		theme.add(background);
		theme.addSeparator();
		theme.add(importTheme);
		theme.add(saveTheme);
		JMenu settings = new JMenu("Settings");
		JMenuItem restorePt = new JMenuItem(new AbstractAction("Set Restore Point")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Settings -> Set Restore Point. "
		        		+ "Allows the user to set the amount of restores to keep."); 
		    }
		});
		JMenu units = new JMenu("Set units");
		JMenuItem metric = new JMenuItem(new AbstractAction("Metric")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Settings -> Set Units -> Metric. "
		        		+ "Allows the user to set the units of measurement to the metric system."); 
		    }
		});
		JMenuItem imperial = new JMenuItem(new AbstractAction("Imperial")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Settings -> Set Units -> Imperial. "
		        		+ "Allows the user to set the units of measurement to the imperial system."); 
		    }
		});
		JMenuItem customary = new JMenuItem(new AbstractAction("Customary")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Window -> Settings -> Set Units -> Customary. "
		        		+ "Allows the user to set the units of measurement to the customary system."); 
		    }
		});
		units.add(metric);
		units.add(imperial);
		units.add(customary);
		settings.add(units);
		settings.add(restorePt);
		JMenu windowMenu = new JMenu("Window");
		windowMenu.add(theme);
		windowMenu.add(settings);
		menuBar.add(windowMenu);
		// Help menu
		JMenu help = new JMenu("Help");
		JMenuItem link = new JMenuItem(new AbstractAction("Link (CTRL + ?)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Link. Opens a related webpage: http://www.myfridgefood.com/"); 
		    }
		});
		JMenu accessibility = new JMenu("Accessibility");
		JMenuItem fontSize = new JMenuItem(new AbstractAction("Font Size")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Accessibility -> Font Size. Allows the user to edit the font size."); 
		    }
		});
		JMenuItem resolution = new JMenuItem(new AbstractAction("Resolution")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Accessibility -> Resolution. Allows the user to adjust the resolution."); 
		    }
		});
		JMenuItem invert = new JMenuItem(new AbstractAction("Invert")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Accessibility -> Invert. Inverts the colours being displayed."); 
		    }
		});
		JMenuItem bold = new JMenuItem(new AbstractAction("Bold")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Accessibility -> Bold. Bolds all text."); 
		    }
		});
		accessibility.add(fontSize);
		accessibility.add(resolution);
		accessibility.add(invert);
		accessibility.add(bold);
		help.add(accessibility);
		JMenuItem feedback = new JMenuItem(new AbstractAction("Feedback (CTRL + H)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Feedback (CTRL + H). Opens a window to allow the user provide feedback."); 
		    }
		});
		JMenuItem donate = new JMenuItem(new AbstractAction("Donate (CTRL + $)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Donate (CTRL + $). Allows the user to donate."); 
		    }
		});
		help.add(feedback);
		help.add(donate);
		menuBar.add(help);
		
		// ----- TOOL BAR -----
		JToolBar toolBar = new JToolBar("Tool Bar");
		JButton searchTool = new JButton(new AbstractAction("Search")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Search (CTRL + F). Allows the user to search for an item in the database.");
		    }
		});
		searchTool.setIcon(Resources.getImage("search.png"));
		searchTool.setFont(new Font("Arial", Font.PLAIN, 15));
		searchTool.setVerticalTextPosition(SwingConstants.TOP);
		searchTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(searchTool);
		JButton filterTool = new JButton(new AbstractAction("Filter")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Filter. Allows the user to choose criteria to filter by."); 
		    }
		});
		filterTool.setIcon(Resources.getImage("filter.png"));
		filterTool.setFont(new Font("Arial", Font.PLAIN, 15));
		filterTool.setVerticalTextPosition(SwingConstants.TOP);
		filterTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(filterTool);
		toolBar.addSeparator();
		JButton settingsTool = new JButton(new AbstractAction("Settings")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Settings. Opens the settings window for the application."); 
		    }
		});
		settingsTool.setIcon(Resources.getImage("settings.png"));
		settingsTool.setFont(new Font("Arial", Font.PLAIN, 15));
		settingsTool.setVerticalTextPosition(SwingConstants.TOP);
		settingsTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(settingsTool);
		JButton restoreTool = new JButton(new AbstractAction("Restore")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Restore (CTRL + Z). Restores the most recently deleted item."); 
		    }
		});
		restoreTool.setIcon(Resources.getImage("restore.png"));
		restoreTool.setFont(new Font("Arial", Font.PLAIN, 15));
		restoreTool.setVerticalTextPosition(SwingConstants.TOP);
		restoreTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(restoreTool);
		JButton boldTool = new JButton(new AbstractAction("Bold")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Bold. Bolds all text."); 
		    }
		});
		boldTool.setIcon(Resources.getImage("bold.png"));
		boldTool.setFont(new Font("Arial", Font.PLAIN, 15));
		boldTool.setVerticalTextPosition(SwingConstants.TOP);
		boldTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(boldTool);
		toolBar.setBorder(new EmptyBorder(0, 100, 10, 100));
		
		// Quit Menu item action listener disabled to make testing faster
		// Add ActionListener to Quit
		/*quitItem.addActionListener(new AbstractAction()
		{
		    public void actionPerformed(ActionEvent a)
		    {
			    	PrintWriter pwriter;
					try {
						pwriter = new PrintWriter("menu-actions.txt");
					    	String actions = "-- Menu Bar: --" + "\n";
				        actions += "File Menu:" + "\n";
				        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
				        PrintStream pstream = new PrintStream(ostream);
				        PrintStream previous = System.out;
				        
				        System.setOut(pstream);
				        openItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        saveItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        printAllItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        printRecipesItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        printGroceriesItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        printFridgeItem.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        copy.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        cut.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        paste.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        search.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        restore.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        favorites.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        expiration.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        lowStock.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        leftovers.doClick();
				        System.setOut(previous);

				        System.setOut(pstream);
				        fontStyle.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        fontColour.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        importTheme.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        saveTheme.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        primary.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        secondary.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        swap.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        restorePt.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        metric.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        imperial.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        customary.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        link.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        fontSize.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        resolution.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        invert.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        bold.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        feedback.doClick();
				        System.setOut(previous);
				        
				        System.setOut(pstream);
				        donate.doClick();
				        System.setOut(previous);
				        
				        pwriter.print(ostream.toString());
				        pwriter.close();
				        System.exit(0);
					} catch (FileNotFoundException e) {
						System.out.println("File could not be opened.");
						e.printStackTrace();
					}
		    }
		});*/
		
		frame.getContentPane().add(toolBar, BorderLayout.PAGE_END);
		// Disabled printers for window design assignment
		/*ActionListener favListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("favorites filter checkbox pressed. value: " + favoritesBox.isSelected());
			}
		};     favoritesBox.addActionListener(favListener);
		
		ActionListener expiredListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("expired filter checkbox pressed. value: " + expiredBox.isSelected());
			}
		};     expiredBox.addActionListener(expiredListener);
		
		ActionListener lowListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("low stock filter checkbox pressed. value: " + lowBox.isSelected());
			}
		};     lowBox.addActionListener(lowListener);
		
		ActionListener leftoversListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				System.out.println("leftovers filter checkbox pressed. value: " + leftoversBox.isSelected());
			}
		};     leftoversBox.addActionListener(leftoversListener);*/
		
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
		
	// --------------------------------------
    	// PERSONAS & SCENARIOS
	// --------------------------------------
	/*JFrame			personas = new JFrame("Senarios");
	
	//creates the titles and senarios panels
	JPanel			listPane = new JPanel(new BorderLayout());
	JPanel			descriptPane = new JPanel(new BorderLayout());
	
	//JText Area string
	String text = "default";
	
	//titles for each pane
	listPane.setBorder(BorderFactory.createTitledBorder("scenarios"));
	descriptPane.setBorder(BorderFactory.createTitledBorder("descriptions"));
	
	//set up frame
	personas.setBounds(150, 100, 700, 600);
	personas.getContentPane().setLayout(new BorderLayout());
	
	//add things to frame
	personas.getContentPane().add(listPane, BorderLayout.WEST);
	personas.getContentPane().add(descriptPane, BorderLayout.CENTER);
	
	ArrayList<String> desc = Resources.getLines("scenarios/descriptions.txt");
	ArrayList<String> tit  = Resources.getLines("scenarios/titles.txt");
	
	if (desc.isEmpty()) {
		desc.add("default ");
	}
	if (tit.isEmpty()) {
		tit.add("default ");
	}

	//list
	//String[] sen = new String[] {"long selections" ,"sen 2", "sen 3"};
	DefaultListModel<String> dfl = new DefaultListModel<String>();
	for (int i = 0; i < tit.size(); i++) {
		dfl.addElement(tit.get(i));
	}
	
	JList			scenarios = new JList(dfl);
	scenarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scenarios.setLayoutOrientation(JList.VERTICAL);
	scenarios.setVisibleRowCount(-1);
	scenarios.setSelectedIndex(0);
	
	//JTextArea
	JTextArea		descriptions = new JTextArea(text);
	descriptions.setFont(new Font("Serif", Font.ITALIC, 16));
	descriptions.setLineWrap(true);
	descriptions.setWrapStyleWord(true);
	descriptions.setText(desc.get(scenarios.getSelectedIndex()));
	
	//selection listener
	ListSelectionListener sl = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				
				descriptions.setText(desc.get(scenarios.getSelectedIndex()));
			}
		}
	};
	
	scenarios.addListSelectionListener(sl);
	listPane.add(scenarios, BorderLayout.CENTER);
	descriptPane.add(descriptions, BorderLayout.CENTER);
	
	//sets personas frame to visible and end on exit
	personas.setVisible(true);
	personas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	personas.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
	});*/
	
	// ------------------------------------------------------
	// SURVEY AND ANALYSIS
	// ------------------------------------------------------
	/*JFrame			survey 	= new JFrame("Survey and Analysis");
	survey.setBounds(50, 50, 850, 700);
	survey.getContentPane().setLayout(new BorderLayout());
	
	JPanel 			mainPane = new JPanel();
	mainPane.setLayout(new GridLayout(0,1));
	survey.add(mainPane);

	
	//Question 1 pane
	JPanel			questionPane1		= new JPanel();
    questionPane1.setBorder(BorderFactory.createLineBorder(Color.black));
	questionPane1.setLayout(new GridLayout(5,1));
	mainPane.add(questionPane1);
	
	//Question 1 Responses
	JLabel			question1	= new JLabel("Q1: Which of these, in your opinion, is the biggest problem?");
	JCheckBox		box1 = new JCheckBox("Not knowing what foods I have to make meals with");
	JCheckBox		box2 = new JCheckBox("Not knowing what I need to go grocery shopping for");
	JCheckBox		box3 = new JCheckBox("Accidentally letting my food go bad");
	
	//Q1 add	
	questionPane1.add(question1);
	questionPane1.add(box1);
	questionPane1.add(box2);
	questionPane1.add(box3);
	
	//RANGE
	JPanel range = new JPanel(new GridLayout(0, 1));
	range.setBorder(BorderFactory.createLineBorder(Color.black));
	mainPane.add(range);
	
	JLabel question = new JLabel("Q2: How much of the food that you buy would you estimate that you waste/throw out?");

	range.add(question);
	JRadioButton button1 = new JRadioButton("0-10%");
	JRadioButton button2 = new JRadioButton("10-20%");
	JRadioButton button3 = new JRadioButton("20-30%");
	JRadioButton button4 = new JRadioButton("Over 30%");
	
	ButtonGroup group = new ButtonGroup();
    group.add(button1);
    group.add(button2);
    group.add(button3);
    group.add(button4);
    
    range.add(button1);
    range.add(button2);
    range.add(button3);
    range.add(button4);

    //SPINNER
    //panel for the # of groceries questions
    JPanel spinPanel = new JPanel(new BorderLayout());
    mainPane.add(spinPanel);
    //adds the questions text
    JLabel spinQuestion = new JLabel("Q3: How many people do you share a kitchen with?");
    //creates the spinner model
    SpinnerNumberModel bagModel = new SpinnerNumberModel(0, 0, 100, 1);
    //creates spinner
    JSpinner groSpinner = new JSpinner(bagModel);
    //adds the question text to the panel
    spinPanel.add(spinQuestion, BorderLayout.NORTH);
    //adds the spinner to panel
    spinPanel.add(groSpinner, BorderLayout.SOUTH);
    //adds a border to the panel
    spinPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    JPanel			questPanel = new JPanel();
    questPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    mainPane.add(questPanel);
    GridLayout		questionLayout = new GridLayout(2,1);
    questPanel.setLayout(questionLayout);
    JTextArea		questionArea = new JTextArea("Q4: If you could, what information would you like to always know about "
    				+ "your kitchen, in order of importance? For example food stock, expiration dates, etc.");
    questionArea.setEditable(false);
    questionArea.setLineWrap(true);
    questionArea.setOpaque(false);
    JTextArea		answerArea = new JTextArea();
    answerArea.setBorder(BorderFactory.createLineBorder(Color.black));
    questPanel.add(questionArea);
    questPanel.add(answerArea);

    // SLIDER
 	JPanel q4 = new JPanel(new GridLayout(2, 1));
 	mainPane.add(q4);

 	// Setup parameters for slider
 	int sliderMin = 0;
 	int sliderMax = 4;
 	int sliderInitial = 2;
 	// Create slider
 	JSlider usefullnessSlider = new JSlider(JSlider.HORIZONTAL, sliderMin,
 		sliderMax, sliderInitial);

 	// Create labels for slider in hashtable. Integer value corresponds to its
 	// cooresponding location on slider
 	Hashtable<Integer, JLabel> sliderTable = new Hashtable<Integer, JLabel>();
 	sliderTable.put(new Integer(0), new JLabel("Very Displeased"));
 	sliderTable.put(new Integer(1), new JLabel("Somewhat Displeased"));
 	sliderTable.put(new Integer(2), new JLabel("Neutral"));
 	sliderTable.put(new Integer(3), new JLabel("Relatively Pleased"));
 	sliderTable.put(new Integer(4), new JLabel("Very Pleased"));

 	// Add labels to slider
 	usefullnessSlider.setLabelTable(sliderTable);

 	// Make the slider more useful by adding the following
 	usefullnessSlider.setSnapToTicks(false);
 	usefullnessSlider.setMajorTickSpacing(10);
 	usefullnessSlider.setPaintLabels(true);

 	// Setup the question
 	String scaleQuestionText = "<html> On a scale of 1-5, how pleased are you with the"
 			+ "<BR>organization of your fridge and pantry?</html>";

 	JLabel scaleQuestion = new JLabel(scaleQuestionText);

 	// Add the question to the frame
 	q4.add(scaleQuestion);
 	q4.add(usefullnessSlider);
 	q4.setBorder(BorderFactory.createLineBorder(Color.black));
    
    JButton finish = new JButton("Finish");
    mainPane.add(finish);

	ActionListener finishListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			
				// RANGE QUESTION CHECK
				boolean rangeIsClicked = false;
				String rangeAnswer = "";
				if (button1.isSelected())
				{
					rangeAnswer = button1.getText();
					rangeIsClicked = true;
				}
				else if (button2.isSelected())
				{
					rangeAnswer = button2.getText();
					rangeIsClicked = true;
				}
				else if (button3.isSelected())
				{
					rangeAnswer = button3.getText();
					rangeIsClicked = true;
				}
				else if (button4.isSelected())
				{
					rangeAnswer = button4.getText();
					rangeIsClicked = true;
				}
				
				boolean selectIsClicked = false;
				String selectAnswer = "";
				if (box1.isSelected())
				{
					selectAnswer += box1.getText() + "\n";
					selectIsClicked = true;
				}
				if (box2.isSelected())
				{
					selectAnswer += box2.getText() + "\n";
					selectIsClicked = true;
				}
				if (box3.isSelected())
				{
					selectAnswer += box3.getText() + "\n";
					selectIsClicked = true;
				}
				
				int spinnerVal = (Integer) groSpinner.getValue();
				
				
				int sliderVal = usefullnessSlider.getValue();
				String sliderString = sliderTable.get(sliderVal).getText();
				
						
				
				// FINAL WRITE
				if (selectIsClicked && rangeIsClicked && !(answerArea.getText().isEmpty()))
				{
					BufferedWriter output = null;
				    try {
				        File file = new File("survey_results.txt");
				        output = new BufferedWriter(new FileWriter(file));
				        
				        output.write(question1.getText() + "\n");
				        output.write(selectAnswer + "\n");
				        
				        output.write(question.getText() + "\n");
				        output.write(rangeAnswer + "\n\n");
				        
				        output.write(spinQuestion.getText() + "\n");
				        output.write(spinnerVal + "\n\n");
				        
				        output.write(questionArea.getText() + "\n");
				        output.write(answerArea.getText() + "\n\n");
				        
				        output.write("Q5: How useful would an application that keeps "
				        		+ "track of the items in your fridge and pantry and allows "
				        		+ "you to set up reminders when you are running low on a "
				        		+ "specific item be to you?\n");
				        output.write(sliderString + "\n\n");

				    } catch ( IOException e1 ) {
				        e1.printStackTrace();
				    } finally {
				      if ( output != null ) {
				        try {
							output.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				      }
				    }
				    
				  	survey.dispose();
				    survey.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(survey, "Please answer all questions.");
				}
			
		}
	};     finish.addActionListener(finishListener);
	*/
		
		//sets personas frame to visible and end on exit
		/* survey.setVisible(true);
		survey.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		survey.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});*/
	}

	//**********************************************************************
	// Private Inner Classes
	//**********************************************************************
	private static final class HelloPanel extends JPanel
	{
		private final String	message;

		public HelloPanel(String message)
		{
			this.message = ((message != null) ? message : "");
		}

		public HelloPanel()
		{
			this("");
		}

		public void	paintComponent(Graphics g)
		{
			FontMetrics	fm = g.getFontMetrics(FONT);
			int			fw = fm.stringWidth(message);
			int			fh = fm.getMaxAscent() + fm.getMaxDescent();
			int			x = (getWidth() - fw) / 2;
			int			y = (getHeight() - fh) / 2;
			Rectangle		r = new Rectangle(x, y, fw + 4, fh + 1);

			if (FILL_COLOR != null)
			{
				g.setColor(FILL_COLOR);
				g.fillRect(r.x, r.y, r.width - 1, r.height - 1);
			}

			if (EDGE_COLOR != null)
			{
				g.setColor(EDGE_COLOR);
				g.drawRect(r.x, r.y, r.width - 1, r.height - 1);

				g.setFont(FONT);
				g.drawString(message, r.x + 2, r.y + fm.getMaxAscent());
			}
		}
	}
}

//******************************************************************************
