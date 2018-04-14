/*
 * Changes the renderer so that the default highlighting is yellow
 * the soon to be "expired" item which is less than 3 days left are red
 * and when a red is highlighted it is orange instead of yellow
 * I did a lot of googling and reading to figure this one out
 */

class MyRenderer extends DefaultTableCellRenderer
{
    //color constants
    private static final Color	ALERT_COLOR = Color.decode("#e60000");
    private static final Color	SELECT_COLOR = Color.decode("#ffcc00");
    private static final Color  MIX_COLOR = Color.decode("#ff6600");

    public MyRenderer()
    {
        super();
        setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
    {
        if(Integer.parseInt((String)table.getValueAt(row, 3)) < 3)
        {
                setForeground(Color.black);
                if (isSelected)
                {
                    setBackground(MIX_COLOR);
                }
                else {
                    setBackground(ALERT_COLOR);
                }
        }
        else
        {
                if (isSelected)
                {
                    setBackground(SELECT_COLOR);
                }
                else {
                setBackground(Color.white);
                }
        }
        setText(value.toString());
        return this;
    }
}
