/*


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

//TODO comment
public class iconBar
{
    public static JToolBar render()
    {
        JToolBar toolBar = new JToolBar("Tool Bar");
        JButton searchTool = new JButton(new AbstractAction("Search")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("ToolBar --> Search (CTRL + F). Allows the user to search for an item in the database.");
            }
        });
        searchTool.setIcon(Resources.getImage("icons/search.png"));
        searchTool.setFont(new Font("Arial", Font.PLAIN, 15));
        searchTool.setVerticalTextPosition(SwingConstants.TOP);
        searchTool.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar.add(searchTool);
        JButton filterTool = new JButton(new AbstractAction("Filter")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("ToolBar --> Filter. Allows the user to choose criteria to filter by.");
            }
        });
        filterTool.setIcon(Resources.getImage("icons/filter.png"));
        filterTool.setFont(new Font("Arial", Font.PLAIN, 15));
        filterTool.setVerticalTextPosition(SwingConstants.TOP);
        filterTool.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar.add(filterTool);
        toolBar.addSeparator();

        JButton restoreTool = new JButton(new AbstractAction("Restore")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("ToolBar --> Restore (CTRL + Z). Restores the most recently deleted item.");
            }
        });
        restoreTool.setIcon(Resources.getImage("icons/restore.png"));
        restoreTool.setFont(new Font("Arial", Font.PLAIN, 15));
        restoreTool.setVerticalTextPosition(SwingConstants.TOP);
        restoreTool.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar.add(restoreTool);

        JButton settingsTool = new JButton(new AbstractAction("Settings")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("ToolBar --> Settings. Opens the settings window for the application.");
            }
        });
        settingsTool.setIcon(Resources.getImage("icons/settings.png"));
        settingsTool.setFont(new Font("Arial", Font.PLAIN, 15));
        settingsTool.setVerticalTextPosition(SwingConstants.TOP);
        settingsTool.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar.add(settingsTool);

        JButton boldTool = new JButton(new AbstractAction("Bold")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("ToolBar --> Bold. Bolds all text.");
            }
        });
        boldTool.setIcon(Resources.getImage("icons/bold.png"));
        boldTool.setFont(new Font("Arial", Font.PLAIN, 15));
        boldTool.setVerticalTextPosition(SwingConstants.TOP);
        boldTool.setHorizontalTextPosition(SwingConstants.CENTER);
        toolBar.add(boldTool);
        toolBar.setBorder(new EmptyBorder(0, 100, 10, 100));

        // Quit Menu item action listener disabled to make testing faster
        // Add ActionListener to Quit
        quitItem.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent a)
            {
                    PrintWriter pwriter;
                    try {
                        pwriter = new PrintWriter("menu-actions.txt");
                            String actions = "-- Menu Bar: --" + "\n";
                        actions += "File Menu:" + "\n";
                        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
                        PrintStream pstream = new PrintStream(ostream);
                        PrintStream previous = System.out;

                        System.setOut(pstream);
                        openItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        saveItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        printAllItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        printRecipesItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        printGroceriesItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        printFridgeItem.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        copy.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        cut.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        paste.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        search.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        restore.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        favorites.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        expiration.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        lowStock.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        leftovers.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        restorePt.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        metric.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        imperial.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        customary.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        link.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        fontSize.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        resolution.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        invert.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        bold.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        feedback.doClick();
                        System.setOut(previous);

                        System.setOut(pstream);
                        donate.doClick();
                        System.setOut(previous);

                        pwriter.print(ostream.toString());
                        pwriter.close();
                        System.exit(0);
                    } catch (FileNotFoundException e) {
                        System.out.println("File could not be opened.");
                        e.printStackTrace();
                    }
            }
        });
        return toolBar;
    }
}
