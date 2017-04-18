package Task10.app;
import Task10.pages.CheckoutPage;
import Task10.pages.MainPage;
import Task10.pages.ProductPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Application {

    MainPage mainPage;
    ProductPage productPage;
    CheckoutPage checkoutPage;

    WebDriver driver;

    private final String baseUrl = "http://localhost/litecart/";

    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        openMainPage();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    private void openMainPage() {
        driver.navigate().to(baseUrl);
    }

    public void addProductsToCart(Integer numOfProducts) {
        for (int i = 1; i <= numOfProducts; i++) {
            mainPage.openProduct();
            productPage.addProductToCart();
            openMainPage();
        }
    }

    public void deleteProductsFromCart() {
        mainPage.openCart();
        while (checkoutPage.getItemsInOrder() > 0) {
            checkoutPage.deleteProduct();
        }
        checkoutPage.waitForEmptyCart();
    }

    public boolean isCartEmpty() {
        openMainPage();
        return mainPage.getProductsInCart() == 0;
    }

    public void stop() {
        driver.quit();
        driver = null;
    }

}
