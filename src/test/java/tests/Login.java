package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;
import utilities.*;

import static org.testng.Assert.*;

public class Login extends TestBaseRapor {

    Select select;

    @DataProvider
    public static Object[][] language() {
        return new Object[][]{
                {"Türkçe", "Merhaba "},
                {"English", "Hello "},
                {"Português", "Olá "},
                {"日本語", "こんにちは "},
        };
    }

    @DataProvider
    public static Object[][] username() {
        return new Object[][]{
                {"USERNAME", "INVALIDPASSWORD"},
                {"INVALIDUSERNAME", "PASSWORD"},
                {"INVALIDUSERNAME", "INVALIDPASSWORD"},
                {"USERNAME", "BLANKE"},
                {"BLANKE", "PASSWORD"},
                {"BLANKE", "BLANKE"},
        };
    }

    @Test(dataProvider = "language")
    public void loginDifferentLanguage(String language, String hello) {

        extentTest = extentReports.createTest("TC_01-02-03-04 Login with Different Language Tests",
                "User logs in to the app with valid ID and valid Password and different Language");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty("USERNAME"));
        extentTest.info("User enters valid username to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty("PASSWORD"));
        extentTest.info("User enters valid password to password text box");

        select = new Select(loginPage().languageDropDown);
        if (!select.getFirstSelectedOption().getText().equals(language)) {
            select.selectByVisibleText(language);
        }
        extentTest.info("User chooses English from language dropdown");

        clickElement(loginPage().enterButton);
        extentTest.info("User clicks enter button");

        String welcome = dashboardPage().helloButton.getText() + " " + Driver.getDriver().findElement(By.xpath("//h6/*[.=' " + ConfigReader.getProperty("USERNAME") + "']")).getText();
        assertEquals(hello + ConfigReader.getProperty("USERNAME"), welcome);
        extentTest.pass("User verifies the welcome message '" + hello + " selenium' is visible");

        clickElement(dashboardPage().helloTextButton);
        clickElement(dashboardPage().signOutButton);
        clickElement(dashboardPage().enterButtonAfterSignOut);

    }

    @Test(dataProvider = "username")
    public void negativeLoginTest(String username, String password) {

        extentTest = extentReports.createTest("TC_05-06-07-08-09-10 Negative Login Tests",
                "User logs in to the app with valid ID and valid Password and different Language");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty(username));
        extentTest.info("User enters valid username to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty(password));
        extentTest.info("User enters valid password to password text box");

        select = new Select(loginPage().languageDropDown);
        if (!select.getFirstSelectedOption().getText().equals("English")) {
            select.selectByVisibleText("English");
        }
        extentTest.info("User chooses English from language dropdown");

        clickElement(loginPage().enterButton);
        extentTest.info("User clicks enter button");

        isElementPresent(loginPage().alertText);
        extentTest.pass("User verifies the error message is visible");


    }

    @AfterSuite
    public void tearDownTest() {
        Driver.quitDriver();
    }
}
