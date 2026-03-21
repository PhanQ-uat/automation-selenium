package org.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.selenium.constants.DriverType;
import org.selenium.factory.abstractFactory.DriverManagerAbstract;
import org.selenium.factory.abstractFactory.DriverManagerFactoryAbstract;
import org.testng.ITestResult;
import org.testng.annotations.*;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser) {
        browser = System.getProperty("browser", browser);
        if (browser == null) browser = "CHROME";
        setDriverManager(DriverManagerFactoryAbstract.
                getManager(DriverType.valueOf(browser)));
        setDriver(getDriverManager().getDriver());
        logger.info("Current Thread: {}, DRIVER = {}", Thread.currentThread().getId(), getDriver());
    }

    @Parameters("browser")
    @AfterMethod
    public synchronized void quitDriver(@Optional String browser, ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}.{}", result.getTestClass().getRealClass().getSimpleName(), result.getMethod().getMethodName());
            File destFile = new File("screenshots" + File.separator + browser + File.separator +
                    result.getTestClass().getRealClass().getSimpleName() + "_" + result.getMethod().getMethodName() + ".png");
//            takeScreenshot(destFile);
            takeScreenshotUsingAshot(destFile);
            logger.info("Screenshot saved to: {}", destFile.getAbsolutePath());
        }
        getDriverManager().getDriver().quit();
        logger.info("Driver quit successfully");
    }

    @AfterClass
    public void tearDownClass() {
        WebDriverManager.chromedriver().clearDriverCache();
    }

    protected DriverManagerAbstract getDriverManager() {
        return this.driverManager.get();
    }

    private void setDriverManager(DriverManagerAbstract driverManager) {
        this.driverManager.set(driverManager);
    }

    protected WebDriver getDriver() {
        return this.driver.get();
    }

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    private void takeScreenshotUsingAshot(File destFile) {
        logger.debug("Taking screenshot using AShot");
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());
        try {
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
            logger.debug("Screenshot successfully written to file");
        } catch (IOException e) {
            logger.error("Failed to save screenshot", e);
        }
    }
}
