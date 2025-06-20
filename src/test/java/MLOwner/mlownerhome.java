package MLOwner;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class mlownerhome {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void testMLOwnerHomePageButtons() {
        try {
            // 1. Login
            driver.get("https://mmpro.aasait.lk/");
            System.out.println("Current URL: " + driver.getCurrentUrl());

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href='/signin'] button")));
            loginButton.click();
            System.out.println("Clicked login button");

            // Login form
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-in_username")));
            usernameField.sendKeys("pasindu");

            WebElement passwordField = driver.findElement(By.id("sign-in_password"));
            passwordField.sendKeys("12345678");

            WebElement signInButton = driver.findElement(By.cssSelector("button[type='submit']"));
            signInButton.click();
            System.out.println("Submitted login form");

            // Wait for home page to load
            wait.until(ExpectedConditions.urlContains("/mlowner/home"));
            System.out.println("Reached ML Owner Home page");

            // 2. Test the three main cards/buttons
            testViewLicensesCard();
            testMLRequestCard();
            testRequestedLicensesCard();

            System.out.println("All tests completed successfully!");

        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void testViewLicensesCard() {
        System.out.println("=== Testing View Licenses Card ===");

        WebElement viewLicensesCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'custom-card')]//*[name()='svg' and contains(@class, 'anticon-file-search')]/ancestor::div[contains(@class, 'custom-card')]")));

        WebElement clickMeButton = viewLicensesCard.findElement(
                By.xpath(".//button[contains(@class, 'ml-card-button')]"));
        clickMeButton.click();
        System.out.println("Clicked View Licenses button");

        wait.until(ExpectedConditions.urlContains("/mlowner/home/viewlicenses"));
        System.out.println("Navigated to View Licenses page");

        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("/mlowner/home"));
        System.out.println("Returned to home page");
    }

    private void testMLRequestCard() {
        System.out.println("=== Testing ML Request Card ===");

        WebElement mlRequestCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'custom-card')]//*[name()='svg' and contains(@class, 'anticon-file-add')]/ancestor::div[contains(@class, 'custom-card')]")));

        WebElement clickMeButton = mlRequestCard.findElement(
                By.xpath(".//button[contains(@class, 'ml-card-button')]"));
        clickMeButton.click();
        System.out.println("Clicked ML Request button");

        wait.until(ExpectedConditions.urlContains("/mlowner/home/mlrequest"));
        System.out.println("Navigated to ML Request page");

        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("/mlowner/home"));
        System.out.println("Returned to home page");
    }

    private void testRequestedLicensesCard() {
        System.out.println("=== Testing Requested Licenses Card ===");

        WebElement requestedLicensesCard = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(@class, 'custom-card')]//*[name()='svg' and contains(@class, 'anticon-file-sync')]/ancestor::div[contains(@class, 'custom-card')]")));

        WebElement clickMeButton = requestedLicensesCard.findElement(
                By.xpath(".//button[contains(@class, 'ml-card-button')]"));
        clickMeButton.click();
        System.out.println("Clicked Requested Licenses button");

        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("ant-modal-content")));
        System.out.println("Requested Licenses modal is visible");

        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Close')]")));
        closeButton.click();
        System.out.println("Closed the modal");
    }
}
