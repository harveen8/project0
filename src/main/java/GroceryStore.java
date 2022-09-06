import java.util.*;

public class GroceryStore {
    static public void main(String[] args){
        groceryService gc= new groceryService();
        databaseFunctions db= new databaseFunctions();
        List<String> isles = Arrays.asList("produce", "bakery", "meat", "dairy", "convenience");
        boolean visitingStore = true;

        while(visitingStore){
            System.out.println("Welcome! Please select an option: first isle, choose isle, leave.");
            Scanner userInput = new Scanner(System.in);
            String line = userInput.nextLine();

            if(line.equals("leave")){
                visitingStore = false;

            } else if(line.equals("first isle")){
                int isleNumber=0;
                while(visitingStore && isleNumber<isles.size()){
                    List<item> currentIsleItemList;
                    List<String> options= Arrays.asList("add item", "remove item", "next isle", "view cart", "leave");
                    boolean badOption=true;
                    //print this isle's items
                    System.out.println("Welcome to the " +isles.get(isleNumber)+" isle! \n");
                    System.out.println("Here are the items in this Isle:");
                    currentIsleItemList=  db.returnItemsInIsle(isles.get(isleNumber));
                    db.printOutItems(currentIsleItemList);
                    System.out.println("\n");
                    //checks if option is correct
                    while(badOption ) {
                        System.out.println("Select an option: add item, remove item, next isle, view cart, leave.");
                        Scanner userInput1 = new Scanner(System.in);
                        String newline = userInput1.nextLine();
                        if (options.contains(newline)) {
                            if(newline.equals("add item")){
                                gc.addItem(isles.get(isleNumber));
                            }else if(newline.equals("remove item")){
                                if(db.returnCart().size()==0){
                                    System.out.println("No items in your cart. Start Shopping!");
                                }else {
                                    gc.removeItem();
                                }
                            }else if(newline.equals("view cart")){
                                if(db.returnCart().size()==0){
                                    System.out.println("No items in your cart. Start Shopping!");
                                }else {
                                    System.out.println("Here are the items in your cart:");
                                    db.printOutItems(db.returnCart());
                                    System.out.println("\n");
                                }
                            }else if(newline.equals("next isle")){
                                badOption=false;
                            } else if(newline.equals("leave")) {
                                badOption=false;
                                visitingStore=false;
                            }
                        }
                    }
                    isleNumber++;
                }
             visitingStore=false;

            } else if(line.equals("choose isle")){
                boolean badIsle = true;
                while(badIsle){
                    System.out.println("Please select an isle: produce, bakery, meat, dairy, convenience.");
                    Scanner userInput9 = new Scanner(System.in);
                    String isleName = userInput9.nextLine();
                    if(isles.contains(isleName)){ //is good
                        List<item> currentIsleItemList;
                        List<String> options= Arrays.asList("add item", "remove item", "choose isle", "view cart", "leave");
                        boolean badOption=true;

                        //print this isle's items
                        System.out.println("Welcome to the " +isleName+" isle! \n ");
                        System.out.println("Here are the items in this isle:");
                        currentIsleItemList=  db.returnItemsInIsle(isleName);
                        db.printOutItems(currentIsleItemList);
                        System.out.println("\n");
                        //checks if option is correct
                        while(badOption ) {
                            System.out.println("Select an option: add item, remove item, choose isle, view cart, leave.");
                            Scanner userInput2 = new Scanner(System.in);
                            String inline = userInput2.nextLine();
                            if (options.contains(inline)) {
                                if(inline.equals("add item")){
                                    gc.addItem(isleName);
                                }else if(inline.equals("remove item")){
                                    if(db.returnCart().size()==0){
                                        System.out.println("No items in your cart. Start Shopping!");
                                    }else {
                                        gc.removeItem();
                                    }
                                }else if(inline.equals("view cart")){
                                    if(db.returnCart().size()==0){
                                        System.out.println("No items in your cart. Start Shopping!");
                                    }else {
                                        System.out.println("Here are the items in your cart:");
                                        db.printOutItems(db.returnCart());
                                        System.out.println("\n");
                                    }
                                }else if(inline.equals("choose isle")){
                                    badOption=false;
                                }else if(inline.equals("leave")){
                                    badOption=false;
                                    badIsle=false;
                                    visitingStore=false;
                                }
                            }
                        }
                    }
                }
            }
        }
        gc.payAndLeave();
    }
}
