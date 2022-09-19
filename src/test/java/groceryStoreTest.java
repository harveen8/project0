

import Model.item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;


public class groceryStoreTest {

    @Test void addItemToCart() throws SQLException{
        Service.cartService cs = Mockito.mock(Service.cartService.class);
        cs.addItemToCart("potato", 2);
        Mockito.verify(cs).addItemToCart(any(String.class), any(int.class));
    }
    @Test void addItemToGrocery() throws SQLException{
        Service.adminService as = Mockito.mock(Service.adminService.class);
        item mockItem = new item("potato", "produce", 50, 1);
        as.addItemToGroceryStore(mockItem);
        Mockito.verify(as).addItemToGroceryStore(any(item.class));
    }
    @Test void addAdmin() throws SQLException{
        Service.adminService as = Mockito.mock(Service.adminService.class);
        as.addAdmin("potato","produce" );
        Mockito.verify(as).addAdmin(any(String.class),any(String.class));
    }


}
