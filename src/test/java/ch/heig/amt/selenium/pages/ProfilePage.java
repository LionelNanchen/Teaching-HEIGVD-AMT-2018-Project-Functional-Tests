package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends Page {

    By inputFirstname = By.id("inputFirstname");
    By inputLastname = By.id("inputLastname");
    By buttonUpdate = By.id("buttonUpdate");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public ProfilePage enterFirstname(String firstname) {
        driver.findElement(inputFirstname).sendKeys(firstname);
        return this;
    }

    public ProfilePage enterLastname(String lastname) {
        driver.findElement(inputFirstname).sendKeys("John");
        driver.findElement(inputLastname).sendKeys(lastname);
        return this;
    }

    public ProfilePage clearInputs() {
        driver.findElement(inputFirstname).clear();
        driver.findElement(inputLastname).clear();
        return this;
    }
}
