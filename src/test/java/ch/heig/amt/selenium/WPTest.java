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
import java.util.Random;

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
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

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
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openCreateApplication();
        applicationPage.enterDescription("description");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void createApplicationDescriptionInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openCreateApplication();
        applicationPage.enterApplicationName("application name");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void itShouldBePossibleToEditAnApplication() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.editApplication("application name", "description");
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void editApplicationNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.clearInputs();
        applicationPage.enterDescription("description");
        applicationPage.expectingFailure();
    }

    @Test
    @ProbeTest(tags = "WebUI")
    public void editApplicationDescriptionNameInputShouldNotBeEmtpy() {
        driver.get(baseUrl);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("john@doe.com");
        loginPage.enterPassword("doejohn");
        ProfilePage profilePage = (ProfilePage) loginPage.submitForm(ProfilePage.class);
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();

        applicationPage.openEditApplication();
        applicationPage.clearInputs();
        applicationPage.enterApplicationName("application name");
        applicationPage.expectingFailure();
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
        ApplicationsPage applicationPage = profilePage.goToApplicationPage();
        for (int i = 0; i < 25; ++i) {
            applicationPage.createApplication("Application" + i, "Description" + i);
        }

        //Assert if there is actually 10 applications in page 1
        Assert.assertEquals(applicationPage.getApplications().size(), 10);

        //Change page, verifiy if page 2 has 10 applications and page 3 only 5 and wait 1 second per page.
        for (int i = 1; i <= 2; ++i) {
            applicationPage = applicationPage.nextPage();
            Assert.assertEquals(applicationPage.getApplications().size(), 10/i);
        }

        //copy the current URL, logout and try to go directly to the url (page 3 of applications)
        String url = driver.getCurrentUrl();
        loginPage = applicationPage.logout();
        loginPage.goToUrl(url);
    }
}
