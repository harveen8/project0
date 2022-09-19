package Service;

import DAO.databaseFunctions;
import Model.item;
import Model.logIn;
import org.apache.log4j.Logger;

import java.util.List;

public class adminService {
    databaseFunctions db= new databaseFunctions();
    public List<logIn> returnAllAdmin(){
        return db.getAllAdmins();
    }

    public void addAdmin(String user, String pass){
        db.addAdmin(user, pass);
    }

    public void addItemToGroceryStore(item thing){
        db.addItemToGroceryStore(thing);
    }

    public void removeAdmin(String user){
        db.removeAdmin(user);
    }

}
