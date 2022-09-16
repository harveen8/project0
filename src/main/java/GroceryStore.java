
import DAO.databaseFunctions;
import Model.item;
import Service.cartService;
import Service.groceryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;


import org.apache.log4j.Logger;

public class GroceryStore {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(databaseFunctions.class);
        logger.info("Information");
        groceryService gs = new groceryService();
        cartService cs = new cartService();
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins);
        app.start(9000);
        app.get("/grocerystore/items", ctx -> {ctx.json(gs.getAllItems());});
        app.get("/grocerystore/cart", ctx -> {ctx.json(cs.itemsInCart());});
        app.get("/grocerystore/cart/items/{name}", ctx ->
        {ctx.json(cs.getSpecificCartItem(ctx.pathParam("name")));
        });
        app.get("/grocerystore/{isle}", ctx ->
        {ctx.json(gs.getItemInIsle(ctx.pathParam("isle")));
        });
        app.get("/grocerystore/items/{name}", ctx ->
        {ctx.json(gs.getItem(ctx.pathParam("name")));
        });
        app.post("grocerystore/cart/add", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            item requestPainting = mapper.readValue(ctx.body(), item.class);
            cs.addItemToCart(requestPainting.item_name, requestPainting.quantity);
        });
        app.post("grocerystore/cart/remove", ctx ->{
            ObjectMapper mapper = new ObjectMapper();
            item requestPainting = mapper.readValue(ctx.body(), item.class);
            cs.removeItemInCart(requestPainting.item_name, requestPainting.quantity);
        });
        app.get("/grocerystore/cart/empty", ctx -> {ctx.json(cs.emptyCart());});





    }
}
