/*
This class will create the icon bar.
*/
package main.java.edu.ou.cs.hci.stages;

import javax.swing.*; //needed for GUI elements
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.ou.cs.hci.resources.*;

public class iconBar
{
    //this will create the toolBar object for the main frame
    public static JToolBar render()
    {
        JToolBar toolBar = new JToolBar("Tool Bar"); //creates the prime toolBar
        JButton searchTool = new JButton(new AbstractAction("Search") //creates the search icon
        {
            public void actionPerformed(ActionEvent a) //when the search icon is clicked
            {
                System.out.println("ToolBar --> Search (CTRL + F). Allows the user to search for an item in the database.");
            }
        });
        searchTool.setIcon(Resources.getImage("icons/search.png")); //set the icon for search
        searchTool.setFont(new Font("Arial", Font.PLAIN, 15)); //set font
        searchTool.setVerticalTextPosition(SwingConstants.TOP); //set y coordinate position
        searchTool.setHorizontalTextPosition(SwingConstants.CENTER); //set x coordinate position
        toolBar.add(searchTool); //add the search button to the prime toolBar
        JButton filterTool = new JButton(new AbstractAction("Filter") //creates the filter button
        {
            public void actionPerformed(ActionEvent a) //when filter is clicked
            {
                System.out.println("ToolBar --> Filter. Allows the user to choose criteria to filter by.");
            }
        });
        filterTool.setIcon(Resources.getImage("icons/filter.png")); //set filter icon
        filterTool.setFont(new Font("Arial", Font.PLAIN, 15)); //set filter font
        filterTool.setVerticalTextPosition(SwingConstants.TOP); //set y coordinate position
        filterTool.setHorizontalTextPosition(SwingConstants.CENTER); //set x coordinate position
        toolBar.add(filterTool); //add the filter button to the prime toolbar
        toolBar.addSeparator(); //add seperator to toolbar to collect search & filter together

        // ----- After 1st Seperator ----- //
        JButton restoreTool = new JButton(new AbstractAction("Restore") //creates the restore button
        {
            public void actionPerformed(ActionEvent a) //when the restore button is clicked
            {
                System.out.println("ToolBar --> Restore (CTRL + Z). Restores the most recently deleted item.");
            }
        });
        restoreTool.setIcon(Resources.getImage("icons/restore.png")); //sets the restore icon
        restoreTool.setFont(new Font("Arial", Font.PLAIN, 15)); //sets the restore font
        restoreTool.setVerticalTextPosition(SwingConstants.TOP); //set y coordinate position
        restoreTool.setHorizontalTextPosition(SwingConstants.CENTER); //set x coordinate position
        toolBar.add(restoreTool); //adds the restore button to the prime toolBar

        JButton settingsTool = new JButton(new AbstractAction("Settings") //creates the settings button
        {
            public void actionPerformed(ActionEvent a) //when settings is clicked
            {
                System.out.println("ToolBar --> Settings. Opens the settings window for the application.");
            }
        });
        settingsTool.setIcon(Resources.getImage("icons/settings.png")); //sets the settings icon
        settingsTool.setFont(new Font("Arial", Font.PLAIN, 15)); //sets the font
        settingsTool.setVerticalTextPosition(SwingConstants.TOP); //set y coordinate position
        settingsTool.setHorizontalTextPosition(SwingConstants.CENTER); //set x coordinate position
        toolBar.add(settingsTool); //adds the settings button to prime toolBar

        JButton boldTool = new JButton(new AbstractAction("Bold") //creates the bold button
        {
            public void actionPerformed(ActionEvent a) //when bold is clicked
            {
                System.out.println("ToolBar --> Bold. Bolds all text.");
            }
        });
        boldTool.setIcon(Resources.getImage("icons/bold.png")); //sets the bold icon
        boldTool.setFont(new Font("Arial", Font.PLAIN, 15)); //sets the font
        boldTool.setVerticalTextPosition(SwingConstants.TOP); //set y coordinate position
        boldTool.setHorizontalTextPosition(SwingConstants.CENTER); //set x coordinate position
        toolBar.add(boldTool); //adds the bold button to the prime toolBar
        toolBar.setBorder(new EmptyBorder(0, 100, 10, 100)); //sets the toolBar border

        return toolBar; //returns the rendered toolBar
    }
}
