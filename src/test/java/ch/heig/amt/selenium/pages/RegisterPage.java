package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends Page {

    By inputFirstname = By.id("inputFirstname");
    By inputLastname = By.id("inputLastname");
    By inputEmail = By.id("inputEmail");
    By inputPassword = By.id("inputPassword");
    By inputConfirmPassword = By.id("inputConfirmPassword");
    By buttonRegister = By.id("buttonRegister");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public RegisterPage enterFirstname(String firstname) {
        driver.findElement(inputFirstname).sendKeys(firstname);
        return this;
    }

    public RegisterPage enterLastname(String lastname) {
        driver.findElement(inputLastname).sendKeys(lastname);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        driver.findElement(inputEmail).sendKeys(email);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        driver.findElement(inputPassword).sendKeys(password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String password) {
        driver.findElement(inputConfirmPassword).sendKeys(password);
        return this;
    }

    public Page submitForm(Class<? extends Page> expectedPageClass) {
        driver.findElement(buttonRegister).click();
        Page targetPage = null;
        try {
            targetPage = expectedPageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception ex) {
            throw new RuntimeException("Exception when using reflection: " + ex.getMessage());
        }
        return targetPage;
    }

    public RegisterPage submitFormExpectingFailure() {
        driver.findElement(buttonRegister).click();
        return this;
    }
}
