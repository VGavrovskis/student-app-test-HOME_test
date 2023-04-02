import com.github.javafaker.Faker;
import constants.AllConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import page_objects.AddStudentPage;
import page_objects.AllStudentsPage;
import page_objects.Notifications;

import java.sql.Driver;
import java.time.Duration;

public class StudentAppTestHomeTest {
    WebDriver driver;
    WebDriverWait driverWait;
    Faker dataFaker = new Faker();
    AllStudentsPage allStudentsPage;
    AddStudentPage addStudentPage;
    Notifications notifications;
    private final String APP_URL = "http://app.acodemy.lv";

    @BeforeMethod
    public void initialize() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(APP_URL);
        allStudentsPage = new AllStudentsPage(driver);
        addStudentPage = new AddStudentPage(driver);
        notifications = new Notifications(driver);
    }
    @AfterMethod
    public void  tearDown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void openStudentAppTestHomeTest() {
        allStudentsPage.waitAndClickOnAddStudentButton();
        String name = addStudentPage.waitAndSetValueForNameField();
        addStudentPage.waitAndSetValueForEmailField();
        addStudentPage.waitAndSetGender(AllConstants.GenderConstants.MALE);
        addStudentPage.clickOnSubmitButton();

        Assert.assertEquals(notifications.getMessageFromNotification(), "Student successfully added");
        Assert.assertEquals(notifications.getDescriptionFromNotification(), name + " was added to the system");

        notifications.getPopUpCloseButton().click();
        Assert.assertTrue(driverWait.until(ExpectedConditions.invisibilityOf(notifications.getPopUpCloseButton())));
    }
}
