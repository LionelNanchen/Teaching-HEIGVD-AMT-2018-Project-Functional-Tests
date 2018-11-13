package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ApplicationPage extends Page {

    By buttonCreateNewApplication = By.id("buttonCreateNewApplication");
    By dropdownButton = By.className("dropdown-toggle");
    By dropdownEdit = By.className("dropdownEdit");

    By inputApplicationName = By.id("inputApplicationName");
    By inputDescription = By.id("inputDescription");
    By buttonConfirm = By.id("buttonConfirm");

    By modal = By.id("appModal");
    By failureNameMessage = By.id("failureNameMessage");
    By failureDescriptionMessage = By.id("failureDescriptionMessage");

    public ApplicationPage(WebDriver driver) {
        super(driver);
    }

    public ApplicationPage openCreateApplication() {
        driver.findElement(buttonCreateNewApplication).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
        return this;
    }

    public ApplicationPage openEditApplication() {
        driver.findElements(dropdownButton).get(0).click();
        driver.findElements(dropdownEdit).get(0).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
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

    public ApplicationPage clearInputs() {
        driver.findElement(inputApplicationName).clear();
        driver.findElement(inputDescription).clear();
        return this;
    }

    //this method call expectingSuccess
    public ApplicationPage createApplication(String name, String description) {
        driver.findElement(buttonCreateNewApplication).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
        driver.findElement(inputApplicationName).sendKeys(name);
        driver.findElement(inputDescription).sendKeys(description);
        return expectingSuccess();
    }

    public ApplicationPage expectingSuccess() {
        driver.findElement(buttonConfirm).click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));

        if (driver.findElements(failureNameMessage).size() == 0 && driver.findElements(failureDescriptionMessage).size() == 0) {
            return this;
        } else {
            throw new IllegalStateException("It is not a success.");
        }
    }

    public ApplicationPage expectingFailure() {
        driver.findElement(buttonConfirm).click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));

        if (driver.findElements(failureNameMessage).size() != 0 || driver.findElements(failureDescriptionMessage).size() != 0) {
            return this;
        } else {
            throw new IllegalStateException("It is not a success.");
        }
    }

    public List getApplications() {
        return driver.findElements(By.className("application"));
    }
}
