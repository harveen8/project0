package DAO;

import Model.item;
import Util.connectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String getItemIsle(String name){
        List<item> itemList=returnAllItems();
        for(int i=0; i< itemList.size(); i++){
            String currentName= itemList.get(i).item_name;
            if(currentName.equals(name)){
                return itemList.get(i).isle;
            }
        }
        return "";
    }


    public List<item> connectLists(List<item> one,List<item> two ){
       List<item> newList = Stream.concat(one.stream(), two.stream())
               .collect(Collectors.toList());
       return newList;
    }
   public  List<item> returnItemsInIsle(String isleName){
      if(isleName.equals("produce")){
          return returnAllProduceItems();
      }else if(isleName.equals("bakery")){
          return returnAllBakeryItems();
      }else if(isleName.equals("meat")){
          return returnAllMeatItems();
      }else if(isleName.equals("dairy")){
          return returnAllDairyItems();
      }else if(isleName.equals("convenience")){
          return returnAllConvenienceItems();
      }else{
          return null;
      }
   }

    public  List<item> returnAllItems(){
        List<item> produce = returnAllProduceItems();
        List<item> bakery = returnAllBakeryItems();
        List<item> pb= connectLists(produce,bakery);
        List<item> meat = returnAllMeatItems();
        List<item> pbm=connectLists(pb, meat);
        List<item> dairy=returnAllDairyItems();
        List<item> pbmd=connectLists(pbm,dairy);
        List<item> convenience = returnAllConvenienceItems();
        return connectLists(pbmd,convenience);
    }
    public List<item> returnAllProduceItems(){
        List<item> groceryItemArray = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select groceryItem.item_name, groceryItem.quantity," +
                    "groceryItem.price from produce  join groceryItem on " +
                    "groceryItem.item_name= produce.item_name");
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), "produce",
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<item> returnAllBakeryItems(){
        List<item> groceryItemArray = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select groceryItem.item_name, groceryItem.quantity," +
                    "groceryItem.price from bakery  join groceryItem on " +
                    "groceryItem.item_name= bakery.item_name");
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), "bakery",
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<item> returnAllMeatItems(){
        List<item> groceryItemArray = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select groceryItem.item_name, groceryItem.quantity," +
                    "groceryItem.price from meat join groceryItem on " +
                    "groceryItem.item_name= meat.item_name");
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), "meat",
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<item> returnAllDairyItems(){
        List<item> groceryItemArray = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select groceryItem.item_name, groceryItem.quantity," +
                    "groceryItem.price from dairy join groceryItem on " +
                    "groceryItem.item_name= dairy.item_name");
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), "dairy",
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<item> returnAllConvenienceItems(){
        List<item> groceryItemArray = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select groceryItem.item_name, groceryItem.quantity," +
                    "groceryItem.price from convenience join groceryItem on " +
                    "groceryItem.item_name= convenience.item_name");
            while(rs.next()){
                item groceryItem = new item(rs.getString("item_name"), "convenience",
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
               updateGroceryStore(thing, getSpecificThing(thing.item_name).quantity - amount);
           }else {
               PreparedStatement statement = conn.prepareStatement("insert into groceryCart(item_name, " +
                       " quantity, price)" + "values (?, ?,?)");
               statement.setString(1, thing.item_name);
               statement.setInt(2, amount);
               statement.setFloat(3, thing.price);
               statement.executeUpdate();
               //edit the original database for the amount
               updateGroceryStore(thing, getSpecificThing(thing.item_name).quantity - amount);
           }
       }catch(SQLException e){
           e.printStackTrace();
       }

   }

    public item getSpecificThing(String name){
      List<item> allItems= returnAllItems();
      for(int i=0; i< allItems.size(); i++){
          if(name.equals(allItems.get(i).item_name)){
              return allItems.get(i);
          }
      }
      return null;
    }
    public void removeItemFromCart(item thing, int quantity){
        try{
            //we want to delete all
            if(getSpecificCartItem(thing.item_name).quantity == quantity){

                PreparedStatement statement = conn.prepareStatement("delete from groceryCart where item_name = ?");
                statement.setString(1,thing.item_name);
                statement.executeUpdate();
                updateGroceryStore(thing, quantity+ getSpecificThing(thing.item_name).quantity);

            }else{
                // we want to remove some
                PreparedStatement statement = conn.prepareStatement("update groceryCart set quantity = ? " +
                        "where item_name = ?");
                statement.setInt(1,getSpecificCartItem(thing.item_name).quantity-quantity);
                statement.setString(2, thing.item_name);
                statement.executeUpdate();
                updateGroceryStore(thing, quantity+ getSpecificThing(thing.item_name).quantity);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        returnCart();
    }

    public void updateGroceryStore(item thing, int amount){
        try{
            PreparedStatement statement = conn.prepareStatement("update groceryItem set quantity = ? " +
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
                item groceryItem = new item(rs.getString("item_name"), getItemIsle(rs.getString("item_name")),
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
                item groceryItem = new item(rs.getString("item_name"), getItemIsle(rs.getString("item_name")),
                        rs.getInt("quantity"),rs.getFloat("price"));
                groceryItemArray.add(groceryItem);
            }
            return groceryItemArray.get(0);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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




