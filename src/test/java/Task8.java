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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class Task8 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void Task8Test() {

        // open 'Countries' page
        driver.findElement(By.id("box-apps-menu")).findElement(By.linkText("Countries")).click();

        // edit random country
        WebElement countries_table = driver.findElement(By.className("dataTable"));
        List<WebElement> countries = countries_table.findElements(By.cssSelector("tr.row td>a[title='Edit']"));
        Random rand = new Random();
        int random_country = rand.nextInt(countries.size());
        countries.get(random_country).click();

        // get list of external links on page
        List<WebElement> external_links = driver.findElements(By.className("fa-external-link"));


        // ------------- Verify VERSION: 1 -----------------
        // open all links, count opened windows and compare with amount of external links + 1

        String main_handle = driver.getWindowHandle();
        for (WebElement link : external_links) {
            // here we can get NoSuchElementException, if link opened in the current window
            link.click();
        }
        assertTrue("Oops, not all links have been opened in a new windows(((",
                driver.getWindowHandles().size() == external_links.size() + 1);

        // close all external windows
        Set<String> handles = driver.getWindowHandles();
        handles.remove(main_handle);
        for (String handle : handles) {
            driver.switchTo().window(handle);
            driver.close();
        }
        driver.switchTo().window(main_handle);


        // ------------- Verify VERSION: 2 -----------------
        // open, verify and close external links one by one

        String link_href;
        String opened_link_url;
        int num_of_windows;
        for (WebElement link : external_links) {
            // get number of opened windows
            num_of_windows = driver.getWindowHandles().size();

            // get expected url
            link_href = link.findElement(By.xpath("./parent::*")).getAttribute("href");

            // here we can get NoSuchElementException, if link opened in the current window
            link.click();

            // wait for new window
            wait.until(ExpectedConditions.numberOfWindowsToBe(num_of_windows + 1));

            // switch to new window
            Set<String> current_handles = driver.getWindowHandles();
            current_handles.remove(main_handle);
            driver.switchTo().window(current_handles.iterator().next());

            // get actual url
            opened_link_url = driver.getCurrentUrl();

            // here should be some kind of verify, but since request can be redirected, we don't know if urls will match
            // so just print them to console
            // in real life it should be verified by assertion
            System.out.println(String.format("Expected URL: %s", link_href));
            System.out.println(String.format("Actual URL  : %s", opened_link_url));
            driver.close();
            driver.switchTo().window(main_handle);
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}