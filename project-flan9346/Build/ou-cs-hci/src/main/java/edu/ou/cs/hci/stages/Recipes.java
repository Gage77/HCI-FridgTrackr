/*
This is the recipes class. It will create the GUI for the recipes view,
create and maintain the database of food objects in the user's recipes list,
and create and maintain a HashMap of all of the recipe .txt files. It will also
work with readr in order to save.
*/
package edu.ou.cs.hci.stages;

import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*; //needed for GUI elements

public class Recipes
{
	private boolean isChanged = false; //has this database been chaged?
	private ArrayList<food> items; //will hold the database
	private JPanel panel; //the panel which the recipe will be displayed
	//create the text are in which all recipe txt will be viewed
	private JTextArea txtView = new JTextArea();
	private JPanel recipeEntryDisplay; //need for action listener

    //this constructor creates a fridge database with no entries
    public Recipes()
    {
		items = new ArrayList<food>();
    }

	//used to create the database (does not alter isChanged flag)
	public void in(food entry)
	{
		items.add(entry);
	}

	//adds a food object to the database
	public void add(food entry)
	{
		items.add(entry);
		//updates isChanged to show changes
		isChanged = true;
	}

	//this well report if changes have been made to the database
    public boolean changeMade()
    {
        return isChanged;
    }

	//used by readr to create CSV
    public StringBuilder out()
    {
		StringBuilder out = new StringBuilder(); //the data to be outputed

		for (food foodStuff: items) //for each element in items do the following
		{
			//turns an entry into a single line
			out.append("2,");                      		//sets the line's id
			out.append(foodStuff.getName()+",");		//sets the line's name

			if(foodStuff.getTxtFile() != null) //if the food has a file object
			{
				//convert the file into a path string
				out.append(foodStuff.getTxtFile().getPath()+",");
				//NOTE because these path names exist outside of the app
				// this will prevent them from being mobile
			}
			else //if the food has a file path
				out.append(foodStuff.getFile()+","); 	//sets the line's file path
			out.append(foodStuff.getIngredients()+","); //sets the line's ingrds.
			out.append(foodStuff.getImage()+","); 		//sets the line's image path
			out.append("\n");   //add a newline before the next line
		}
		return out; //return the built CSV chunk
    }

    //creates the UI
    public JPanel render()
    {
        //creates a panel & layout
        panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Recipes"));
		//adds the recipe entry display to the primary panel
		recipeEntryDisplay = renderEntryDisplay();
		panel.add(recipeEntryDisplay, BorderLayout.CENTER);
		//adds the filters to the recipes master panel
        panel.add(renderFilterPanel(), BorderLayout.NORTH);
		return panel; //sends back the finished panel
    }

	//this will be used by render to create the recipe viewer
	private HashMap<String, JTextArea> CreateRecipeViewer()
	{
		BufferedReader br; //the reader for all recipe files
		//will store the recipe text
		HashMap<String, JTextArea> map = new HashMap<String, JTextArea>();

		for (food thisFood: items) //for all food items
		{
			//will hold the text to display
			JTextArea textBox = new JTextArea();
			File recipePath;
			//if the food has a file object instead of a file path
			if(thisFood.getTxtFile() != null)
				recipePath = thisFood.getTxtFile();
			else //if this food has a file path instead of a file object
				recipePath = new File(thisFood.getFile());
			try{
				//make a reader for that file
				br = new BufferedReader(new FileReader(recipePath));
				String line = br.readLine(); //read 1st line of the file
				while(line != null) //while there is another line
				{
  					textBox.append(line + "\n"); //add line to the JTextArea
  					line = br.readLine(); //read the next line
				}//the text file should now be loaded into textBox
				//loads the textField into a map accessed by the recipes name
				map.put(thisFood.getName(), textBox);
			} catch(IOException e){
				//create an error message if IO problems encountered
				JOptionPane.showMessageDialog(null,
				"ERROR: Recipe Viewer encountered an IOException");
				return map;}
		}
		return map;
	}

	//this will make the table the the GUI will show
	private MyTable generateTable()
	{
		//this creates the column headers for the table
		String[] titles = new String[] {"Name"};
		//fields will store all of the entries in the database for the GUI
		ArrayList<String[]> fields = new ArrayList<String[]>();
		for (food foodStuff: items) //for each element in items do the following
		{
			//creates a single row of the table
			String[] currentRow = new String[1]; //creates an array for this row
			currentRow[1] = foodStuff.getName(); //sets this row's name
			fields.add(currentRow); //adds this row to the fields ArrayList
		}
		//builds a table with titles and a downgraded fields array
		MyTable builtTable = new MyTable(fields.toArray(new String[0][1]), titles);
		return builtTable; // return
	}

	private JPanel renderEntryDisplay()
	{
		//this will hold the display for all the entries in the database
		JPanel entryDisplay = new JPanel(new BorderLayout());

		//creates a table to hold the recipes
		MyTable recipeTable = generateTable();
		//sets the table so only 1 selection can be made at once
		recipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this is what the action listener will be attached to
		ListSelectionModel select = recipeTable.getSelectionModel();
		//adds the recipes table to the entry display
		entryDisplay.add(new JScrollPane(recipeTable), BorderLayout.WEST);
		//prevents recipes from being edited
		txtView.setEditable(false); //NOTE change in later version
		//creates the scroll pane for all recipe data
		JScrollPane recipeViewer = new JScrollPane(txtView);

		//creates the map of entries
		HashMap<String, JTextArea> entryMap = CreateRecipeViewer();
		//creates the actions listener for the table
		ListSelectionListener listenForSelection = new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				//get the selected row
				int row = recipeTable.getSelectedRow();
				if(row == -1) //in case the table has not been built
					return;
				//get the name of the selected recipe
				String nameSelected = (String)recipeTable.getValueAt(row, 1);
				//using the name of recipe get the JTextArea & updates the txt box
				txtView = entryMap.get(nameSelected);
				//redraw the scroll pane with new txt box
				recipeViewer.repaint();
			}
		};
		//adds an action listener to the table
		select.addListSelectionListener(listenForSelection);
		//adds recipe viewer to the entry display
		entryDisplay.add(recipeViewer, BorderLayout.EAST);
		return entryDisplay;
	}

	//this will create the filter options
	private JPanel renderFilterPanel()
	{
		//filterPanel will hold all of the filtering options
		JPanel		filterPanel = new JPanel(new GridLayout(1, 4));
		filterPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
		//filter by fav option
		JCheckBox	favoritesBox = new JCheckBox();
		favoritesBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel		favoritesLabel = new JLabel("Favorites");
		//filters by what is in stock
		JCheckBox	inStockBox = new JCheckBox();
		inStockBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel		inStockLabel = new JLabel("In Stock");
		//add all options to the main filter panel
		filterPanel.add(favoritesBox);
		filterPanel.add(favoritesLabel);
		filterPanel.add(inStockBox);
		filterPanel.add(inStockLabel);
		//mid is then used to center the filterPanel
		JPanel	mid = new JPanel(new BorderLayout());
		mid.add(filterPanel, BorderLayout.CENTER);
		return mid;
	}

	//this is called to update the view
	public void repaint()
	{
		panel.remove(recipeEntryDisplay); //remove the old display
		recipeEntryDisplay = renderEntryDisplay(); //create a new one
		//load new display into main panel
		panel.add(recipeEntryDisplay, BorderLayout.CENTER);
		panel.revalidate(); //tell BorderLayout changes have been made
		panel.repaint(); //repaint just in case
	}
}
