import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import static org.junit.Assert.*;


public class Task4 {

    private WebDriver driver;

    private boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void Task4Test() {

        // define locators
        By menu_l = By.id("box-apps-menu");
        By menu_item_l = By.id("app-");
        By sub_menu_item_l = By.className("name");

        for (int i = 0; i < driver.findElements(menu_item_l).size(); i++) {

            // click on menu item
            driver.findElement(menu_l).findElements(menu_item_l).get(i).click();

            // count sub menus
            int sub_menus = driver.findElement(menu_l).findElements(menu_item_l).get(i).findElements(sub_menu_item_l).size();

            // go through sub menus if any exists
            if (sub_menus > 1) {
                for (int j = 1; j < sub_menus; j++) {
                    // click on sub menu item
                    driver.findElement(menu_l).findElements(menu_item_l).get(i).findElements(sub_menu_item_l).get(j).click();
                    // verify that header exists in sub menu
                    assertTrue("Page Title (h1 element) not found (sub menu)", isElementPresent(By.cssSelector("h1")));
                }
            }

            // verify that header exists in menu
            assertTrue("Page Title (h1 element) not found (main menu)", isElementPresent(By.cssSelector("h1")));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}