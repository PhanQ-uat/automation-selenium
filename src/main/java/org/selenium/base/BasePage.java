package org.selenium.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class BasePage {

    private final long longTimeout = 30;
    private final long shortTimeout = 3;

    public static BasePage getBasePageObject() {
        return new BasePage();
    }

    public void sleepInSecond(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void sleepInMiliSecond(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private String getDynamicLocator(String dynamicXpath, String... dynamicValues) {
        return String.format(dynamicXpath, (Object[]) dynamicValues);
    }
    public WebDriverWait setTimeoutExplicit(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(30));
    }


    public void setTimeoutImplicit(WebDriver driver, long longTimeoutImplicit) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(longTimeoutImplicit));
    }
    public WebElement waitForElementVisible(WebDriver driver, String xpathLocator) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
    }

    public WebElement waitForElementVisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(xpathLocator, dynamicValues))));
    }

    public List<WebElement> waitForAllElementVisible(WebDriver driver, String xpathLocator) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));

    }

    public List<WebElement> waitForAllElementVisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(getDynamicLocator(xpathLocator, dynamicValues))));
    }

    public boolean waitForElementInvisible(WebDriver driver, String xpathLocator) {
        setTimeoutImplicit(driver, shortTimeout);
        boolean status = setTimeoutExplicit(driver).until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
        setTimeoutImplicit(driver, longTimeout);
        return status;
    }

    public boolean waitForElementInvisible(WebDriver driver, String xpathLocator, String... dynamicValues) {
        setTimeoutImplicit(driver, shortTimeout);
        boolean status = setTimeoutExplicit(driver).until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(xpathLocator, dynamicValues))));
        setTimeoutImplicit(driver, longTimeout);
        return status;
    }

    public WebElement waitForElementClickable(WebDriver driver, String xpathLocator) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
    }

    public WebElement waitForElementClickable(WebDriver driver, String xpathLocator, String... dynamicValues) {
        return setTimeoutExplicit(driver).until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(xpathLocator, dynamicValues))));
    }

    public void clickToElement(WebDriver driver, String xpathLocator) {
        waitForElementClickable(driver, xpathLocator).click();
    }

    private By getByXpath(String xpathLocator) {
        return By.xpath(xpathLocator);
    }
}