package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.TestBaseRapor;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class SeleniumGridFirefox extends TestBaseRapor {


    WebDriver driver;

    @Test
    public void gridFireFoxLogin() throws MalformedURLException, InterruptedException {
        extentTest = extentReports.createTest("Selenium Grid CrossBrowser Firefox Tests",
                "User logs in to the app with valid ID and valid Password ");
        driver= new RemoteWebDriver(new URL("http://192.168.56.1:4444"),new FirefoxOptions());

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        driver.findElement(By.cssSelector("#userNamePlaceHolder")).sendKeys(ConfigReader.getProperty("VALIDUSERNAME"));
        extentTest.info("User enters valid username to user name text box");

        driver.findElement(By.cssSelector("#passwordPlaceHolder")).sendKeys(ConfigReader.getProperty("VALIDPASSWORD"));
        extentTest.info("User enters valid password to password text box");

        Select select=new Select(driver.findElement(By.cssSelector("#ddlModel")));
        select.selectByVisibleText(ConfigReader.getProperty("DEFAULTLANGUAGE"));
        extentTest.info("User chooses "+ConfigReader.getProperty("DEFAULTLANGUAGE")+" from language dropdown");

        driver.findElement(By.cssSelector("#loginLabel")).click();
        extentTest.info("User clicks enter button");

        Assert.assertTrue(driver.findElement(By.cssSelector(".kt-header__topbar-user")).isDisplayed());
        extentTest.pass("User verifies hello message is visible");
        driver.quit();

    }

}
