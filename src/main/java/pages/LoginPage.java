package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

public class LoginPage {
    private final Logger logger = Logger.getLogger(LoginPage.class);

    @FindBy(id = "user_email")
    private WebElement usernameField;

    @FindBy(id = "user_password")
    private WebElement passwordField;

    @FindBy(className = "btn")
    private WebElement loginButton;

    @FindBy(className = "flash-message")
    private WebElement flashMessage;


    public LoginPage() {
        PageFactory.initElements(Browser.getDriver(), this);
    }

    public String getFlashMessage() {
        return Browser.getElementText(flashMessage);
    }

    public void visit() {
        logger.info("Visiting login page");
        Browser.navigateToUrl("https://mastodon.social/auth/sign_in");
    }

    public void login(final String username, final String password) {
        logger.info("Login as " + username);
        Browser.writeText(usernameField, username);
        Browser.writeText(passwordField, password);
        Browser.clickElement(loginButton);
    }
}
