

public class readr
{
    // Private Class Members
    private Fridge fridge;
    private Recipes recipe;
    private Groceries groceries;

    //constructor
    public readr(Fridge fr, Recipes rp, Groceries gr)
    {
        this.fridge = fr;
        this.recipe = rp;
        this.groceries = gr;
    }

    //this will take a file path and load it using helper methods
    public in(String filePath)
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
                String id; //used to testgroceries
                switch(line[0]) //compare id's with the first string
                {
                    case 0: groceries.add(food.makeGr(line));//builds grocery
                    case 1: fridge.add(food.makeFrg(line)); //builds fridge food
                    case 2: recipes.add(food.makeRcp(line)); //builds recipe
                }
                lineIn = br.readLine(); //reads the next line
            }

        } catch (FileNotFoundException e) { //in case of a file not found error
                e.printStackTrace(); //print stack trace
        } catch (IOException e) { //in case of an IOException
                e.printStackTrace(); //print stack trace
        } finally { //always check
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
        output.append(fridge.out);  //add the fridge database to the CSV
        output.append(grocery.out); //add the grocery database to the CSV
        output.append(recipe.out);  //add the recipe database to the CSV
        return (output.toString()); //returns the "file" as a string
    }

}
