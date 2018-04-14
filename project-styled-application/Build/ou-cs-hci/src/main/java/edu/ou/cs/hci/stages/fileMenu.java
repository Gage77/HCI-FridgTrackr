/*


*/
package main.java.edu.ou.cs.hci.stages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; //needed for GUI elements

public class fileMenu
{

    //creates the UI
    public static JMenuBar render()  //TODO comment
    {
        JMenuBar menuBar = new JMenuBar();
        // File menu
        JMenuItem openItem = new JMenuItem(new AbstractAction("Open (CTRL + O)")
        {
            public void actionPerformed(ActionEvent a)  //TODO write load
            {
                System.out.println("File -> Open (CTRL + O). Opens a FridgTrackr file.");
            }
        });
        JMenuItem saveItem = new JMenuItem(new AbstractAction("Save (CTRL + S)")
        {
            public void actionPerformed(ActionEvent a)  //TODO write save
            {
                System.out.println("File -> Save (CTRL + S). Saves the current FridgTrackr file.");
            }
        });
        JMenuItem printAllItem = new JMenuItem(new AbstractAction("All (CTRL + P)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("File -> Print -> All (CTRL + P). Prints the full FridgTrackr file.");
            }
        });
        //JMenuItem printSelectedItem = new JMenuItem("Currently Shown");
        JMenuItem printFridgeItem = new JMenuItem(new AbstractAction("Fridge Stock")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("File -> Print -> Fridge Stock. Prints the current fridge stock.");
            }
        });
        JMenuItem printRecipesItem = new JMenuItem(new AbstractAction("Recipes")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("File -> Print -> Recipes. Prints the currently stored recipes.");
            }
        });
        JMenuItem printGroceriesItem = new JMenuItem(new AbstractAction("Groceries")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("File -> Print -> Groceries. Prints the current grocery list.");
            }
        });
        JMenu printSubmenu = new JMenu("Print");
        JMenuItem quitItem = new JMenuItem("Quit	(CTRL + Q)"); // ActionListener added later
        printSubmenu.add(printAllItem);
        //printSubmenu.add(printSelectedItem);
        printSubmenu.add(printFridgeItem);
        printSubmenu.add(printRecipesItem);
        printSubmenu.add(printGroceriesItem);
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(printSubmenu);
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
        // Edit menu
        JMenuItem copy = new JMenuItem(new AbstractAction("Copy (CTRL + C)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Copy (CTRL + C). Copies the selection.");
            }
        });
        JMenuItem cut = new JMenuItem(new AbstractAction("Cut (CTRL + X)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Cut (CTRL + X). Cuts the selection.");
            }
        });
        JMenuItem paste = new JMenuItem(new AbstractAction("Paste (CTRL + V)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Paste (CTRL + V). Pastes the selection.");
            }
        });
        JMenuItem search = new JMenuItem(new AbstractAction("Search (CTRL + F)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Search (CTRL + F). Allows the user to search for a string.");
            }
        });
        JMenuItem restore = new JMenuItem(new AbstractAction("Restore (CTRL + Z)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Restore (CTRL + Z). Restores the last deleted item to a limit.");
            }
        });
        JMenuItem favorites = new JMenuItem(new AbstractAction("Favorites")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Filter By: --> Favorites. Filters by favorited items.");
            }
        });
        JMenuItem expiration = new JMenuItem(new AbstractAction("Expired")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Filter By: --> Expired. Filters by expired items.");
            }
        });
        JMenuItem lowStock = new JMenuItem(new AbstractAction("Low Stock")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Filter By: --> Low Stock. Filters by low stock.");
            }
        });
        JMenuItem leftovers = new JMenuItem(new AbstractAction("Leftovers")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Edit -> Filter By: --> Leftovers. Filters by items marked as leftovers.");
            }
        });
        JMenu filterBySubmenu = new JMenu("Filter By:");
        filterBySubmenu.add(favorites);
        filterBySubmenu.add(expiration);
        filterBySubmenu.add(lowStock);
        filterBySubmenu.add(leftovers);
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(copy);
        editMenu.add(cut);
        editMenu.add(paste);
        editMenu.addSeparator();
        editMenu.add(search);
        editMenu.add(restore);
        editMenu.add(filterBySubmenu);
        menuBar.add(editMenu);

        JMenuItem resolution = new JMenuItem(new AbstractAction("Resolution")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Resolution. Allows the user to adjust the resolution.");
            }
        });
        JMenu settings = new JMenu("Settings");
        JMenuItem restorePt = new JMenuItem(new AbstractAction("Set Restore Point")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Restore Point. "
                        + "Allows the user to set the amount of restores to keep.");
            }
        });
        JMenu units = new JMenu("Set units");
        JMenuItem metric = new JMenuItem(new AbstractAction("Metric")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Units -> Metric. "
                        + "Allows the user to set the units of measurement to the metric system.");
            }
        });
        JMenuItem imperial = new JMenuItem(new AbstractAction("Imperial")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Units -> Imperial. "
                        + "Allows the user to set the units of measurement to the imperial system.");
            }
        });
        JMenuItem customary = new JMenuItem(new AbstractAction("Customary")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Units -> Customary. "
                        + "Allows the user to set the units of measurement to the customary system.");
            }
        });
        units.add(metric);
        units.add(imperial);
        units.add(customary);
        settings.add(units);
        settings.add(restorePt);
        JMenu windowMenu = new JMenu("Window");
        windowMenu.add(resolution);
        windowMenu.add(settings);
        menuBar.add(windowMenu);
        // Help menu
        JMenu help = new JMenu("Help");
        JMenuItem link = new JMenuItem(new AbstractAction("Link (CTRL + ?)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Link. Opens a related webpage: http://www.myfridgefood.com/");
            }
        });
        JMenu accessibility = new JMenu("Accessibility");
        JMenuItem fontSize = new JMenuItem(new AbstractAction("Font Size")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Font Size. Allows the user to edit the font size.");
            }
        });
        JMenuItem invert = new JMenuItem(new AbstractAction("Invert")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Invert. Inverts the colours being displayed.");
            }
        });
        JMenuItem bold = new JMenuItem(new AbstractAction("Bold")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Bold. Bolds all text.");
            }
        });
        accessibility.add(fontSize);
        accessibility.add(invert);
        accessibility.add(bold);
        help.add(accessibility);
        JMenuItem feedback = new JMenuItem(new AbstractAction("Feedback (CTRL + H)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Feedback (CTRL + H). Opens a window to allow the user provide feedback.");
            }
        });
        JMenuItem donate = new JMenuItem(new AbstractAction("Donate (CTRL + $)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Donate (CTRL + $). Allows the user to donate.");
            }
        });
        help.add(feedback);
        help.add(donate);
        menuBar.add(help);

        //returns the finished menu
        return menuBar;
    }
}
