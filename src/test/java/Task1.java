import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

public class Task1 {

    private WebDriver driver1, driver2;
    private WebDriverWait wait1, wait2;

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        driver1 = new ChromeDriver();
        FirefoxDriverManager.getInstance().setup();
        driver2 = new FirefoxDriver();
        wait1 = new WebDriverWait(driver1, 10);
        wait2 = new WebDriverWait(driver2, 10);
    }

    @Test
    public void Task1Test() {
        driver1.get("http://www.google.com");
        driver1.findElement(By.name("q")).sendKeys("webdriver");
        driver1.findElement(By.name("btnG")).click();
        wait1.until(ExpectedConditions.titleIs("webdriver - Пошук Google"));
        driver2.get("http://www.google.com");
        driver2.findElement(By.name("q")).sendKeys("webdriver");
        driver2.findElement(By.name("btnG")).click();
        wait2.until(ExpectedConditions.titleIs("webdriver - Пошук Google"));
    }

    @After
    public void stop() {
        driver1.quit();
        driver1 = null;
        driver2.quit();
        driver2 = null;
    }
}