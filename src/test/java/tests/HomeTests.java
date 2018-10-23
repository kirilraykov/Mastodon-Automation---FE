package tests;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.Browser;

import java.util.UUID;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HomeTests {

    private static final String testImagePath = "/mastodon/src/main/resources/dog1.jpeg";
    private final Logger logger = Logger.getLogger(HomeTests.class);

    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void setup() {
        loginPage = new LoginPage();
        homePage = new HomePage();

        loginPage.visit();
        loginPage.login("kiril.raykov@scalefocus.com", "insecure");
    }

    @Test
    //posts random string and asserts it's present in toots section
    public void createTootValid() {
        final String uuid = UUID.randomUUID().toString();

        homePage.writeToot(uuid);
        homePage.pressTootButton();

        Browser.threadSleep(2000);

        assertTrue(homePage.isTootPresent(uuid));
    }

    @Test
    //uploads image, asserts its in the toot field and then in toots section
    public void uploadImageAssertSameImageShown() {
        homePage.uploadImage(testImagePath);

        Browser.threadSleep(3000);

        assertTrue(homePage.isThumbnailImagePresent());

        final String uploadedImageHash = homePage.getUploadedImageHash();

        homePage.pressTootButton();

        Browser.threadSleep(3000);

        assertTrue(homePage.isImagePresentInToots(uploadedImageHash));
    }

    /*  asserting the toot char counter works by:
        1. Writing toot with half the size of the max toot and asserting the toot button is clickable
        2. Writing toot with one char less then the max toot and asserting toot btn is clickable(boundary value)
        3. Writing toot with 100 more chars than max toot and asserting toot btn is NOT clickable
      */
    @Test
    public void verifyCharCounterUpdateAndLimit() {
        final int maxTootSize = parseInt(homePage.getTootCharCount());

        int currentTootSize = maxTootSize / 2;
        String randomStringSize = randomAlphabetic(currentTootSize);

        homePage.writeToot(randomStringSize);
        assertTrue(parseInt(homePage.getTootCharCount()) == maxTootSize - currentTootSize);
        assertTrue(homePage.isTootButtonClickable());

        currentTootSize = maxTootSize - (maxTootSize - 1);
        randomStringSize = randomAlphabetic(currentTootSize);

        homePage.writeToot(randomStringSize);
        assertTrue(parseInt(homePage.getTootCharCount()) == maxTootSize - currentTootSize);
        assertTrue(homePage.isTootButtonClickable());

        currentTootSize = maxTootSize + 100;
        randomStringSize = randomAlphabetic(currentTootSize);

        homePage.writeToot(randomStringSize);
        assertTrue(parseInt(homePage.getTootCharCount()) == maxTootSize - currentTootSize);

        assertFalse(homePage.isTootButtonClickable());
    }

    @Test
    public void verifyEditDisplayName() {

    }


    @After
    public void tearDown() {
        Browser.closeBrowser();
    }
}
