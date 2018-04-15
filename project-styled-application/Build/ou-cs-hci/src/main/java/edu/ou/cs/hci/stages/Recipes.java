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
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Recipes"));

        //creates a table to hold the groceries
		MyTable groceryTable = generateTable();
		//adds the grocery table to the panel
		panel.add(new JScrollPane(groceryTable), BorderLayout.CENTER);


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
			//TODO code some kind of recipe view
		}
		//builds a table with titles and a downgraded fields array
		MyTable builtTable = new MyTable(fields.toArray(), titles);
		return builtTable; // return
	}
}
