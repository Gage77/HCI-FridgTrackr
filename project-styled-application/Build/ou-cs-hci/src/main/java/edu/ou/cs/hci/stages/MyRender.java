/*
 * Changes the renderer so that the default highlighting is yellow
 * the soon to be "expired" item which is less than 3 days left are red
 * and when a red is highlighted it is orange instead of yellow
 * I did a lot of googling and reading to figure this one out
 */
 package edu.ou.cs.hci.stages;

 import javax.swing.JTable;
 import java.awt.Component;
 import javax.swing.table.DefaultTableCellRenderer; //needed to extend
 import java.awt.Color; //need to handle color operations

class MyRenderer extends DefaultTableCellRenderer
{
    //color constants
    private static final Color	ALERT_COLOR = Color.decode("#e60000");
    private static final Color	SELECT_COLOR = Color.decode("#ffcc00");
    private static final Color  MIX_COLOR = Color.decode("#ff6600");

    //constructor
    public MyRenderer()
    {
        super(); //call parent
        setOpaque(true); //sets opaque value
    }

    //creates a custom render for table cells
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        //if there is less than 3 of an item
        if(Integer.parseInt((String)table.getValueAt(row, 3)) < 3)
        {
                setForeground(Color.black);
                if (isSelected) //if a low stocked item is selected
                {
                    setBackground(MIX_COLOR); //color orange
                }
                else //if a low stocked item is not selected
                {
                    setBackground(ALERT_COLOR); //color red
                }
        }
        else //if there is 3 or more of an item
        {
                if (isSelected) //if a normal row is selected
                {
                    setBackground(SELECT_COLOR); //color yellow
                }
                else //if a normal row is not selected
                {
                setBackground(Color.white); //color white
                }
        }
        setText(value.toString()); //sets the texts of the cell
        return this; //return the created cell
    }
}
