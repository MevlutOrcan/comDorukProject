package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.TestBaseCross;



public class LoginCross extends TestBaseCross {
    @Test
    public void upperUsername() {
        System.err.println("TC_18 Cross Browser Login with valid Username in Uppercase");
        System.err.println("\n\n\n \t\t  ********* TEST REPORT  ********* \n");
        driver.get(ConfigReader.getProperty("URL"));
        System.err.println("User goes to App");

        driver.findElement(By.cssSelector("#userNamePlaceHolder")).sendKeys(ConfigReader.getProperty("UPPERCASEUSERNAME"));
        System.err.println("User enters valid username in uppercase");

        driver.findElement(By.cssSelector("#passwordPlaceHolder")).sendKeys(ConfigReader.getProperty("VALIDPASSWORD"));
        System.err.println("User enters valid password in uppercase");

        Select select=new Select(driver.findElement(By.cssSelector("#ddlModel")));
        select.selectByVisibleText(ConfigReader.getProperty("DEFAULTLANGUAGE"));
        System.err.println("User chooses "+ConfigReader.getProperty("DEFAULTLANGUAGE")+"");

        driver.findElement(By.cssSelector("#loginLabel")).click();
        System.err.println("User clicks enter button");

        Assert.assertTrue(driver.findElement(By.cssSelector(".kt-header__topbar-user")).isDisplayed());
        System.err.println("User verifies "+ConfigReader.getProperty("DEFAULTWELCOME")+" message is visible");

        System.err.println("\n\t\t  *********  Test PASSED Successfully  ********* \n\n\n");
    }


}
