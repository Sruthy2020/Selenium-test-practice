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
public class AdminSettingsTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17610)
    public void logInToAdminAccount() {
        //navigate to login pge and log in with tge valid admin credentials...
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("markSmith");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebElement accountOverview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));
        assertNotNull(accountOverview, "The username and password could not be verified.");
    }

    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 18965)
    public void navigateToAdminPage() {
        //click on Admin Page link and verifying successful navigation...
        WebElement adminLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Admin Page")));
        assertNotNull(adminLink, "Admin Page link not found");
        adminLink.click();

        WebElement administration = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Administration')]")));
        assertNotNull(administration, "Navigation to Admin Page failed: Administration header not found");
    }

    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 19379)
    public void modifyDataAccessMode() {
        //selectong - REST (JSON) or JDBC...
        WebElement jdbcAccessModeRadioButton = driver.findElement(By.id("accessMode4"));
        WebElement restJsonAccessModeRadioButton = driver.findElement(By.id("accessMode3"));

        assertNotNull(jdbcAccessModeRadioButton, "JDBC Access Mode radio button not found");
        assertNotNull(restJsonAccessModeRadioButton, "REST JSON Access Mode radio button not found");

        //selecting JDBC mode by clicking the radio button..
        if (!jdbcAccessModeRadioButton.isSelected()) {
            jdbcAccessModeRadioButton.click();
        }

        assertTrue(jdbcAccessModeRadioButton.isSelected(), "JDBC Access Mode is not selected");
        assertFalse(restJsonAccessModeRadioButton.isSelected(), "REST JSON Access Mode should not be selected");
    }

    @Test
    @Order(4)
    @SpiraTestCase(testCaseId = 18966)
    public void modifyBalanceSettings() {
        //clr and updating the initial balance field..
        WebElement initialBalanceField = driver.findElement(By.id("initialBalance"));
        assertNotNull(initialBalanceField, "Initial Balance field not found");
        initialBalanceField.clear();
        initialBalanceField.sendKeys("600");

        //clr and updating the min balance field..
        WebElement minimumBalanceField = driver.findElement(By.id("minimumBalance"));
        assertNotNull(minimumBalanceField, "Minimum Balance field not found");
        minimumBalanceField.clear();
        minimumBalanceField.sendKeys("200");

        assertEquals("600", initialBalanceField.getAttribute("value"), "Initial Balance not set correctly");
        assertEquals("200", minimumBalanceField.getAttribute("value"), "Minimum Balance not set correctly");
    }

    @Test
    @Order(5)
    @SpiraTestCase(testCaseId = 18968)
    public void modifyLoanSettings() {
        //select loan provider from the dropdown..
        WebElement loanProviderDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loanProvider")));
        assertNotNull(loanProviderDropdown, "Loan Provider dropdown not found");
        Select selectLoanProvider = new Select(loanProviderDropdown);
        selectLoanProvider.selectByVisibleText("Local");

        //select loan processor from the dropdown..
        WebElement loanProcessorDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loanProcessor")));
        assertNotNull(loanProcessorDropdown, "Loan Processor dropdown not found");
        Select selectLoanProcessor = new Select(loanProcessorDropdown);
        selectLoanProcessor.selectByVisibleText("Down Payment");


        //clear and set loan processor threshold..
        WebElement thresholdField = driver.findElement(By.id("loanProcessorThreshold"));
        assertNotNull(thresholdField, "Threshold field not found");
        thresholdField.clear();
        thresholdField.sendKeys("10");

        assertEquals("Local", selectLoanProvider.getFirstSelectedOption().getText(), "Loan Provider not set correctly");
        assertEquals("Down Payment", selectLoanProcessor.getFirstSelectedOption().getText(), "Loan Processor not set correctly");
        assertEquals("10", thresholdField.getAttribute("value"), "Threshold not set correctly");
    }

    @Test
    @Order(6)
    @SpiraTestCase(testCaseId = 18969)
    public void submitSettings() {
        //submitting the form and verifing the success message..
        driver.findElement(By.xpath("//input[@value='Submit']")).click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p/b[contains(text(),'Settings saved successfully.')]")));
        assertNotNull(successMessage, "Success message not displayed");
        String actualMessage = successMessage.getText();
        String expectedMessage = "Settings saved successfully.";
        assertEquals(expectedMessage, actualMessage, "Success message content mismatch");
    }


    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}



