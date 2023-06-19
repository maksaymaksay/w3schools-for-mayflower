package w3schools.pages;

import config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import w3schools.dto.Customer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MainPage extends AbstractPage {
    private final AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    private final By sqlStatementField = By.xpath("//*[@id=\"textareaCodeSQL\"]");
    private final By runSQLBtn = By.xpath("//button[contains(@class,\'ws-btn\')]");
    private final By headersOfResultTable = By.xpath("//*[@id=\"divResultSQL\"]/div/table/tbody/tr[1]/th");
    private final By rowsOfResultTable = By.xpath("//*[@id=\"divResultSQL\"]/div/table/tbody/tr");

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage openW3schools() {
        driver.get(appConfig.url());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(runSQLBtn));
        log.info("w3schools page is open");
        return this;
    }

    public List<Customer> getCustomersFromResultTable() {
        Map<String, Integer> columnNameToPositionMap = new HashMap<>();
        List<Customer> customerList = new ArrayList<>();

        List<WebElement> headers = driver.findElements(headersOfResultTable);
        List<WebElement> rows = driver.findElements(rowsOfResultTable);

        for (int i = 0; i < headers.size(); i++) {
            columnNameToPositionMap.put(headers.get(i).getText(), i);
        }

        for (int i = 1; i < rows.size(); i++) {
            List<WebElement> elements = rows.get(i).findElements(By.tagName("td"));
            Customer customer = new Customer();
            customer.setCustomerID(elements.get(columnNameToPositionMap.get("CustomerID")).getText());
            customer.setCustomerName(elements.get(columnNameToPositionMap.get("CustomerName")).getText());
            customer.setContactName(elements.get(columnNameToPositionMap.get("ContactName")).getText());
            customer.setAddress(elements.get(columnNameToPositionMap.get("Address")).getText());
            customer.setCity(elements.get(columnNameToPositionMap.get("City")).getText());
            customer.setPostalCode(elements.get(columnNameToPositionMap.get("PostalCode")).getText());
            customer.setCountry(elements.get(columnNameToPositionMap.get("Country")).getText());
            customerList.add(customer);
        }
        log.info("customerList with size ({}) was returned", customerList.size());
        return customerList;
    }

    public MainPage executeSQLScript(String sqlQuery) {
        String setAttributeJSScript = "arguments[0].setAttribute('value','" + sqlQuery + "')";
        executeJSScript(setAttributeJSScript);

        String windowEditorJSScript = "window.editor.getDoc().setValue('" + sqlQuery + "')";
        executeJSScript(windowEditorJSScript);
        log.info("SQL query ({}) was executed", sqlQuery);

        driver.findElement(runSQLBtn).click();
        return this;
    }

    private void executeJSScript(String js) {
        ((JavascriptExecutor) driver).executeScript(js, driver.findElement(sqlStatementField));
        log.info("JS script ({}) was executed", js);
    }
}
