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
public class LogoutTest {

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

    //test case for logging in,,
    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17597)
    public void testLogin() {
        driver.get("https://parabank.parasoft.com/parabank/index.htm");

        //fill in login form
        driver.findElement(By.name("username")).sendKeys("markSmith");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        //wait until the Overview page is visible...
        WebElement accountOverview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Accounts Overview')]")));
        assertNotNull(accountOverview);

        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://parabank.parasoft.com/parabank/overview.htm", currentUrl, "URL after login is incorrect");
    }

    //test case for logging out...
    @Test
    @Order(2)
    @SpiraTestCase(testCaseId = 18962)
    public void testLogout() {
        //click the logout link...
        driver.findElement(By.linkText("Log Out")).click();

        //waiting until the Customer Login page is visible..
        WebElement loginPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Customer Login')]")));
        assertNotNull(loginPage, "Logout failed, Customer Login not found");

        String actualLoginPageText = driver.findElement(By.xpath("//h2[contains(text(),'Customer Login')]")).getText();
        String expectedLoginPageText = "Customer Login";
        assertEquals(expectedLoginPageText, actualLoginPageText, "Login page title did not match after logout");
    }

    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}