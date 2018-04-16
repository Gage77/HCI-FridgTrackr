//******************************************************************************
// Copyright (C) 2016-2018 University of Oklahoma Board of Trustees.
//******************************************************************************
// Last modified: Tue Feb  9 20:33:16 2016 by Chris Weaver
//******************************************************************************
// Major Modification History:
//
// 20160209 [weaver]:	Original file (for CS 4053/5053 homeworks).
// 20180123 [weaver]:	Modified for use in CS 3053 team projects.
// 20180406 [weaver]:	Added CSV reading.
//
//******************************************************************************
// Notes:
//
//******************************************************************************

package edu.ou.cs.hci.stages;

//import java.lang.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import org.apache.commons.csv.*;	// New library for working with CSVs
import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage6</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class GivenStage7
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
		message = "Same UI, now with Data!";	// Could use an arg for this

		JFrame			frame = new JFrame("Stage 7 - Populated Application");
		JPanel			panel = new HelloPanel(message);

		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

		//ArrayList<String>	personaTitles =
		//	Resources.getLines("personas/titles.txt");

		// This class is a renamed version of the BuildTest class. You can
		// use it as the starter/main class that hooks into whatever code
		// you've created in past stages. See the uncommented 'createScript'
		// line in build.gradle to see how to make an executable that starts
		// with a class like this one.
	}

	//**********************************************************************
	// Private Inner Classes
	//**********************************************************************

	private static final class HelloPanel extends JPanel
		implements ActionListener
	{
		// Names of the columns in the CSV of baseball stadiums
		public static String[]	COLUMNS =
		{
			"ID",
			"Name",
			"City",
			"State",
			"Latitude",
			"Longitude",
			"Capacity",
		};

		private final String	message;
		public final JTable	table;		// Component for displaying the CSV

		public HelloPanel(String message)
		{
			this.message = ((message != null) ? message : "");

			ImageIcon	 icon =
				Resources.getImage("icons/hmmm.png");

			JButton	button = new JButton("Click me to see a CSV...", icon);

			button.addActionListener(this);
			add(button, BorderLayout.NORTH);

			table = new JTable();
			add(new JScrollPane(table), BorderLayout.CENTER);
		}

		public HelloPanel()
		{
			this("");
		}

		public void	paintComponent(Graphics g)
		{
			FontMetrics	fm = g.getFontMetrics(FONT);
			int			fw = fm.stringWidth(message);
			int			fh = fm.getMaxAscent() + fm.getMaxDescent();
			int			x = (getWidth() - fw) / 2;
			int			y = getHeight() - fh - 10;
			Rectangle		r = new Rectangle(x, y, fw + 4, fh + 1);

			if (FILL_COLOR != null)
			{
				g.setColor(FILL_COLOR);
				g.fillRect(r.x, r.y, r.width - 1, r.height - 1);
			}

			if (EDGE_COLOR != null)
			{
				g.setColor(EDGE_COLOR);
				g.drawRect(r.x, r.y, r.width - 1, r.height - 1);

				g.setFont(FONT);
				g.drawString(message, r.x + 2, r.y + fm.getMaxAscent());
			}
		}

		// ActionListener for the button
		public void	actionPerformed(ActionEvent e)
		{
			// Access the CSV as a resource (you won't access them this way!!!)
			URL	url = Resources.getResource("data/stadiums.csv");

			try
			{
				// Create a reader for the CSV
				InputStream		is = url.openStream();
				InputStreamReader	isr = new InputStreamReader(is);
				BufferedReader		r = new BufferedReader(isr);

				// Use the Apache Commons CSV library to read records from it
				CSVFormat			format = CSVFormat.DEFAULT;
				CSVParser			parser = CSVParser.parse(r, format);
				java.util.List<CSVRecord>	records = parser.getRecords();

				// Allocate a 2-D array to keep the rows and columns in memory
				String[][]	values = new String[records.size()][COLUMNS.length];

				for (CSVRecord record : records)	// Loop over the rows...
				{
					Iterator<String>	k = record.iterator();
					int				i = (int)record.getRecordNumber() - 1;
					int				j = 0;		// Column number

					// Print each record to the console
					System.out.println("***** #" + i + " *****");

					while (k.hasNext())			// Loop over the columns...
					{
						values[i][j] = k.next();	// Grab each cell's value

						// Print each value to the console...
						System.out.println(COLUMNS[j] + " = " + values[i][j]);
						j++;
					}

					System.out.println();

					// ...and have the table show the entire value array.
					table.setModel(new DefaultTableModel(values, COLUMNS));
				}

				is.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}

//******************************************************************************
