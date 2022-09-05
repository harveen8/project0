
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class databaseFunctions {
    Connection conn= connectionUtil.getConnection();
    public boolean checkIfIn(String thing, List<item> thingList){
        for(int i=0; i< thingList.size(); i++){
            String name= thingList.get(i).item_name;
            if(name.equals(thing)){
                return true;
            }
        }
        return false;
    }
   public  List<item> returnItemsInIsle(String isleName){
       List<item> groceryItemArray = new ArrayList<>();
       try {
           PreparedStatement statement = conn.prepareStatement("Select * From groceryItems where isle = ?");
           statement.setString(1, isleName);
           ResultSet rs = statement.executeQuery();
           while(rs.next()){
               item groceryItem = new item(rs.getString("item_name"), rs.getString("isle"),
                       rs.getInt("quantity"),rs.getFloat("price"));
               groceryItemArray.add(groceryItem);
           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       return groceryItemArray;
   }

   public void printOutItems(List<item> itemArray){
       for (int i=0; i<itemArray.size(); i++ ){
           System.out.println( itemArray.get(i).toString());
       }
   }
   
   public void addItemToCart(item thing, int amount){
       try{
           databaseFunctions db = new databaseFunctions();
           if(existsInCart(thing)){
               item newOne = db.getSpecificCartItem(thing.item_name);
               PreparedStatement statement = conn.prepareStatement("update groceryCart set quantity = ? " +
                       "where item_name = ?");
               statement.setInt(1,newOne.quantity+amount);
               statement.setString(2, thing.item_name);
               statement.executeUpdate();
               //edit the original database for the amount
               updateGroceryStore(thing, getSpecificItem(thing.item_name).quantity - amount);
           }else {
               PreparedStatement statement = conn.prepareStatement("insert into groceryCart(item_name, " +
                       "isle, quantity, price)" + "values (?, ?,?,?)");
               statement.setString(1, thing.item_name);
               statement.setString(2, thing.isle);
               statement.setInt(3, amount);
               statement.setFloat(4, thing.price);
               statement.executeUpdate();
               //edit the original database for the amount
               updateGroceryStore(thing, getSpecificItem(thing.item_name).quantity - amount);
           }
       }catch(SQLException e){
           e.printStackTrace();
       }

   }

    public item getSpecificItem(String name){
        try {
            List<item> groceryItemArray = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("Select * From groceryItems where item_name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), rs.getString("isle"),
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray.get(0);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null; //should not get here so it is fine
    }
    public void removeItemFromCart(item thing, int quantity){
        try{
            //we want to delete all
            if(getSpecificCartItem(thing.item_name).quantity == quantity){

                PreparedStatement statement = conn.prepareStatement("delete from groceryCart where item_name = ?");
                statement.setString(1,thing.item_name);
                statement.executeUpdate();
                updateGroceryStore(thing, quantity+getSpecificItem(thing.item_name).quantity);

            }else{
                // we want to remove some
                PreparedStatement statement = conn.prepareStatement("update groceryCart set quantity = ? " +
                        "where item_name = ?");
                statement.setInt(1,getSpecificCartItem(thing.item_name).quantity-quantity);
                statement.setString(2, thing.item_name);
                statement.executeUpdate();
                updateGroceryStore(thing, quantity+getSpecificItem(thing.item_name).quantity);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        returnCart();
    }

    public void updateGroceryStore(item thing, int amount){
        try{
            PreparedStatement statement = conn.prepareStatement("update groceryItems set quantity = ? " +
                    "where item_name = ?");
            statement.setInt(1,amount);
            statement.setString(2, thing.item_name);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<item> returnCart(){
        List<item> groceryCartArray = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("Select * From groceryCart");

            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), rs.getString("isle"),
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryCartArray.add(groceryItem);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return groceryCartArray;

        }
    public item getSpecificCartItem(String name){
        try {
            List<item> groceryItemArray = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("Select * From groceryCart where item_name = ?");
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), rs.getString("isle"),
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray.get(0);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null; //should not get here so it is fine
    }

    public void emptyCart(){
        databaseFunctions db= new databaseFunctions();
        List<item> cart= db.returnCart();
        for(int i=0; i< cart.size(); i++){
            db.removeItemFromCart(cart.get(i), cart.get(i).quantity);
        }
    }

    public boolean existsInCart(item thing){
        databaseFunctions db= new databaseFunctions();
        List<item> cart = db.returnCart();
        for(int i=0; i< cart.size(); i++){
            if(cart.get(i).item_name.equals(thing.item_name)){
                return true;
            }
        }
        return false;
    }



}




