package Task10.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Page {

    WebDriver driver;

    protected boolean isElementPresent(By by) {
        return driver.findElements(by).size() > 0;
    }

    public Page(WebDriver driver) {
        this.driver = driver;
    }

}
