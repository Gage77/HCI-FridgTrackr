/*


*/
package main.java.edu.ou.cs.hci.stages;

import javax.swing.*; //needed for GUI elements

public class Groceries
{

    //this constructor creates a fridge database with no entries
    public Groceries()
    {

    }

    //adds a food object to the database
    public add(food entry)
    {
        //TODO write groceries add
    }

    //creates the UI
    public render()
    {
        //creates a panel & layout
        JPanel panel = new JPanel(new BorderLayout());
        //give the panel a title
        panel.setBorder(BorderFactory.createTitledBorder("Groceries"));

        //creates a table to hold the groceries
		MyTable groceryTable = new MyTable( products1, colName1 );
		//adds the grocery table to the panel
		panel.add(new JScrollPane(groceryTable), BorderLayout.CENTER);

        //sends back the finished panel
        return panel;
    }

}
