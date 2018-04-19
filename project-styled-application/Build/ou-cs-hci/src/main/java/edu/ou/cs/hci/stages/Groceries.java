/*


*/
package edu.ou.cs.hci.stages;

import java.awt.BorderLayout;
import java.util.ArrayList; //needed for array lists
import javax.swing.*; //needed for GUI elements

public class Groceries
{
    ArrayList<food> items; //will hold the database
    boolean isChanged = false;

    //this constructor creates a fridge database with no entries
    public Groceries()
    {
        items = new ArrayList<food>();
    }

    //adds a food object to the database
    public void add(food entry)
    {
        items.add(entry);
    }

    //this well report if changes have been made to the database
    public boolean changeMade()
    {
        //updates isChanged to show changes
        isChanged = true;
        return isChanged; //returns a value showing changes
    }

    //used by readr to create CSV
    public StringBuilder out()
    {
        StringBuilder out = new StringBuilder(); //the data to be outputed

        for (food foodStuff: items) //for each element in items do the following
        {
            //turns an entry into a single line
            out.append("1,");                      //sets the line's id
            out.append(foodStuff.getName()+",");   //sets the line's name
            out.append(foodStuff.getAmount()+","); //sets the line's amount
            out.append("\n");   //add a newline before the next line
        }
        return out; //return the built CSV chunk
    }

    //returns the ArrayList that holds all entries
    public ArrayList<food> getItems()
    {
        return items;
    }

    //creates the UI
    public JPanel render()
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
        ArrayList<String[]> fields = new ArrayList<String[]>();
        for (food foodStuff: items) //for each element in items do the following
        {
            //creates a single row of the table
            String[] currentRow = new String[2]; //creates an array for this row
            currentRow[0] = foodStuff.getName();     //sets this row's name
            currentRow[1] = foodStuff.getAmount();   //sets this row's amount
            fields.add(currentRow); //adds this row to the fields ArrayList
        }
        //builds a table with titles and a downgraded fields array
        MyTable builtTable = new MyTable(fields.toArray(), titles);
        return builtTable; // return
    }

}
