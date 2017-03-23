import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import static org.junit.Assert.*;


public class Task4 {

    private WebDriver driver;

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
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
                }
            }

            // verify that header exists
            boolean header = driver.findElements(By.cssSelector("h1") ).size() != 0;
            assertTrue(header);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}