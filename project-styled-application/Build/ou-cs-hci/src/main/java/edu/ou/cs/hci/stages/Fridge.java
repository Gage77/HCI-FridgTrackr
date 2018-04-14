
/**
This is the fridge class, and it will create the GUI for the fridge view
as well as create and maintain the database of food objects in the user's
fridge. It will also work with readr in order to save
*/


import javax.swing.*; //needed for GUI elements

public class Fridge
{
    //this constructor creates a fridge database with no entries
    public Fridge()
    {

    }

    //adds a food object to the database
    public add(food entry)
    {
        //TODO write fridge add
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Fridge"));

        //creates a custom data table for displaying fridge entries
        MyTable fridgeTable = new MyTable(products, colName);
        //create a render to be used with the data table
        MyRenderer renderer = new MyRenderer();
        //sets the default values/behaviour of data table
        fridgeTable.setDefaultRenderer(Object.class, renderer);
		fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
		fridgeTable.setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.getTableHeader().setFont(new Font("Lucida Console", Font.PLAIN, 13));
		fridgeTable.setRowHeight(20);
        //adds the data table to the fridge category panel
		fridge.add(new JScrollPane(fridgeTable), BorderLayout.CENTER);

        // ----- FRIDGE TAB FILTER CHECKBOX PANEL ----- //
        //filterPanel will hold all of the filtering options
        JPanel			filterPanel = new JPanel(new GridLayout(1, 8));
        filterPanel.setBorder(new EmptyBorder(0, 50, 0, 50));
        //filter by favorites option
        JCheckBox		favoritesBox = new JCheckBox();
        favoritesBox.setText("★");
        //filter by expiration date option
        JCheckBox		expiredBox = new JCheckBox();
        expiredBox.setHorizontalAlignment(JCheckBox.RIGHT);
        JLabel			expiredLabel = new JLabel("Expired");
        //filter by amount option
        JCheckBox		lowBox = new JCheckBox();
        lowBox.setHorizontalAlignment(JCheckBox.RIGHT);
        JLabel			lowLabel = new JLabel("Low Stock");
        //filter by leftovers option
        JCheckBox		leftoversBox = new JCheckBox();
        leftoversBox.setHorizontalAlignment(JCheckBox.RIGHT);
        JLabel			leftoversLabel = new JLabel("Leftovers");
        //add everything to the main filter panel
        filterPanel.add(favoritesBox);
        filterPanel.add(lowBox);
        filterPanel.add(lowLabel);
        filterPanel.add(expiredBox);
        filterPanel.add(expiredLabel);
        filterPanel.add(leftoversBox);
        filterPanel.add(leftoversLabel);
        //this JPanel exists to keep the filters centered
        JPanel			mid = new JPanel(new BorderLayout());
        mid.add(filterPanel, BorderLayout.CENTER);
        //adds the filters to the fridge master panel
        panel.add(mid, BorderLayout.NORTH);

        //sends back the finished panel
        return panel;

    }
}
