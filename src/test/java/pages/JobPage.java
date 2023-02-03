package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class JobPage {
    public JobPage() {// constractor olusturup public yapmamiz gerekir
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//div[.='Job' and @class=\"kt-portlet\"]")
    public WebElement jobButton;

    @FindBy(xpath = "//*[@class=\"col-6 col-md-3 disabled\" and .='Job']")
    public List<WebElement> pasiveJobButtonList;

     @FindBy(xpath = "//*[@class=\"dx-template-wrapper dx-button-content\" and .=' Select from List ']")
    public WebElement selectFromListButton;

    @FindBy(xpath = "//*[@class=\"dx-template-wrapper dx-button-content\" and .=' Select from Work Order ']")
    public WebElement selectFromWorkOrderButton;

    @FindBy(xpath = "//*[(@autocomplete='off' and @aria-describedby='dx-col-51') or (@autocomplete='off' and @aria-describedby='dx-col-64')]")
    public WebElement orderReferenceNoSearchBox;

    @FindBy(css = "[class=\"dx-radiobutton-icon\"]")
    public List<WebElement> radioButtonList;

    @FindBy(css = "[aria-label=\"Continue\"]")
    public WebElement continueButton;

    @FindBy(xpath = "//*[@class=\"dx-template-wrapper dx-button-content\" and .=' Start ']")
    public WebElement startButton;

    @FindBy(xpath = "//*[@id=\"swal2-title\" and .='Success']")
    public WebElement successMessage;

    @FindBy(xpath = "//*[@class=\"swal2-confirm swal2-styled\"]")
    public WebElement okButton;

    @FindBy(xpath = "//div[@id='activeJobListButton']/parent::div")
    public WebElement activeJobInformationButton;

    @FindBy(css = ".col-6.col-md-2.disabled>#activeJobListButton")
    public List<WebElement> notEnabledActiveJobInformationButtonList;



    @FindBy(xpath = "//div[@class=\"row\"]/div[@class=\"col\" and .=' Machine ']//following-sibling::div")
    public WebElement machineName;

    @FindBy(xpath = "//div[@class=\"row\"]/div[@class=\"col\" and .=' Order Reference No ']//following-sibling::div")
    public WebElement orderReferenceNuber;

    @FindBy(xpath = "//div[@class=\"row\"]/div[@class=\"col\" and .=' Order Op  ID ']//following-sibling::div")
    public WebElement orderOpId;

    @FindBy(xpath = "//div[@class=\"row\"]/div[@class=\"col\" and .=' Plan Quantity (total/rem ) ']//following-sibling::div")
    public WebElement planQuantity;

    @FindBy(xpath = "//div[@class=\"row\"]/div[@class=\"col\" and .=' Speed ']//following-sibling::div")
    public WebElement speed;

    @FindBy(xpath = "(//*[@class=\"dx-icon dx-icon-close\"])[2]")
    public WebElement xButton;

    @FindBy(xpath = "//div[@class=\"dx-template-wrapper dx-button-content\" and .=' Finish the Job ']")
    public WebElement finishTheJobButton;

    @FindBy(xpath = "//*[@type=\"button\" and .='No']")
    public WebElement noButton;

    @FindBy(xpath = "//*[@type=\"button\" and .='Yes']")
    public WebElement yesButton;




}
