/*

The food class will be used to create food objects that will
be the entries on each of our databases

*/
public class food
{
    //private class members
    private String id;
    private String name = null;
    private String amount = null;

    private boolean isFav = null;
    private boolean isLeftover = null;
    private String date = null; //TODO change to some other data type

    private String filePath = null;
    private String ingredients = null;
    private String imagePath = null;

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

        switch(fav) //compare fav string to parse boolean
        {
            case 0: this.isFav = False; //if item is not a favorite
            case 1: this.isFav = True; //if item is a favorite
        }

        switch(leftover) //compare leftover string to parse boolean
        {
            case 0: this.isLeftover = False; //if item is not a leftover
            case 1: this.isLeftover = True; //if item is a leftover
        }

    }

    //constructor for recipes
    public food(String id, String name, String filePath, String ingredients, String imagePath)
    {
        this.id = id;                       //set the id to the given id
        this.name = name;                   //set name to the given name
        this.filePath = filePath;           //set the filePath to the recipe txt
        this.ingredients = ingredients;     //set the value of ingredients
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
                        lineIn[3],line[4],line[5]));
    }

    //makes a recipes entry from a line of the CSV
    public static makeRcp(String[] lineIn)
    {
        //call the recipe item constructor
        return(new food(lineIn[0], lineIn[1], lineIn[2], lineIn[3], line[4]));
    }

    //TODO add getters & setters for food's values
    //getters for food
    public String getId()
    {
        return id;
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
        return id;
    }

    public String getImage()
    {
        return imagePath;
    }
}
