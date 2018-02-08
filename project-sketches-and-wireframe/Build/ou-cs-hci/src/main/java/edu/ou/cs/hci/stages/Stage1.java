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
import javax.swing.*;

/**
 * The <CODE>BuildTest</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */

public final class Stage1
{
	//Public Class Members
	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 36);
	private static final Color	FILL_COLOR = Color.YELLOW;
	private static final Color	EDGE_COLOR = Color.RED;

	//main
	public static void main(String[] args)
	{
		//creates the base JFrame on which everything will be displayed
		JFrame			frame = new JFrame("FridgTrackr");

		//creates the 3 category panels
		JPanel			recipes = new JPanel(new BorderLayout());
		JPanel			fridge = new JPanel(new BorderLayout());
		JPanel			groceries = new JPanel(new BorderLayout());

		//adds a button to each of the 3 category panels so new data can e added
		JButton			rAdd = new JButton("add");
		JButton			fAdd = new JButton("add");
		JButton			gAdd = new JButton("add");

		//adds a title to each category panel
		recipes.setBorder(BorderFactory.createTitledBorder("recipes"));
		fridge.setBorder(BorderFactory.createTitledBorder("fridge"));
		groceries.setBorder(BorderFactory.createTitledBorder("groceries"));

		//sets the defualt size of the main window
		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());

		//creates the pane that will store the category tabs
		JTabbedPane tabs = new JTabbedPane();
		//sets icons for tabs
		Icon fridgeIcon = new ImageIcon(Stage1.class.getResource("refrigerator.png"));
		Icon recipesIcon = new ImageIcon(Stage1.class.getResource("contract.png"));
		Icon groceriesIcon = new ImageIcon(Stage1.class.getResource("groceries.png"));
		//adds tabs to JTabbedPane
		tabs.addTab("Fridge", fridgeIcon, fridge);
		tabs.addTab("Recipes", recipesIcon, recipes);
		tabs.addTab("Groceries", groceriesIcon, groceries);
		//adds the JTabbedPane to the base pane
		frame.getContentPane().add(tabs, BorderLayout.CENTER);

		//creates the content of the fridge category panel
		String[] colName = new String[] {"Name" ,"Amount", "Delete"};
		Object[][] products = new Object[][] {
                { "Apples" ,"15", "[x]" },
                { "Oranges" ,"20", "[x]"},
                { "Peaches" ,"10", "[x]"},
							};
		//creates a table to hold the fridge panel data
    	JTable fridgeTable = new JTable( products, colName );
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
    	recipes.add( new JScrollPane(table2));

		//creates the filters
		JPanel			filterPanel = new JPanel(new FlowLayout());
		JCheckBox		favoritesBox = new JCheckBox();
		JLabel			favoritesLabel = new JLabel("Favorites");
		JCheckBox		expiredBox = new JCheckBox();
		JLabel			expiredLabel = new JLabel("Expired");
		JCheckBox		lowBox = new JCheckBox();
		JLabel			lowLabel = new JLabel("Low Stock");
		JCheckBox		leftoversBox = new JCheckBox();
		JLabel			leftoversLabel = new JLabel("Leftovers");

		//adds the filters to the filter panel
		filterPanel.add(favoritesBox);
		filterPanel.add(favoritesLabel);
		filterPanel.add(expiredBox);
		filterPanel.add(expiredLabel);
		filterPanel.add(lowBox);
		filterPanel.add(lowLabel);
		filterPanel.add(leftoversBox);
		filterPanel.add(leftoversLabel);
		//adds the filter panel to the base frame
		frame.getContentPane().add(filterPanel, BorderLayout.NORTH);

    	//adds the add buttons
    	fridge.add(fAdd, BorderLayout.PAGE_END);
    	recipes.add(rAdd, BorderLayout.PAGE_END);
    	groceries.add(gAdd, BorderLayout.PAGE_END);

		//sets the base frame to visible & to end on exit
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	}

}

//******************************************************************************
