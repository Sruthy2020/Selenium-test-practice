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
public class TransferFundsTest {

    private static ChromeDriver driver;
    private static String newAccountNumber;
    private static String checkingAccountNumber;
    private static WebDriverWait wait;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //test case for logging in
    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17621)
    public void testLogin() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
        driver.findElement(By.name("username")).sendKeys("markSmith");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        WebElement openAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Open New Account")));
        assertNotNull(openAccountLink); //making sure the login was successful..

        WebElement checkingAccountElement = driver.findElement(By.xpath("//tr[1]/td[1]/a"));
        checkingAccountNumber = checkingAccountElement.getText();
        assertNotNull(checkingAccountNumber, "Checking account number not found");
    }



    //test case for opening a new savings account for transferring..
    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 17637)
    public void testOpenNewAccount() {
        WebElement openAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Open New Account")));
        openAccountLink.click();             //going to open account page..

        Select accountTypeDropdown = new Select(driver.findElement(By.id("type")));
        accountTypeDropdown.selectByVisibleText("SAVINGS");

        Select fromAccountDropdown = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccountDropdown.selectByIndex(0);

        driver.findElement(By.xpath("//input[@value='Open New Account']")).click();

        //the newly created account number
        WebElement newAccountNumberElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("newAccountId")));
        newAccountNumber = newAccountNumberElement.getText();
        assertNotNull(newAccountNumber);
    }

    //test case for transferring funds to the new account...
    @Test
    @Order(3)
    @SpiraTestCase(testCaseId = 17638)
    public void testTransferFunds() {
        WebElement transferFundsLink = driver.findElement(By.linkText("Transfer Funds"));
        transferFundsLink.click();

        //choose the new savings account as the from account..
        Select newAccountDropdown = new Select(driver.findElement(By.id("fromAccountId")));
        newAccountDropdown.selectByValue(newAccountNumber);

        //choosing the checking account as the 'to' account..
        Select toAccountDropdown = new Select(driver.findElement(By.id("toAccountId")));
        toAccountDropdown.selectByValue(checkingAccountNumber);

        //enter the amount to transfer..
        driver.findElement(By.id("amount")).clear();
        driver.findElement(By.id("amount")).sendKeys("100");

        //submit the transfer form...
        driver.findElement(By.xpath("//input[@value='Transfer']")).click();

        WebElement transferSuccessMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Transfer Complete!')]")));
        assertNotNull(transferSuccessMessage);
    }

    //test case for verifying the account balance after the transfer..
    @Test
    @Order(4)
    @SpiraTestCase(testCaseId = 17639)
    public void testVerifyAccountBalance() {
        WebElement overviewLink = driver.findElement(By.linkText("Accounts Overview"));
        overviewLink.click();

        //get the balance for the new savings account...
        WebElement savingsAccountBalanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='" + newAccountNumber + "']/parent::td/following-sibling::td[1]")));
        String savingsAccountBalance = savingsAccountBalanceElement.getText();

        assertEquals("$100.00", savingsAccountBalance, "The savings account balance is incorrect.");
    }

    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}





