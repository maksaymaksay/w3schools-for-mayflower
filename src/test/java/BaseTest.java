import config.AppConfig;
import factory.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import static factory.Browsers.getBrowserByStringName;
import static java.time.Duration.ofSeconds;

@Slf4j
public class BaseTest {

    protected WebDriver driver;
    private final AppConfig appConfig = ConfigFactory.create(AppConfig.class);

    @Before
    public void setUp() {
        driver = WebDriverFactory.create(getBrowserByStringName(appConfig.browserName()));
        driver.manage().timeouts().implicitlyWait(ofSeconds(5));
        log.info("Driver is up");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
            driver.quit();
            log.info("Driver is quited");
        }
    }
}
