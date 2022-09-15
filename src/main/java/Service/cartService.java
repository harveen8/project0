package Service;
import DAO.databaseFunctions;
import Model.item;

import java.util.List;

public class cartService {

    databaseFunctions db = new databaseFunctions();

    public void addItemToCart(String name, int quantity ){
        if(quantity!=0) {
            item addingItem = db.getSpecificThing(name);
            db.addItemToCart(addingItem, quantity);
        }
    }

    public List<item> itemsInCart(){
        return db.returnCart();
    }

    public void emptyCart(){
         db.emptyCart();
    }

    public void removeItemInCart(String name, int quantity){
        item removeItem = db.getSpecificThing(name);
        db.removeItemFromCart(removeItem, quantity);
    }




}
