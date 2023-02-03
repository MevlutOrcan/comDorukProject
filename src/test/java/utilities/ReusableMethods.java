package utilities;

import com.github.javafaker.Faker;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import pages.Pages;


import java.io.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static org.testng.Assert.*;

public class ReusableMethods extends Pages {
    static Faker faker;
    Actions actions=new Actions(Driver.getDriver());
    public void moveElement(WebElement element){
        waitForVisibility(element,9);
        actions.moveToElement(element);
    }
    public static void waitThread(int second){
        try {
            Thread.sleep(second* 1000L);
        } catch (InterruptedException e) {
            System.err.println("***** Wait Exception *****");
        }

    }
    public static String getScreenshot(String name) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        FileUtils.copyFile(source, finalDestination);
        return target;
    }
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static void clickElement(WebElement element) {
        waitForVisibility(element, 40);
        element.click();

    }

    public static void isElementPresent(WebElement element) {
        waitForVisibility(element, 40);
        assertTrue(element.isDisplayed());
    }

    public static void enterKeys(WebElement element, String keys) {
        waitForVisibility(element, 40);
        element.sendKeys(keys);
    }

    public static void enterKeys(WebElement element, String keys, Boolean clear) {
        waitForVisibility(element, 40);
        if (clear) {
            element.clear();
        }
        element.sendKeys(keys);
    }

    public static Faker getFaker() { // getFaker method
        return faker = new Faker();
    }
    public static void jsclick(WebElement webElement){
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

            js.executeScript("arguments[0].scrollIntoView(true);", webElement);
            js.executeScript("arguments[0].click()", webElement);


    }
    public static String  getValueWithJs(String elementId){
        JavascriptExecutor js=(JavascriptExecutor)Driver.getDriver();
        String value=js.executeScript("return document.getElementById('"+elementId+"').value").toString();
        return value;
    }
    public static void setValueByJS(WebElement element,String key, String text){
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('"+key+"','"+text+"')",element);
    }

    public static void removeValueByJS(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)Driver.getDriver();
        js.executeScript("arguments[0].removeAttribute('class','disabled')",element);
    }


}
