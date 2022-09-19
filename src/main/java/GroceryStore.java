
import DAO.databaseFunctions;
import Model.item;
import Model.logIn;
import Service.adminService;
import Service.cartService;
import Service.groceryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;


import org.apache.log4j.Logger;

public class GroceryStore {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(GroceryStore.class);
        logger.info("Began Logging");
        groceryService gs = new groceryService();
        cartService cs = new cartService();
        adminService as = new adminService();
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins);
        app.start(9000);
        app.get("/grocerystore/items", ctx -> {ctx.json(gs.getAllItems());});
        app.get("/grocerystore/cart", ctx -> {ctx.json(cs.itemsInCart());});
        app.get("/grocerystore/cart/items/{name}", ctx ->
        {ctx.json(cs.getSpecificCartItem(ctx.pathParam("name")));
        });
        app.get("/grocerystore/{isle}", ctx ->
        {ctx.json(gs.getItemInIsle(ctx.pathParam("isle")));});
        app.get("/grocerystore/items/{itemName}", ctx ->
        {ctx.json(gs.getItem(ctx.pathParam("itemName")));});
        app.post("grocerystore/cart/add", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            item requestPainting = mapper.readValue(ctx.body(), item.class);
            cs.addItemToCart(requestPainting.item_name, requestPainting.quantity);
        });
        app.post("grocerystore/cart/remove", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            item newItem = mapper.readValue(ctx.body(), item.class);
            cs.removeItemInCart(newItem.item_name, newItem.quantity);
        });
        app.get("/grocerystore/cart/empty", ctx -> {ctx.json(cs.emptyCart());});
        app.get("/grocerystore/admin/all", ctx -> {ctx.json(as.returnAllAdmin());});
        app.post("grocerystore/admin/add", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            logIn newLogIn = mapper.readValue(ctx.body(), logIn.class);
            as.addAdmin(newLogIn.username, newLogIn.password);
        });
        app.post("grocerystore/admin/remove", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            logIn user = mapper.readValue(ctx.body(), logIn.class);
            as.removeAdmin(user.username);
        });
        app.post("grocerystore/admin/add/item", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            item newItem = mapper.readValue(ctx.body(), item.class);
            as.addItemToGroceryStore(newItem);
        });




    }
}
