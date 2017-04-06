import com.google.common.io.Files;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static org.junit.Assert.*;


public class Task9 {

    private EventFiringWebDriver driver;

    public static class EventsListener extends
            AbstractWebDriverEventListener {

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Try to locate element: " + by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Element is located: " + by);
        }

        public void onException(Throwable throwable, WebDriver driver) {
            File temp_file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                Files.copy(temp_file, new File("screenshot-" + System.currentTimeMillis() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(new EventsListener());
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void Task9Test() {

        HashMap<String, List<String>> menu_tree = new HashMap<String, List<String>>();

        JSONParser parser = new JSONParser();
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("menu_tree.json").getFile());

            Object obj = parser.parse(new FileReader(file));

            JSONObject jsonObject = (JSONObject) obj;
            Set<String> menus = jsonObject.keySet();

            for (String sub_menu : menus) {
                menu_tree.put(sub_menu, (List<String>) jsonObject.get(sub_menu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // define locators
        By menu_l = By.id("box-apps-menu");

        for (String menu : menu_tree.keySet()) {
            // click on menu item
            driver.findElement(menu_l).findElement(By.linkText(menu)).click();

            if (menu_tree.get(menu).size() > 1) {
                for (String sub_menu : menu_tree.get(menu)) {

                    // click on sub menu item
                    driver.findElement(menu_l).findElement(By.linkText(sub_menu)).click();

                    // verify that header exists in sub menu
                    assertTrue("Page Title (h1 element) not found (sub menu)", isElementPresent(By.cssSelector("h1")));
                }
            }
            assertTrue("Page Title (h1 element) not found (main menu)", isElementPresent(By.cssSelector("h1")));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}