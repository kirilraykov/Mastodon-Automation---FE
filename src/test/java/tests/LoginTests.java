package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.Browser;

public class LoginTests {
    private LoginPage loginPage;
    private HomePage homePage;

    @Before
    public void setup() {
        loginPage = new LoginPage();
        homePage = new HomePage();
        loginPage.visit();
    }

    @Test
    public void validLogin() {
        loginPage.login("kiril.raykov@scalefocus.com", "insecure");
        Assert.assertTrue(homePage.getProfileNameText().contains("kraykov"));
    }

    @Test
    public void wrongPasswordLogin() {
        loginPage.login("kiril.raykov@scalefocus.com", "sadasd");
        Assert.assertTrue(loginPage.getFlashMessage().contains("Invalid"));
    }

    @Test
    public void emptyPasswordLogin() {
        loginPage.login("kiril.raykov@scalefocus.com", "");
        Assert.assertTrue(loginPage.getFlashMessage().contains("Invalid"));
    }

    @After
    public void teardown() {
        Browser.closeBrowser();
    }
}
