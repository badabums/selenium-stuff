package Task10.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {


    @FindBy(name = "add_cart_product")
    WebElement addProductButton;
    @FindBy(css = "select[name*=options]")
    Select size;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void addProductToCart() {
        addProductButton.click();
        By size_l = By.cssSelector("select[name*=options]");

        if (isElementPresent(size_l)) {
            size.selectByIndex(0);
//            Select product_size = new Select(driver.findElement(size_l));
//            int any_size = rand.nextInt(product_size.getOptions().size() - 1) + 1;
//            product_size.selectByIndex(any_size);
        }
    }


}
