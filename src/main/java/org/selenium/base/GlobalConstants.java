package org.selenium.base;

import lombok.Getter;

@Getter
public class GlobalConstants {

    // Bill Pugh Singleton Implementation
    private GlobalConstants() {

    }

    public static GlobalConstants getGlobalConstant() {
        return SingletonHelper.GLOBAL_CONSTANT_INSTANCE;
    }

    private static class SingletonHelper {
        private static final GlobalConstants GLOBAL_CONSTANT_INSTANCE = new GlobalConstants();
    }

    // Double Check Locking Singleton
//	private static volatile GlobalConstants globalConstantInstance;
//
//	private GlobalConstants() {
//
//	}
//
//	public static GlobalConstants getGlobalConstant() {
//		if (globalConstantInstance == null) {
//			synchronized (GlobalConstants.class) {
//				if (globalConstantInstance == null) {
//					globalConstantInstance = new GlobalConstants();
//				}
//			}
//		}
//		return globalConstantInstance;
//	}

    private final long longTimeout = 30;
    private final long shortTimeout = 3;

}