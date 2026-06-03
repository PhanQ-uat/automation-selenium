package org.selenium.utils;

import java.io.Serial;

public class StepFailedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8055522483052319788L;

    public StepFailedException(String message) {
        super(message);
    }

    public StepFailedException(String message, Throwable e) {
        super(message, e);
    }

    public StepFailedException(Throwable e) {
        super(e);
    }

}
