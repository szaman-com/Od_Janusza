package base;

import Utilities.PropertyManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserDriverFactory {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private String browser;
    private Logger log;
    private Boolean isHeadless = PropertyManager.getInstance().getHeadless();

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() {
        // Create driver


        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "firefox":

                FirefoxBinary firefoxBinary = new FirefoxBinary();
                if (isHeadless) {
                    firefoxBinary.addCommandLineOptions("--headless");
                }
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;


            default:
                System.out.println("Do not know how to start: " + browser + ", starting chrome.");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver.set(new ChromeDriver());
                break;
        }

        return driver.get();
    }





}
