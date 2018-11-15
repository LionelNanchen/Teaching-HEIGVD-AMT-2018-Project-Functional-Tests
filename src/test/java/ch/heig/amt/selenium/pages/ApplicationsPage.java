package ch.heig.amt.selenium.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ApplicationsPage extends Page {

    By buttonCreateNewApplication = By.id("buttonCreateNewApplication");
    By dropdownButton = By.className("dropdown-toggle");
    By dropdownEdit = By.className("dropdownEdit");

    By inputApplicationName = By.id("inputApplicationName");
    By inputDescription = By.id("inputDescription");
    By buttonConfirm = By.id("buttonConfirm");

    By modal = By.id("appModal");
    By failureNameMessage = By.id("failureNameMessage");
    By failureDescriptionMessage = By.id("failureDescriptionMessage");

    By nextPage = By.id("nextPage");
    By previousPage = By.id("previousPage");

    public ApplicationsPage(WebDriver driver) {
        super(driver);
    }

    public ApplicationsPage openCreateApplication() {
        driver.findElement(buttonCreateNewApplication).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
        return this;
    }

    public ApplicationsPage openEditApplication() {
        driver.findElements(dropdownButton).get(0).click();
        driver.findElements(dropdownEdit).get(0).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
        return this;
    }

    public ApplicationsPage enterApplicationName(String name) {
        driver.findElement(inputApplicationName).sendKeys(name);
        return this;
    }

    public ApplicationsPage enterDescription(String description) {
        driver.findElement(inputDescription).sendKeys(description);
        return this;
    }

    public ApplicationsPage clearInputs() {
        driver.findElement(inputApplicationName).clear();
        driver.findElement(inputDescription).clear();
        return this;
    }

    public ApplicationsPage createApplication(String name, String description) {
        openCreateApplication();
        enterApplicationName(name);
        enterDescription(description);
        return expectingSuccess();
    }

    public ApplicationsPage editApplication(String name, String description) {
        openEditApplication();
        clearInputs();
        enterApplicationName(name);
        enterDescription(description);
        return expectingSuccess();
    }

    public ApplicationsPage nextPage() {
        driver.findElement(nextPage).click();
        return new ApplicationsPage(driver);
    }

    public ApplicationsPage previousPage() {
        driver.findElement(previousPage).click();
        return new ApplicationsPage(driver);
    }

    public ApplicationsPage expectingSuccess() {
        driver.findElement(buttonConfirm).click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));

        if (driver.findElements(failureNameMessage).size() == 0 && driver.findElements(failureDescriptionMessage).size() == 0) {
            return this;
        } else {
            throw new IllegalStateException("It is not a success.");
        }
    }

    public ApplicationsPage expectingFailure() {
        driver.findElement(buttonConfirm).click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));

        if (driver.findElements(failureNameMessage).size() != 0) {
            Assert.assertEquals("Oh! Please enter a valid name", driver.findElement(failureNameMessage).getText());
        } else if (driver.findElements(failureDescriptionMessage).size() != 0) {
            Assert.assertEquals("Oh! Please enter a valid description", driver.findElement(failureDescriptionMessage).getText());
        } else {
            throw new IllegalStateException("It is not a success.");
        }
        return this;

//        if (driver.findElements(failureNameMessage).size() != 0 || driver.findElements(failureDescriptionMessage).size() != 0) {
//            return this;
//        } else {
//            throw new IllegalStateException("It is not a success.");
//        }
    }

    public List getApplications() {
        return driver.findElements(By.className("application"));
    }
}
