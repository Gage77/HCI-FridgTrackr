/*


*/
package main.java.edu.ou.cs.hci.stages;

import javax.swing.*; //needed for GUI elements

public class Groceries
{
    ArrayList<food> items; //will hold the database

    //this constructor creates a fridge database with no entries
    public Groceries()
    {
        items = new ArrayList<food>();
    }

    //adds a food object to the database
    public add(food entry)
    {
        items.add(entry);
    }

    //returns the ArrayList that holds all entries
    public ArrayList<food> getItems()
    {
        return items;
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Groceries"));

        //creates a table to hold the groceries
		MyTable groceryTable = generateTable();
		//adds the grocery table to the panel
		panel.add(new JScrollPane(groceryTable), BorderLayout.CENTER);

        //sends back the finished panel
        return panel;
    }

    //this will make the table the the GUI will show
    private MyTable generateTable()
    {
        //this creates the column headers for the table
        String[] titles = new String[] {"Name", "Amount"};
        //fields will store all of the entries in the database for the GUI
        ArrayList fields = ArrayList();
        for (food foodStuff: items) //for each element in items do the following
        {
            //creates a single row of the table
            String[] currentRow = String[2]; //creates an array for this row
            String[0] = foodStuff.getName();     //sets this row's name
            String[1] = foodStuff.getAmount();   //sets this row's amount
            fields.add(currentRow); //adds this row to the fields ArrayList
        }
        //builds a table with titles and a downgraded fields array
        MyTable builtTable = new MyTable(fields.toArray(), titles);
        return builtTable; // return
    }

}
