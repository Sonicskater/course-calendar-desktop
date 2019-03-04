package main.model.database;

public class DBUniqueID {
	public DBUniqueID() {
		
	}
	public String TypeCode;
	public int NumCode;
	
	public String toString() {
		return (TypeCode+"_"+NumCode);
	}
}
