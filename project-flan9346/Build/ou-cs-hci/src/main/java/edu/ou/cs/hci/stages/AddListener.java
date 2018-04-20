package edu.ou.cs.hci.stages;

import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.*;

//this class will handle requests to add new items to the databases
public class AddListener implements ActionListener
{
    private static final String[] UNITS = {"Cup(s)","g","kg","L","lb",
                                           "ml","oz","tsp","Tbsp"};
    private Fridge fr;
    private Recipes re;
    private Groceries gr;
    private JTabbedPane tabs;
    private food toAdd;
    //action listeners file/image loading
    private File txtFile = null;
    private String imagePath = null;

    //constructor
    AddListener(Fridge fr, Recipes re, Groceries gr, JTabbedPane tabs)
    {
        this.fr = fr;
        this.re = re;
        this.gr = gr;
        this.tabs = tabs;
    }

    @Override  //when an event occurs call this
    public void actionPerformed(ActionEvent e)
    {
        int index = tabs.getSelectedIndex();
        if(index == 0) //finds the correct database to add to
            frWindow(); //if a fridge
        else if(index == 1)
            reWindow(); //if a recipe
        else if(index == 2)
            grWindow(); //if a grocery

    }

    //the add pop-up for fridge databases
    private void frWindow()
    {
        JFrame frame = new JFrame("Add to Fridge"); //creates the window
        JPanel panel = new JPanel(new FlowLayout()); //creates the panel
        //create fields
        JTextField nameField = new JTextField("name"); //creates the name field
        JTextField amountField = new JTextField("amount"); //creates the amount field
        JCheckBox fav = new JCheckBox("Favorite?"); //creates the fav checkbox
        JComboBox<String> units = new JComboBox<String>(UNITS); //creates combo box
        JButton areDone = new JButton("Finished"); //creates finished button
        JTextField dateField = new JTextField("mm/dd/yy"); //creates the name field
        JCheckBox leftover = new JCheckBox("Leftovers?"); //creates the leftover checkbox
        //add fields
        panel.add(nameField);
        panel.add(fav);
        panel.add(amountField);
        panel.add(units);
        panel.add(dateField);
        panel.add(leftover);
        panel.add(areDone);
        frame.add(panel);
        frame.setSize(new Dimension(600, 500));
        frame.setVisible(true); //makes the pop up
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //when finished is pressed
        areDone.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //these will store the entered data
                String name = null;
                String amount = null;
                String date = null;
                int isFavorite = 0;
                int isLeftover = 1;
                //this will set the varibles from the field
                if(!(nameField.getText().equals("name"))) //if name is not default
                    name = nameField.getText(); //set name
                if(!(amountField.getText().equals("amount"))) //if amount != default
                    amount = amountField.getText(); //set amount
                if(!(dateField.getText().equals("epiration date: mm/dd/yy"))) //if date is not default
                    date = dateField.getText(); //set date
                if(fav.isSelected()) //if item is a fav
                    isFavorite = 1; //set true flag
                if(leftover.isSelected()) //if item is a leftover
                    isLeftover = 1; //set true flag
                if(name != null && amount != null && (date!=null || leftover.isSelected()))
                {
                    //creates food item
                    toAdd = new food("0",String.valueOf(isFavorite), name,
                                          amount, date, String.valueOf(isLeftover));
                    fr.add(toAdd); //adds item to the database
                    frame.dispose(); //close window
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Please fill in the name & amount fields, and"+
                    "\n"+"either the epiration date field or make the item a leftover");
                }
            }
        });
    }

    //the add pop-up for recipes databases
    private void reWindow()
    {
        JFrame frame = new JFrame("Add to Recipes"); //creates the window
        JPanel panel = new JPanel(new FlowLayout()); //creates the panel
        //create fields
        JTextField nameField = new JTextField("name"); //creates the name field
        JComboBox<String> units = new JComboBox<String>(UNITS); //creates combo box
        JTextField ingField = new JTextField("ingredients"); //creates the ingredients field
        //these buttons will allow the user to select a file path for the .txt and image
        JButton loadFile = new JButton("Select a decription text file (.txt only)");
        JButton loadImage = new JButton("Select a image");
        JButton areDone = new JButton("Finished"); //creates finished button
        //add fields
        panel.add(nameField);
        panel.add(units);
        panel.add(ingField);
        panel.add(loadFile);
        panel.add(loadImage);
        panel.add(areDone);
        frame.add(panel);

        loadFile.addActionListener(new ActionListener(){
            @Override //when load file is pressed
            public void actionPerformed(ActionEvent arg0){
                txtFile = recipeTxtFile(); //call method to deal with loading .txt
            }});
        loadImage.addActionListener(new ActionListener(){
            @Override //when load image is pressed
            public void actionPerformed(ActionEvent arg0){
                imagePath = recipeImageFile(); //call method to deal with loading image
            }});

        //frame setup
        frame.setSize(new Dimension(600, 500));
        frame.setVisible(true); //makes the pop up
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //when finished is pressed
        areDone.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //these will store the entered data
                String name = null;
                String ingredients = null;
                boolean fileFlag = false; //used to short logic test
                //this will set the varibles from the field
                if(!(nameField.getText().equals("name"))) //if name is not default
                    name = nameField.getText(); //set name
                if(!(ingField.getText().equals("ingredients"))) //if amount != default
                    ingredients = ingField.getText(); //set amount

                if(imagePath != null && txtFile != null)
                {
                    fileFlag = true;
                }

                if(name != null && ingredients != null && fileFlag)
                {
                    //creates food item
                    toAdd = new food("2", name, ingredients, txtFile, imagePath);
                    re.add(toAdd); //adds item to the database
                    frame.dispose(); //close window
                }
                else //if not all the required fields have been filled
                {
                    JOptionPane.showMessageDialog(frame, "Please fill in the name & ingredients fields, and"+
                    "\n"+"select a both a .txt file and an image");
                }
            }
        });
    }

    //the add pop-up for grocery databases
    private void grWindow()
    {
        JFrame frame = new JFrame("Add to Groceries"); //creates the window
        JPanel panel = new JPanel(new FlowLayout()); //creates the panel
        //create fields
        JTextField nameField = new JTextField("name"); //creates the name field
        JTextField amountField = new JTextField("amount"); //creates the amount field
        JComboBox<String> units = new JComboBox<String>(UNITS); //creates combo box
        JButton areDone = new JButton("Finished"); //creates finished button
        //add fields
        panel.add(nameField);
        panel.add(amountField);
        panel.add(units);
        panel.add(areDone);
        frame.add(panel);
        frame.setSize(new Dimension(600, 500));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true); //makes the pop up
        //when finished is pressed
        areDone.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //these will store the entered data
                String name = null;
                String amount = null;
                //this will set the varibles from the field
                if(!(nameField.getText().equals("name"))) //if name is not default
                    name = nameField.getText(); //set name
                if(!(amountField.getText().equals("amount"))) //if amount != default
                    amount = amountField.getText(); //set amount
                if(name != null && amount != null)
                {
                    //creates food item
                    toAdd = new food("1", name, amount);
                    gr.add(toAdd); //adds item to the database
                    frame.dispose(); //close window
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "Please fill in the name & amount fields");
                }
            }
        });
    }

    //this will read in a txt file
    private File recipeTxtFile()
    {
        //file chooser can throw a FileNotFoundException. Check for it
        try
        {
            //creates the fileChooser
            JFileChooser txtFile = new JFileChooser();
            //sets a filter so only .txt files can be choosen
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
            txtFile.setFileFilter(filter);
            //stores the return of the file chooser after close
            int result= txtFile.showOpenDialog(txtFile);
            //if a file was choosen
            if(result == JFileChooser.APPROVE_OPTION)
            {
                //capture that file
                File file = txtFile.getSelectedFile();
                return file;
            }
        } //end of try
        catch(Exception e)
        {
            e.printStackTrace(); //print the stack trace on an error
        } //end of catch
        return null;
    }

    private String recipeImageFile()
    {
        //file chooser can throw a FileNotFoundException. Check for it
        try
        {
            //creates the fileChooser
            JFileChooser imageFile = new JFileChooser();
            //sets a filter so only .txt files can be choosen
            FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            imageFile.setFileFilter(filter);
            //stores the return of the file chooser after close
            int result= imageFile.showOpenDialog(imageFile);
            //if an image was choosen
            if(result == JFileChooser.APPROVE_OPTION)
            {
                //capture that file
                File file = imageFile.getSelectedFile();
                return file.getPath();
            }
        } //end of try
        catch(Exception e)
        {
            e.printStackTrace(); //print the stack trace on an error
        } //end of catch
        return null;

    }
}
