package org.selenium.factory.abstractFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class ChromeDriverManagerAbstract extends DriverManagerAbstract {
    @Override
    protected void startDriver() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Add essential Chrome options
        options.addArguments("--no-sandbox");  // Required for macOS/Linux
        options.addArguments("--disable-dev-shm-usage");  // Prevent memory issues
        options.addArguments("--start-maximized");  // Important for UI testing
        options.addArguments("--disable-gpu");  // Stability on some systems
        
        // Anti-detection options (most important)
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        
        // User agent to appear human (very important)
        options.addArguments("--user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/146.0.0.0 Safari/537.36");
        
        // Experimental options to prevent detection
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        // Suppress logging
        options.addArguments("--log-level=3");
        options.addArguments("--silent");

        try {
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        } catch (Exception e) {
            System.err.println("Failed to start Chrome driver: " + e.getMessage());
            throw new RuntimeException("Chrome browser not found or not properly installed. Please install Google Chrome.", e);
        }
    }
}
