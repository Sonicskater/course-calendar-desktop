package main.model.database;

public abstract class DBData {
	private DBUniqueID id;
	
	public DBData(DBUniqueID id, String type) throws IDTypeMismatchExcception {
		if (id.TypeCode != type) {
			throw new IDTypeMismatchExcception();
		}
		this.setId(id);
	}

	public DBUniqueID getId() {
		return id;
	}

	public void setId(DBUniqueID id) {
		this.id = id;
	}
	
}
