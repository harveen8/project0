import java.text.SimpleDateFormat;
import java.util.*;

public class groceryService {
    public void payAndLeave(){
        databaseFunctions db= new databaseFunctions();
        if(db.returnCart().size()==0){
            System.out.println("Thanks for visiting, come back soon!");
        }else{
            boolean yesOrNo=true;
            Scanner userInput = new Scanner(System.in);
            while(yesOrNo){
                System.out.println("Would you like to try for a coupon for 0, 15, 20, or 30% off on an isle: yes or no?");
                String newline = userInput.nextLine();
                if(newline.equals("yes")){
                    coupons();
                    yesOrNo=false;
                }else if(newline.equals("no")){
                    List<item> cart;
                    cart=db.returnCart();
                    float total =0;
                    System.out.println("Here is your receipt:");
                    //print date
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Date date = new Date();
                    String newOne= formatter.format(date);
                    String[] val= newOne.split(" ");
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("   Date: " + val[0] + "  Time: "+val[1]+  "    ");
                    System.out.println("-----------------------------------------------------------------------------------    ");
                    //print out bought items
                    for(int i=0; i< cart.size(); i++){
                        System.out.println("Item Name: " + cart.get(i).item_name +
                                " | Isle: " + cart.get(i).isle  +
                                " | Quantity: " + cart.get(i).quantity +
                                " | Price: $" + String. format("%.2f", cart.get(i).price )+
                                " | Total Price: $" +  String. format("%.2f", cart.get(i).price*cart.get(i).quantity));
                        total=total+(cart.get(i).price*cart.get(i).quantity);
                    }
                    System.out.println("-----------------------------------------------------------------------------------    ");
                    //print out total items and cost
                    System.out.println("   Total Items: " + cart.size() + "      "+ "Total Price: $" + String. format("%.2f", total)+"   ");
                    System.out.println("--------------------------------------------------------------------------------------");
                    System.out.println("Thanks for visiting, come back soon!");
                    //empties the cart and restocks the store
                    db.emptyCart();
                    yesOrNo=false;
                }
            }
        }
    }
    public static void coupons(){
        databaseFunctions db = new databaseFunctions();
        List<String> isles = Arrays.asList("produce", "bakery", "meat", "dairy", "convenience", "all");
        List<Integer> percentages = Arrays.asList(0, 15, 20, 30);
        List<item> cart= db.returnCart();
        float total =0;
        float nonCouponTotal=0;
        String isleToApplyCoupon = randomItemChooser(isles);
        float percent = randomItemChooser(percentages);
        System.out.println("Congratulations: you got "+percent+ "% off on all "+isleToApplyCoupon+" items! \n" );
        System.out.println("Here is your receipt:");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = new Date();
        String newOne= formatter.format(date);
        String[] val= newOne.split(" ");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println("   Date: " + val[0] + "  Time: "+val[1]+  "    ");
        System.out.println("---------------------------------------------------------------------------------------------------------    ");
        //print out bought items
        for(int i=0; i< cart.size(); i++){
            if(cart.get(i).isle.equals(isleToApplyCoupon) || isleToApplyCoupon.equals("all")){
                System.out.println("Item Name: " + cart.get(i).item_name +
                        " | Isle: " + cart.get(i).isle +
                        " | Quantity: " + cart.get(i).quantity +
                        " | Price: $" + String.format("%.2f", cart.get(i).price) +
                        " | Total : $" + String.format("%.2f", cart.get(i).price * cart.get(i).quantity) +
                        " | Total with Coupon : $" + String.format("%.2f", ((cart.get(i).price * cart.get(i).quantity)*(1-(percent/100)))));
                total = total + (((cart.get(i).price * cart.get(i).quantity)*(1-(percent/100))));
                nonCouponTotal=nonCouponTotal+(cart.get(i).price * cart.get(i).quantity);
            }else {
                System.out.println("Item Name: " + cart.get(i).item_name +
                        " | Isle: " + cart.get(i).isle +
                        " | Quantity: " + cart.get(i).quantity +
                        " | Price: $" + String.format("%.2f", cart.get(i).price) +
                        " | Total Price: $" + String.format("%.2f", cart.get(i).price * cart.get(i).quantity));
                total = total + (cart.get(i).price * cart.get(i).quantity);
                nonCouponTotal+=(cart.get(i).price * cart.get(i).quantity);
            }
        }
        System.out.println("---------------------------------------------------------------------------------------------------------    ");
        //print out total items and cost
        System.out.println("   Total Items: " + cart.size() + "      "+ "Price Without Coupon: $" + String. format("%.2f", nonCouponTotal)+"   "+ "Total Price: $" + String. format("%.2f", total));
        System.out.println("   You saved $"+(String.format("%.2f",(nonCouponTotal-total)))+ " with the "+ percent +
                "% coupon on "+isleToApplyCoupon+" items!");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Thanks for visiting, come back soon!");
        //empties the cart and restocks the store
        db.emptyCart();
    }

    public static <T> T randomItemChooser(List<T> str) {
        Random rand = new Random();
        T randomElement = str.get(rand.nextInt(str.size()));
        return randomElement;
    }
    public void addItem(String currentIsle){
        //some boilerplate code
        databaseFunctions db= new databaseFunctions();
        Scanner userInput = new Scanner(System.in);
        List <item> currentIsleItemList = db.returnItemsInIsle(currentIsle);
        boolean ask=true;   //needed for while loop
        String itemName="";    //will hold the name of item we want
        //loop to get item name
        db.printOutItems(currentIsleItemList);
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
        if (maxAmount==0) {
            System.out.println("Sorry, all out of Stock!");
            keepAsking = false;
        }else {
            System.out.println("How many would you like? Max is " + maxAmount + " items.");
        }
        //check if it works, or keep asking
        while(keepAsking){
            Scanner userInput4 = new Scanner(System.in);
            String inp = userInput4.nextLine();
            int inpNumber= Integer.parseInt(inp);
            if(inpNumber>0 && inpNumber<=maxAmount){
                db.addItemToCart(db.getSpecificItem(itemName), inpNumber);
                keepAsking=false;
            } else if (inpNumber==0) {
                System.out.println("Okay, none added!");
                keepAsking=false;
            }else {
                System.out.println("Please enter a valid number between 1 and " + maxAmount + ".");
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
        }//get the quantity available
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
}
