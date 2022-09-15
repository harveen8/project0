

import Model.item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;


public class groceryStoreTest {

    @Test void addItem() throws SQLException{
        Service.groceryService gc = Mockito.mock(Service.groceryService.class);
        Service.cartService cs = Mockito.mock(Service.cartService.class);
        cs.addItemToCart("potato", 2);
        Mockito.verify(cs).addItemToCart(any(String.class), any(int.class));
    }

}
