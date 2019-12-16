package ucll.project.ui;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteDriverHelper {
    private static WebDriver driver;

    public static WebDriver getDriver() throws MalformedURLException {
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capability);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        return driver;
    }
}
