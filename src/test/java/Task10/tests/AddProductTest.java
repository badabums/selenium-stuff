package Task10.tests;
import Task10.app.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class AddProductTest {

    private Application app;

    @Before
    public void start() {
        app = new Application();
        app.start();
    }

    @Test
    public void Task10Test() {
        app.addProductsToCart(3);
        app.deleteProductsFromCart();
        assertTrue("There are some products in the cart", app.isCartEmpty());
    }

    @After
    public void stop() {
        app.stop();
    }
}
