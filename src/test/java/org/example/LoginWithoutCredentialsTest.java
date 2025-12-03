package org.example;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@SpiraTestConfiguration(
        url = "",
        login = "",
        rssToken = "",
        projectId = 455
)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginWithoutCredentialsTest {

    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    //test case for login attempt without entering any credentials...
    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17472)
    public void loginWithoutCredentials() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Please enter a username and password')]")));

        String actualError = errorMessage.getText();
        String expectedError = "Please enter a username and password.";
        assertEquals(expectedError, actualError, "Error message did not match for missing credentials.");
    }


    //test case for login attempt with only username...
    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 17643)
    public void loginWithOnlyUsername() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("markSmith");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Please enter a username and password.')]")));

        String actualError = errorMessage.getText();
        String expectedError = "Please enter a username and password.";
        assertEquals(expectedError, actualError, "Error message did not match for missing password.");
    }


    //test case for login attempt with only password...
    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 17644)
    public void loginWithOnlyPassword() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Please enter a username and password.')]")));

        String actualError = errorMessage.getText();
        String expectedError = "Please enter a username and password.";
        assertEquals(expectedError, actualError, "Error message did not match for missing username.");
    }

    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}



