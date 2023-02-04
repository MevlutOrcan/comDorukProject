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
                {"Türkçe", "Merhaba ","TC_01"},
                {"English", "Hello ","TC_02"},
                {"Português", "Olá ","TC_03"},
                {"日本語", "こんにちは ","TC_04"},
        };
    }

    @DataProvider
    public static Object[][] username() {
        return new Object[][]{
                {"VALIDUSERNAME", "INVALIDPASSWORD","TC_05 "},
                {"INVALIDUSERNAME", "VALIDPASSWORD","TC_06 "},
                {"INVALIDUSERNAME", "INVALIDPASSWORD","TC_07 "},
                {"VALIDUSERNAME", "BLANKE","TC_08 "},
                {"BLANKE", "VALIDPASSWORD","TC_09 "},
                {"BLANKE", "BLANKE","TC_010 "},
                {"VALIDUSERNAME", "SPECIALCHAR","TC_011 "},
                {"SPECIALCHAR", "VALIDPASSWORD","TC_012 "},
                {"SPECIALCHAR", "SPECIALCHAR","TC_013 "},
                {"VALIDUSERNAME", "SPACES","TC_014 "},
                {"SPACES", "VALIDPASSWORD","TC_015 "},
                {"SPACES", "SPACES","TC_016 "},
        };
    }

    @Test(dataProvider = "language")
    public void loginDifferentLanguage(String language, String hello,String testCaseNo) {

        extentTest = extentReports.createTest(testCaseNo+" Login with Different Language Tests",
                "User logs in to the app with valid ID and valid Password and different Language");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty("VALIDUSERNAME"));
        extentTest.info("User enters valid username to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty("VALIDPASSWORD"));
        extentTest.info("User enters valid password to password text box");

        select = new Select(loginPage().languageDropDown);
        if (!select.getFirstSelectedOption().getText().equals(language)) {
            select.selectByVisibleText(language);
        }
        extentTest.info("User chooses "+language+" from language dropdown");

        clickElement(loginPage().enterButton);
        extentTest.info("User clicks enter button");

        String welcome = dashboardPage().helloButton.getText() + " " + Driver.getDriver().findElement(By.xpath("//h6/*[.=' " + ConfigReader.getProperty("VALIDUSERNAME") + "']")).getText();
        assertEquals(hello + ConfigReader.getProperty("VALIDUSERNAME"), welcome);
        extentTest.pass("User verifies the welcome message '" + hello + " selenium' is visible");

        clickElement(dashboardPage().helloTextButton);
        clickElement(dashboardPage().signOutButton);
        clickElement(dashboardPage().enterButtonAfterSignOut);

    }

    @Test(dataProvider = "username")
    public void negativeLoginTest(String username, String password,String testCaseNo) {

        extentTest = extentReports.createTest(testCaseNo+" Negative Login Tests",
                "User logs in to the app with "+username+" as Username -"+ConfigReader.getProperty(username)+"- and "+password+" as Password -"+ConfigReader.getProperty(password)+"-");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty(username));
        extentTest.info("User enters "+username+" as username -"+ConfigReader.getProperty(username)+"- to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty(password));
        extentTest.info("User enters "+password+" as password -"+ConfigReader.getProperty(password)+"-to password text box");

        select = new Select(loginPage().languageDropDown);
        if (!select.getFirstSelectedOption().getText().equals(ConfigReader.getProperty("DEFAULTLANGUAGE"))) {
            select.selectByVisibleText(ConfigReader.getProperty("DEFAULTLANGUAGE"));
        }
        extentTest.info("User chooses "+ConfigReader.getProperty("DEFAULTLANGUAGE")+" from language dropdown");

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
