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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.*;


public class Task6 {

    private WebDriver driver;
    private WebDriverWait wait;

    private void setCheckboxValue(WebElement checkbox, boolean value) {
        if ((!checkbox.isSelected() && value) || (checkbox.isSelected() && !value))
        {
            checkbox.click();
        }
    }

    @Before
    public void start() {
        ChromeDriverManager.getInstance().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost/litecart/admin/login.php");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void Task4Test() {

        // product name
        String product_name = "Awesome duck!!!";

        // define some locators
        By menu_l = By.id("box-apps-menu");
        By menu_item_l = By.id("app-");
        By tabs_container_l = By.className("tabs");

        // open 'add product' page
        driver.findElement(menu_l).findElements(menu_item_l).get(1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div a[class=button]")));
        driver.findElements(By.cssSelector("div a[class=button]")).get(1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));

        List<WebElement> radio_status = driver.findElements(By.cssSelector("input[type=radio]"));
        List<WebElement> text_fields = driver.findElements(By.cssSelector("input[type=text]"));
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type=checkbox]"));

        // set status 'enabled'
        radio_status.get(0).click();

        // set name
        text_fields.get(0).sendKeys(product_name);

        // set code
        text_fields.get(1).sendKeys("646046");

        // set categories
        setCheckboxValue(checkboxes.get(0), false);
        setCheckboxValue(checkboxes.get(1), true);
        setCheckboxValue(checkboxes.get(2), true);

        // set default category
        Select default_category = new Select(driver.findElement(By.cssSelector("select[name=default_category_id]")));
        default_category.selectByVisibleText("Subcategory");

        // set product groups
        setCheckboxValue(checkboxes.get(3), false);
        setCheckboxValue(checkboxes.get(4), false);
        setCheckboxValue(checkboxes.get(5), true);

        // set quantity
        WebElement quantity = driver.findElement(By.cssSelector("input[type=number]"));
        quantity.clear();
        quantity.sendKeys("10");

        // set sold out status
        Select sold_out_status = new Select(driver.findElement(By.cssSelector("select[name=sold_out_status_id]")));
        sold_out_status.selectByIndex(2);

        // set date valid from
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date_valid_from = new Date();
        driver.findElement(By.cssSelector("input[name=date_valid_from]")).sendKeys(dateFormat.format(date_valid_from));

        // set date valid to
        Calendar cal = Calendar.getInstance();
        cal.setTime(date_valid_from);
        cal.add(Calendar.DATE, 5);
        Date date_valid_to = cal.getTime();
        driver.findElement(By.cssSelector("input[name=date_valid_to]")).sendKeys(dateFormat.format(date_valid_to));

        // upload a file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("image.jpg").getFile());
        driver.findElement(By.cssSelector("input[type=file]")).sendKeys(file.getAbsolutePath());

        // go to prices tab
        driver.findElement(tabs_container_l).findElement(By.linkText("Prices")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(text(), 'Prices')]")));

        // set purchase price
        WebElement purchase_price = driver.findElements(By.cssSelector("input[type=number]")).get(5);
        purchase_price.clear();
        purchase_price.sendKeys("2");
        Select currency = new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]")));
        currency.selectByValue("USD");

        // set price
        WebElement price_usd = driver.findElements(By.cssSelector("input[type=number]")).get(6);
        WebElement price_eur = driver.findElements(By.cssSelector("input[type=number]")).get(7);
        price_usd.clear();
        price_usd.sendKeys("5");
        price_eur.clear();
        price_eur.sendKeys("5");

        // save the product
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.dataTable")));

        // get list of products
        List<WebElement> products = driver.findElements(By.cssSelector("table.dataTable tr.row"));
        List<String> products_str = products.stream().map(WebElement::getText).collect(Collectors.toList());

        // verify that product exist in table
        assertTrue(String.format("Product '%s' not found in the table", product_name), products_str.contains(product_name));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}