package main.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import main.model.database.DBUniqueID;
import main.model.types.Department;
import main.model.types.Program;

public class DummyClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4064740008720574220L;
	public boolean on = true;
	String[] hold;
	ArrayList <Department> DepartmentList;
	ArrayList <Program> ProgramList;
	public void main(String[] args){
		try{
			ObjectInputStream fIn = new ObjectInputStream(new FileInputStream("Save"));
			DummyClass save = (DummyClass) fIn.readObject();
			this.DepartmentList = save.DepartmentList;
			this.ProgramList = save.ProgramList;
		}catch (Exception e){
			
		}
		System.out.println("Press 1,2,3 to create new Departments, Progarms, and Courses");
		System.out.println("Press 4,5,6 to list Departments, Programs, and Courses.");
		System.out.println("Enter quit to exit program");
		while(on){
			Scanner user = new Scanner(System.in);
			String input = user.nextLine();
			switch(input){
			case "1":
				System.out.println("Enter Name/id of new department");
				input = user.nextLine();
				hold = input.split("/");
				DBUniqueID newId = new DBUniqueID();
				try{
				newId.TypeCode = "Department";
				newId.NumCode = Integer.parseInt(hold[1]);
				Department d = new Department(newId, hold[0]);
				DepartmentList.add(d);
				}catch (Exception e){
					System.out.println("Invalid input");
				}
				break;
			case "2":
				System.out.println("Enter Name/id of new program");
				input = user.nextLine();
				hold = input.split("/");
				DBUniqueID newId2 = new DBUniqueID();
				try{
				newId2.TypeCode = "Program";
				newId2.NumCode = Integer.parseInt(hold[1]);
				Program p = new Program(newId2, hold[0]);
				ProgramList.add(p);
				}catch (Exception e){
					System.out.println("Invalid input");
				}
				break;
			case "3":
				break;
			case "4":
				for (Department d : DepartmentList){
					System.out.println(d.getId());
				}
				break;
			case "5":
				for (Program p : ProgramList){
					System.out.println(p.getId());
				}
				break;
			case "6":
				break;
			case "quit":
				on = false;
				break;
			default:
				System.out.println("Command not reconized");
				break;
			}
		}
		ObjectOutputStream fOut;
		try {
			fOut = new ObjectOutputStream(new FileOutputStream("Save"));
			fOut.writeObject(this);
			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
