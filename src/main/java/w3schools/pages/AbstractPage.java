package w3schools.pages;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {

    protected final WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }
}
