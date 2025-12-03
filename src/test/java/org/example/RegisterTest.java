package org.example;

import com.inflectra.spiratest.addons.junitextension.SpiraTestCase;
import com.inflectra.spiratest.addons.junitextension.SpiraTestConfiguration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpiraTestConfiguration(
        url = "",
        login = "",
        rssToken = "",
        projectId = 455
)

//Pls run this test first so that we create an account and then test all others...
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTest {

    private static ChromeDriver driver;

    @BeforeAll
    public static void setup() {
        //setting up ChromeDriver and wait...
        System.setProperty("Webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    //test case for registering a new user
    @Test
    @Order(1)
    @SpiraTestCase(testCaseId = 17535)
    public void registerNewUser() {
        driver.get("https://parabank.parasoft.com/parabank/register.htm");

        //fill form..
        driver.findElement(By.id("customer.firstName")).sendKeys("Mark");
        driver.findElement(By.id("customer.lastName")).sendKeys("Smith");

        driver.findElement(By.id("customer.address.street")).sendKeys("123 Main St");
        driver.findElement(By.id("customer.address.city")).sendKeys("Springfield");
        driver.findElement(By.id("customer.address.state")).sendKeys("IL");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("62704");

        driver.findElement(By.id("customer.phoneNumber")).sendKeys("555-1234");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");

        //enter the username and password[for login i am gonna usse this]
        driver.findElement(By.id("customer.username")).sendKeys("markSmith");
        driver.findElement(By.id("customer.password")).sendKeys("password123");
        driver.findElement(By.id("repeatedPassword")).sendKeys("password123");

        //submitting the registration form..
        driver.findElement(By.xpath("//input[@value='Register']")).click();

        String actualMessage = driver.findElement(By.xpath("//p[contains(text(), 'Your account was created successfully')]")).getText();
        String expectedMessage = "Your account was created successfully. You are now logged in.";
        assertEquals(expectedMessage, actualMessage);
    }

    //closing the browser...
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}