package Task10.pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {

    @FindBy(css = "li.product")
    WebElement product;
    @FindBy(css = "span.quantity")
    WebElement cartQuantity;


    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openProduct() {
        product.click();
    }

    public Integer getProductsInCart() {
        return  Integer.parseInt(cartQuantity.getText());
    }

}
