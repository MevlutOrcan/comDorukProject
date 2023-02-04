package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Pages;
import utilities.ConfigReader;
import utilities.TestBaseCross;

import static org.testng.Assert.assertEquals;

public class LoginCross extends TestBaseCross {
    Pages pages = new Pages();

    @Test
    public void upperUsername() {
        System.out.println("TC 15 ");
        System.out.println("\n\n\n \t\t  ********* TEST REPORT  ********* \n");
        driver.get(ConfigReader.getProperty("URL"));
        System.out.println("User goes to App");

        driver.findElement(By.cssSelector("#userNamePlaceHolder")).sendKeys(ConfigReader.getProperty("UPPERCASEUSERNAME"));
        System.out.println("User enters valid username in uppercase");

        driver.findElement(By.cssSelector("#passwordPlaceHolder")).sendKeys(ConfigReader.getProperty("PASSWORD"));
        System.out.println("User enters valid password in uppercase");

        Select select=new Select(driver.findElement(By.cssSelector("#ddlModel")));
        select.selectByVisibleText(ConfigReader.getProperty("DEFAULTLANGUAGE"));
        System.out.println("User chooses "+ConfigReader.getProperty("DEFAULTLANGUAGE")+"");

        driver.findElement(By.cssSelector("#loginLabel")).click();
        System.out.println("User clicks enter button");

        Assert.assertTrue(driver.findElement(By.cssSelector(".kt-header__topbar-user")).isDisplayed());
        System.out.println("User verifies Hello messahe is visible");

        System.out.println("\n\t\t  *********  Test PASSED Successfully  ********* \n\n\n");
    }


}
