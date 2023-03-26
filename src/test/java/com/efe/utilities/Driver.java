package com.efe.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {
        /*
    Creating a private constructor, so we are closing
    access to the object of this class from outside the class
     */

    private Driver(){}

    private static WebDriver driver; // value is null by default

    /*
    We make WebDriver private, because we want to close access from outside the class.
    We make it static because we will use it in a static method.
     */

    public static WebDriver getDriver(){

        if (driver == null){

                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        return driver;

    }

    /*
    This method will make sure our driver value is always null after using quit() method
     */
    public static void closeDriver(){
        if (driver != null){
            driver.quit(); // this line will terminate the existing session. value will not even be null
            driver = null;
        }
    }
}
