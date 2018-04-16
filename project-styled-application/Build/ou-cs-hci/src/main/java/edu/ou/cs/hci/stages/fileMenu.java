/*


*/
package main.java.edu.ou.cs.hci.stages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*; //needed for GUI elements

public class fileMenu
{

    //creates the UI
    public static JMenuBar render(readr buff)
    {
        JMenuBar menuBar = new JMenuBar(); //the primary menu bar

        // ----- File menu -----//
        //the open command & the open listener
        JMenuItem openItem = new JMenuItem(new AbstractAction("Open (CTRL + O)")
        {
            public void actionPerformed(ActionEvent a) //the open listener
            {
                //file chooser can throw a FileNotFoundException. Check for it
                try
                {
                    //creates the fileChooser
                    JFileChooser fileBox = new JFileChooser();
                    //stores the return of the file chooser after close
                    int result= jFileChooser.showOpenDialog(this);
                    //if a file was choosen
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        //capture that file
                        File file=jFileChooser.getSelectedFile();
                        //checks to see if the file already exists
                        if(file.exists())
                        {
                            //creates a overwrite option box
                            JOptionPane overwriteBox  = JOptionPane();
                            //shows the overwrite box and prompts the user to select another file
                            JOptionPane.showConfirmDialog(this,"The file exists, please select another",
                                                          "Existing file",JOptionPane.OK_OPTION);
                        }
                        else //if the file does not exist, read it in
                        {
                            //pass the readr the file path so it can load
                            buff.in(file.getPath());
                        }
                    }//end of overwrite box
                } //end of try
                catch(FileNotFoundException e)
                {
                    e.printStackTrace(); //print the stack trace on an error
                } //end of catch
            }
        }); //end of load listener
        //the save command & the save listener
        JMenuItem saveItem = new JMenuItem(new AbstractAction("Save (CTRL + S)")
        {
            public void actionPerformed(ActionEvent a) //the save listener
            {
                //file chooser can throw a FileNotFoundException. Check for it
                try
                {
                    //creates the fileChooser
                    JFileChooser fileBox = new JFileChooser();
                    //stores the return of the file chooser after close
                    int result = jFileChooser.showSaveDialog(this);
                    //if a valid path for saving was choosen
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            //create a file writer for the new file
                            FileWriter writer = new FileWriter(fileBox.getSelectedFile()+".txt")
                            {
                                //tell the readr to create a CSV string
                                writer.write(buff.out());
                                writer.close; //closes the writer
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } //end of try
                catch(FileNotFoundException e)
                {
                    e.printStackTrace(); //print the stack trace on an error
                } //end of catch
                //TODO write save listener
            }
        }); //end of save listener
        // ----- Print Menu ----- //
        //NOTE do we want to get rid of all these print options?
        JMenuItem printAllItem = new JMenuItem(new AbstractAction("All (CTRL + P)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("File -> Print -> All (CTRL + P). Prints the full FridgTrackr file.");
            }
        });
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

        //creates a menu for the print options
        JMenu printSubmenu = new JMenu("Print");
        //creates the quit option
        JMenuItem quitItem = new JMenuItem("Quit	(CTRL + Q)"); // ActionListener added later

        // ----- Print Menu ----- //
        printSubmenu.add(printAllItem);         //add print all to print menu
        printSubmenu.add(printFridgeItem);      //add print fridge to print menu
        printSubmenu.add(printRecipesItem);     //add print recipes to print menu
        printSubmenu.add(printGroceriesItem);   //add print groceries to print menu

        // ----- File Menu ----- //
        JMenu fileMenu = new JMenu("File"); //create the file menu
        fileMenu.add(openItem);     //adds the open option to the file menu
        fileMenu.add(saveItem);     //adds the save option to the file menu
        fileMenu.add(printSubmenu); //adds the print menu to the file menu
        fileMenu.add(quitItem);     //adds the quit option to the file menu
        menuBar.add(fileMenu);      //adds the file menu to the bar

        // ----- Edit menu ----- //
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

        // ----- Filter Menu ----- //
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
        // ----- Filter Menu ----- //
        JMenu filterBySubmenu = new JMenu("Filter By:"); //create filter menu
        filterBySubmenu.add(favorites);     //adds the favorite filter option
        filterBySubmenu.add(expiration);    //adds the expiration filter option
        filterBySubmenu.add(lowStock);      //adds the low stock filter option
        filterBySubmenu.add(leftovers);     //adds the leftover filter option

        // ----- Edit Menu ----- //
        JMenu editMenu = new JMenu("Edit"); //create the edit menu
        editMenu.add(copy);             //adds the copy option to the edit menu
        editMenu.add(cut);              //adds the cut option to the edit menu
        editMenu.add(paste);            //adds the paste option to the edit menu
        editMenu.addSeparator();        //adds a separator to the edit menu
        editMenu.add(search);           //adds the search option to the edit menu
        editMenu.add(restore);          //adds the restore option to the edit menu
        editMenu.add(filterBySubmenu);  //adds the filter menu to the edit menu
        menuBar.add(editMenu);          //adds the edit menu to the bar


        // ----- Settings Menu ----- //
        JMenuItem restorePt = new JMenuItem(new AbstractAction("Set Restore Point")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Restore Point. "
                        + "Allows the user to set the amount of restores to keep.");
            }
        });
        // ----- Unit Menu ----- //
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
        // ----- Units Menu ----- //
        JMenu units = new JMenu("Set units"); //creates the units menu
        units.add(metric);      //adds the metric option to the units menu
        units.add(imperial);    //adds the imperial option to the units menu
        units.add(customary);   //adds the customary option to the units menu

        // ----- Settings Menu ----- //
        JMenu settings = new JMenu("Settings"); //creates the settings menu
        settings.add(units);        //adds the set units option to the settings menu
        settings.add(restorePt);    //adds the set restore point to the settings menu
        menuBar.add(settings);      //adds the settings menu to the bar

        // ----- Help menu ----- //
        JMenuItem link = new JMenuItem(new AbstractAction("Link (CTRL + ?)")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Link. Opens a related webpage: http://www.myfridgefood.com/");
            }
        });
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

        // ----- Accessibility Menu ----- //
        JMenuItem fontSize = new JMenuItem(new AbstractAction("Font Size")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Font Size. Allows the user to edit the font size.");
            }
        });
        JMenuItem resolution = new JMenuItem(new AbstractAction("Resolution")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Help -> Accessibility -> Resolution. Allows the user to adjust the resolution.");
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

        // ----- Accessibility Menu ----- //
        JMenu accessibility = new JMenu("Accessibility"); //creates the acc. menu
        accessibility.add(fontSize);    //adds the font size option to the menu
        accessibility.add(invert);      //adds the invert option to the menu
        accessibility.add(bold);        //adds the bold text option to the menu

        // ----- Help Menu ----- //
        JMenu help = new JMenu("Help"); //creates the help menu
        help.add(accessibility);    //adds accessibility option to the help menu
        help.add(feedback);         //adds feedback option to the help menu
        help.add(donate);           //adds donate option to the help menu
        menuBar.add(help);          //adds the help menu to the bar

        //returns the finished menu
        return menuBar;
    }
}
