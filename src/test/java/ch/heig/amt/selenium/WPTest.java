package ch.heig.amt.selenium;

import ch.heig.amt.selenium.pages.*;
import io.probedock.client.annotations.ProbeTest;
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
    public void confirmPasswordMustBeTheSameAsPassword() {
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

    @Test
    @ProbeTest(tags = "WebUI")
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
        profilePage.logout(LoginPage.class);
    }

    //Test for Application page

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
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createDescriptionNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openCreateApplication();
        applicationPage.enterApplicationName("application name");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void updateApplicationNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.enterDescription("description");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void updateDescriptionNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.enterApplicationName("application name");
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
        loginPage = (LoginPage) registerPage.submitForm(LoginPage.class);
        //login with new account
        loginPage.enterEmail("an@email.com");
        loginPage.enterPassword("password");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        //Create 25 applications
        ApplicationPage applicationPage = profilePage.goToApplicationPage();
        for (int i = 0; i < 25; ++i) {
            applicationPage.createApplication("Application" + i, "Description" + i);
        }
    }
}
