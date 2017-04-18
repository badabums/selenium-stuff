package Task10.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class ProductPage extends Page {

    final String selectSize = "select[name*=options]";
    @FindBy(name = "add_cart_product")
    WebElement addProductButton;
    @FindBy(css = selectSize)
    WebElement size;

    MainPage mainPage;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        mainPage = new MainPage(driver);
    }

    public void addProductToCart() {
        if (isElementPresent(By.cssSelector(selectSize))) {
            new Select(size).selectByIndex(1);
        }
        addProductButton.click();
        int cartQuantity = mainPage.getProductsInCart();
        wait.until(ExpectedConditions.textToBePresentInElement(mainPage.cartQuantity, Integer.toString(cartQuantity + 1)));
    }
}
