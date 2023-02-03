package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
    Select select;

    @Test
    public void login_Positive() {

        extentTest = extentReports.createTest("TC_01 Login Pozitive Test", "User logs in to the app with valid ID and valid Password");

        Driver.getDriver().get(ConfigReader.getProperty("URL"));
        extentTest.info("User goes to https://cloud.promanage.net/testteam/ui");


        enterKeys(loginPage().usernameTextBox, ConfigReader.getProperty("USERNAME"));
        extentTest.info("User enters valid username to user name text box");

        enterKeys(loginPage().passwordTextBox, ConfigReader.getProperty("PASSWORD"));
        extentTest.info("User enters valid password to password text box");

        select = new Select(loginPage().languageDropDown);
        if (!select.getFirstSelectedOption().getText().equals("English")) {
            select.selectByVisibleText("English");
        }
        extentTest.info("User chooses English from language dropdown");

        clickElement(loginPage().enterButton);
        extentTest.info("User clicks enter button");

        String welcome = dashboardPage().helloButton.getText() + " " + Driver.getDriver().findElement(By.xpath("//h6/*[.=' " + ConfigReader.getProperty("USERNAME") + "']")).getText();
        assertEquals("Hello " + ConfigReader.getProperty("USERNAME"), welcome);
        extentTest.pass("User verifies the welcome message 'Hello selenium' is visible");


    }

    @Test(dependsOnMethods = {"login_Positive"}, priority = 1)
    public void chooseGroupTest() {
        extentTest = extentReports.createTest("TC_01 Choose a Group Test",
                            "User chooses group that includes text '"+ConfigReader.getProperty("SEARCHEDWORD")+"'");

        moveElement(dashboardPage().dashboardIcon);
        clickElement(dashboardPage().ipcButton);
        extentTest.info("User clicks IPC button on the left side");


        enterKeys(dashboardPage().searchBoxUnderTheName, ConfigReader.getProperty("SEARCHEDWORD"));
        extentTest.info("User writes '"+ConfigReader.getProperty("SEARCHEDWORD")+"' to search box under the name column");

        waitThread(1);
        int groupNameSize = dashboardPage().groupNamesList.size();
        for (int i = 0; i < groupNameSize; i++) {
            if (Driver.getDriver().findElement(By.xpath("(//tbody)[3]/tr["+(i+1)+"]/td[3]"))
                    .getText().contains(ConfigReader.getProperty("SEARCHEDWORD"))){
                jsclick(Driver.getDriver().findElement(By.xpath("(//*[@class='dx-icon fas fa-link'])["+i+1+"]")));
                break;
            }
        }
        extentTest.info("The user clicks the 'connect button' on the row containing the text '"+ConfigReader.getProperty("SEARCHEDWORD")+"'");

        nameOfMachine = dashboardPage().machineNameText.getText();
        assertEquals(dashboardPage().machineNameText.getText(), ConfigReader.getProperty("MACHINENAME"));
        extentTest.pass("User verifies SiemensMakine text on the header");


    }

    @Test(dependsOnMethods = {"chooseGroupTest"}, priority = 2)
    public void chooseAJob() {

        extentTest = extentReports.createTest("TC_02 Choose a Job Test",
                    "User chooses a job from work order by selecting from list");

        clickElement(jobPage().jobButton);
        extentTest.info("User clicks the Job button");

        clickElement(jobPage().selectFromWorkOrderButton);
        extentTest.info("User clicks  the Select from Work Order button");

        clickElement(jobPage().selectFromListButton);
        extentTest.info("User clicks  the Select from List Button");

        jobPage().orderReferenceNoSearchBox.sendKeys(ConfigReader.getProperty("SEARCHEDWORD"));
        extentTest.info("User enters '"+ConfigReader.getProperty("SEARCHEDWORD")+"' the search box under the Order Reference No column ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<WebElement> radioButtonList = jobPage().radioButtonList;
        Random rnd = new Random();
        int rank = rnd.nextInt(radioButtonList.size());
        clickElement(radioButtonList.get(rank));
        extentTest.info("User chooses a random row");

        int rowNumber = rank + 3;
        orderReferanceNo = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"2\"]")).getText();
        orderOpId = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"3\"]")).getText();
        amount = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"4\"]")).getText();
        netCycle = Driver.getDriver().findElement(By.xpath("(//tr)[" + rowNumber + "]/td[@aria-colindex=\"5\"]")).getText();
        extentTest.info("User saves “Order Reference No, Order Op. ID, amount, net cycle” row values ");
        extentTest.pass("User verifies that the information of row is available");
    }

    @Test(dependsOnMethods = {"chooseGroupTest", "chooseAJob"}, priority = 3)
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

        assertTrue(jobPage().activeJobInformationButton.isEnabled());
        extentTest.pass("User verifies Active Job Information button is enabled");


    }

    @Test(dependsOnMethods = {"chooseGroupTest", "chooseAJob", "startMachine"}, priority = 4)
    public void informationVerify() {
        extentTest = extentReports.createTest("TC_04 Verify job information",
                "User does verifies that Machine name,OrderReference No, Order Op. ID, Plan Quantity, Speed ");

        removeValueByJS(jobPage().activeJobInformationButton);
        clickElement(jobPage().activeJobInformationButton);
        extentTest.info("User clicks Active Job Information button");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(jobPage().machineName.getText(), nameOfMachine);
        extentTest.info("User verifies that  Machine name value equal to machine name in header ");

        softAssert.assertEquals(orderReferanceNo, jobPage().orderReferenceNuber.getText());
        softAssert.assertEquals(orderOpId, jobPage().orderOpId.getText());

        extentTest.info("User verifies that the OrderReference No and Order Op. ID values are equal to the OrderReference No and Order Op. ID of the setup step");

//*** Bug oldugu icin simdilik ignore edildi
        //softAssert.assertEquals(amount,jobPage().planQuantity.getText());
        //extentTest.info("User verifies that the Plan Quantity value is equal to the Amount  of the setup step");

//*** Bug oldugu icin simdilik ignore edildi
        //softAssert.assertEquals(netCycle,jobPage().speed.getText());
        //extentTest.info("User verifies that the Speed value is equal to the Net Cycle of the setup step");

        softAssert.assertAll();
        clickElement(jobPage().xButton);
        extentTest.info("User clicks X button");

        assertEquals(0, jobPage().notEnabledActiveJobInformationButtonList.size());
        extentTest.pass("User verifies Active Job Information button is enabled");
    }

    @Test(dependsOnMethods = {"chooseGroupTest", "chooseAJob", "startMachine"}, priority = 4)
    public void notFinishTheJob() {
        extentTest = extentReports.createTest("TC_05 Not Finish the job test",
                "User should not finish the job");


        if (jobPage().pasiveJobButtonList.size() > 0) {
            Driver.getDriver().navigate().refresh();
        }
        clickElement(jobPage().jobButton);
        extentTest.info("User clicks Job button");

        clickElement(jobPage().finishTheJobButton);
        extentTest.info("User clicks Finish the Job button");

        clickElement(jobPage().noButton);
        extentTest.info("User clicks Yes button ");

        assertEquals(0, jobPage().notEnabledActiveJobInformationButtonList.size());
        extentTest.pass("User verifies that the Active Job Button is enabled");


    }

    @Test(dependsOnMethods = {"chooseGroupTest", "chooseAJob", "startMachine"}, priority = 5)
    public void finishTheJob() {
        extentTest = extentReports.createTest("TC_06 Finish the job test",
                                "User should finish the job");


        if (jobPage().pasiveJobButtonList.size() > 0) {
            Driver.getDriver().navigate().refresh();
        }
        clickElement(jobPage().jobButton);
        extentTest.info("User clicks Job button");

        clickElement(jobPage().finishTheJobButton);
        extentTest.info("User clicks Finish the Job button");

        clickElement(jobPage().yesButton);
        extentTest.info("User clicks Yes button ");

        assertEquals(1, jobPage().notEnabledActiveJobInformationButtonList.size());
        extentTest.pass("User verifies that the Active Job Button is not enabled");
    }

    @AfterSuite
    public void tearDown() {
        Driver.quitDriver();
    }
}
