package Task10.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class CheckoutPage extends Page {

    final String orderSummary = "table.dataTable td.item";
    @FindBy(css = "button[value=Remove]")
    WebElement deleteButton;
    @FindBy(css = orderSummary)
    List<WebElement> orderSummaryItems;
    @CacheLookup
    @FindBy(id = "checkout-cart-wrapper")
    WebElement emptyCart;


    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void deleteProduct() {
        int currentNumOfItems = getItemsInOrder();
        deleteButton.click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(orderSummary), currentNumOfItems - 1));
    }

    public void waitForEmptyCart() {
        wait.until(ExpectedConditions.elementToBeClickable(emptyCart));
    }

    public Integer getItemsInOrder() {
        return orderSummaryItems.size();
    }


}
