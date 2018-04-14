

/*
 * Changes the table so that the default highlighting is yellow
 */
public class MyTable extends JTable
{
    private static final Color	SELECT_COLOR = Color.decode("#ffcc00");

    MyTable(Object[][] obj, String[] cols){
                            super(obj,cols);

    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        this.setSelectionBackground(Color.decode(SELECT_COLOR));

    }

}
