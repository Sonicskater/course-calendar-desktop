package controller;
import model.database.DataBase;
import model.database.DBUniqueID;

public class CacheTestClass {


    public static void main(){
        DataBase cache = DataBase.INSTANCE;
        try {
            cache.getCourse(new DBUniqueID());
        }catch (Exception e){

        }
    }

}
