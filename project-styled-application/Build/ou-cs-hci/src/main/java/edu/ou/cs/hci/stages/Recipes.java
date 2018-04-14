/*


*/


import javax.swing.*; //needed for GUI elements

public class Recipes
{
    //this constructor creates a fridge database with no entries
    public Recipes()
    {

    }

	//adds a food object to the database
    public add(food entry)
    {
		//TODO write recipes add
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Recipes"));

        //creates a table to hold the groceries
		MyTable groceryTable = new MyTable( products1, colName1 );
		//adds the grocery table to the panel
		panel.add(new JScrollPane(groceryTable) BorderLayout.CENTER);


        // ----- RECIPES TAB FILTER CHECKBOX PANEL ----- //
		//filterPanel will hold all of the filtering options
		JPanel			filterPanel = new JPanel(new GridLayout(1, 4));
		filterPanel.setBorder(new EmptyBorder(5, 50, 5, 50));
		//creates a 2nd back button
		JButton 		back = new JButton("←");
		back.setPreferredSize(new Dimension(100, 50));
		back.setFont(new Font("Arial", Font.PLAIN, 25));
		//creates a 2nd add button
		JButton 		add = new JButton("+");
		add.setFont(new Font("Arial", Font.PLAIN, 25));
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

}
