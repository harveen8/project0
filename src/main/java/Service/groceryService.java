package Service;

import java.text.SimpleDateFormat;
import java.util.*;
import DAO.databaseFunctions;
import Model.item;


public class groceryService {
    databaseFunctions db = new databaseFunctions();
  public List<item> getItemInIsle(String isle){
      return db.returnItemsInIsle(isle);
  }

  public item getItem(String name){
      return db.getSpecificThing(name);
  }

public List<item> getAllItems(){
      return db.returnAllItems();
}


}
