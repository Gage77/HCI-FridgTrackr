

public class readr
{
    // Public Class Members
    Fridge fridge;
    Redcipes recipe;
    Groceries groceries;

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
                    //TODO create food make methods
                    case 2: groceries.add(food.makeGr(line));//builds grocery
                    case 4: recipes.add(food.makeRcp(line)); //builds recipe
                    case 5: fridge.add(food.makeFrg(line)); //builds fridge food
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
    public out()
    {
        //TODO write code for readr saving
        //TODO write code for database -> CSV conversion
    }

}
