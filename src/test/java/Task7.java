import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;


public class Task7 {

    private WebDriver driver;
    private WebDriverWait wait;

    private boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/");
    }

    @Test
    public void Task7Test() {

        // number of products to add
        int products_num = 10;

        // some locators
        By add_button_l = By.name("add_cart_product");
        By cart_quantity_l = By.cssSelector("span.quantity");
        By logo_l = By.id("logotype-wrapper");
        By product_l = By.cssSelector("li.product");
        By size_l = By.cssSelector("select[name*=options]");
        By item_l = By.cssSelector("table.dataTable td.item");

        WebElement cart_quantity;
        Random rand = new Random();
        int any_product;

        // add 3 products to the cart
        for (int i = 1; i < products_num + 1; i++) {

            // open home page
            driver.findElement(logo_l).click();

            // open random product
            List<WebElement> products = driver.findElements(product_l);
            any_product = rand.nextInt(products.size());
            products.get(any_product).click();

            // locate cart quantity element
            cart_quantity = driver.findElement(cart_quantity_l);

            // select random size if choice is given
            if (isElementPresent(size_l)) {
                Select product_size = new Select(driver.findElement(size_l));
                int any_size = rand.nextInt(product_size.getOptions().size() - 1) + 1;
                product_size.selectByIndex(any_size);
            }

            // add product to cart
            driver.findElement(add_button_l).click();

            // wait until cart quantity changed
            wait.until(ExpectedConditions.textToBePresentInElement(cart_quantity, Integer.toString(i)));
        }

        // open the cart
        driver.findElement(By.cssSelector("a.content")).click();

        // since there can be the same products in the cart, count them in the table
        // then delete one by one (same items delete by single action)
        int all_items = driver.findElements(item_l).size();
        for (int i = 1; i <= all_items; i++) {
            driver.findElement(By.cssSelector("button[value=Remove]")).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(item_l, all_items - i));
        }

        // verify number of products in the cart is 0 (double check)
        assertTrue("No message about empty cart", isElementPresent(By.id("checkout-cart-wrapper")));
        driver.findElement(logo_l).click();
        int in_the_cart = Integer.parseInt(driver.findElement(cart_quantity_l).getText());
        assertTrue("There are some products in the cart", in_the_cart == 0);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}