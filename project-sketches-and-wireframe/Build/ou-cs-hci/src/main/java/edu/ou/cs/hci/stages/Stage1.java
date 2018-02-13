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
import java.util.ArrayList;
import edu.ou.cs.hci.resources.Resources;

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

		//create the scenarios frame with relevant information. Comment
		//out the below line to not generate Scenarios frame
		createScenarios();

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

	/*************************************
	*	Create scenarios frame with relavent information
	* from /hci/resources/scenarios and
	* /hci/resources/personas
	*************************************/
	public static void createScenarios()
	{
		//scenarios and personas JFrame
		JFrame spFrame = new JFrame("Scenarios");

		//sets the defualt size of the main window
		spFrame.setBounds(700, 50, 600, 400);
		spFrame.getContentPane().setLayout(new BorderLayout());

		//get titles of scenarios from /resources/scenarios/titles.txt using
		//Resources.java
		ArrayList<String> sTitles = Resources.getLines("scenarios/titles.txt");
			//handle potential null error by displaying error message in JList
			if (sTitles.get(0) == null)
			{
				sTitles.set(0, "ERROR: No data found in provided path");
			}

		//get descriptions of scenarios from /resources/scenarios/descriptions.txt
		//using Resources.java
		ArrayList<String> sDescription = Resources.getLines("scenarios/descriptions.txt");
			//handle potential null error by displaying error message in JTextArea
			if (sDescription.get(0) == null)
			{
				sDescription.set(0, "ERROR: No data found in provided path");
			}

		//create and populate scenarios title jlist for right side of split pane
		//also ensure that JList is SINGLE_SELECTION
		JList<Scenario> scenarioTitle = new JList<Scenario>();
		DefaultListModel<Scenario> model = new DefaultListModel<>();
		scenarioTitle.setModel(model);
		scenarioTitle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//create non-editable text area for right side of split pane
		//also ensure that JTextArea is not editable
		JTextArea scenarioText = new JTextArea();
		scenarioText.setEditable(false);
		scenarioText.setLineWrap(true);
		scenarioText.setWrapStyleWord(true);

		//populate JList scenarioTitle with Scenario objects created from
		//ArrayList's sTitles and sDescription
		for (int i = 0; i < sTitles.size(); i++)
		{
			model.addElement(new Scenario(sTitles.get(i), sDescription.get(i)));
		}

		//add listener to update stuff and thangs in the JTextArea
		scenarioTitle.getSelectionModel().addListSelectionListener(e -> {
			scenarioText.setText(null);
			Scenario s = scenarioTitle.getSelectedValue();
			scenarioText.append("Description: \n" + s.getDescription());
		});

		// Select the first title by default
		scenarioTitle.setSelectedIndex(0);

		//create split pane
		JSplitPane scenarioPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(scenarioTitle), scenarioText);

		//main panel
		JPanel scenariosPanel = new JPanel(new BorderLayout());
		scenariosPanel.add(scenarioPane, BorderLayout.CENTER);

		//add dat stuff to da frame
		spFrame.add(scenariosPanel, BorderLayout.CENTER);

		spFrame.setVisible(true);
		spFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/*************************************
	*	Private class to hold Scenario information
	* including the title and a relavent description
	*************************************/
	private static class Scenario
	{
		String title;	// Title of scenario
		String description;	// Description of scenario

		// Base constructor
		public Scenario(String t, String d)
		{
			title = t;
			description = d;
		}

		// Getters and setters
		public String getTitle()
		{
			return title;
		}

		public String getDescription()
		{
			return description;
		}

		public void setTitle(String t)
		{
			title = t;
		}

		public void setDescription(String d)
		{
			description = d;
		}

		// Override toString method to just show the scenario title
		@Override
		public String toString()
		{
			return title;
		}
	}

}

//******************************************************************************
