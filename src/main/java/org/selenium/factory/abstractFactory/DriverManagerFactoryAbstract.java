package org.selenium.factory.abstractFactory;

import org.selenium.constants.DriverType;

public class DriverManagerFactoryAbstract {
    public static DriverManagerAbstract getManager(DriverType driverType) {
            switch (driverType){
                case CHROME -> {
                    return new ChromeDriverManagerAbstract();
                }
                default -> {
                    throw new IllegalArgumentException("Driver type not supported: " + driverType);
                }
            }
    }

}
