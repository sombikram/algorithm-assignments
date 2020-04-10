
import java.util.*;
import java.io.*;

public class CourseScheduler {
	
	private int numOfCourses;
	private int[][] table;
	private int givenSlot;
	private String scheduleOutput[];
	private String arrayToString= "";
	private String reachable;
	
	
	private LinkedList<String> courseNames = new LinkedList<>();
	private LinkedList<String> courseCodes = new LinkedList<>();
	private LinkedList<String> courseClassrooms = new LinkedList<>();
	private LinkedList<String> courseInstructors = new LinkedList<>();
	private LinkedList<String> courseNamesCodes = new LinkedList<>();
	private LinkedList<Integer> visitedCourses = new LinkedList<>();
	
	private Map<Integer,Integer> courseTimeSlot= new HashMap<>(); // <Course, Time Slot>
	
	
	public CourseScheduler(String filename) {
		
		try {
			Scanner myFile = new Scanner(new File(filename));
			myFile.useDelimiter("(\r\n|[\n\r])|,");
			
			numOfCourses = Integer.parseInt(myFile.next());
			table = new int[numOfCourses][numOfCourses];
			
			for(int i =0;i<numOfCourses;i++) {
				Arrays.fill(table[i], 0);
				courseTimeSlot.put(i, 0);
			}
			
			while(myFile.hasNextLine()) {
				courseNames.add(myFile.next());
				courseCodes.add(myFile.next());
				courseClassrooms.add(myFile.next() +" "+ myFile.next().substring(1)); //removing ' i.e. '104 => 104
				courseInstructors.add(myFile.nextLine().substring(1));//removing , since we use nextLine i.e. ,Thakuri S => Thakuri S 
			}
			
			for(int i =0; i<numOfCourses;i++) {
				courseNamesCodes.add(courseNames.get(i)+ " "+courseCodes.get(i));
			}
			
			
			setTable();	
			
			
		}
		
		catch(FileNotFoundException  e) {
			System.out.println("File Not Found");
		}
	}


	public boolean solutionExists(int m) {
		givenSlot = m;
	
		return graphColor(0);
	}
	
	public String getSchedule(int m) {
		scheduleOutput = new String[m];
		if(solutionExists(m)) {
			for(int i = 0;i<givenSlot;i++) {
				scheduleOutput[i] = "Time "+ (i+1) +":\n";
				for(int j = 0;j<numOfCourses;j++) {
					if(courseTimeSlot.get(j) == i+1) {
						scheduleOutput[i] += courseNames.get(j)+" "+courseCodes.get(j)+" ";
						scheduleOutput[i] += courseClassrooms.get(j)+ " "+ courseInstructors.get(j)+"\n";
					}
				}
			}
			
			for(int i = 0;i<givenSlot;i++) {
				arrayToString += scheduleOutput[i];
			}
			
			return "\n"+arrayToString;
			
		}
		return "No solution with "+m +" slots";
	}
	
	public String reachable(String from) {
		reachableHelper(from);
		
		reachable = ""+visitedCourses.size()+ " COURSES: "+courseNamesCodes.get(visitedCourses.get(0));
		for(int i=1;i<visitedCourses.size();i++) {
			reachable +=", "+ courseNamesCodes.get(visitedCourses.get(i));
		}
		
		return reachable;
	}
	
	
	public boolean graphColor(int currentCourse) {
		
		if(currentCourse == numOfCourses) {
			return true;
		}
		
		for(int i = 1;i<=givenSlot;i++) {
			if(canColor(currentCourse, i)) {
				courseTimeSlot.put(currentCourse,i);
				return graphColor(currentCourse+1);
			}
		}
		
		return false;
	}
	
	
	public boolean canColor(int currentCourse, int slot) {
		for(int i = 0;i<numOfCourses;i++) {
			if(currentCourse != i && table[currentCourse][i] == 1 && courseTimeSlot.get(i) == slot) {
				return false;
			}
		}
		return true;
	}
	
	
	
	
	public boolean sameInstructor(int i, int j) {
		if(courseInstructors.get(i).equals(courseInstructors.get(j))) {
			return true;
		}
		return false;
	}
	public boolean sameClassroom(int i, int j) {
		if(courseClassrooms.get(i).equals(courseClassrooms.get(j))) {
			return true;
		}
		return false;
	}
	public boolean sameDiscipline(int i, int j) {
		if(courseNames.get(i).equals(courseNames.get(j)) && courseCodes.get(i).charAt(0) == courseCodes.get(j).charAt(0)) {
			return true;
		}
		return false;
	}
	
	
	
	public void setTable() {
		for(int i = 0; i<numOfCourses;i++) {
			for(int j = 0; j<numOfCourses;j++) {
				if(i != j && (sameInstructor(i,j) || sameClassroom(i,j) || sameDiscipline(i,j))) {
					table[i][j] = 1;
				}
			}
		}
	}
	
	
	public void reachableHelper(String from) {
		int index = courseNamesCodes.indexOf(from);
		visitedCourses.add(index);
		
		for(int i = 0;i<numOfCourses;i++) {
			if(index != i && table[index][i] == 1 && !visitedCourses.contains(i)) {
				reachableHelper(courseNamesCodes.get(i));
			}
		}
	}
	
	
	
	
}
