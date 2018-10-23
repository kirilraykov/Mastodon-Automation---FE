package tests;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class LandingTests {

    @Test
    public void checkTitle() {
        System.setProperty("webdriver.chrome.driver", "src//main//resources//chromedriver");

        WebDriver driver = new ChromeDriver();
        driver.get("https://mastodon.social/");
        String title = driver.getTitle();

        assertEquals("mastodon.social - Mastodon", title);

        driver.close();
    }
}
