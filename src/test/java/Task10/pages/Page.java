package Task10.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Page {

    private WebDriver driver;
    WebDriverWait wait;

    protected boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

}
