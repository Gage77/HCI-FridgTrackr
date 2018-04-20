/*
 * Changes the table so that the default highlighting is yellow
 */

package edu.ou.cs.hci.stages;

import javax.swing.event.ListSelectionEvent; //needed to modify selection event
import javax.swing.JTable; //needed to extend
import java.awt.Color; //needed for color operations


public class MyTable extends JTable
{
    //makes the selection color a constant
    private static final Color	SELECT_COLOR = Color.decode("#ffcc00");

    //overrides the parent constructor
    MyTable(Object[][] obj, String[] cols)
    {
        //call parent
        super(obj,cols);
    }

    @Override  //overrides the parent
    public void valueChanged(ListSelectionEvent e)
    {
        //sets the selection color when an entry is selected
        this.setSelectionBackground(SELECT_COLOR);

    }

}
