package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Browser;

import java.util.List;

public class HomePage {
    private final Logger logger = Logger.getLogger(HomePage.class);

    @FindBy(className = "navigation-bar__profile-account")
    private WebElement profileName;

    @FindBy(className = "autosuggest-textarea__textarea")
    private WebElement tootArea;

    @FindBy(className = "button--block")
    private WebElement tootButton;

    @FindBy(className = "status__content")
    private List<WebElement> tootsList;

    @FindBy(className = "media-gallery__item-thumbnail")
    private List<WebElement> imagesList;

    @FindBy(xpath = "//input[@type=\"file\"]")
    private WebElement inputFile;

    @FindBy(className = "compose-form__upload-thumbnail")
    private WebElement uploadedThumbnail;

    @FindBy(className = "character-counter")
    private WebElement charCounterField;

    public HomePage() {
        PageFactory.initElements(Browser.getDriver(), this);
    }

    public String getProfileNameText() {
        return Browser.getElementText(profileName);
    }

    public void writeToot(final String tootText) {
        Browser.writeText(tootArea, tootText);
    }

    public void pressTootButton() {
        Browser.clickElement(tootButton);
    }

    public boolean isTootButtonClickable() {
        return Browser.isElementEnabled(tootButton);
    }

    public String getUploadedImageHash() {
        return Browser.getUploadedImageHashCode(uploadedThumbnail);
    }

    public boolean isThumbnailImagePresent() {
        return Browser.isElementPresent(uploadedThumbnail);
    }

    public String getTootCharCount() {
        return Browser.getElementText(charCounterField);
    }

    public boolean isTootPresent(final String tootText) {
        for (int i = 0; i < tootsList.size(); i++) {
            final String text = Browser.getElementText(tootsList.get(i));
            logger.info(text);
            if (text.contains(tootText)) {
                return true;
            }
        }
        return false;
    }

    public boolean isImagePresentInToots(final String imageUrl) {
        for (int i = 0; i < imagesList.size(); i++) {
            final String imageHref = Browser.getElementHref(imagesList.get(i));
            logger.info(imageUrl);
            if (imageHref.contains(imageUrl)) {
                return true;
            }
        }
        return false;
    }

    public void uploadImage(final String imagePath) {
        logger.info("Uploading " + imagePath);
        Browser.injectJavaScript("document.querySelector('input[type=\"file\"]').style.display = 'block';");
        Browser.writeText(inputFile, imagePath);
        Browser.explicitWaitForElement(uploadedThumbnail, 15);
    }
}
