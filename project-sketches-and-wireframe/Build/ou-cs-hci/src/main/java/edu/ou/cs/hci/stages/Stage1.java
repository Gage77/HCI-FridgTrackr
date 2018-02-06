//******************************************************************************
// Copyright (C) 2016-2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  9 20:33:16 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file (for CS 4053/5053 homeworks).
// 20180123 [weaver]:	Modified for use in CS 3053 team projects.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.stages;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//******************************************************************************

/**
 * The <CODE>BuildTest</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage1
{
	//**********************************************************************
	// Public Class Members
	//**********************************************************************

	private static final Font	FONT =
		new Font(Font.SERIF, Font.ITALIC, 36);
	private static final Color	FILL_COLOR = Color.YELLOW;
	private static final Color	EDGE_COLOR = Color.RED;

	//**********************************************************************
	// Private Members
	//**********************************************************************

	// State (internal) variables
	private static String		message;

	//**********************************************************************
	// Main
	//**********************************************************************

	public static void main(String[] args)
	{
		message = "Build Test";	// Could use an arg for this

		JFrame			frame = new JFrame("Fridge Program");

		JPanel			recipes = new JPanel(new BorderLayout());
		JPanel			fridge = new JPanel(new BorderLayout());
		JPanel			groceries = new JPanel(new BorderLayout());

		JButton			rAdd = new JButton("add");
		JButton			fAdd = new JButton("add");
		JButton			gAdd = new JButton("add");

		//adds a title to each scroll section
		recipes.setBorder(BorderFactory.createTitledBorder("recipes"));
		fridge.setBorder(BorderFactory.createTitledBorder("fridge"));
		groceries.setBorder(BorderFactory.createTitledBorder("groceries"));

		//creates the main window
		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(recipes, BorderLayout.LINE_START);
		frame.getContentPane().add(fridge, BorderLayout.CENTER);
		frame.getContentPane().add(groceries, BorderLayout.LINE_END);

		//adds the scroll window
		 String[] colName = new String[] {"Name" ,"Amount", "Delete"};
        Object[][] products = new Object[][] {
                { "Apples" ,"15", "[x]" },
                { "Oranges" ,"20", "[x]"},
                { "Peaches" ,"10", "[x]"},
            };

        JTable table = new JTable( products, colName );
		fridge.add( new JScrollPane( table ) );

		String[] colName1 = new String[] {"Name" ,"Amount", "Delete"};
			 Object[][] products1 = new Object[][] {
							 { "Apples" ,"15", "[x]" },
							 { "Oranges" ,"20", "[x]"},
							 { "Peaches" ,"10", "[x]"},
					 };

			 JTable table1 = new JTable( products1, colName1 );
		groceries.add(new JScrollPane(table1) );

		 String[] colName2 = new String[] {"Name","Delete"};
        Object[][] products2 = new Object[][] {
                { "Grilled Cheese", "[x]" },
                { "Pizza", "[x]" },
                { "Mac & Cheese", "[x]" },
            };
        JTable table2 = new JTable( products2, colName2);
        recipes.add( new JScrollPane(table2));

        //adds the add buttons
        fridge.add(fAdd, BorderLayout.PAGE_END);
        recipes.add(rAdd, BorderLayout.PAGE_END);
        groceries.add(gAdd, BorderLayout.PAGE_END);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	}

}

//******************************************************************************
