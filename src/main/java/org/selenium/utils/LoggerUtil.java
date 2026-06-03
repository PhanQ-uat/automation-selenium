package org.selenium.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

public class LoggerUtil {
    private static final Deque<String> testStepStack = new LinkedList<>();
    private static final Logger logger =  LogManager.getLogger(LoggerUtil.class);

    private String formatMessage(String msg, String msgType) {
        return String.format("%s: %s", msgType, msg);
    }

    public static void stopStep(boolean testMethodFailed) {
        if (!testStepStack.isEmpty()) {
            String lastStepId = testStepStack.pop();
            if (testMethodFailed){
                Allure.getLifecycle().updateStep(lastStepId, stepResult -> stepResult.setStatus(Status.FAILED));
            } else{
                Allure.getLifecycle().updateStep(lastStepId, stepResult -> stepResult.setStatus(Status.PASSED));
            }
            Allure.getLifecycle().stopStep(lastStepId);
        }
    }

    public static void stopStep() {
        stopStep(false);
    }

    public void step(String stepTitle) {
        logStep(stepTitle);

        stopStep();
        String stepId = UUID.randomUUID().toString();
        Allure.getLifecycle().startStep(stepId, new StepResult().setName(stepTitle));
        Allure.getLifecycle().updateStep(stepId, stepResult -> stepResult.setStatus(Status.FAILED));
        testStepStack.push(stepId);
    }

    public void step(String stepTitle, Object... args) {
        step(String.format(stepTitle, args));
    }

    private void logStep(String msg) {
        String formattedMsg = formatMessage(msg, "Description");
        logger.debug(formattedMsg);
    }

    public void trace(String msg) {
        String formattedMsg = formatMessage(msg, "TRACE");
        logger.trace(formattedMsg);
//        Allure.addAttachment("TRACE", formattedMsg);
    }

    public void trace(String msg, Object... args) {
        trace(String.format(msg, args));
    }

    public void debug(String msg) {
        String formattedMsg = formatMessage(msg, "DEBUG");
        logger.debug(formattedMsg);
//        Allure.addAttachment("DEBUG", formattedMsg);
    }

    public void debug(String msg, Object... args) {
        debug(String.format(msg, args));
    }

    public void info(String msg) {
        String formattedMsg = formatMessage(msg, "INFO");
        logger.info(formattedMsg);
        Allure.step(formattedMsg);
    }

    public void info(String msg, Object... args) {
        info(String.format(msg, args));
    }

    public void warn(String msg) {
        String formattedMsg = formatMessage(msg, "WARN");
        logger.warn(formattedMsg);
        Allure.addAttachment("WARN", formattedMsg);
    }

    public void warn(String msg, Object... args) {
        warn(String.format(msg, args));
    }

    public void error(String msg) {
        String formattedMsg = formatMessage(msg, "ERROR");
        logger.error(formattedMsg);
        Allure.addAttachment("ERROR", formattedMsg);
    }

    public void error(String msg, Object... args) {
        error(String.format(msg, args));
    }

    public void passed(String msg) {
        String formattedMsg = formatMessage(msg, "PASSED");
        logger.info(formattedMsg);
        Allure.step(formattedMsg);
    }

    public void passed(String msg, Object... args) {
        passed(String.format(msg, args));
    }

    public void failed(String msg) {
        String formattedMsg = formatMessage(msg, "FAILED");
        logger.error(formattedMsg);
        throw new StepFailedException(msg);
    }

    public void failed(String msg, Object... args) {
        failed(String.format(msg, args));
    }

//    public void failedAndStop(String msg) {
//        String formattedMsg = formatMessage(msg, "FAILED");
//        logger.error(formattedMsg);
//        Allure.step(formattedMsg, () -> {
//            Assert.fail(msg);
//        });
//    }
//
//    public void failedAndStop(String msg, Object... args) {
//        failedAndStop(String.format(msg, args));
//    }

    public void objectPassed(String msg) {
        String formattedMsg = formatMessage(msg, "PASSED");
        logger.info(formattedMsg);
    }

    public void objectPassed(String msg, Object... args) {
        objectPassed(String.format(msg, args));
    }

    public void objectInfo(String msg) {
        String formattedMsg = formatMessage(msg, "INFO");
        logger.info(formattedMsg);
    }

    public void objectInfo(String msg, Object... args) {
        objectInfo(String.format(msg, args));
    }

    public void objectWarning(String msg) {
        String formattedMsg = formatMessage(msg, "WARNING");
        logger.warn(formattedMsg);
    }

    public void objectWarning(String msg, Object... args) {
        objectWarning(String.format(msg, args));
    }
}
