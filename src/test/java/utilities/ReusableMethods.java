package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import pages.Pages;


import java.io.*;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.testng.Assert.*;

public class ReusableMethods extends Pages {

    Actions actions = new Actions(Driver.getDriver());
    static Select select;

    public void moveElement(WebElement element) {
        waitForVisibility(element, 9);
        actions.moveToElement(element);
    }

    public static void waitThread(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            System.err.println("***** Wait Exception *****");
        }

    }

    public static String getScreenshot(String name) {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/target/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            System.out.println("screenshot doesn't work");
        }
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

    public static void jsclick(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();

        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        js.executeScript("arguments[0].click()", webElement);


    }

    public static void setValueByJS(WebElement element, String key, String text) {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].setAttribute('" + key + "','" + text + "')", element);
    }

// If System is not offline but there are a problem to get element also,
// take me a screenshot and make enable to element and continue test
    public static String toEnableElement(WebElement element) {
        String classValue = element.getAttribute("class");
        String message="";
        if (classValue.contains("disabled")) {
            message="||********* There are a problem to get *********||";
            getScreenshot(element.getText()+" Element Disabled");
            classValue = classValue.replace("disabled", "");
            setValueByJS(element, "class", classValue);
        }
        return message;
    }

    public static void clickConnectButtonAtTheSameRowSearchedGroupName(List<WebElement> webElementList) {
        waitThread(1);//
        int groupNameSize = webElementList.size();
        for (int i = 0; i < groupNameSize; i++) {
            if (Driver.getDriver().findElement(By.xpath("(//tbody)[3]/tr[" + (i + 1) + "]/td[3]"))
                    .getText().contains(ConfigReader.getProperty("SEARCHEDWORD"))) {
                jsclick(Driver.getDriver().findElement(By.xpath("(//*[@class='dx-icon fas fa-link'])[" + i + 1 + "]")));
                break;
            }
        }
    }

    public static void chooseVisibleTextFromDropDown(WebElement elementDropDown,String visibleText) {
        select = new Select(elementDropDown);
        if (!select.getFirstSelectedOption().getText().equals(visibleText)) {
            select.selectByVisibleText(visibleText);
        }
    }


}
