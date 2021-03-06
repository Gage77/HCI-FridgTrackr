
/**
This is the fridge class, and it will create the GUI for the fridge view
as well as create and maintain the database of food objects in the user's
fridge. It will also work with readr in order to save
*/

package edu.ou.cs.hci.stages;

import java.awt.Font;
//import layout managers
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.ArrayList; //needed for array lists
import javax.swing.*; //needed for GUI elements

public class Fridge
{
    private ArrayList<food> items; //will hold the database
    private Boolean  isChanged; //has an entry been changed?
    private Font font;
    private JPanel panel;

    //this constructor creates a fridge database with no entries
    public Fridge()
    {
        items = new ArrayList<food>();
        isChanged = false;
        font = new Font("Lucida Console", Font.PLAIN, 13);
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

    //returns the ArrayList that holds all entries
    public ArrayList<food> getItems()
    {
        return items;
    }

    //this well report if changes have been made to the database
    public Boolean changeMade()
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
            out.append("0,");                           //sets the line's id
            out.append(foodStuff.getFavStr()+",");      //sets the line's fav value
            out.append(foodStuff.getName()+",");        //sets the line's name
            out.append(foodStuff.getAmount()+",");      //sets the line's amount
            if(Integer.parseInt(foodStuff.getDaysLeft()) > 0) //if the food is not expired
                out.append(foodStuff.getDaysLeft()+",");    //sets the line's expr value
            else
                out.append("Expired"); //if epxired, update field
            out.append(foodStuff.getLeftoverStr()+","); //sets the line's leftvr value
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
        panel.setBorder(BorderFactory.createTitledBorder("Fridge"));

        //creates a custom data table for displaying fridge entries
        MyTable fridgeTable = generateTable();
        //create a render to be used with the data table
        MyRenderer renderer = new MyRenderer();
        //sets the default values/behaviour of data table
        fridgeTable.setDefaultRenderer(Object.class, renderer);
        fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
        fridgeTable.setFont(font);
        fridgeTable.getTableHeader().setFont(font);
        fridgeTable.setRowHeight(20);
        //adds the data table to the fridge category panel
        panel.add(new JScrollPane(fridgeTable), BorderLayout.CENTER);

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
        JPanel mid = new JPanel(new BorderLayout());
        mid.add(filterPanel, BorderLayout.CENTER);
        //adds the filters to the fridge master panel
        panel.add(mid, BorderLayout.NORTH);

        //sends back the finished panel
        return panel;

    }

    //this is called to update the view
    public void repaint()
    {
        //creates a custom data table for displaying fridge entries
        MyTable fridgeTable = generateTable();
        //create a render to be used with the data table
        MyRenderer renderer = new MyRenderer();
        //sets the default values/behaviour of data table
        fridgeTable.setDefaultRenderer(Object.class, renderer);
        fridgeTable.getColumnModel().getColumn(0).setMaxWidth(25);
        fridgeTable.setFont(font);
        fridgeTable.getTableHeader().setFont(font);
        fridgeTable.setRowHeight(20);
        //adds the data table to the fridge category panel
        panel.add(new JScrollPane(fridgeTable), BorderLayout.CENTER);
        //repaints
        panel.repaint();
    }

    //this will make the table the the GUI will show
    private MyTable generateTable()
    {
        //this creates the column headers for the table
        String[] titles = new String[] {"☆", "Name" ,"Amount",
                                        "Days Left", "Leftovers?"};
        //fields will store all of the entries in the database for the GUI
        ArrayList<String[]> fields = new ArrayList<String[]>();
        for (food foodStuff: items) //for each element in items do the following
        {
            //creates a single row of the table
            String[] currentRow = new String[5]; //creates an array for this row
            currentRow[0] = foodStuff.getFavStr();      //sets this row's fav value
            currentRow[1] = foodStuff.getName();        //sets this row's name
            currentRow[2] = foodStuff.getAmount();      //sets this row's amount
            currentRow[3] = foodStuff.getDaysLeft();    //sets this row's expr value
            currentRow[4] = foodStuff.getLeftoverStr(); //sets this row's leftvr value
            fields.add(currentRow); //adds this row to the fields ArrayList
        }
        //builds a table with titles and a downgraded fields array
        MyTable builtTable = new MyTable(fields.toArray(new String[0][5]), titles);
        return builtTable; // return
    }
}
