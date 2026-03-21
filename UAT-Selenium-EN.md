# UAT Selenium Framework - Chrome Driver Guide

## 📋 Table of Contents
- Framework Overview
- Chrome Driver Structure
- Usage Guide
- Important Options

---

## 🏗️ Chrome Framework Structure

### **1. Abstract Factory Pattern**
```
DriverManagerAbstract (Abstract Class)
├── ChromeDriverManagerAbstract (Implementation)
├── SafariDriverManagerAbstract (Implementation)
└── DriverManagerFactoryAbstract (Factory)
```

### **2. Test Base Class**
```
BaseTest
├── ThreadLocal<DriverManagerAbstract> driverManager
├── ThreadLocal<WebDriver> driver
├── @BeforeMethod setup
├── @AfterMethod cleanup
└── Screenshot on failure
```

---

## 🚀 Chrome Driver Flow

### **Startup Process:**

1. **WebDriverManager Setup**
```java
WebDriverManager.chromedriver().setup();
```
- Automatically downloads ChromeDriver
- Manages version compatibility
- Sets up driver binary

2. **Chrome Options Configuration**
```java
ChromeOptions options = new ChromeOptions();
// Essential options
options.addArguments("--no-sandbox");           // Required for macOS/Linux
options.addArguments("--disable-dev-shm-usage");    // Prevent memory issues
options.addArguments("--start-maximized");          // UI testing
options.addArguments("--disable-gpu");              // Stability

// Anti-detection (most important)
options.addArguments("--disable-blink-features=AutomationControlled");
options.addArguments("--disable-extensions");
options.addArguments("--user-agent=Mozilla/5.0...");  // Fake human user
```

3. **Driver Creation**
```java
driver = new ChromeDriver(options);
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

---

## 🔧 How Each Component Works

### **DriverManagerAbstract.java**
```java
public abstract class DriverManagerAbstract {
    protected WebDriver driver;
    
    // Template method for all drivers
    protected abstract void startDriver();
    
    // Singleton pattern per thread
    public WebDriver getDriver() {
        if (driver == null) {
            startDriver();  // Call specific implementation
        }
        return driver;
    }
    
    public void closeDriver() {
        driver.quit();
    }
}
```

### **ChromeDriverManagerAbstract.java**
```java
public class ChromeDriverManagerAbstract extends DriverManagerAbstract {
    @Override
    protected void startDriver() {
        // 1. Setup WebDriverManager
        WebDriverManager.chromedriver().setup();
        
        // 2. Configure options
        ChromeOptions options = new ChromeOptions();
        // ... add arguments ...
        
        // 3. Create driver instance
        driver = new ChromeDriver(options);
        
        // 4. Configure driver behavior
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
```

### **DriverManagerFactoryAbstract.java**
```java
public class DriverManagerFactoryAbstract {
    public static DriverManagerAbstract getManager(DriverType driverType) {
        switch (driverType) {
            case CHROME -> new ChromeDriverManagerAbstract();
            case SAFARI -> new SafariDriverManagerAbstract();
            default -> throw new IllegalArgumentException();
        }
    }
}
```

### **BaseTest.java**
```java
public class BaseTest {
    // Thread-safe for parallel execution
    protected ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    @BeforeMethod
    public void startDriver(@Optional String browser) {
        // 1. Get browser type (default: CHROME)
        browser = (browser == null) ? "CHROME" : browser;
        
        // 2. Create driver manager
        setDriverManager(DriverManagerFactoryAbstract
            .getManager(DriverType.valueOf(browser)));
        
        // 3. Initialize driver
        setDriver(getDriverManager().getDriver());
    }
    
    @AfterMethod
    public void quitDriver(ITestResult result) {
        // 1. Take screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            takeScreenshotUsingAshot(destFile);
        }
        
        // 2. Cleanup driver
        getDriverManager().closeDriver();
    }
}
```

---

## 🎯 Chrome Options Detailed Explanation

### **Essential Options (Required)**
```java
--no-sandbox              // Required for macOS/Linux
--disable-dev-shm-usage     // Prevent /dev/shm issues
--start-maximized           // Important for UI testing
--disable-gpu              // Stability on some systems
```

### **Anti-Detection Options (Against bot detection)**
```java
--disable-blink-features=AutomationControlled  // Disable automation indicators
--disable-extensions                     // Avoid conflicts
--disable-infobars                      // Hide info bars
--no-first-run                         // Skip setup screens
--user-agent=Mozilla/5.0...             // Fake real human user
```

### **Experimental Options**
```java
excludeSwitches: ["enable-automation"]    // Hide automation flags
useAutomationExtension: false               // Disable automation extension
```

---

## 🧪 Test Execution Flow

### **1. Test Initialization**
```
@Test class extends BaseTest
├── @BeforeMethod calls startDriver()
├── DriverManagerFactory creates ChromeDriverManager
├── ChromeDriverManager.startDriver() is called
└── WebDriver instance is created
```

### **2. Test Execution**
```java
@Test
public void testSearch() {
    WebDriver driver = getDriver();  // Get driver from ThreadLocal
    
    // Perform test steps...
    driver.get("https://google.com");
    driver.findElement(By.name("q")).sendKeys("search term");
    // ...
}
```

### **3. Test Cleanup**
```
@Test method completes
├── @AfterMethod is called
├── Screenshot taken if failure
├── Driver.quit() is called
└── ThreadLocal is cleaned up
```

---

## 🔍 Thread Safety

### **ThreadLocal Implementation**
```java
// Each thread has separate driver
protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

// Thread-safe getter
protected WebDriver getDriver() {
    return this.driver.get();  // Get current thread's driver
}

// Thread-safe setter
private void setDriver(WebDriver driver) {
    this.driver.set(driver);  // Set driver for current thread
}
```

### **Parallel Execution Support**
- Each test thread = separate browser instance
- No conflicts between tests
- Scalable for parallel testing

---

## 📸 Screenshot Mechanism

### **AShot Integration**
```java
private void takeScreenshotUsingAshot(File destFile) {
    Screenshot screenshot = new AShot()
        .shootingStrategy(ShootingStrategies.viewportPasting(100))
        .takeScreenshot(getDriver());
    
    ImageIO.write(screenshot.getImage(), "PNG", destFile);
}
```

- **Full page screenshot** (viewport pasting)
- **High quality** images
- **Automatic on failure**
- **Organized by browser & test method

---

## 🚀 How to Use Framework

### **1. Run Single Test**
```bash
mvn test -Dtest=BrowserLaunchTest#testChromeBrowserLaunch
```

### **2. Run All Tests**
```bash
mvn test
```

### **3. Run with Browser Specific**
```bash
mvn test -Dbrowser=CHROME -Dtest=BrowserLaunchTest
mvn test -Dbrowser=SAFARI -Dtest=BrowserLaunchTest
```

### **4. Run via TestNG XML**
```bash
mvn test -DsuiteFile=testng.xml
```

---

## 🔧 Configuration Files

### **pom.xml Properties**
```xml
<selenium.version>4.21.0</selenium.version>
<driver.version>5.7.0</driver.version>
<testng.version>7.7.0</testng.version>
```

### **testng.xml Configuration**
```xml
<parameter name="browser" value="CHROME"/>
<classes>
    <class name="org.example.BrowserLaunchTest"/>
</classes>
```

---

## 🛠️ Troubleshooting

### **Common Issues & Solutions**

1. **Chrome Binary Not Found**
```bash
brew install --cask google-chrome
```

2. **Google Detection**
```java
// Add anti-detection options
options.addArguments("--disable-blink-features=AutomationControlled");
options.addArguments("--user-agent=Mozilla/5.0...");
```

3. **Memory Issues**
```java
options.addArguments("--disable-dev-shm-usage");
```

4. **Timeout Issues**
```java
// Increase wait time
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
```

---

## 📊 Best Practices

### **✅ Recommended**
1. **Use Factory Pattern** - Easy to maintain, extend
2. **ThreadLocal for Driver** - Thread-safe
3. **Abstract base class** - Consistent interface
4. **Automatic cleanup** - Avoid memory leaks
5. **Screenshot on failure** - Easy debugging

### **❌ Avoid**
1. **Hard-coded browser strings** - Use enum instead
2. **Static driver instances** - Thread safety issues
3. **Manual driver management** - Easy to forget cleanup
4. **No error handling** - Flaky tests

---

## 🎯 Summary

This framework uses:
- **Abstract Factory Pattern** for flexibility
- **Thread-Safe Design** for parallel execution  
- **Comprehensive Chrome Options** for stability
- **Automatic Resource Management** for reliability
- **Built-in Screenshot** for debugging

**Result:** A robust, scalable, and maintainable Selenium framework!
