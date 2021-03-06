package com.orangehrm.base;

import com.orangehrm.utils.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

import java.util.concurrent.TimeUnit;

public class BasePage {

    public static WebDriver driver;
    public static WebDriver initializeDriver(){
        ConfigReader.readProperties(Constants.CONFIGURATION_FILE);
        String browser = ConfigReader.getProperty("browser").toLowerCase();
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "opera":
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;
                default:
                    throw new RuntimeException("Invalid browser name...!");
        }
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("url"));
        PageManager.initialize();
        return driver;
    }
    public static void tearDown(){
        if(driver != null){
            driver.quit();
        }

    }
}
