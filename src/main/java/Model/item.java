package Model;

public class item {
    public String item_name ;
    public String isle;
    public int quantity;
    public float price;

    public item(String item_name, String isle, int quantity, float price) {
        this.item_name = item_name;
        this.isle = isle;
        this.quantity = quantity;
        this.price = price;
    }
    public item(){}


    @Override
    public String toString() {
        return "Item Name: " + item_name  +
                "  | Isle: " + isle  +
                "  | Quantity: " + quantity +
                "  | Price: $" + String. format("%.2f", price) +"\n" ;
    }
}
