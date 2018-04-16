/*


*/


import javax.swing.*; //needed for GUI elements

public class Recipes
{
	ArrayList<food> items; //will hold the database

    //this constructor creates a fridge database with no entries
    public Recipes()
    {
		items = new ArrayList<food>();
    }

	//adds a food object to the database
    public add(food entry)
    {
		items.add(entry);
		//updates isChanged to show changes
        isChanged = true;
    }

	//this well report if changes have been made to the database
    public changeMade()
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
			out.append(foodStuff.getFile()+","); 		//sets the line's file path
			out.append(foodStuff.getIngredients()+","); //sets the line's ingrds.
			out.append(foodStuff.getImage()+","); 		//sets the line's image path
			out.append("\n");   //add a newline before the next line
		}
		return out; //return the built CSV chunk
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Recipes"));

		// ----- Recipe Data View ----- //
		//this will hold the display for allthe entries in the database
		JPanel entryDisplay = new JPanel(new BorderLayout());
        //creates a table to hold the recipes
		MyTable recipeTable = generateTable();
		//sets the table so only 1 selection can be made at once
		recipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//this is what the action listener will be attached to
		ListSelectionModel select = recipeTable.getSelectionModel();
		//adds the recipes table to the entry display
		entryDisplay.add(new JScrollPane(recipeTable), BorderLayout.WEST);
		//creates the map of entries
		HashMap map = CreateRecipeViewer();
		//create the default displayed "recipe"
		JTextArea defaultArea = new JTextArea();
		defaultArea.setEditable(False);
		//creates scroll pane
		JScrollPane recipeViewer = new JScrollPane(defaultArea);
		//creates the actions listener for the table
		ListSelectionListener listenForSelection = new ListSelectionListener()
		{
      		public void valueChanged(ListSelectionEvent e)
			{
				//get the selected row
				int row = recipeTable.getSelectedRow();
				//get the name of the selected recipe
				String nameSelected = recipeTable.getValueAt(row, 1);
				//using the name of the selected recipe get the JTable
				JTable recipeDetails = map.get(nameSelected);
				//display the selected recipe data
				recipeViewer.setViewportView(recipeDetails);

          	}
        };
		//adds an action listener to the table
		select.addListSelectionListener(listenForSelection);

		//adds recipe viewer to the entry display
		entryDisplay.add(recipeViewer, BorderLayout.EAST);
		//adds the recipe entry display to the primary panel
		panel.add(entryDisplay, BorderLayout.CENTER);

        // ----- RECIPES TAB FILTER CHECKBOX PANEL ----- //
		//filterPanel will hold all of the filtering options
		JPanel			filterPanel = new JPanel(new GridLayout(1, 4));
		filterPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
		//creates a 2nd back button
		JButton 		back = new JButton("←");
		back.setPreferredSize(new Dimension(100, 50));
		Font font = new Font("Arial", Font.PLAIN, 25);
		back.setFont(font);
		//creates a 2nd add button
		JButton 		add = new JButton("+");
		add.setFont(font);
		add.setPreferredSize(new Dimension(100, 50));
		//filter by fav option
		JCheckBox		favoritesBox = new JCheckBox();
		favoritesBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			favoritesLabel = new JLabel("Favorites");
		//filters by what is in stock
		JCheckBox		inStockBox = new JCheckBox();
		inStockBox.setHorizontalAlignment(JCheckBox.RIGHT);
		JLabel			inStockLabel = new JLabel("In Stock");
		//add all options to the main filter panel
		filterPanel.add(favoritesBox);
		filterPanel.add(favoritesLabel);
		filterPanel.add(inStockBox);
		filterPanel.add(inStockLabel);
		//mid is then used to center the filterPanel
		JPanel	mid = new JPanel(new BorderLayout());
		mid.add(back, BorderLayout.WEST);
		mid.add(add, BorderLayout.EAST);
		mid.add(filterPanel, BorderLayout.CENTER);

		//sends back the finished panel
		return panel;
    }

	//this will be used by render to create the recipe viewer
	private JScrollPane CreateRecipeViewer()
	{
		BufferedReader br; //the reader for all recipe files
		HashMap map = new HashMap(); //will store the recipe text

		for (food thisFood: items) //for all food items
		{
			//will hold the text to display
			JTextArea textBox = new JTextArea();
			//create a file from this entries file path
			File recipePath = new file(thisFood.getFile());
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
		}

		return map;
	}

	//this will make the table the the GUI will show
	private MyTable generateTable()
	{
		//this creates the column headers for the table
		String[] titles = new String[] {"Name"};
		//fields will store all of the entries in the database for the GUI
		ArrayList fields = ArrayList();
		for (food foodStuff: items) //for each element in items do the following
		{
			//creates a single row of the table
			String[] currentRow = String[1]; //creates an array for this row
			String[1] = foodStuff.getName(); //sets this row's name
			fields.add(currentRow); //adds this row to the fields ArrayList
		}
		//builds a table with titles and a downgraded fields array
		MyTable builtTable = new MyTable(fields.toArray(), titles);
		return builtTable; // return
	}
}
