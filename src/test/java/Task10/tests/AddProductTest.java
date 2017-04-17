package Task10.tests;
import Task10.app.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddProductTest {

    private Application app;

    @Before
    public void start() {
        app = new Application();
        app.start();
    }

    @Test
    public void Task10Test() {
        app.openProduct();
        System.out.print("debug");
        app.addProductToCart();
        System.out.print("debug");
        Integer ss = app.getProductsInCart();
        System.out.print(ss);
        System.out.print("debug");

    }

    @After
    public void stop() {
        app.stop();
    }
}
