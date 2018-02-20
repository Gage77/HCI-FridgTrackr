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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.ou.cs.hci.resources.*;

//******************************************************************************

/**
 * The <CODE>Stage3</CODE> class.<P>
 *
 * @author  Chris Weaver
 * @version %I%, %G%
 */
public final class Stage3
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

	//main
	public static void main(String[] args)
	{
		//MAIN WINDOW creates the base JFrame on which everything will be displayed
		JFrame			frame = new JFrame("FridgTrackr");

		//creates the 3 category panels
		JPanel			recipes = new JPanel(new BorderLayout());
		JPanel			fridge = new JPanel(new BorderLayout());
		JPanel			groceries = new JPanel(new BorderLayout());

		//adds a button to each of the 3 category panels so new data can e added
		JButton			rAdd = new JButton("add");
		JButton			fAdd = new JButton("add");
		JButton			gAdd = new JButton("add");

		//adds a title to each category panel
		recipes.setBorder(BorderFactory.createTitledBorder("recipes"));
		fridge.setBorder(BorderFactory.createTitledBorder("fridge"));
		groceries.setBorder(BorderFactory.createTitledBorder("groceries"));

		//sets the defualt size of the main window
		frame.setBounds(50, 50, 600, 600);
		frame.getContentPane().setLayout(new BorderLayout());

		//creates the pane that will store the category tabs
		JTabbedPane tabs = new JTabbedPane();
		//sets icons for tabs
		Icon fridgeIcon = new ImageIcon(Stage3.class.getResource("refrigerator.png"));
		Icon recipesIcon = new ImageIcon(Stage3.class.getResource("contract.png"));
		Icon groceriesIcon = new ImageIcon(Stage3.class.getResource("groceries.png"));
		//adds tabs to JTabbedPane
		tabs.addTab("Fridge", fridgeIcon, fridge);
		tabs.addTab("Recipes", recipesIcon, recipes);
		tabs.addTab("Groceries", groceriesIcon, groceries);
		//adds the JTabbedPane to the base pane
		frame.getContentPane().add(tabs, BorderLayout.CENTER);

		//creates the content of the fridge category panel
		String[] colName = new String[] {"Name" ,"Amount", "Delete"};
		Object[][] products = new Object[][] {
                { "Apples" ,"15", "[x]" },
                { "Oranges" ,"20", "[x]"},
                { "Peaches" ,"10", "[x]"},
							};
		//creates a table to hold the fridge panel data
    	JTable fridgeTable = new JTable( products, colName );
		//adds the data panel to the fridge category panel
		fridge.add(new JScrollPane(fridgeTable));

		//creates the content of the groceries category panel
		String[] colName1 = new String[] {"Name" ,"Amount", "Delete"};
		Object[][] products1 = new Object[][] {
							 { "Apples" ,"15", "[x]" },
							 { "Oranges" ,"20", "[x]"},
							 { "Peaches" ,"10", "[x]"},
						 };
		//creates a table to hold the groceries panel data
		JTable table1 = new JTable( products1, colName1 );
		//adds the data panel to the fridge category panel
		groceries.add(new JScrollPane(table1) );

		//creates the content of the recipes category panel
		String[] colName2 = new String[] {"Name","Delete"};
    	Object[][] products2 = new Object[][] {
                { "Grilled Cheese", "[x]" },
                { "Pizza", "[x]" },
                { "Mac & Cheese", "[x]" },
            };
		//creates a table to hold the recipes panel data
		JTable table2 = new JTable( products2, colName2);
		//adds the data panel to the recipes category panel
    	recipes.add( new JScrollPane(table2));

		//creates the filters
		JPanel			filterPanel = new JPanel(new FlowLayout());
		JCheckBox		favoritesBox = new JCheckBox();
		JLabel			favoritesLabel = new JLabel("Favorites");
		JCheckBox		expiredBox = new JCheckBox();
		JLabel			expiredLabel = new JLabel("Expired");
		JCheckBox		lowBox = new JCheckBox();
		JLabel			lowLabel = new JLabel("Low Stock");
		JCheckBox		leftoversBox = new JCheckBox();
		JLabel			leftoversLabel = new JLabel("Leftovers");

		//adds the filters to the filter panel
		filterPanel.add(favoritesBox);
		filterPanel.add(favoritesLabel);
		filterPanel.add(expiredBox);
		filterPanel.add(expiredLabel);
		filterPanel.add(lowBox);
		filterPanel.add(lowLabel);
		filterPanel.add(leftoversBox);
		filterPanel.add(leftoversLabel);
		//adds the filter panel to the base frame
		frame.getContentPane().add(filterPanel, BorderLayout.NORTH);

    	//adds the add buttons
    	fridge.add(fAdd, BorderLayout.PAGE_END);
    	recipes.add(rAdd, BorderLayout.PAGE_END);
    	groceries.add(gAdd, BorderLayout.PAGE_END);
    	
    	
    	//PERSONAS & SCENARIOS window
	JFrame			personas = new JFrame("Senarios");
	
	//creates the titles and senarios panels
	JPanel			listPane = new JPanel(new BorderLayout());
	JPanel			descriptPane = new JPanel(new BorderLayout());
	
	//JText Area string
	String text = "default";
	
	//titles for each pane
	listPane.setBorder(BorderFactory.createTitledBorder("scenarios"));
	descriptPane.setBorder(BorderFactory.createTitledBorder("descriptions"));
	
	//set up frame
	personas.setBounds(150, 100, 700, 600);
	personas.getContentPane().setLayout(new BorderLayout());
	
	//add things to frame
	personas.getContentPane().add(listPane, BorderLayout.WEST);
	personas.getContentPane().add(descriptPane, BorderLayout.CENTER);
	
	ArrayList<String> desc = Resources.getLines("scenarios/descriptions.txt");
	ArrayList<String> tit  = Resources.getLines("scenarios/titles.txt");
	
	if (desc.isEmpty()) {
		desc.add("default ");
	}
	if (tit.isEmpty()) {
		tit.add("default ");
	}

	//list
	//String[] sen = new String[] {"long selections" ,"sen 2", "sen 3"};
	DefaultListModel<String> dfl = new DefaultListModel<String>();
	for (int i = 0; i < tit.size(); i++) {
		dfl.addElement(tit.get(i));
	}
	
	JList			scenarios = new JList(dfl);
	scenarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	scenarios.setLayoutOrientation(JList.VERTICAL);
	scenarios.setVisibleRowCount(-1);
	scenarios.setSelectedIndex(0);
	
	//JTextArea
	JTextArea		descriptions = new JTextArea(text);
	descriptions.setFont(new Font("Serif", Font.ITALIC, 16));
	descriptions.setLineWrap(true);
	descriptions.setWrapStyleWord(true);
	descriptions.setText(desc.get(scenarios.getSelectedIndex()));
	
	//selection listener
	ListSelectionListener sl = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				
				descriptions.setText(desc.get(scenarios.getSelectedIndex()));
			}
		}
	};
	
	scenarios.addListSelectionListener(sl);
	
	
	listPane.add(scenarios, BorderLayout.CENTER);
	descriptPane.add(descriptions, BorderLayout.CENTER);
	
	
	
	//SURVEY WINDOW


	/*
	BufferedWriter output = null;
    try {
        File file = new File("example.txt");
        output = new BufferedWriter(new FileWriter(file));
        output.write(text);
    } catch ( IOException e ) {
        e.printStackTrace();
    } finally {
      if ( output != null ) {
        output.close();
      }
    }
    */
	
	JFrame			survey 	= new JFrame("Survey 1.1");
	survey.setBounds(50, 50, 850, 700);
	survey.getContentPane().setLayout(new BorderLayout());
	
	JPanel 			mainPane = new JPanel();
	mainPane.setLayout(new GridLayout(0,1));
	survey.add(mainPane);

	
	//Question 1 pane
	JPanel			questionPane1		= new JPanel();
    questionPane1.setBorder(BorderFactory.createLineBorder(Color.black));
	questionPane1.setLayout(new GridLayout(5,1));
	mainPane.add(questionPane1);
	
	//Question 1 Responses
	JLabel			question1	= new JLabel(" 1) Select options that you would find helpful?");
	JCheckBox		box1 = new JCheckBox("Being reminded when food in your kitchen is going to expire");
	JCheckBox		box2 = new JCheckBox("Having a constantly updated list of your personal recipes that could be made using ingredients in your kitchen ");
	JCheckBox		box3 = new JCheckBox("Having a constantly updated record of how much money you’ve spent on groceries (i.e. monthly expenses, weekly expenses) ");
	JCheckBox		box4 = new JCheckBox("Being able to export your food quantities, grocery expenses, and/or recipies into a printable document");
	
	//Q1 add	
	questionPane1.add(question1);
	questionPane1.add(box1);
	questionPane1.add(box2);
	questionPane1.add(box3);
	questionPane1.add(box4);
	
	
	//RANGE
	JPanel range = new JPanel(new GridLayout(0, 1));
	range.setBorder(BorderFactory.createLineBorder(Color.black));
	mainPane.add(range);
	
	JLabel question = new JLabel(" 2) How much of the food that you buy would you estimate that you waste/throw out?");

	range.add(question);
	JRadioButton button1 = new JRadioButton("0-10%");
	JRadioButton button2 = new JRadioButton("10-20%");
	JRadioButton button3 = new JRadioButton("20-30%");
	JRadioButton button4 = new JRadioButton("Over 30%");
	
	ButtonGroup group = new ButtonGroup();
    group.add(button1);
    group.add(button2);
    group.add(button3);
    group.add(button4);
    
    range.add(button1);
    range.add(button2);
    range.add(button3);
    range.add(button4);

    //SPINNER
    //panel for the # of groceries questions
    JPanel spinPanel = new JPanel(new BorderLayout());
    mainPane.add(spinPanel);
    //adds the questions text
    JLabel spinQuestion = new JLabel(" 3) On average, how many bags of groceries do you buy a week?");
    //creates the spinner model
    SpinnerNumberModel bagModel = new SpinnerNumberModel(0 /*default*/, 0 /*min*/, 100/*max*/, 1/*incr.*/);
    //creates spinner
    JSpinner groSpinner = new JSpinner(bagModel);
    //adds the question text to the panel
    spinPanel.add(spinQuestion, BorderLayout.NORTH);
    //adds the spinner to panel
    spinPanel.add(groSpinner, BorderLayout.SOUTH);
    //adds a border to the panel
    spinPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    
    
    
    JPanel			questPanel = new JPanel();
    questPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    mainPane.add(questPanel);
    GridLayout		questionLayout = new GridLayout(2,1);
    questPanel.setLayout(questionLayout);
    JTextArea		questionArea = new JTextArea(" 4) If you could, what information would you like to always know about "
    				+ "your kitchen, in order of importance? For example food stock, expiration dates, etc.");
    questionArea.setEditable(false);
    questionArea.setLineWrap(true);
    questionArea.setOpaque(false);
    JTextArea		answerArea = new JTextArea();
    answerArea.setBorder(BorderFactory.createLineBorder(Color.black));
    questPanel.add(questionArea);
    questPanel.add(answerArea);
    
    
    // SLIDER
 	JPanel q4 = new JPanel(new GridLayout(2, 1));
 	mainPane.add(q4);

 	// Setup parameters for slider
 	int sliderMin = 0;
 	int sliderMax = 4;
 	int sliderInitial = 2;
 	// Create slider
 	JSlider usefullnessSlider = new JSlider(JSlider.HORIZONTAL, sliderMin,
 		sliderMax, sliderInitial);

 	// Create labels for slider in hashtable. Integer value corresponds to its
 	// cooresponding location on slider
 	Hashtable<Integer, JLabel> sliderTable = new Hashtable<Integer, JLabel>();
 	sliderTable.put(new Integer(0), new JLabel("Not Useful at All"));
 	sliderTable.put(new Integer(1), new JLabel("Kind of Useful"));
 	sliderTable.put(new Integer(2), new JLabel("Neutral"));
 	sliderTable.put(new Integer(3), new JLabel("Useful"));
 	sliderTable.put(new Integer(4), new JLabel("Extremely Useful"));

 	// Add labels to slider
 	usefullnessSlider.setLabelTable(sliderTable);

 	// Make the slider more useful by adding the following
 	usefullnessSlider.setSnapToTicks(false);
 	usefullnessSlider.setMajorTickSpacing(10);
 	usefullnessSlider.setPaintLabels(true);

 	// Setup the question
 	String scaleQuestionText = "<html> 5) How useful would an application that keeps "
 		+ "track of the items in your fridge and pantry <BR> and allows you to set up reminders "
 		+ "when you are running low on a specific item be to you?</html>";

 	JLabel scaleQuestion = new JLabel(scaleQuestionText);

 	// Add the question to the frame
 	q4.add(scaleQuestion);
 	q4.add(usefullnessSlider);
 	q4.setBorder(BorderFactory.createLineBorder(Color.black));
    
    
    
    JButton finish = new JButton("Finish");
    mainPane.add(finish);

ActionListener finishListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			
				// RANGE QUESTION CHECK
				boolean rangeIsClicked = false;
				String rangeAnswer = "";
				if (button1.isSelected())
				{
					rangeAnswer = button1.getText();
					rangeIsClicked = true;
				}
				else if (button2.isSelected())
				{
					rangeAnswer = button2.getText();
					rangeIsClicked = true;
				}
				else if (button3.isSelected())
				{
					rangeAnswer = button3.getText();
					rangeIsClicked = true;
				}
				else if (button4.isSelected())
				{
					rangeAnswer = button4.getText();
					rangeIsClicked = true;
				}
				
				boolean selectIsClicked = false;
				String selectAnswer = "";
				if (box1.isSelected())
				{
					selectAnswer += box1.getText() + "\n";
					selectIsClicked = true;
				}
				if (box2.isSelected())
				{
					selectAnswer += box2.getText() + "\n";
					selectIsClicked = true;
				}
				if (box3.isSelected())
				{
					selectAnswer += box3.getText() + "\n";
					selectIsClicked = true;
				}
				if (box4.isSelected())
				{
					selectAnswer += box4.getText() + "\n";
					selectIsClicked = true;
				}
				
				int spinnerVal = (Integer) groSpinner.getValue();
				
				
				int sliderVal = usefullnessSlider.getValue();
				String sliderString = sliderTable.get(sliderVal).getText();
				
						
				
				// FINAL WRITE
				if (selectIsClicked && rangeIsClicked && !(answerArea.getText().isEmpty()))
				{
					BufferedWriter output = null;
				    try {
				        File file = new File("survey_results.txt");
				        output = new BufferedWriter(new FileWriter(file));
				        
				        output.write(question1.getText() + "\n");
				        output.write(selectAnswer + "\n");
				        
				        output.write(question.getText() + "\n");
				        output.write(rangeAnswer + "\n\n");
				        
				        output.write(spinQuestion.getText() + "\n");
				        output.write(spinnerVal + "\n\n");
				        
				        output.write(questionArea.getText() + "\n");
				        output.write(answerArea.getText() + "\n\n");
				        
				        output.write(" 5) How useful would an application that keeps "
				        		+ "track of the items in your fridge and pantry and allows "
				        		+ "you to set up reminders when you are running low on a "
				        		+ "specific item be to you?\n");
				        output.write(sliderString + "\n\n");

				    } catch ( IOException e1 ) {
				        e1.printStackTrace();
				    } finally {
				      if ( output != null ) {
				        try {
							output.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				      }
				    }
				    
				    System.exit(0); 
				}
				else
				{
					JOptionPane.showMessageDialog(survey, "Please answer all questions.");
				}
			
		}
	};     finish.addActionListener(finishListener);


	

	
		//sets the base frame to visible & to end on exit
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		
		//sets personas frame to visible and end on exit
		//personas.setVisible(true);
		personas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		personas.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		
		//sets personas frame to visible and end on exit
		survey.setVisible(true);
		survey.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		survey.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
	}



	//**********************************************************************
	// Private Inner Classes
	//**********************************************************************

	private static final class HelloPanel extends JPanel
	{
		private final String	message;

		public HelloPanel(String message)
		{
			this.message = ((message != null) ? message : "");
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
			int			y = (getHeight() - fh) / 2;
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
	}
}

//******************************************************************************
