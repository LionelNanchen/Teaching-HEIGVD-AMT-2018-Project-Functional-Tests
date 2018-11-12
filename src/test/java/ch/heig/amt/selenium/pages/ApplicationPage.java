package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ApplicationPage extends Page {

    By buttonCreateNewApplication = By.id("buttonCreateNewApplication");
    By dropdownEdit = By.id("dropdownEdit");

    By inputApplicationName = By.id("inputApplicationName");
    By inputDescription = By.id("inputDescription");
    By buttonConfirm = By.id("buttonConfirm");

    public ApplicationPage(WebDriver driver) {
        super(driver);
    }

    public ApplicationPage openCreateApplication() {
        driver.findElement(buttonCreateNewApplication).click();
        return this;
    }

    public ApplicationPage openEditApplication() {
        driver.findElement(dropdownEdit).click();
        return this;
    }

    public ApplicationPage enterApplicationName(String name) {
        driver.findElement(inputApplicationName).sendKeys(name);
        return this;
    }

    public ApplicationPage enterDescription(String description) {
        driver.findElement(inputDescription).sendKeys(description);
        return this;
    }

    public ApplicationPage submitFormExpectingFailure() {
        driver.findElement(buttonConfirm).click();
        return this;
    }

    public ApplicationPage createApplication(String name, String description) {
        driver.findElement(buttonCreateNewApplication).click();
        driver.findElement(inputApplicationName).sendKeys(name);
        driver.findElement(inputDescription).sendKeys(description);
        driver.findElement(buttonConfirm).click();
        return this;
    }
}
