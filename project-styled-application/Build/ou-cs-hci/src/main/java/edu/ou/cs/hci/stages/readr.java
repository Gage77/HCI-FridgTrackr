/*
This class will read in CSV files, output to CSV files
and check for changes
*/

package edu.ou.cs.hci.stages;

import java.io.IOException; //need to check file in/out trys
import java.io.FileReader; //needed for file reading
import java.io.BufferedReader; //need for buffered readering

public class readr
{
    // Private Class Members
    private Fridge fridge;
    private Recipes recipes;
    private Groceries groceries;

    //constructor
    public readr(Fridge fr, Recipes rp, Groceries gr)
    {
        this.fridge = fr;
        this.recipes = rp;
        this.groceries = gr;
    }

    //this will take a file path and load it using helper methods
    public void in(String filePath)
    {
        try
        {
            //creates the actual input reader
            BufferedReader br = new BufferedReader( new FileReader(filePath));
            String lineIn = br.readLine(); //read the 1st line
            while (lineIn != null) //if there is text
            {
                String[] line = lineIn.split(","); //moves data into an array
                //check the lines ID, create the object, & and to database
                switch(Integer.parseInt(line[0])) //compare id's with the first string
                {
                    case 0: groceries.add(food.makeGr(line));//builds grocery
                    case 1: fridge.add(food.makeFrg(line)); //builds fridge food
                    case 2: recipes.add(food.makeRcp(line)); //builds recipe
                }
                lineIn = br.readLine(); //reads the next line
            }

        } catch (IOException e) { //in case of an IOException
                e.printStackTrace(); //print stack trace
        } finally { //always check  //TODO how to check br from try?
            if (br != null) //if the buffer still has content
            {
                try {
                    br.close(); //close it
                }
                catch (IOException e) { //in case of IOException
                    e.printStackTrace(); //print stack
                }
            }
        }
    }

    //used to save & convert TO CSV
    public String out()
    {
        //this will store the databases we convert to the CSV format
        StringBuilder output = new StringBuilder();
        output.append(fridge.out());  //add the fridge database to the CSV
        output.append(groceries.out()); //add the grocery database to the CSV
        output.append(recipes.out());  //add the recipe database to the CSV
        return (output.toString()); //returns the "file" as a string
    }

    //used to detect changes
    public Boolean changeMade()
    {
        //if a change was made to any database
        if(fridge.changeMade() || recipes.changeMade() || groceries.changeMade())
        {
            return Boolean.TRUE; //return that changes have been made
        }
        return Boolean.FALSE; //otherwise report no changes
    }

}
