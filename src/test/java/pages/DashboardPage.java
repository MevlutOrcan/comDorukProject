package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class DashboardPage {
    public DashboardPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(className = "pm-color-gray")
    public WebElement helloButton;

    @FindBy(css = ".kt-header__topbar-user")
    public WebElement helloTextButton;

    @FindBy(xpath = "//*[@class=\"kt-notification__item-title \"]")
    public WebElement signOutButton;

    @FindBy(css = ".dx-button-text")
    public WebElement enterButtonAfterSignOut;



    @FindBy(css = "[class=\"kt-menu__link-icon pmi icon-dasboard\"]")
    public WebElement dashboardIcon;

    @FindBy(css = "[class=\"kt-menu__link-icon fas fa-desktop\"]")
    public WebElement ipcButton;

    @FindBy(xpath = "//tr/td[3]//input")
    public WebElement searchBoxUnderTheName;

    @FindBy(xpath = "(//tbody)[3]/tr/td[3]")
    public List<WebElement> groupNamesList;

    @FindBy(css = ".kt-margin-b-0.kt-valign-middle.kt-block-inline")
    public WebElement machineNameText;


}
