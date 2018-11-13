package ch.heig.amt.selenium;

import ch.heig.amt.selenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class WPTest {
    private String baseUrl = "http://localhost:8080/Gamification-WP1";
    private WebDriver driver;

    @Before
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
        driver = new ChromeDriver();
    }


    public void itShouldBePossibleToRegister() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();
        registerPage.enterFirstname("John");
        registerPage.enterLastname("Doe");
        registerPage.enterEmail("john@doe.com");
        registerPage.enterPassword("doejohn");
        registerPage.enterConfirmPassword("doejohn");
        registerPage.submitForm(ProfilePage.class);
    }

    //Test for Registration page

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldNotBePossibleToRegisterWithoutAllInputsFilled() {
        Random random = new Random();
        int i = random.nextInt(4);
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();

        if (i != 0) registerPage.enterFirstname("firstname");
        if (i != 1) registerPage.enterLastname("lastname");
        if (i != 2) registerPage.enterEmail("an@email.com");
        if (i != 3) {
            registerPage.enterPassword("password");
            registerPage.enterConfirmPassword("password");
        }

        registerPage.submitFormExpectingFailure();
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
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.createApplication("application name", "description");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createApplicationNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openCreateApplication();
        applicationPage.enterDescription("description");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createDescriptionInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openCreateApplication();
        applicationPage.enterApplicationName("application name");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void editApplicationNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.clearInputs();
        applicationPage.enterDescription("description");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void editDescriptionNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.clearInputs();
        applicationPage.enterApplicationName("application name");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void scenario() {
        driver.get(baseUrl);
        //register
        LoginPage loginPage = new LoginPage(driver);
        RegisterPage registerPage = loginPage.goToRegisterPage();
        registerPage.enterFirstname("Jane");
        registerPage.enterLastname("Doe");
        registerPage.enterEmail("jane@doe.com");
        registerPage.enterPassword("doejane");
        registerPage.enterConfirmPassword("doejane");
        ProfilePage profilePage = (ProfilePage) registerPage.submitForm(ProfilePage.class);
        //Create 25 applications
        ApplicationPage applicationPage = profilePage.goToApplicationPage();
        for (int i = 0; i < 25; ++i) {
            applicationPage.createApplication("Application" + i, "Description" + i);
        }

        Assert.assertEquals(applicationPage.getApplications().size(), 25);

        loginPage = applicationPage.logout();

        driver.get(baseUrl + "/application");
    }
}
