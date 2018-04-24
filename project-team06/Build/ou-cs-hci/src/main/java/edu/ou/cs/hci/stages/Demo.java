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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Demo</CODE> class.<P>
 *
 * @author  Chris Weaver & Group 6
 * @version %I%, %G%
 */
public final class Demo
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************
	public static boolean fridgeEdited = false;
	public static boolean groceryEdited = false;
	public static boolean recipeEdited = false;

	public static final String unfilledStar = "\u2606";
	public static final String filledStar = "\u2605";

	public static LocalDate today = LocalDate.now();

	public static String[] UNITS = {"Cup(s)","g","kg","L","lb",
            "ml","oz","tsp","Tbsp"};


	public static String[]	COLUMNS =
	{
		"key",
		filledStar,
		"Name",
		"Amount",
		"Days Left",
		"Leftovers?",
	};

	public static String[]	FRIDGE_COLUMNS =
	{
		filledStar,
		"Name",
		"Amount",
		"Days Left",
		"Leftovers?",
	};

	public static String[]	GROCERY_COLUMNS =
	{
			"Name",
			"Amount",
	};

	public static String[]	RECIPE_COLUMNS =
	{
		"Name",
		"Path",
		"Ingredients",
		"Image Path",
	};


	//**********************************************************************
	// Class helper methods
	//**********************************************************************

	public static boolean isFavorite (String string) {
		if (string.equals(filledStar)) {
			return true;
		}

		return false;
	}

	public static boolean isLeftovers (String string) {
		if (string.equals("Yes")) {
			return true;
		}

		return false;
	}



	//**********************************************************************
	// Main
	//**********************************************************************

	//main
	public static void main(String[] args) throws MalformedURLException
	{


		//MAIN WINDOW creates the base JFrame on which everything will be displayed
		JFrame			frame = new JFrame("FridgTrackr");

		// Top panel
		JPanel top = new JPanel(new BorderLayout());
		JLabel welcome = new JLabel("Welcome to FridgTrackr!");
		welcome.setIcon(Resources.getImage("icons/FridgTrackr_Logo.png"));
		welcome.setFont(new Font("Lucida Console", Font.PLAIN, 14));
		welcome.setBorder(new EmptyBorder(5, 95, 5, 80));
		Icon backIcon = Resources.getImage("icons/back.png");
		JButton back = new JButton();
		back.setIcon(backIcon);
		back.setPreferredSize(new Dimension(100, 50));
		back.setFont(new Font("Arial", Font.PLAIN, 25));
		Icon addIcon = Resources.getImage("icons/add.png");
		JButton add = new JButton();
		add.setIcon(addIcon);
		add.setFont(new Font("Arial", Font.PLAIN, 25));
		add.setPreferredSize(new Dimension(100, 50));
		top.add(back, BorderLayout.LINE_START);
		top.add(welcome, BorderLayout.CENTER);
		top.add(add, BorderLayout.LINE_END);

		/*
		 * Add Button
		 */





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
		// creates icons
		Icon fridgeIcon = Resources.getImage("icons/refrigerator.png");
		Icon recipesIcon = Resources.getImage("icons/contract.png");
		Icon groceriesIcon = Resources.getImage("icons/groceries.png");
		//adds tabs to JTabbedPane
		tabs.addTab("Fridge", fridgeIcon, fridge);
		tabs.addTab("Recipes", recipesIcon, recipes);
		tabs.addTab("Groceries", groceriesIcon, groceries);
		//adds the JTabbedPane to the base pane
		frame.getContentPane().add(tabs, BorderLayout.CENTER);

		/*
		 * Changes the table so that the default highlighting is yellow
		 */
		class MyTable extends JTable{
			MyTable(Object[][] obj, String[] cols){
						            super(obj,cols);

			}
			@Override
			public void valueChanged(ListSelectionEvent e) {
				this.setSelectionBackground(Color.decode("#ffcc00"));

			}
		}

		/*
		 * Changes the renderer so that the default highlighting is yellow
		 * the soon to be "expired" item which is less than 3 days left are red
		 * and when a red is highlighted it is orange instead of yellow
		 * I did a lot of googling and reading to figure this one out
		 */
		class MyRenderer extends DefaultTableCellRenderer
		{
		    public MyRenderer()
		    {
		        super();
		        setOpaque(true);
		    }
		    public Component getTableCellRendererComponent(JTable table, Object value,
		            boolean isSelected, boolean hasFocus, int row, int column)
		    {


		    		String darkRed ="#ff4949";
		    		String orange = "#ff6600";
		    		String yellow = 	"#ffcc00";

		    		String cellValue = (String)table.getValueAt(row, 3);

		    		String cell [] = cellValue.split(" ");

		        //if(Integer.parseInt((String)table.getValueAt(row, 3)) < 3)
		    		if(cell[0].equals("expired"))
		    		{
		        		setForeground(Color.black);
		        		if (isSelected)
		        		{
		        			setBackground(Color.decode(orange));
		        		}
		        		else {
				            setBackground(Color.decode(darkRed));
		        		}
		        }
		    		else if (cell[0].equals("null"))
		    		{
	        			if (isSelected)
	        			{
	        				setBackground(Color.decode(yellow));
	        			}
	        			else {
			            setBackground(Color.white);
	        			}
		        }
		    		else if (Integer.parseInt(cell[0]) < 3)
		    		{
		        		setForeground(Color.black);
		        		if (isSelected)
		        		{
		        			setBackground(Color.decode(orange));
		        		}
		        		else {
				            setBackground(Color.decode(darkRed));
		        		}
		        }
		        else
		        {
	        			if (isSelected)
	        			{
	        				setBackground(Color.decode(yellow));
	        			}
	        			else {
			            setBackground(Color.white);
	        			}
		        }
		        setText(value.toString());
		        return this;
		    }
		}

		JTable table = new JTable();

		MyRenderer renderer = new MyRenderer();
		//table.setDefaultRenderer(Object.class, renderer);
		//table.getColumnModel().getColumn(0).setMaxWidth(25);
		//table.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		//table.getTableHeader().setFont(new Font("Lucida Console", Font.PLAIN, 13));
		//table.setRowHeight(20);

		//URL	url = Resources.getResource("data/save.csv");


		/*

		URL	url = null;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File myFile = fileChooser.getSelectedFile();
		    url = myFile.toURI().toURL();
		}



		try
		{
			// Create a reader for the CSV
			InputStream		is = url.openStream();
			InputStreamReader	isr = new InputStreamReader(is);
			BufferedReader		r = new BufferedReader(isr);

			// Use the Apache Commons CSV library to read records from it
			CSVFormat			format = CSVFormat.DEFAULT;
			CSVParser			parser = CSVParser.parse(r, format);
			java.util.List<CSVRecord>	records = parser.getRecords();

			// Allocate a 2-D array to keep the rows and columns in memory
			String[][]	values = new String[records.size()][COLUMNS.length];

			for (CSVRecord record : records)	// Loop over the rows...
			{
				Iterator<String>	k = record.iterator();
				int				i = (int)record.getRecordNumber() - 1;
				int				j = 0;		// Column number

				// Print each record to the console
				System.out.println("***** #" + i + " *****");

				while (k.hasNext())			// Loop over the columns...
				{
					values[i][j] = k.next();	// Grab each cell's value

					// Print each value to the console...
					System.out.println(COLUMNS[j] + " = " + values[i][j]);
					j++;
				}

				System.out.println();

				// ...and have the table show the entire value array.
				//table.setModel(new DefaultTableModel(values, COLUMNS));
				dtm = new DefaultTableModel(values, COLUMNS);
				table.setModel(dtm);

			}

			is.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		*/

		int fridgeCount = 0;
		int groceryCount = 0;
		int recipeCount = 0;

		//fridge Model
		CustomTableModel fridgeModel = new CustomTableModel(FRIDGE_COLUMNS);
		CustomTableModel favoritesModel = new CustomTableModel(FRIDGE_COLUMNS);
		CustomTableModel amountModel = new CustomTableModel(FRIDGE_COLUMNS);
		CustomTableModel expiredModel = new CustomTableModel(FRIDGE_COLUMNS);
		CustomTableModel leftoversModel = new CustomTableModel(FRIDGE_COLUMNS);



		//grocery Model
		CustomTableModel groceryModel = new CustomTableModel(GROCERY_COLUMNS);

		//recipe Model
		CustomTableModel recipeModel = new CustomTableModel(RECIPE_COLUMNS);

		JTable fridgeTable = new JTable(fridgeModel);
		//fridgeTable.setDefaultRenderer(Object.class, renderer);
		JTable groceryTable = new JTable(groceryModel);
		JTable recipeTable = new JTable(recipeModel);

		fridgeTable.setDefaultRenderer(String.class, renderer);

		//table listeners to see if data has changed/tables interacted with
		fridgeModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					fridgeEdited = true;
				}
			}
		});
		groceryModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					groceryEdited = true;
				}
			}
		});
		recipeModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					recipeEdited = true;
				}
			}
		});

		//creating the recipes stuff
		JPanel			listPane = new JPanel(new BorderLayout());
		JPanel			descriptPane = new JPanel(new BorderLayout());

		//JText Area string
		String text = "default";

		//titles for each pane
		listPane.setBorder(BorderFactory.createTitledBorder("Titles"));
		descriptPane.setBorder(BorderFactory.createTitledBorder("Description"));

		//set up tab
		recipes.add(listPane, BorderLayout.WEST);
		recipes.add(descriptPane, BorderLayout.CENTER);

		ArrayList<String> descriptList = new ArrayList<String>();
		ArrayList<String> listList = new ArrayList<String>();

		/*
		//creating the tables to populate the tabs
		for (int i = 0; i < table.getRowCount(); i++) {

			//for fridge items
			if (Integer.parseInt((String)table.getValueAt(i, 0)) == 0) {
				fridgeCount++;

				//cell variables
				String fav = ((Integer.parseInt((String)table.getValueAt(i, 1)) == 1) ? "★" : "☆");
				String name = (String)table.getValueAt(i, 2);
				boolean isAmount = ( ((String)table.getValueAt(i, 3)).equals("null") ? false : true );
				String amount = (String)table.getValueAt(i, 3);
				boolean isDate = ( ((String)table.getValueAt(i, 4)).equals("null") ? false : true );
				String daysleft = (String)table.getValueAt(i, 4);
				String leftovers = ((Integer.parseInt((String)table.getValueAt(i, 5)) == 1) ? "Yes" : "No");

				if (isDate) {
					String [] date = ((String)table.getValueAt(i, 4)).split("/");
					LocalDate expirationDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));

					//Period p = Period.between(today, expirationDate);
					long period1 = ChronoUnit.DAYS.between(today, expirationDate);
					daysleft = ((period1 < 1) ? "expired" : Long.toString(period1));

				}

				if (isAmount) {
					String [] amountAndUnits = ((String)table.getValueAt(i, 3)).split(" ");

					if (Double.parseDouble(amountAndUnits[0]) <= 1) {
						amountModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
					}
				}


				if (fav.equals("★")) {
					favoritesModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
				}

				if (daysleft.equals("expired")) {
					expiredModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
				}

				if (leftovers.equals("Yes")) {
					leftoversModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
				}



				//adding a row to the fridge table
				fridgeModel.addRow(new Object[]{ fav, name, amount, daysleft, leftovers});
			}
			//for grocery items
			else if (Integer.parseInt((String)table.getValueAt(i, 0)) == 1) {
				groceryCount++;

				//cell variable by column
				String name = (String)table.getValueAt(i, 1);
				String amount = (String)table.getValueAt(i, 2);

				//adds row to grocery table
				groceryModel.addRow(new Object[] { name, amount});
			}
			//for recipe items
			else if (Integer.parseInt((String)table.getValueAt(i, 0)) == 2) {
				recipeCount++;

				//recipe variables
				String name = (String)table.getValueAt(i, 1);
				String path = (String)table.getValueAt(i, 2);
				String ingredients = (String)table.getValueAt(i, 3);
				String imagepath = (String)table.getValueAt(i, 4);

				listList.add(name);
				descriptList.add(path + "\n" + ingredients + "\n" + imagepath + "\n");

				recipeModel.addRow(new Object[] {name, path, ingredients, imagepath});
			}
		}*/

		//makes the recipe pane

		if (descriptList.isEmpty()) {
			descriptList.add("default ");
		}
		if (listList.isEmpty()) {
			listList.add("default ");
		}

		//list
		DefaultListModel<String> dfl = new DefaultListModel<String>();
		for (int i = 0; i < listList.size(); i++) {
			dfl.addElement(listList.get(i));
		}

		JList			recipeNames = new JList(dfl);
		recipeNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recipeNames.setLayoutOrientation(JList.VERTICAL);
		recipeNames.setVisibleRowCount(-1);
		recipeNames.setSelectedIndex(0);

		//JTextArea
		JTextArea		descriptions = new JTextArea(text);
		descriptions.setFont(new Font("Serif", Font.ITALIC, 16));
		descriptions.setLineWrap(true);
		descriptions.setWrapStyleWord(true);
		descriptions.setText(descriptList.get(recipeNames.getSelectedIndex()));

		//selection listener
		ListSelectionListener sl = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					descriptions.setText(descriptList.get(recipeNames.getSelectedIndex()));
				}
			}
		};

		recipeNames.addListSelectionListener(sl);
		listPane.add(recipeNames, BorderLayout.CENTER);
		descriptPane.add(descriptions, BorderLayout.CENTER);

		//MyRenderer renderer = new MyRenderer();
		//table.setDefaultRenderer(Object.class, renderer);
		fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
		fridgeTable.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.getTableHeader().setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.setRowHeight(20);
		fridge.add(new JScrollPane(fridgeTable));


		//groceryTable.getColumnModel().getColumn(0).setMaxWidth(100);
		groceryTable.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		groceryTable.getTableHeader().setFont(new Font("Lucida Console", Font.PLAIN, 13));
		groceryTable.setRowHeight(20);
		groceries.add(new JScrollPane(groceryTable));



		System.out.println("FRIDGE COUNT: " + fridgeCount + "\n");
		System.out.println("GROCERY COUNT: " + groceryCount + "\n");
		System.out.println("RECIPE COUNT: " + recipeCount + "\n");

		/*
		 * About page
		 */

		JWindow aboutWindow = new JWindow(frame);
		aboutWindow.setBounds(100, 100, 800, 600);

		JPanel aboutPanel = new JPanel();
		aboutPanel.setLayout(new BorderLayout());
		aboutWindow.add(aboutPanel);

		JEditorPane info;
		URL			html_url = Resources.getResource("about/about.html");

		try
		{
			// Try to load the about.html file in resources
			info = new JEditorPane(html_url);
		}
		catch (IOException ex)
		{
			// If loading fails, use a default message
			info = new JEditorPane("text/plain", "[Loading info failed.]");
		}

		// Setting the editor pane size to  the html layout
		info.setEditable(false);
		info.setPreferredSize(new Dimension(400, 400));
		//info.addHyperlinkListener(this);
		JButton close = new JButton(new AbstractAction("Close Window") {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				aboutWindow.setVisible(false);
			}

		}); aboutPanel.add(close, BorderLayout.NORTH);
		aboutPanel.add(new JScrollPane(info), BorderLayout.CENTER);






		/*
		 * Add Button Listener
		 */

		add.addActionListener(new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame addFrame	= new JFrame("Add to Fridge");
				JPanel fields	= new JPanel(new FlowLayout());
				//fields
				JTextField nameField = new JTextField("name"); //creates the name field
		        JTextField amountField = new JTextField("amount"); //creates the amount field
		        JCheckBox favField = new JCheckBox("Favorite?"); //creates the fav checkbox
		        JComboBox<String> addUnits = new JComboBox<String>(UNITS); //creates combo box
		        JButton finishButton = new JButton("Finished"); //creates finished button
		        JTextField dateField = new JTextField("XX/XX/XXXX"); //creates the name field
		        JCheckBox leftoverField = new JCheckBox("Leftovers?"); //creates the leftover checkbox
		        //add fields
		        fields.add(favField);
		        fields.add(nameField);
		        fields.add(amountField);
		        fields.add(addUnits);
		        fields.add(dateField);
		        fields.add(leftoverField);
		        fields.add(finishButton);
		        addFrame.add(fields);
		        addFrame.setSize(new Dimension(600, 200));
		        addFrame.setVisible(true);
		        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		        finishButton.addActionListener(new AbstractAction() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// return values to add to rows in the fridgeModel and large table
						String favFridgeRet = unfilledStar;
						String favTableRet = "0";
						String nameRet = null;
						String amountRet = null;
						String daysleftRet = null;
						String expireRet = null;
						String leftoverFridgeRet = "No";
						String leftoverTableRet = "0";
						boolean addFields = true;
						boolean isFavor = false;
						boolean isLow = false;
						boolean isExpired = false;
						boolean isLeft = false;

						//favorite field
						if(favField.isSelected()) {
							favFridgeRet = filledStar;
							favTableRet = "1";
							isFavor = true;
						}

						//name field
						if (!nameField.getText().equals("name")) {
							nameRet = nameField.getText();
						}
						else {
							addFields = false;
						}

						//amount field
						if (!amountField.getText().equals("amount")) {
							amountRet = amountField.getText() + " " + UNITS[addUnits.getSelectedIndex()];
							if (Double.parseDouble((String)amountField.getText()) <= 1) {

								isLow = true;
							}

						}
						else {
							addFields = false;
						}

						//expiration date field
						if (!dateField.getText().equals("XX/XX/XXXX") && (dateField.getText().length() == 10) ) {


							if (dateField.getText().charAt(2) == '/' && dateField.getText().charAt(5) == '/') {
								String [] dateArray = dateField.getText().split("/");


								LocalDate expirationDateRet = LocalDate.of(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]));

								long period2 = ChronoUnit.DAYS.between(today, expirationDateRet);
								daysleftRet = ((period2 < 1) ? "expired" : Long.toString(period2));

								if (daysleftRet.equals("expired")) {
									isExpired = true;
								}

								expireRet = (String) dateField.getText();

							}
							else {
								daysleftRet = "null";
								expireRet = "null";
							}

						}
						else {
							daysleftRet = "null";
							expireRet = "null";
						}

						if (leftoverField.isSelected()) {
							leftoverFridgeRet = "Yes";
							leftoverTableRet = "1";
							isLeft = true;
						}


						if (addFields) {

							if (isFavor) {
								favoritesModel.addRow(new Object[] {favFridgeRet, nameRet, amountRet, daysleftRet, leftoverFridgeRet});
							}
							if (isLow) {
								amountModel.addRow(new Object[] {favFridgeRet, nameRet, amountRet, daysleftRet, leftoverFridgeRet});
							}
							if (isExpired) {
								expiredModel.addRow(new Object[] {favFridgeRet, nameRet, amountRet, daysleftRet, leftoverFridgeRet});
							}
							if(isLeft) {
								leftoversModel.addRow(new Object[] {favFridgeRet, nameRet, amountRet, daysleftRet, leftoverFridgeRet});
							}

							fridgeModel.addRow(new Object[] {favFridgeRet, nameRet, amountRet, daysleftRet, leftoverFridgeRet});
							DefaultTableModel dtm = (DefaultTableModel) table.getModel();
							dtm.addRow(new Object[] {"0", favTableRet, nameRet, amountRet, expireRet, leftoverTableRet});
							table.setModel(dtm);

							addFrame.dispose();
						}
						else {
							JOptionPane.showMessageDialog(frame, "Please fill in the name & amount fields");
						}
					}

		        });
			}

		});



		// ----- FRIDGE TAB FILTER CHECKBOX PANEL -----
		JPanel			mid = new JPanel(new BorderLayout());
		JPanel			filterPanel = new JPanel(new GridLayout(1, 8));
		filterPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
		JCheckBox		favoritesBox = new JCheckBox();
		favoritesBox.setText(filledStar);
		//favoritesBox.setHorizontalAlignment(JCheckBox.RIGHT);
		//JLabel			favoritesLabel = new JLabel("Favorites");
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
		//filterPanel.add(favoritesLabel);
		filterPanel.add(lowBox);
		filterPanel.add(lowLabel);
		filterPanel.add(expiredBox);
		filterPanel.add(expiredLabel);
		filterPanel.add(leftoversBox);
		filterPanel.add(leftoversLabel);
		mid.add(filterPanel, BorderLayout.CENTER);
		fridge.add(mid, BorderLayout.NORTH);
		// ----- RECIPES TAB FILTER CHECKBOX PANEL -----
		JPanel			mid2 = new JPanel(new BorderLayout());
		JButton back2 = new JButton("\u2190");
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
		//recipes.add(mid2, BorderLayout.NORTH);

		// ----- MENU BAR -----
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		// File menu
		JMenuItem openItem = new JMenuItem(new AbstractAction("Open (CTRL + O)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		    		URL	url = null;

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File myFile = fileChooser.getSelectedFile();
				    try {
						url = myFile.toURI().toURL();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}



				try
				{
					// Create a reader for the CSV
					InputStream		is = url.openStream();
					InputStreamReader	isr = new InputStreamReader(is);
					BufferedReader		r = new BufferedReader(isr);

					// Use the Apache Commons CSV library to read records from it
					CSVFormat			format = CSVFormat.DEFAULT;
					CSVParser			parser = CSVParser.parse(r, format);
					java.util.List<CSVRecord>	records = parser.getRecords();

					// Allocate a 2-D array to keep the rows and columns in memory
					String[][]	values = new String[records.size()][COLUMNS.length];

					for (CSVRecord record : records)	// Loop over the rows...
					{
						Iterator<String>	k = record.iterator();
						int				i = (int)record.getRecordNumber() - 1;
						int				j = 0;		// Column number

						// Print each record to the console
						System.out.println("***** #" + i + " *****");

						while (k.hasNext())			// Loop over the columns...
						{
							values[i][j] = k.next();	// Grab each cell's value

							// Print each value to the console...
							System.out.println(COLUMNS[j] + " = " + values[i][j]);
							j++;
						}

						System.out.println();

						// ...and have the table show the entire value array.
						//table.setModel(new DefaultTableModel(values, COLUMNS));
						table.setModel(new DefaultTableModel(values, COLUMNS));

					}

					is.close();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

				descriptList.remove(0);
				listList.remove(0);
				//creating the tables to populate the tabs
				for (int i = 0; i < table.getRowCount(); i++) {

					//for fridge items
					if (Integer.parseInt((String)table.getValueAt(i, 0)) == 0) {
						//fridgeCount++;

						//cell variables
						String fav = ((Integer.parseInt((String)table.getValueAt(i, 1)) == 1) ? filledStar : unfilledStar);
						String name = (String)table.getValueAt(i, 2);
						boolean isAmount = ( ((String)table.getValueAt(i, 3)).equals("null") ? false : true );
						String amount = (String)table.getValueAt(i, 3);
						boolean isDate = ( ((String)table.getValueAt(i, 4)).equals("null") ? false : true );
						String daysleft = (String)table.getValueAt(i, 4);
						String leftovers = ((Integer.parseInt((String)table.getValueAt(i, 5)) == 1) ? "Yes" : "No");

						if (isDate) {
							String [] date = ((String)table.getValueAt(i, 4)).split("/");
							LocalDate expirationDate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));

							//Period p = Period.between(today, expirationDate);
							long period1 = ChronoUnit.DAYS.between(today, expirationDate);
							daysleft = ((period1 < 1) ? "expired" : Long.toString(period1));

						}

						if (isAmount) {
							String [] amountAndUnits = ((String)table.getValueAt(i, 3)).split(" ");

							if (Double.parseDouble(amountAndUnits[0]) <= 1) {
								amountModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
							}
						}


						if (fav.equals(filledStar)) {
							favoritesModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
						}

						if (daysleft.equals("expired")) {
							expiredModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
						}

						if (leftovers.equals("Yes")) {
							leftoversModel.addRow(new Object[] {fav, name, amount, daysleft,leftovers});
						}



						//adding a row to the fridge table
						fridgeModel.addRow(new Object[]{ fav, name, amount, daysleft, leftovers});
					}
					//for grocery items
					else if (Integer.parseInt((String)table.getValueAt(i, 0)) == 1) {
						//groceryCount++;

						//cell variable by column
						String name = (String)table.getValueAt(i, 1);
						String amount = (String)table.getValueAt(i, 2);

						//adds row to grocery table
						groceryModel.addRow(new Object[] { name, amount});
					}
					//for recipe items
					else if (Integer.parseInt((String)table.getValueAt(i, 0)) == 2) {
						//recipeCount++;

						//recipe variables
						String name = (String)table.getValueAt(i, 1);
						String path = (String)table.getValueAt(i, 2);
						String ingredients = (String)table.getValueAt(i, 3);
						String imagepath = (String)table.getValueAt(i, 4);

						listList.add(name);
						descriptList.add(path + "\n" + ingredients + "\n" + imagepath + "\n");

						recipeModel.addRow(new Object[] {name, path, ingredients, imagepath});
					}
				}

				if (descriptList.isEmpty()) {
					descriptList.add("default ");
				}
				if (listList.isEmpty()) {
					listList.add("default ");
				}

				//list
				DefaultListModel<String> dfl = new DefaultListModel<String>();
				for (int i = 0; i < listList.size(); i++) {
					dfl.addElement(listList.get(i));
				}

				JList			recipeNames = new JList(dfl);
				recipeNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				recipeNames.setLayoutOrientation(JList.VERTICAL);
				recipeNames.setVisibleRowCount(-1);
				recipeNames.setSelectedIndex(0);

				//JTextArea
				JTextArea		descriptions = new JTextArea(text);
				descriptions.setFont(new Font("Serif", Font.ITALIC, 16));
				descriptions.setLineWrap(true);
				descriptions.setWrapStyleWord(true);
				descriptions.setText(descriptList.get(recipeNames.getSelectedIndex()));

				//selection listener
				ListSelectionListener sl = new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting() == false) {

							descriptions.setText(descriptList.get(recipeNames.getSelectedIndex()));
						}
					}
				};

				recipeNames.addListSelectionListener(sl);
				listPane.removeAll();
				descriptPane.removeAll();
				listPane.add(recipeNames, BorderLayout.CENTER);
				descriptPane.add(descriptions, BorderLayout.CENTER);

		    }
		});
		JMenuItem saveItem = new JMenuItem(new AbstractAction("Save (CTRL + S)")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		    		try {
		    			JFileChooser fileChooser = new JFileChooser();
		    			int result = fileChooser.showOpenDialog(frame);
		    			if (result == JFileChooser.APPROVE_OPTION) {
		    				File file = fileChooser.getSelectedFile();

		    				BufferedWriter output = null;
		    				try {
		    					//File file = new File("created_file.csv");
		    					output = new BufferedWriter(new FileWriter(file));

		    					/*
		    					for (int i = 0; i < table.getRowCount(); i++) {

		    						for (int j = 0; j < table.getColumnCount() - 1; j++) {

		    							output.write(((String)table.getValueAt(i, j)) + ",");

		    						}

		    						output.write(((String)table.getValueAt(i, table.getColumnCount()-1)));
		    					}
		    					*/

		    					int fridgeRow = 0;
		    					int groceryRow = 0;
		    					int recipeRow = 0;

		    					for (int i = 0; i < table.getRowCount(); i++) {

		    						int section = Integer.parseInt((String)table.getValueAt(i, 0));

		    						switch(section) {

		    						//fridge writing
		    						case 0:

		    							//write tab divider
		    							output.write("0,");

		    							//write favorite status
		    							if (isFavorite((String)fridgeTable.getValueAt(fridgeRow, 0))) {
			    							output.write("1" + ",");
			    						}
			    						else {
			    							output.write("0" + ",");
			    						}

		    							//write name
			    						output.write((String)fridgeTable.getValueAt(fridgeRow, 1) + ",");

			    						//write amount
			    						output.write((String)fridgeTable.getValueAt(fridgeRow, 2) + ",");

			    						//write expiration date from original table
			    						output.write((String)table.getValueAt(i, 4) + ",");

			    						//write leftovers
			    						if (isLeftovers((String)fridgeTable.getValueAt(fridgeRow, 4))) {
			    							output.write("1");
			    						}
			    						else {
			    							output.write("0");
			    						}

			    						//newline
			    						output.write("\n");
			    						fridgeRow++;

			    						break;

		    						case 1:

		    							output.write("1,");
			    						output.write((String)groceryTable.getValueAt(groceryRow, 0) + ",");
			    						output.write((String)groceryTable.getValueAt(groceryRow, 1) + ",");
			    						output.write("null,");
			    						output.write("null,");
			    						output.write("null\n");
			    						groceryRow++;

			    						break;

		    						case 2:

		    							output.write("2,");

			    						for (int j = 0; j < recipeTable.getColumnCount(); j++) {

			    							output.write((String)recipeTable.getValueAt(recipeRow, j) + ",");

			    						}

			    						output.write("null");

			    						output.write("\n");
			    						recipeRow++;

			    						break;

		    						}

		    					}

		    					/*
		    					 * 		    					//BEGIN OF EDIT
		    					//writing the fridgeTable
		    					for (int i = 0; i < fridgeTable.getRowCount(); i++) {

		    						//write tab divider
		    						output.write("0,");

		    						//write favorite status
		    						if (isFavorite((String)fridgeTable.getValueAt(i, 0))) {
		    							output.write("1" + ",");
		    						}
		    						else {
		    							output.write("0" + ",");
		    						}

		    						//write name
		    						output.write((String)fridgeTable.getValueAt(i, 1) + ",");

		    						//write amount
		    						output.write((String)fridgeTable.getValueAt(i, 2) + ",");

		    						//write expiration date from original table
		    						output.write((String)table.getValueAt(i, 4) + ",");

		    						//write leftovers
		    						if (isLeftovers((String)fridgeTable.getValueAt(i, 4))) {
		    							output.write("1");
		    						}
		    						else {
		    							output.write("0");
		    						}

		    						output.write("\n");
		    					}

		    					for (int i = 0; i < groceryTable.getRowCount(); i++) {

		    						output.write("1,");
		    						output.write((String)groceryTable.getValueAt(i, 0) + ",");
		    						output.write((String)groceryTable.getValueAt(i, 1) + ",");
		    						output.write("null,");
		    						output.write("null,");
		    						output.write("null\n");

		    					}

		    					for (int i = 0; i < recipeTable.getRowCount(); i++) {

		    						output.write("2,");

		    						for (int j = 0; j < recipeTable.getColumnCount(); j++) {

		    							output.write((String)recipeTable.getValueAt(i, j) + ",");

		    						}

		    						output.write("null");

		    						output.write("\n");
		    					}

		    					*/
		    					//END OF EDIT

		    					fridgeEdited = false;
		    					groceryEdited = false;
		    					recipeEdited = false;

		    				}
		    				catch (Exception e1) {
		    					e1.printStackTrace();
		    				} finally {
		    					if (output != null) {
		    						try {
		    							output.close();
		    						}
		    						catch (Exception e1) {
		    							e1.printStackTrace();
		    						}
		    					}
		    				}

		    			}
		    			else if (result == fileChooser.CANCEL_OPTION) {
		    				JOptionPane.showMessageDialog(frame, "No file selected.");

		    			}
		    		}
		    		catch(Exception e45) {
		    			JOptionPane.showMessageDialog(frame, "File Does not Exist.");
		    		}
		    }
		});


		ActionListener favListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (favoritesBox.isSelected()) {

					fridgeTable.setModel(favoritesModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
				else {
					fridgeTable.setModel(fridgeModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
			}
		};     favoritesBox.addActionListener(favListener);

		ActionListener expiredListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (expiredBox.isSelected()) {

					fridgeTable.setModel(expiredModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
				else {
					fridgeTable.setModel(fridgeModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
			}
		};     expiredBox.addActionListener(expiredListener);


		ActionListener lowListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (lowBox.isSelected()) {

					fridgeTable.setModel(amountModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
				else {
					fridgeTable.setModel(fridgeModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
			}
		};     lowBox.addActionListener(lowListener);

		ActionListener leftoversListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				if (leftoversBox.isSelected()) {

					fridgeTable.setModel(leftoversModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
				else {
					fridgeTable.setModel(fridgeModel);
					fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
				}
			}
		};     leftoversBox.addActionListener(leftoversListener);



		//menu items
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
		JMenuItem aboutItem = new JMenuItem(new AbstractAction("About")
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				aboutWindow.setVisible(true);

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
		fileMenu.add(aboutItem);
		fileMenu.addSeparator();
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

		JMenuItem resolution = new JMenuItem(new AbstractAction("Resolution")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("Help -> Accessibility -> Resolution. Allows the user to adjust the resolution.");
		    }
		});
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
		windowMenu.add(resolution);
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
		searchTool.setIcon(Resources.getImage("icons/search.png"));
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
		filterTool.setIcon(Resources.getImage("icons/filter.png"));
		filterTool.setFont(new Font("Arial", Font.PLAIN, 15));
		filterTool.setVerticalTextPosition(SwingConstants.TOP);
		filterTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(filterTool);
		toolBar.addSeparator();

		JButton restoreTool = new JButton(new AbstractAction("Restore")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Restore (CTRL + Z). Restores the most recently deleted item.");
		    }
		});
		restoreTool.setIcon(Resources.getImage("icons/restore.png"));
		restoreTool.setFont(new Font("Arial", Font.PLAIN, 15));
		restoreTool.setVerticalTextPosition(SwingConstants.TOP);
		restoreTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(restoreTool);

		JButton settingsTool = new JButton(new AbstractAction("Settings")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Settings. Opens the settings window for the application.");
		    }
		});
		settingsTool.setIcon(Resources.getImage("icons/settings.png"));
		settingsTool.setFont(new Font("Arial", Font.PLAIN, 15));
		settingsTool.setVerticalTextPosition(SwingConstants.TOP);
		settingsTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(settingsTool);

		JButton boldTool = new JButton(new AbstractAction("Bold")
		{
		    public void actionPerformed(ActionEvent a)
		    {
		        System.out.println("ToolBar --> Bold. Bolds all text.");
		    }
		});
		boldTool.setIcon(Resources.getImage("icons/bold.png"));
		boldTool.setFont(new Font("Arial", Font.PLAIN, 15));
		boldTool.setVerticalTextPosition(SwingConstants.TOP);
		boldTool.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(boldTool);
		toolBar.setBorder(new EmptyBorder(0, 100, 10, 100));







		// Quit Menu item action listener disabled to make testing faster
		// Add ActionListener to Quit
		quitItem.addActionListener(new AbstractAction()
		{
		    public void actionPerformed(ActionEvent a)
		    {
		    		if (fridgeEdited || groceryEdited || recipeEdited) {

		    			Object[] options = {"quit without saving", "cancel", "save before quitting"};
		    			int n = JOptionPane.showOptionDialog(frame, "What would you like to do?", "Quit",
		    					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

		    			if(n == 0) {
		    				System.exit(0);
		    			}
		    			else if (n == 1) {

		    			}
		    			else if (n == 2) {
		    				saveItem.doClick();
		    				System.exit(0);
		    			}

		    		}

		    		System.exit(0);
		    }
		});

		frame.getContentPane().add(toolBar, BorderLayout.PAGE_END);



		//sets the base frame to visible & to end on exit
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
		});
	}

	private static ActionListener AbstractAction() {
		// TODO Auto-generated method stub
		return null;
	}
}

//******************************************************************************
