package ch.heig.amt.selenium;

import ch.heig.amt.selenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;

public class WPTest {
    private String baseUrl = "http://localhost:8080/Gamification-WP1";
    private WebDriver driver;

    @Before
    public void openBrowser() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("chromedriver").getFile());
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        driver = new ChromeDriver();
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldBePossibleToRegister() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();
        registerPage.enterFirstname("New");
        registerPage.enterLastname("User");
        registerPage.enterEmail("new@user.com");
        registerPage.enterPassword("password");
        registerPage.enterConfirmPassword("password");
        registerPage.submitForm(ProfilePage.class);
    }

    //Test for Registration page

    public void tryRegister(String firstname, String lastname, String email, String password) {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();
        registerPage.enterFirstname(firstname);
        registerPage.enterLastname(lastname);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(password);
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldNotBePossibleToRegisterWithoutAllInputsFilled() {
        tryRegister("", "lastname", "an@email.com", "password");
        tryRegister("firstname", "", "an@email.com", "password");
        tryRegister("firstname", "lastname", "", "password");
        tryRegister("firstname", "lastname", "an@email.com", "");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void toRegisterConfirmPasswordMustBeTheSameAsPassword() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();

        registerPage.enterFirstname("firstname");
        registerPage.enterLastname("lastname");
        registerPage.enterEmail("an@email.com");
        registerPage.enterPassword("password");
        registerPage.enterConfirmPassword("confirmPassword");

        registerPage.submitFormExpectingFailure();
    }

    //Test for Login page

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldNotBePossibleToSigninWithAnInvalidEmail() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("this is not a valid email address");
        loginPage.enterPassword("password");
        loginPage.submitFormExpectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void successfulSigninShouldRedirectToProfilePage() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        loginPage.submitForm(ProfilePage.class);
    }

    //Test Logout

    @Test
    @ProbeTest(tags = "WebUI")
    public void logoutShouldQuitAndRedirectToLoginPage() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        profilePage.logout();
    }

    //Test for Application page

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldBePossibleToCreateAnApplication() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationsPage = profilePage.goToApplicationPage();

        applicationsPage.createApplication("application name", "description");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createApplicationNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationsPage = profilePage.goToApplicationPage();

        applicationsPage.openCreateApplication();
        applicationsPage.enterDescription("description");
        applicationsPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createApplicationDescriptionInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationsPage = profilePage.goToApplicationPage();

        applicationsPage.openCreateApplication();
        applicationsPage.enterApplicationName("application name");
        applicationsPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldBePossibleToEditAnApplication() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationsPage = profilePage.goToApplicationPage();

        applicationsPage.editApplication("application name", "description");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void scenario() {
        driver.get(baseUrl);
        //register new user (Jane Doe)
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();
        registerPage.enterFirstname("Jane");
        registerPage.enterLastname("Doe");
        registerPage.enterEmail("jane@doe.com");
        registerPage.enterPassword("doejane");
        registerPage.enterConfirmPassword("doejane");
        ProfilePage profilePage = (ProfilePage) registerPage.submitForm(ProfilePage.class);
        //Create 25 applications
        ApplicationsPage applicationsPage = profilePage.goToApplicationPage();
        for (int i = 0; i < 25; ++i) {
            applicationsPage.createApplication("Application" + i, "Description" + i);
        }

        //Assert if there is actually 10 applications in page 1
        Assert.assertEquals(applicationsPage.getApplications().size(), 10);

        //Change page, verifiy if page 2 has 10 applications and page 3 only 5 and wait 1 second per page.
        for (int i = 1; i <= 2; ++i) {
            applicationsPage = applicationsPage.nextPage();
            Assert.assertEquals(applicationsPage.getApplications().size(), 10/i);
        }

        //copy the current URL, logout and try to go directly to the url (page 3 of applications)
        String url = driver.getCurrentUrl();
        loginPage = applicationsPage.logout();
        loginPage.goToUrl(url);
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8080/Gamification-WP1/login");
    }
}
