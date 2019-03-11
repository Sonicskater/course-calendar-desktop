package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import model.database.DBUniqueID;
import model.types.Department;
import model.types.Program;

public class DummyClass implements Serializable {
	private static final long serialVersionUID = 4064740008720574220L;
	public boolean run = true;
	
	String[] hold;
	ArrayList <Department> deparments;
	ArrayList <Program> programs;
	
	Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args){
	    new DummyClass().main2(args);
    }
	
	public void main2(String[] args){
		initialize();
		
		boolean authenticated = false;
		int userType = -1;
		while (!authenticated) {
			System.out.println("Enter an account type to login as:"
					+ "\n\t1: Student"
					+ "\n\t2: Faculty");
			userType = getIntInput(1, 2);
			
			authenticated = authenticate(userType);
		}
		
		switch (userType) {
			case 1:
				studentActions(run);
				break;
			case 2:
				facultyActions(run);
				break;
		}
		
		terminate();
	}
	
	
	private void initialize() {
		System.out.println("Welcome to the UWinnipeg course management system!");
		try{
			ObjectInputStream fInput = new ObjectInputStream(new FileInputStream("Save"));
			DummyClass save = (DummyClass) fInput.readObject();
			this.deparments = save.deparments;
			this.programs = save.programs;
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	
	private void terminate() {
		ObjectOutputStream fOutput;
		try {
			fOutput = new ObjectOutputStream(new FileOutputStream("Save"));
			fOutput.writeObject(this);
			fOutput.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		scan.close();
	}
	
	
    private int getIntInput(int rangeStart, int rangeEnd) {
        int intInput = -1;
        while (scan.hasNext()) {
        	if (scan.hasNextInt()) {
                intInput = scan.nextInt();
                if (intInput >= rangeStart && intInput <= rangeEnd) {
                	return intInput;
                } else {
                	System.out.println("Enter an integer between " + rangeStart + " and " + rangeEnd);
                }
        	} else {
                System.out.println("Enter an integer between " + rangeStart + " and " + rangeEnd);
                scan.next();
            }
        }
        System.out.println("Error on reading integer input");
        return intInput;
    }
	
  
	private boolean authenticate(int userType) {
		boolean authenticated = false;
		String uname;
		String pass;
		
		System.out.println("Enter your username:");
		uname = scan.next();
		System.out.println("Enter your password:");
		pass = scan.next();
		
		if (true) {
			authenticated = true;
		}
		
		if (authenticated) {
			System.out.println("Successfully logged in as " + uname + "!");
			return true;
		}
		
		System.out.println("Login attempt failed!");
		return false;
	}
	
	
	private void studentActions(boolean run) {
		while (run) {
			System.out.println("Enter a selection to perform an action:"
					+ "\n\t0: Exit the System"
					+ "\n\t1: List Departments"
					+ "\n\t2: List Programs"
					+ "\n\t3: List Courses");
			int selection = getIntInput(0, 3);
			switch (selection) {
			case 0:
				run = false;
				System.out.println("Exiting the system!");
				break;
			case 1:
				System.out.println("Listing departments...");
				for (Department d : deparments){
					System.out.println(d.getId());
				}
				break;
			case 2:
				System.out.println("Listing programs...");
				for (Program p : programs){
					System.out.println(p.getId());
				}
				break;
			case 3:
				System.out.println("Listing courses...");
				break;
			}
		}
	}
	
	
	private void facultyActions(boolean run) {
		String input;
		
		while (run) {
			System.out.println("Enter a selection to perform an action:"
					+ "\n\t0: Exit the System"
					+ "\n\t1: List Departments"
					+ "\n\t2: List Programs"
					+ "\n\t3: List Courses"
					+ "\n\t4: Create New Department"
					+ "\n\t5: Create New Program"
					+ "\n\t6: Create New Course");
			int selection = getIntInput(0, 6);
			switch (selection) {
			case 0:
				run = false;
				System.out.println("Exiting the system!");
				break;
			case 1:
				System.out.println("Listing departments...");
				for (Department d : deparments){
					System.out.println(d.getId());
				}
				break;
			case 2:
				System.out.println("Listing programs...");
				for (Program p : programs){
					System.out.println(p.getId());
				}
				break;
			case 3:
				System.out.println("Listing courses...");
				break;
			case 4:
				System.out.println("Creating new department...");
				System.out.println("Enter Name/id of new department");
				input = scan.next();
				hold = input.split("/");
				DBUniqueID newId = new DBUniqueID();
				try{
				newId.TypeCode = "Department";
				newId.NumCode = Integer.parseInt(hold[1]);
				Department d = new Department(newId, hold[0]);
				deparments.add(d);
				} catch (Exception e){
					System.out.println("Invalid input");
				}
				break;
			case 5:
				System.out.println("Creating new program...");
				System.out.println("Enter Name/id of new program");
				input = scan.next();
				hold = input.split("/");
				DBUniqueID newId2 = new DBUniqueID();
				try{
				newId2.TypeCode = "Program";
				newId2.NumCode = Integer.parseInt(hold[1]);
				Program p = new Program(newId2, hold[0]);
				programs.add(p);
				} catch (Exception e){
					System.out.println("Invalid input");
				}
				break;
			case 6:
				System.out.println("Creating new course...");
				break;
			}
		}
	}
}