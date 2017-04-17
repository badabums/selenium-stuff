package Task10.app;

import Task10.pages.CheckoutPage;
import Task10.pages.MainPage;
import Task10.pages.ProductPage;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


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
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.navigate().to(baseUrl);
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
//        wait = new WebDriverWait(driver, 10);
//        driver.get("http://localhost/litecart/");

    }

    public void openProduct() {
        mainPage.openProduct();
    }

    public void addProductToCart() {
        productPage.addProductToCart();
    }

    public Integer getProductsInCart() {
        return  mainPage.getProductsInCart();
    }

    public void stop() {
        driver.quit();
        driver = null;
    }

}
