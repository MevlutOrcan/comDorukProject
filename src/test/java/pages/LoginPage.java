package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class LoginPage {
    public LoginPage() {// constractor olusturup public yapmamiz gerekir
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "userNamePlaceHolder")
    public WebElement usernameTextBox;

    @FindBy(id = "passwordPlaceHolder")
    public WebElement passwordTextBox;

    @FindBy(id = "loginLabel")
    public WebElement enterButton;

    @FindBy(id = "ddlModel")
    public WebElement languageDropDown;

    @FindBy(css = ".alert.pm-alert-box")
    public WebElement alertText;


}
