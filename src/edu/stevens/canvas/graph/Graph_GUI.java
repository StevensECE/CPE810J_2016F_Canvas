package edu.stevens.canvas.graph;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
@author: Zeming Wang
@modified: _
This class is for the GUI part of graph group.
It will build an interface to react with the user
including buttons to choose the mode, type of graph
//11.16:
 * Change the buttons to radio buttons
 * Reallocate the GUI
 * Talk with the download group, and find that the data they get doesn't show us the type of assignment
 Which means we cannot directly know which assignment is quiz or test or project.
 * Talk with the download group, know that data is store in .txt file
 Add read function to this class
*/

public class Graph_GUI extends JFrame implements ActionListener{
	//private elements here
	private JTextField inputText;
	private final String[] graphTypeName= {"Histogram","Distrubution"};//add more here	
	private JRadioButton[] graphTypeRButton= new JRadioButton[graphTypeName.length];
	private final String[] studentTypeName= {"Single Student","All Student"};
	private JRadioButton[] studentTypeButton= new JRadioButton[studentTypeName.length];
	private final String[] assignmentNumName= {"One Assignment","All Assignment"};
	private JRadioButton[] assignmentNumButton= new JRadioButton[assignmentNumName.length];
	private final String[] assignmentTypeName= {"Quiz","Test","Project","Assignment","All"};
	private JRadioButton[] assignmentTypeButton= new JRadioButton[assignmentTypeName.length];
	//This is used to choose the data range, is it going to draw a graph for a student or all student?
	//And is the target data include just once assignment, all assignment
	//or one certain type of assignment?(all test or all quiz)
	//Pressed button should change its color or show some different before being pressed.
	private JTextField outputText= new JTextField("");
	private JMenuBar menubar;
	private JMenu menuFile;
	private File file;
	private JMenuItem openFile,saveFile,exit;
	private FileDialog openDia,saveDia;
	private boolean allStudent=false,allAssignment=false,isHistogram=false,isDistribution=false;
	private String assignmentTypeChoosen;
	//default is for one assignment for a single student

	public Graph_GUI(){
        super();
        init();
        this.setSize(800,600);
        this.setBackground(Color.LIGHT_GRAY);
        this.setTitle("Graph GUI");
        this.setLocation(100, 100);
        this.setResizable(false);
        this.pack();
	}
	//Constructor of the frame
	
    private void init() {
		menubar=getMenu();
		setJMenuBar(menubar);
		//Set the menu here
		inputText = new JTextField(14);
        JPanel commandPanel1 = new JPanel();
        commandPanel1.setLayout(new GridLayout(3, 3, 3, 3));
        ButtonGroup studentTypeGroup= new ButtonGroup();
        //Add a Panel to hold the button
        for (int i = 0; i < studentTypeName.length; i++) {
        	studentTypeButton[i] = new JRadioButton(studentTypeName[i]);
        	commandPanel1.add(studentTypeButton[i]);
        	studentTypeGroup.add(studentTypeButton[i]);
        	studentTypeButton[i].setForeground(Color.blue);
        	studentTypeButton[i].addActionListener(this);
        }
        JPanel commandPanel2 = new JPanel();
        commandPanel2.setLayout(new GridLayout(4, 4, 3, 3));
        ButtonGroup graphTypeGroup= new ButtonGroup();

        for (int i = 0; i < graphTypeName.length; i++) {
        	graphTypeRButton[i] = new JRadioButton(graphTypeName[i]);
        	commandPanel2.add(graphTypeRButton[i]);
            graphTypeGroup.add(graphTypeRButton[i]);
        	graphTypeRButton[i].setForeground(Color.blue);
        	graphTypeRButton[i].addActionListener(this);
        }
        JPanel commandPanel3 = new JPanel();
        commandPanel3.setLayout(new GridLayout(3, 3, 3, 3));
        ButtonGroup assignmentNumGroup= new ButtonGroup();

        for (int i = 0; i < assignmentNumName.length; i++) {
        	assignmentNumButton[i] = new JRadioButton(assignmentNumName[i]);
        	commandPanel3.add(assignmentNumButton[i]);
        	assignmentNumGroup.add(assignmentNumButton[i]);
        	assignmentNumButton[i].setForeground(Color.blue);
        	assignmentNumButton[i].addActionListener(this);
        }
        JPanel commandPanel4 = new JPanel();
        commandPanel4.setLayout(new GridLayout(4, 4, 3, 3));
        ButtonGroup assignmentTypeGroup= new ButtonGroup();

        for (int i = 0; i < assignmentTypeName.length; i++) {
        	assignmentTypeButton[i] = new JRadioButton(assignmentTypeName[i]);
        	commandPanel4.add(assignmentTypeButton[i]);
        	assignmentTypeGroup.add(assignmentTypeButton[i]);
        	assignmentTypeButton[i].setForeground(Color.blue);
        	assignmentTypeButton[i].addActionListener(this);
        }
        
		Container c= this.getContentPane();
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add("Center", outputText);
        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.add("North", commandPanel2);
        right.add("West", commandPanel1);
        right.add("East", commandPanel3);
        right.add("South", commandPanel4);
        c.setLayout(new GridLayout(1,8, 2, 2));
        c.add(top, "WEST");
        c.add(right,"EAST");
    }
	
	class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("exit")){
		System.exit(0); 
		}
			if (e.getActionCommand().equals("open a file..")){
				openDia.setVisible(true);
				String dirPath = openDia.getDirectory();
				String fileName = openDia.getFile();

				if(dirPath == null || fileName == null)
						return ;

				inputText.setText("");

				file = new File(dirPath,fileName);
				
				try
				{
						BufferedReader bufr = new BufferedReader(new FileReader(file));
						// Read the text file by line.
						
						String line = null;

						while( (line = bufr.readLine())!= null)
						{
							//to do: where should we put these input text?
						}
						bufr.close();
				}
				catch (IOException ex)
				{
					throw new RuntimeException("Failed to open");
				}
		}
			if (e.getActionCommand().equals("save..")){
				if(file == null)
				{
					saveDia.setVisible(true);
					String dirPath = saveDia.getDirectory();
					String fileName = saveDia.getFile();

					if(dirPath == null || fileName == null)
						return ;
					file = new File(dirPath,fileName);
				}
				try
				{
//To do, how do we save the graph?
				}
				catch (Exception ex)//It should be IOExceotion
				{
					throw new RuntimeException("Failed to save");
				}
			}
		}
}
	//The actionlistener reacts to the operation on Menu
	
	public void actionPerformed(ActionEvent e){//To do: adding details method to react
        String label = e.getActionCommand();
        if (label.equals(studentTypeName[0])) {//Single student
        	allStudent=false;
//We should load the data and let the user choose which student will be taken
        } else if (label.equals(studentTypeName[1])) {//All student
        	allStudent=true;
        }
        
        if (label.equals(graphTypeName[0])) {//Histogram
        	isHistogram=true;
        	isDistribution=false;
        } else if (label.equals(graphTypeName[1])) {//Distribution
        	isDistribution=true;
        	isHistogram=false;
        }
        
        if (label.equals(assignmentNumName[0])) {//one assignment
        	allAssignment=false;
//We should load the data and let the user choose which assignment will be taken
        } else if (label.equals(assignmentNumName[1])) {//all assignment
        	allAssignment=true;
        }
        
        if (label.equals(assignmentTypeName[0])) {//quiz
        	assignmentTypeChoosen=assignmentTypeName[0];
        } else if (label.equals(assignmentTypeName[1])) {//test
        	assignmentTypeChoosen=assignmentTypeName[1];
        }else if (label.equals(assignmentTypeName[2])) {//project
        	assignmentTypeChoosen=assignmentTypeName[2];
        }else if (label.equals(assignmentTypeName[3])) {//assignment or homework
        	assignmentTypeChoosen=assignmentTypeName[3];
        }
        //Should we make it multiple choices?
        else{//default and All
        	assignmentTypeChoosen="All";
        }
	}
	//React to the operations, like press buttons
		
	JMenuBar getMenu(){//Set the menu here
		MenuListener myMenuListener=new MenuListener();
		menubar=new JMenuBar();
		menuFile=new JMenu("file");
		openFile=new JMenuItem("open a file..");
		//What should the open file do? to load a file that store the grade?
		saveFile=new JMenuItem("save..");
		//Save file should save the image
		exit=new JMenuItem("exit");
		openFile.addActionListener(myMenuListener);
		saveFile.addActionListener(myMenuListener);
		exit.addActionListener(myMenuListener);
		menuFile.add(openFile);
		menuFile.add(saveFile);
		menuFile.addSeparator();
		menuFile.add(exit);
		menubar.add(menuFile);
		openDia = new FileDialog(this, "my open file",FileDialog.LOAD);
		saveDia = new FileDialog(this,"my save file",FileDialog.SAVE);
		return menubar;
		}
	//Setup the MenuBar and Menu
	
    private String getNameFromText() {
    	String rusult=null;
    	//to do, if specific name of student or assignment given, directly output
		return rusult;//To get the input text,
    }
	
    public static void main(String args[]) {
        Graph_GUI c1 = new Graph_GUI();
        c1.setVisible(true);
    }
}
