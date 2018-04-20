/*

The food class will be used to create food objects that will
be the entries on each of our databases

*/

package edu.ou.cs.hci.stages;

import java.io.File;
import java.time.LocalDate; //needed for checking days left

public class food
{
    //private class members
    private String id;
    private String name = null;
    private String amount = null;

    private boolean isFav = false;
    private boolean isLeftover = false;
    private String date = null;

    private String filePath = null;
    private String ingredeints = null;
    private String imagePath = null;
    private File   txtFile = null;

    //constructor for groceries
    public food(String id, String name, String amount)
    {
        //sets the name, amount an id of the grocery entry
        this.name = name;
        this.amount = amount;
        this.id = id;
    }

    //constructor for fridge entries
    public food(String id, String fav, String name, String amount, String expirDate, String leftover)
    {
        this.id = id;           //set the id to the given id
        this.name = name;       //set name to the given name
        this.amount = amount;   //set amount to the given amount
        this.date = expirDate;  //set date to the given expiration date

        switch(Integer.parseInt(fav)) //compare fav string to parse boolean
        {
            case 0: this.isFav = false; //if item is not a favorite
            case 1: this.isFav = true; //if item is a favorite
        }

        switch(Integer.parseInt(leftover)) //compare leftover string to parse boolean
        {
            case 0: this.isLeftover = false; //if item is not a leftover
            case 1: this.isLeftover = true; //if item is a leftover
        }

    }

    //constructor for recipes
    public food(String id, String name, String filePath, String ingredeints, String imagePath)
    {
        this.id = id;                       //set the id to the given id
        this.name = name;                   //set name to the given name
        this.filePath = filePath;           //set the filePath to the recipe txt
        this.ingredeints = ingredeints;     //set the value of ingredients
        this.imagePath = imagePath;         //set the filePath to food image
    }


    //constructor for recipes that are added by user
    public food(String id, String name, String ingredeints, File txt, String image)
    {
        this.id = id;                       //set the id to the given id
        this.name = name;                   //set name to the given name
        this.ingredeints = ingredeints;     //set the value of ingredients
        this.txtFile = txt;                 //set the file for the txt data
        this.imagePath = imagePath;         //set the filePath to food image
    }

    //makes a groceries entry from a line of the CSV
    public static food makeGr(String[] lineIn)
    {
        //call the groceries entry constructor
        return (new food(lineIn[0], lineIn[1], lineIn[2]));
    }

    //makes a fridge entry from a line of the CSV
    public static food makeFrg(String[] lineIn)
    {
        //call the fridge item constructor
        return(new food(lineIn[0],lineIn[1],lineIn[2],
                        lineIn[3],lineIn[4],lineIn[5]));
    }

    //makes a recipes entry from a line of the CSV
    public static food makeRcp(String[] lineIn)
    {
        //call the recipe item constructor
        return(new food(lineIn[0], lineIn[1], lineIn[2], lineIn[3], lineIn[4]));
    }

    //this will compute the days left before the food is expired
    public String getDaysLeft()
    {
        //since the date format is mm/dd/yy this "parses" date string
        String[] dateArray = date.split("/");
        //creates a LocalDate to store the expiration date
        LocalDate expirDate = LocalDate.of(Integer.parseInt(dateArray[2]),
                                           Integer.parseInt(dateArray[0]),
                                           Integer.parseInt(dateArray[1]));
        //gets current date
        LocalDate today = LocalDate.now();
        //how many days until the food is expired? Return value
        return Integer.toString(expirDate.until(today).getDays());
    }

    //getters for food
    public String getId()
    {
        return id;
    }

    public File getTxtFile()
    {
        return txtFile;
    }

    public String getName()
    {
        return name;
    }

    public String getAmount()
    {
        return amount;
    }

    public boolean getFav()
    {
        return isFav;
    }

    public String getFavStr()
    {
        return  String.valueOf(isFav);
    }

    public boolean getLeftover()
    {
        return isLeftover;
    }

    public String getLeftoverStr()
    {
        return  String.valueOf(isLeftover);
    }

    public String getDate()
    {
        return date;
    }

    public String getFile()
    {
        return filePath;
    }

    public String getIngredients()
    {
        return ingredeints;
    }

    public String getImage()
    {
        return imagePath;
    }
}
