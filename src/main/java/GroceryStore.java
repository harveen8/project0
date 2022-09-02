import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class GroceryStore {


   public void addItem(List<item> currentIsleItemList){
       //some boilerplate code
       databaseFunctions db= new databaseFunctions();
       Scanner userInput = new Scanner(System.in);
       boolean ask=true;   //needed for while loop
       String itemName="";    //will hold the name of item we want

       //loop to get item name
       while(ask) {
           System.out.println("Please enter the name of the item you would like to add.");
           String newline = userInput.nextLine();
           if(db.checkIfIn(newline,currentIsleItemList)){
               itemName=newline;
                ask=false;
           }
       }

       //get the quantity available
       boolean keepAsking=true;
       int maxAmount= db.getSpecificItem(itemName).quantity;

       System.out.println("How many would you like? Max is " + maxAmount + " items.");
       //check if it works, or keep asking
       while(keepAsking){
           Scanner userInput4 = new Scanner(System.in);
           String inp = userInput4.nextLine();
           int inpNumber= Integer.parseInt(inp);
           if(inpNumber>0 && inpNumber<=maxAmount){
               db.addItemToCart(db.getSpecificItem(itemName), inpNumber);
               keepAsking=false;
           }else{
               System.out.println("Please enter a valid number between 1 and " + maxAmount+".");
           }
       }
   }


   public void removeItem(){
       databaseFunctions db= new databaseFunctions();
       Scanner userInput = new Scanner(System.in);
       boolean ask=true;   //needed for while loop
       String itemName="";    //will hold the name of item we want to remove

       //loop to get item name
       while(ask) {
           System.out.println("Here are the items in you cart:");
           db.printOutItems(db.returnCart());
           System.out.println("Please enter the name of the item you would like to delete.");

           String newline = userInput.nextLine();
           if(db.checkIfIn(newline,db.returnCart())){
               itemName=newline;
               ask=false;
           }
       }

       //get the quantity available
       boolean keepAsking=true;
       int maxAmount= db.getSpecificCartItem(itemName).quantity;
       System.out.println("How many would you like to remove ? Max is " + maxAmount + " items.");
       //check if it works, or keep asking
       while(keepAsking){
           Scanner userInput7 = new Scanner(System.in);
           String inp = userInput7.nextLine();
           int inpNumber= Integer.parseInt(inp);
           if(inpNumber>0 && inpNumber<=maxAmount){
               db.removeItemFromCart(db.getSpecificItem(itemName), inpNumber);
               keepAsking=false;
           }else if(maxAmount==0) {
               System.out.println("Sorry, all out of stock!");
               keepAsking=false;
           }else{
               System.out.println("Please enter a valid number between 1 and " + maxAmount+".");
           }
       }
   }

   public void payAndLeave(){
       databaseFunctions db= new databaseFunctions();
       if(db.returnCart().size()==0){
           System.out.println("Thanks for visiting, come back soon!");
       }else{
           List<item> cart;
           cart=db.returnCart();
           double total =0.0;
           System.out.println("Here is your receipt:");

           //print date
           SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           Date date = new Date();
           String newOne= formatter.format(date);
           String[] val= newOne.split(" ");
           System.out.println("----------------------------------------------------------------");
           System.out.println("   Date: " + val[0] + "  Time: "+val[1]+  "    ");

           System.out.println("    --------------------------------------------------------    ");
           //print out bought items
           for(int i=0; i< cart.size(); i++){
               System.out.println("item_name='" + cart.get(i).item_name +
                       "  | isle='" + cart.get(i).isle  +
                       "  | quantity=" + cart.get(i).quantity +
                       "  | price per item=" + cart.get(i).price +
                       "  | total price=" + cart.get(i).price*cart.get(i).quantity);
               total=total+(cart.get(i).price*cart.get(i).quantity);
           }
           System.out.println("    --------------------------------------------------------    ");
           String strDouble = String. format("%. 2f", total);
           //print out total items and cost
           System.out.println("   Total Items: " + cart.size() + "      "+ "Total Price: " + strDouble+"   ");

           System.out.println("----------------------------------------------------------------");
           System.out.println("Thanks for visiting, come back soon!");

       }



   }







    static public void main(String[] args){

        databaseFunctions db= new databaseFunctions();
        GroceryStore g = new GroceryStore();
        List<String> isles = Arrays.asList("produce", "bakery", "meat", "dairy", "convenience");
        boolean visitingStore = true;

        while(visitingStore){
            System.out.println("Welcome! Please select an option: first isle, choose isle, leave");
            Scanner userInput = new Scanner(System.in);
            String line = userInput.nextLine();

            //they want to leave
            if(line.equals("leave")){
               // g.payAndLeave();
                visitingStore = false;

            } else if(line.equals("first isle")){

                int isleNumber=0;
                while(visitingStore && isleNumber<isles.size()){
//
                    List<item> currentIsleItemList;
                    List<String> options= Arrays.asList("add item", "remove item", "next isle", "view cart", "leave");
                    boolean badOption=true;

                    //print this isle's items
                    System.out.println("Welcome to the " +isles.get(isleNumber)+" isle! ");
                    System.out.println("Here are the items in this Isle:");

                    currentIsleItemList=  db.returnItemsInIsle(isles.get(isleNumber));
                    db.printOutItems(currentIsleItemList);

                    //checks if option is correct
                    while(badOption ) {
                        System.out.println("Select an option: add item, remove item, next isle, view cart, leave");
                        Scanner userInput1 = new Scanner(System.in);
                        String newline = userInput1.nextLine();
                        if (options.contains(newline)) {
                            if(newline.equals("add item")){
                                g.addItem(currentIsleItemList);
                            }else if(newline.equals("remove item")){
                                if(db.returnCart().size()==0){
                                    System.out.println("No items in your cart. Start Shopping!");
                                }else {
                                    g.removeItem();
                                }
                            }else if(newline.equals("view cart")){
                                if(db.returnCart().size()==0){
                                    System.out.println("No items in your cart. Start Shopping!");
                                }else {
                                    System.out.println("Here are the items in your cart:");
                                    db.printOutItems(db.returnCart());
                                }
                            }else if(newline.equals("next isle")){
                                badOption=false;
                                //break;

                            } else if(newline.equals("leave"))
                            {//leave
                              //  g.payAndLeave();
                                badOption=false;
                                visitingStore=false;

                            }
                        }
                        //it was a bad option, so you have to keep asking
                    }
                    isleNumber++;

                }


             visitingStore=false;
            } else if(line.equals("choose isle")){
                boolean badIsle = true;
                while(badIsle){
                    System.out.println("Please select an isle: produce, bakery, meat, dairy, convenience");
                    Scanner userInput9 = new Scanner(System.in);
                    String islename = userInput9.nextLine();
                    if(isles.contains(islename)){ //is good

                        List<item> currentIsleItemList;
                        List<String> options= Arrays.asList("add item", "remove item", "choose isle", "view cart", "leave");
                        boolean badOption=true;

                        //print this isle's items
                        System.out.println("Welcome to the " +islename+" isle! ");
                        System.out.println("Here are the items in this isle:");

                        currentIsleItemList=  db.returnItemsInIsle(islename);
                        db.printOutItems(currentIsleItemList);

                        //checks if option is correct
                        while(badOption ) {
                            System.out.println("Select an option: add item, remove item, choose isle, view cart, leave");
                            Scanner userInput2 = new Scanner(System.in);
                            String inline = userInput2.nextLine();
                            if (options.contains(inline)) {
                                if(inline.equals("add item")){
                                    g.addItem(currentIsleItemList);
                                }else if(inline.equals("remove item")){
                                    if(db.returnCart().size()==0){
                                        System.out.println("No items in your cart. Start Shopping!");
                                    }else {
                                        g.removeItem();
                                    }
                                }else if(inline.equals("view cart")){
                                    if(db.returnCart().size()==0){
                                        System.out.println("No items in your cart. Start Shopping!");
                                    }else {
                                        System.out.println("Here are the items in your cart:");
                                        db.printOutItems(db.returnCart());
                                    }
                                }else if(inline.equals("choose isle")){
                                    badOption=false;

                                }else if(inline.equals("leave")){//leave
                                    //g.payAndLeave();
                                    badOption=false;
                                    visitingStore=false;

                                }
                            }
                            //it was a bad option, so you have to keep asking
                        }
                        badIsle=false;

                    }

                }


            }
        }
        g.payAndLeave();


    }

}