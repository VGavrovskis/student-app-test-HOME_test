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
import utils.ConfigHelper;
import utils.DriverManager;

import java.sql.Driver;
import java.time.Duration;

import static utils.ConfigHelper.getConfig;

public class StudentAppTestHomeTest {
    WebDriver driver = DriverManager.getInstance();
    WebDriverWait driverWait;
    Faker dataFaker = new Faker();
    AllStudentsPage allStudentsPage;
    AddStudentPage addStudentPage;
    Notifications notifications;
    private final String APP_URL = "http://app.acodemy.lv";

    @BeforeMethod
    public void initialize() {
        driverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(getConfig().getString("student.app.hostname"));
        driver.get(APP_URL);
        allStudentsPage = new AllStudentsPage();
        addStudentPage = new AddStudentPage();
        notifications = new Notifications();
    }
    @AfterMethod (alwaysRun = true)
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
