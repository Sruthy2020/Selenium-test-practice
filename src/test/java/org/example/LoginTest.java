package org.example;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
public class LoginTest {

    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17536)
    public void loginWithCorrectCredentials() {
        //opening the login page..
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


    //closing the browser..
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}
