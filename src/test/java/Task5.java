import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.selenium.factory.WebDriverPool;
import static org.junit.Assert.*;


public class Task5 {

    @AfterClass
    public static void stopAllBrowsers() {
        WebDriverPool.DEFAULT.dismissAll();
    }

    @Test
    public void testInFirefox() {
        FirefoxDriverManager.getInstance().setup();
        actualTest(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.firefox()));
    }


    @Test
    public void testInChrome() {
        ChromeDriverManager.getInstance().setup();
        actualTest(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.chrome()));
    }

    @Test
    public void testInIE() {
        InternetExplorerDriverManager.getInstance().arch64().setup();
        actualTest(WebDriverPool.DEFAULT.getDriver(DesiredCapabilities.internetExplorer()));
    }

    private void actualTest(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/");

        // Define locators
        By regular_price_l = By.className("regular-price");
        By campaign_price_l = By.className("campaign-price");

        // Find campaign box with product
        WebElement campaigns_box = driver.findElement(By.id("box-campaigns"));
        WebElement product = campaigns_box.findElement(By.cssSelector("li[class*='product']"));
        WebElement product_regular_price = product.findElement(regular_price_l);
        WebElement product_campaign_price = product.findElement(campaign_price_l);

        // Get all needed properties
        String p_name = product.findElement(By.className("name")).getText();
        String p_regular_price_value = product_regular_price.getText();
        String p_regular_price_tag = product_regular_price.getAttribute("tagName");
        String p_regular_price_color = product_regular_price.getCssValue("color");
        String p_campaign_price_value = product_campaign_price.getText();
        String p_campaign_price_tag = product_campaign_price.getAttribute("tagName");
        String p_campaign_price_color = product_campaign_price.getCssValue("color");

        // Go to product page
        product.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));

        // Find container with a product
        WebElement product_box = driver.findElement(By.id("box-product"));
        product_regular_price = product_box.findElement(regular_price_l);
        product_campaign_price = product_box.findElement(campaign_price_l);

        // Verify properties
        assertEquals(p_name, driver.findElement(By.cssSelector("h1.title")).getText());
        assertEquals(p_regular_price_value, product_regular_price.getText());
        assertEquals(p_regular_price_tag, product_regular_price.getAttribute("tagName"));
        assertEquals(p_regular_price_color, product_regular_price.getCssValue("color"));
        assertEquals(p_campaign_price_value, product_campaign_price.getText());
        assertEquals(p_campaign_price_tag, product_campaign_price.getAttribute("tagName"));
        assertEquals(p_campaign_price_color, product_campaign_price.getCssValue("color"));
    }

}