package org.example;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpiraTestConfiguration(
        url = "",
        login = "",
        rssToken = "",
        projectId = 455
)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OpenSavingsAccountTest {

    private static ChromeDriver driver;
    private static WebDriverWait wait;
    private static String checkingAccountNumber;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //test case for logging in...
    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17537)
    public void login() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        //entering correct username and password...
        driver.findElement(By.name("username")).sendKeys("markSmith");
        driver.findElement(By.name("password")).sendKeys("password123");

        //clicking the login button..
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        //watiing for the welcome message to be visible..
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));

        String actualMessage = welcomeMessage.getText();
        String expectedMessage = "Accounts Overview";
        assertEquals(expectedMessage, actualMessage, "Login successful, but 'Accounts Overview' was not found.");

    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 19417)
    public void retrieveCheckingAccountNumber() {
        //retrieveing the checking account number from the Overview page
        WebElement checkingAccountElement = driver.findElement(By.xpath("//tr[1]/td[1]/a"));
        checkingAccountNumber = checkingAccountElement.getText();
        assertNotNull(checkingAccountNumber, "Failed to retrieve checking account number");
    }

    // test case for navigating to the Open New Account page...
    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 19188)
    public void navigateToOpenNewAccount() {
        driver.findElement(By.linkText("Open New Account")).click();

        // waiting until the Open New Account page is visible...
        WebElement openAccountForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Open New Account')]")));
        assertNotNull(openAccountForm, "Failed to open 'Open New Account' page");
    }

    //test case for selecting account type and opening a new account..
    @Test
    @Order(4)
    @SpiraTestCase(testCaseId = 19189)
    public void selectAccountTypeAndOpenAccount() {
        //select SAVINGS from account type dropdown..
        Select accountTypeDropdown = new Select(driver.findElement(By.id("type")));
        accountTypeDropdown.selectByVisibleText("SAVINGS");

        //select the retrieved checking account number from the from Account dropdown..
        Select fromAccountDropdown = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccountDropdown.selectByValue(checkingAccountNumber);

        //submit the form to open the new account..
        driver.findElement(By.xpath("//input[@value='Open New Account']")).click();

        //checking for the confirmation message is visible or not..
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Account Opened!')]")));
        assertNotNull(confirmationMessage, "Failed to open new account");

        String actualConfirmation = driver.findElement(By.xpath("//p[contains(text(),'Congratulations, your account is now open.')]")).getText();
        String expectedConfirmation = "Congratulations, your account is now open.";
        assertEquals(expectedConfirmation, actualConfirmation, "Account opening confirmation message did not match");
    }

    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}