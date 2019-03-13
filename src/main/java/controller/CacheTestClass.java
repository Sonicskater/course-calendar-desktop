package controller;
import model.database.DBCache;
import model.database.DBUniqueID;
import model.types.*;
public class CacheTestClass {


    public static void main(){
        DBCache cache = DBCache.INSTANCE;
        try {
            cache.GetCourse(new DBUniqueID());
        }catch (Exception e){

        }
    }

}

