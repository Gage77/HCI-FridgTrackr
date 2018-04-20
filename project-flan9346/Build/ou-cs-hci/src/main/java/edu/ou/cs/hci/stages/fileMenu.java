/*


*/
package edu.ou.cs.hci.stages;

import java.io.FileWriter; //needed for file writing
import javax.swing.JFileChooser; //needed for file chooser
import java.awt.event.*; //needed for listeners
import javax.swing.*; //needed for GUI elements
import java.io.File; //needed to create a file


public class fileMenu
{

    //creates the UI
    public static JMenuBar render(readr buff)
    {
        JMenuBar menuBar = new JMenuBar(); //the primary menu bar

        // ----- File menu -----//
        //the open command & the open listener
        JMenuItem openItem = new JMenuItem(new AbstractAction("Open")
        {
            public void actionPerformed(ActionEvent a) //the open listener
            {
                //file chooser can throw a FileNotFoundException. Check for it
                try
                {
                    //creates the fileChooser
                    JFileChooser fileBox = new JFileChooser();
                    //stores the return of the file chooser after close
                    int result= fileBox.showOpenDialog(fileBox);
                    //if a file was choosen
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        //capture that file
                        File file = fileBox.getSelectedFile();
                        //checks to see if the file already exists
                        if(file.exists())
                        {
                            //shows the overwrite box and prompts the user to select another file
                            JOptionPane.showConfirmDialog(null,"The file exists, please select another",
                                                          "Existing file",JOptionPane.OK_OPTION);
                        }
                        else //if the file does not exist, read it in
                        {
                            //pass the readr the file path so it can load
                            buff.in(file.getPath());
                        }
                    }//end of overwrite box
                } //end of try
                catch(Exception e)
                {
                    e.printStackTrace(); //print the stack trace on an error
                } //end of catch
            }
        }); //end of load listener
        //the save command & the save listener
        JMenuItem saveItem = new JMenuItem(new AbstractAction("Save")
        {
            public void actionPerformed(ActionEvent a) //the save listener
            {
                //file chooser can throw a FileNotFoundException. Check for it
                try
                {
                    //creates the fileChooser
                    JFileChooser fileBox = new JFileChooser();
                    //stores the return of the file chooser after close
                    int result = fileBox.showSaveDialog(fileBox);
                    //if a valid path for saving was choosen
                    if(result == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            //create a file writer for the new file
                            FileWriter writer = new FileWriter(fileBox.getSelectedFile()+".txt");
                            //tell the readr to create a CSV string
                            writer.write(buff.out());
                            writer.close(); //closes the writer
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } //end of try
                catch(Exception e)
                {
                    e.printStackTrace(); //print the stack trace on an error
                } //end of catch
            }
        }); //end of save listener

        //creates the quit option
        JMenuItem quitItem = new JMenuItem(new AbstractAction("Quit")
        {
            public void actionPerformed(ActionEvent a)
            {
                if(buff.changeMade())
                {
                    //creates the prompt message
                    String message = "You have made changes. Would you like to save?";
                    //prompts the user
                    int choice = JOptionPane.showConfirmDialog(null, message);
                    //if they choose to save
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        //file chooser can throw a FileNotFoundException. Check for it
                        try
                        {
                            //creates the fileChooser
                            JFileChooser fileBox = new JFileChooser();
                            //stores the return of the file chooser after close
                            int result = fileBox.showSaveDialog(fileBox);
                            //if a valid path for saving was choosen
                            if(result == JFileChooser.APPROVE_OPTION)
                            {
                                //create a file writer for the new file
                                FileWriter writer = new FileWriter(fileBox.getSelectedFile()+".txt");
                                //tell the readr to create a CSV string
                                writer.write(buff.out());
                                writer.close(); //closes the writer
                            }
                        } //end of try
                        catch(Exception e)
                        {
                            e.printStackTrace(); //print the stack trace on an error
                        } //end of catch
                        System.exit(0); //quits after saving
                    }
                    //if they choose not to save
                    if(choice == JOptionPane.NO_OPTION)
                    {
                        System.exit(0); //quits after saving
                    }
                }
            }
        });

        // ----- File Menu ----- //
        JMenu fileMenu = new JMenu("File"); //create the file menu
        fileMenu.add(openItem);     //adds the open option to the file menu
        fileMenu.add(saveItem);     //adds the save option to the file menu
        fileMenu.add(quitItem);     //adds the quit option to the file menu
        menuBar.add(fileMenu);      //adds the file menu to the bar

        // ----- Edit menu ----- //
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
        JMenuItem restorePt = new JMenuItem(new AbstractAction("Set Restore Point")
        {
            public void actionPerformed(ActionEvent a)
            {
                System.out.println("Window -> Settings -> Set Restore Point. "
                        + "Allows the user to set the amount of restores to keep.");
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
        editMenu.add(search);           //adds the search option to the edit menu
        editMenu.add(restore);          //adds the restore option to the edit menu
        editMenu.add(filterBySubmenu);  //adds the filter menu to the edit menu
        editMenu.add(restorePt);        //adds the set restore point to the edit menu
        menuBar.add(editMenu);          //adds the edit menu to the bar

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
        JMenuItem about = new JMenuItem(new AbstractAction("About")
        {
            public void actionPerformed(ActionEvent a)
            {
                aboutApp.render(); //creates the about window
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
        accessibility.add(resolution);  //adds the resolution option to the menu

        // ----- Help Menu ----- //
        JMenu help = new JMenu("Help"); //creates the help menu
        help.add(accessibility);    //adds accessibility option to the help menu
        help.add(about);            //adds about option to the help menu
        help.add(feedback);         //adds feedback option to the help menu
        help.add(donate);           //adds donate option to the help menu
        help.add(link);             //adds the link option to the help menu
        menuBar.add(help);          //adds the help menu to the bar

        //returns the finished menu
        return menuBar;
    }
}
