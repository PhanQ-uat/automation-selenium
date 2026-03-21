package org.selenium.base;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger = LoggerFactory.getLogger(BasePage.class);

    private static final int TIMEOUT = 10;
    private static final int RETRY = 2;

    protected WebDriverWait waitLong;
    protected WebDriverWait waitShort;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitShort = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ========================= CLICK =========================
    public void click(By locator) {
        executeWithRetry("CLICK", locator, () -> {
            WebElement element = waitUntilClickable(locator);
            element.click();
        });
    }

    // ========================= SEND KEYS =========================
    public void sendKeys(By locator, String text) {
        executeWithRetry("SEND_KEYS", locator, () -> {
            WebElement element = waitUntilVisible(locator);
            element.clear();
            element.sendKeys(text);
        });
    }

    // ========================= GET TEXT =========================
    public String getText(By locator) {
        WebElement element = waitUntilVisible(locator);
        return element.getText();
    }

    // ========================= IS DISPLAYED =========================
    public boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false; // expected
        }
    }

    // ========================= CORE RETRY =========================
    private void executeWithRetry(String actionName, By locator, Runnable action) {
        int attempts = 0;

        while (attempts <= RETRY) {
            try {
                logger.info("[" + actionName + "] Attempt " + (attempts + 1) + " on: " + locator);
                action.run();
                logger.info("[" + actionName + "] SUCCESS: " + locator);
                return;
            } catch (StaleElementReferenceException | ElementClickInterceptedException e) {
                logger.warn("[" + actionName + "] Retry due to: " + e.getClass().getSimpleName());
                attempts++;
                if (attempts > RETRY) {
                    handleFailure(actionName, locator, e);
                }

            } catch (Exception e) {
                handleFailure(actionName, locator, e);
            }
        }
    }

    // ========================= FAILURE HANDLER =========================
    private void handleFailure(String actionName, By locator, Exception e) {
        logger.error("[" + actionName + "] FAILED on: " + locator);
        captureScreenshot();
        attachToAllure("Failure Screenshot");

        aiDebug(actionName, locator, e);

        throw new RuntimeException(actionName + " failed on: " + locator, e);
    }

    // ========================= WAIT =========================
    private WebElement waitUntilVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ========================= SCREENSHOT =========================
    private void captureScreenshot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // save file logic
    }

    // ========================= ALLURE =========================
    private void attachToAllure(String name) {
        Allure.addAttachment(name, new ByteArrayInputStream(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
        ));
    }

    // ========================= AI DEBUG =========================
    private void aiDebug(String action, By locator, Exception e) {
        logger.info("===== AI DEBUG START =====");

        String dom = driver.getPageSource();
        String error = e.getMessage();

        // giả lập gửi AI (sau này integrate OpenAI API)
        logger.info("Action: " + action);
        logger.info("Locator: " + locator);
        logger.info("Error: " + error);
        logger.info("DOM length: " + dom.length());

        logger.info("===== AI DEBUG END =====");
    }
}
