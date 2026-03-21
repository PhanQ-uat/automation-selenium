package org.example;

import org.openqa.selenium.WebDriver;
import org.selenium.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BrowserLaunchTest extends BaseTest {

    @Test
    public void testChromeBrowserLaunch() {
        WebDriver driver = getDriver();
        
        // Verify browser is launched
        Assert.assertNotNull(driver, "Driver should not be null");
        
        // Navigate to a test page
        driver.get("https://www.google.com");
        
        // Verify page title
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Google"), "Page title should contain 'Google'");
        
        // Verify current URL
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("google.com"), "URL should contain 'google.com'");
        
        System.out.println("Browser launched successfully!");
        System.out.println("Title: " + title);
        System.out.println("URL: " + currentUrl);
    }

}
