package edu.stevens.canvas.graph;

import java.io.*;
import java.util.*;

/**
 * class to get the grade file
 * @author Lan Chang
 *
 */

public class GradeGroup {
	private static File[] fileList;
	private boolean allStudent, allAssignment;
	private String assignmentTypeChoosen, assignment;
	private static ArrayList<Double> grade;
	private static ArrayList<Integer> num;
	private int group;
	private double fullScore, width;
	private boolean done;
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true) {
			getMultiGradeFile();
			done = true;
		}
	}
	
	public GradeGroup(boolean allStudent, boolean allAssignment, String assignmentTypeChoosen, String assignment, double width) {
		this.allStudent = allStudent;
		this.allAssignment = allAssignment;
		this.assignmentTypeChoosen = assignmentTypeChoosen;
		this.assignment = assignment;
		this.width = width;
		
		File file = new File("grade");
		fileList = file.listFiles();
		
		if (this.allStudent == true && this.allAssignment == false && !this.assignment.equals("")) {
			getSingleGradeFile();
			cal();
			done = true;
		}
		
		if (this.allStudent == true && this.allAssignment == true) {
			getMultiGradeFile();
			cal();
			done = true;
		}
	}
	
	public void getMultiGradeFile() {
		String assignmentRegex = "[Aa]ssignment[0-9]*[.txt]*";
		String projectRegex = "[Pp]roject[0-9]*[.txt]*";
		String testRegex = "[Tt]est[0-9]*[.txt]*";
		String quizRegex = "[Qq]uiz[0-9]*[.txt]*";
		
		ArrayList<Integer> index = new ArrayList<Integer>();
		
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].getName().matches(assignmentRegex) && assignmentTypeChoosen.matches(assignmentRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(projectRegex) && assignmentTypeChoosen.matches(projectRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(testRegex) && assignmentTypeChoosen.matches(testRegex)) {
				index.add(i);
			}
			if (fileList[i].getName().matches(quizRegex) && assignmentTypeChoosen.matches(quizRegex)) {
				index.add(i);
			}
			if (assignmentTypeChoosen.equals("All")) {
				index.add(i);
			}
		}
		
		HashMap<String, GradeCount> multiGrade = new HashMap<String, GradeCount> ();
		
		for (int i = 0; i < index.size(); i++) {			
			Scanner fileInput;
			try {
				fileInput = new Scanner(fileList[index.get(i)]);
				
				fullScore = Double.parseDouble(fileInput.next());
				
				while (fileInput.hasNext()) {
					String id_temp = fileInput.next();
					String grade_temp = fileInput.next();
					if (!multiGrade.containsKey(id_temp)) {
						int count0 = 1;
						double tg0 = Double.parseDouble(grade_temp) / fullScore * 100;
						multiGrade.put(id_temp, new GradeCount(tg0, count0));
					}
					else {
						double tg0 = Double.parseDouble(grade_temp) / fullScore * 100;
						int count1 = multiGrade.get(id_temp).getN();
						double tg1 = multiGrade.get(id_temp).getTg() * multiGrade.get(id_temp).getN();
						int count2 = count1 + 1;
						double tg2 = (tg1 + tg0) / count2;
						multiGrade.put(id_temp, new GradeCount(tg2, count2));
					}
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		grade = new ArrayList<Double> ();
		
		for (HashMap.Entry<String, GradeCount> entry : multiGrade.entrySet()) {
			grade.add(entry.getValue().getTg());
		}
		
		fullScore = 100;
		
		group = (int) (100 / width);
	}
	
	public void getSingleGradeFile() {
		int index = -1;
		String filename = assignment + ".txt";
		
		for (int i = 0; i < fileList.length; i++) {
			if (filename.equals(fileList[i].getName())) {
				index = i;
			}
		}
		
		grade = new ArrayList<Double> ();
		
		Scanner fileInput;
		try {
			fileInput = new Scanner(fileList[index]);
			fullScore = Double.parseDouble(fileInput.next());
			group = (int) (fullScore / width);
			while (fileInput.hasNext()) {
				String id_temp = fileInput.next();
				String grade_temp = fileInput.next();
				grade.add(Double.parseDouble(grade_temp));
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void cal() {		
		num = new ArrayList<Integer> ();
		
		for (int i = 0; i < group; i++) {
			num.add(0);
		}
		for (int i = 0; i < grade.size(); i++) {
			if (grade.get(i) != fullScore) {
				num.set((int) (grade.get(i) / width), num.get((int) (grade.get(i) / width)) + 1);
			}
			else {
				num.set(group - 1, num.get(group - 1) + 1);
			}
		}
	}
	
	public ArrayList<Integer> getNum() {
		return num;
	}
	
	public ArrayList<Double> getGrade() {
		return grade;
	}
	
	public boolean getDone() {
		return done;
	}
	
	public double getFull() {
		return fullScore;
	}
}

class GradeCount {
	private double tg;
	private int n;
	
	public GradeCount(double tg, int n) {
		this.tg = tg;
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
	public double getTg() {
		return tg;
	}
}