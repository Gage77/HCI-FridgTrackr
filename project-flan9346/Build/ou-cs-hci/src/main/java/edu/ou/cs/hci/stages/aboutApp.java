/*
This class is a static class that will create and display a
window describing our app
*/

package edu.ou.cs.hci.stages;

import javax.swing.*; //needed for JWindow and other GUI objects
import java.awt.*;
import java.awt.event.*; //needed for ActionListener
import edu.ou.cs.hci.resources.*; //needed for Resources call

public class aboutApp
{
    //this constat will hold the name of the about file for easy maintenance
    private static final String filename = "about.HTML";
    //this class will render the about window & display it
    public static void render()
    {
        JWindow win = new JWindow(); //NOTE is this focusable?
        //sets the layout to add things from top to bottom
        win.getContentPane().setLayout(new BoxLayout(win.getContentPane(), BoxLayout.Y_AXIS));
        win.getContentPane().add(new JScrollPane(aboutApp.editorPane())); //adds HTML text
        JButton close = new JButton("Close");  //creates the close button
        win.getContentPane().add(close);
        close.addActionListener(new ActionListener() //listens for close
        {
            public void actionPerformed(ActionEvent e) //when button is clicked
            {
                win.dispose(); //close window
            }
        });
        win.setSize(new Dimension(500, 500));
        win.setFocusableWindowState(true); //allowa the window to be focused on
        win.setVisible(true); //makes window visible
    }

    //this class will construct the editor pane that will display the HTML text
    private static JEditorPane editorPane()
    {
        try
        {
            JEditorPane editor = new JEditorPane(); //this will be the editor returned
            editor.setPage(Resources.getResource(filename)); //gives the editor the URL of the HTML file
            editor.setEditable(false); //prevents the about page from being edited
            return editor; //return the editor which is now displaying the HTML file
        } catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR: could not access about.HTML file");
            return null; //if the editor could not be built
        }
    }
}
