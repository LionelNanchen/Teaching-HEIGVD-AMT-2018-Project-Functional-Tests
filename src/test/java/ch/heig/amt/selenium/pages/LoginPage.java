package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends Page {

    By inputEmail = By.id("inputEmail");
    By inputPassword = By.id("inputPassword");
    By buttonSignIn = By.id("buttonSignIn");
    By buttonRegister = By.id("buttonRegister");

    public LoginPage(WebDriver driver) {
        super(driver);

        if (!"Gamification-WP1 - Login".equals(driver.getTitle())) throw new IllegalStateException("This is not the correct page");
    }

    public LoginPage enterEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
        return this;
    }

    public RegisterPage goToRegisterPage() {
        driver.findElement(buttonRegister).click();
        return new RegisterPage(driver);
    }

    public Page submitForm(Class<? extends Page> expectedPageClass) {
        driver.findElement(buttonSignIn).click();
        Page targetPage = null;
        try {
            targetPage = expectedPageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception ex) {
            throw new RuntimeException("Exception when using reflection: " + ex.getMessage());
        }
        return targetPage;
    }

    public LoginPage submitFormExpectingFailure() {
        driver.findElement(buttonSignIn).click();
        return this;
    }
}
