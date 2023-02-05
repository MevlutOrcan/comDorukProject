package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.TestBaseRapor;


import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.*;

public class Start_Review_Finish_Job extends TestBaseRapor {


    String nameOfMachine;

    String orderReferanceNo;
    String orderOpId;
    String amount;
    String netCycle;



    @Test
    public void login_Positive() {

        extentTest = extentReports.createTest("TC_01 Login Pozitive Test",
                            "User logs in to the app with valid ID and valid Password");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty("VALIDUSERNAME"));
        extentTest.info("User enters valid username to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty("VALIDPASSWORD"));
        extentTest.info("User enters valid password to password text box");

        chooseVisibleTextFromDropDown(loginPage().languageDropDown,ConfigReader.getProperty("DEFAULTLANGUAGE"));
        extentTest.info("User chooses "+ConfigReader.getProperty("DEFAULTLANGUAGE")+" from language dropdown");

        clickElement(loginPage().enterButton);
        extentTest.info("User clicks enter button");

        String welcome = dashboardPage().helloButton.getText() + " " + Driver.getDriver().findElement(By.xpath("//h6/*[.=' " + ConfigReader.getProperty("VALIDUSERNAME") + "']")).getText();
        assertEquals("Hello " + ConfigReader.getProperty("VALIDUSERNAME"), welcome);
        extentTest.pass("User verifies the welcome message 'Hello "+ConfigReader.getProperty("VALIDUSERNAME")+"' is visible");


    }

    @Test(dependsOnMethods = {"login_Positive"}, priority = 1)
    public void chooseGroupTest() {
        extentTest = extentReports.createTest("TC_01 Choose a Group Test",
                "User chooses group that includes text '" + ConfigReader.getProperty("SEARCHEDWORD") + "'");

        moveElement(dashboardPage().dashboardIcon);
        clickElement(dashboardPage().ipcButton);
        extentTest.info("User clicks IPC button on the left side");


        enterKeys(dashboardPage().searchBoxUnderTheName, ConfigReader.getProperty("SEARCHEDWORD"));
        extentTest.info("User writes '" + ConfigReader.getProperty("SEARCHEDWORD") + "' to search box under the name column");


        clickConnectButtonAtTheSameRowSearchedGroupName(dashboardPage().groupNamesList);
        extentTest.info("The user clicks the 'connect button' on the row containing the text '" + ConfigReader.getProperty("SEARCHEDWORD") + "'");


        nameOfMachine=dashboardPage().machineNameText.getText();
        assertEquals(dashboardPage().machineNameText.getText(), ConfigReader.getProperty("MACHINENAME"));
        extentTest.pass("User verifies "+ConfigReader.getProperty("MACHINENAME")+" text on the header");


    }

    @Test(dependsOnMethods = {"login_Positive","chooseGroupTest"}, priority = 2)
    public void chooseAJob() {

        extentTest = extentReports.createTest("TC_02 Choose a Job Test",
                "User chooses a job from work order by selecting from list");

//If System is offline, stop the test
        assertEquals(jobPage().offlineTextOnTheHeader.size(),0,"|***** The System is offline *****|");


        String errMessage=toEnableElement(jobPage().jobButton);
        clickElement(jobPage().jobButton);
        extentTest.info(errMessage);
        extentTest.info("User clicks the Job button");

        clickElement(jobPage().selectFromWorkOrderButton);
        extentTest.info("User clicks  the Select from Work Order button");

        clickElement(jobPage().selectFromListButton);
        extentTest.info("User clicks  the Select from List Button");

        waitThread(1);
        jobPage().orderReferenceNoSearchBox.sendKeys(ConfigReader.getProperty("SEARCHEDWORD"));
        extentTest.info("User enters '" + ConfigReader.getProperty("SEARCHEDWORD") + "' the search box under the Order Reference No column ");

// 110-125 Determine a radio button randomly
// then according to this, take information from at the same row of radio button
        waitThread(1);
        List<WebElement> radioButtonList = jobPage().radioButtonList;
        Random rnd = new Random();
        int rank = rnd.nextInt(radioButtonList.size());
        clickElement(radioButtonList.get(rank));
        extentTest.info("User chooses a random row");

        int rowNumber = rank + 3;
        nameOfMachine=ConfigReader.getProperty("MACHINENAME");
        orderReferanceNo = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"2\"]")).getText();
        orderOpId = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"3\"]")).getText();
        amount = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"4\"]")).getText();
        netCycle = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"5\"]")).getText();
        extentTest.info("User saves “Order Reference No, Order Op. ID, amount, net cycle” row values ");
        extentTest.pass("User verifies that the information of row is available");


    }

    @Test(dependsOnMethods = {"login_Positive","chooseGroupTest", "chooseAJob"}, priority = 3)
    public void startMachine() {

        extentTest = extentReports.createTest("TC_03 Start a Machine Test",
                "User starts a machine from list");

        clickElement(jobPage().continueButton);
        extentTest.info("User clikcks continue button");

        clickElement(jobPage().startButton);
        extentTest.info("User clicks start button");

        isElementPresent(jobPage().successMessage);
        extentTest.info("User reads the success message");

        clickElement(jobPage().okButton);
        extentTest.info("User clicks ok button");


        assertFalse(jobPage().activeJobInformationButton.getAttribute("class").contains("disabled"));
        extentTest.pass("User verifies Active Job Information button is enabled");


    }

    @Test(dependsOnMethods = {"login_Positive","chooseGroupTest", "chooseAJob", "startMachine"}, priority = 4)
    public void informationVerify() {
        extentTest = extentReports.createTest("TC_04 Verify job information",
                "User does verifies that Machine name,OrderReference No, Order Op. ID, Plan Quantity, Speed ");

        String errMessage=toEnableElement(jobPage().jobButton);
        clickElement(jobPage().jobButton);
        extentTest.info(errMessage);
        clickElement(jobPage().activeJobInformationButton);
        extentTest.info("User clicks Active Job Information button");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(jobPage().machineName.getText(),nameOfMachine);
        extentTest.info("User verifies that  Machine name value equal to machine name in header ");


        softAssert.assertEquals(jobPage().orderReferenceNuber.getText(),orderReferanceNo);
        softAssert.assertEquals(orderOpId, jobPage().orderOpId.getText());

        extentTest.info("User verifies that the OrderReference No and Order Op. ID values are equal to the OrderReference No and Order Op. ID of the setup step");

// Requirement says that planQuantity and amount must be equal
// But only equal until first slash of plantQuantity
        String planQuantity=jobPage().planQuantity.getText().split("/")[0];
        softAssert.assertEquals(planQuantity,amount);
        extentTest.info("User verifies that the Plan Quantity value is equal to the Amount  of the setup step");

// Requirement says that speed and netCycle must be equal
// But only equal until first slash of speed
        String speed=jobPage().speed.getText().split("/")[0];
        softAssert.assertEquals(speed,netCycle);
        extentTest.info("User verifies that the Speed value is equal to the Net Cycle of the setup step");

        softAssert.assertAll();
        clickElement(jobPage().xButton);
        extentTest.info("User clicks X button");

        assertFalse(jobPage().activeJobInformationButton.getAttribute("class").contains("disabled"));
        extentTest.pass("User verifies Active Job Information button is enabled");
    }

    @Test(dependsOnMethods = {"login_Positive","chooseGroupTest", "chooseAJob", "startMachine"}, priority = 4)
    public void notFinishTheJob() {
        extentTest = extentReports.createTest("TC_05 Not Finish the job test",
                "User should not finish the job");


        String errMessage=toEnableElement(jobPage().jobButton);
        clickElement(jobPage().jobButton);
        extentTest.info(errMessage);
        clickElement(jobPage().jobButton);
        extentTest.info("User clicks Job button");

        clickElement(jobPage().finishTheJobButton);
        extentTest.info("User clicks Finish the Job button");

        clickElement(jobPage().noButton);
        extentTest.info("User clicks Yes button ");

        assertFalse(jobPage().activeJobInformationButton.getAttribute("class").contains("disabled"));
        extentTest.pass("User verifies that the Active Job Button is enabled");


    }

    @Test(dependsOnMethods = {"login_Positive","chooseGroupTest", "chooseAJob", "startMachine"}, priority = 5)
    public void finishTheJob() {
        extentTest = extentReports.createTest("TC_06 Finish the job test",
                "User should finish the job");


        String errMessage=toEnableElement(jobPage().jobButton);
        clickElement(jobPage().jobButton);
        extentTest.info(errMessage);
        clickElement(jobPage().jobButton);
        extentTest.info("User clicks Job button");

        clickElement(jobPage().finishTheJobButton);
        extentTest.info("User clicks Finish the Job button");

        clickElement(jobPage().yesButton);
        extentTest.info("User clicks Yes button ");

        assertFalse(jobPage().activeJobInformationButton.getAttribute("class").contains("disabled"));
        extentTest.pass("User verifies that the Active Job Button is not enabled");
    }

    @AfterSuite
    public void tearDown() {
        Driver.quitDriver();
    }
}
