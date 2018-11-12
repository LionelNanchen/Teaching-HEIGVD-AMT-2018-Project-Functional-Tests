package ch.heig.amt.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Page {

    final WebDriver driver;

    By menuItemProfile = By.id("menuItemProfile");
    By menuItemApplications = By.id("menuItemApplications");
    By menuItemUsers = By.id("menuItemUsers");
    By menuItemLogout = By.id("menuItemLogout");

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public ApplicationPage goToApplicationPage() {
        driver.findElement(menuItemApplications).click();
        return new ApplicationPage(driver);
    }

    public ProfilePage goToProfilePage() {
        driver.findElement(menuItemProfile).click();
        return new ProfilePage(driver);
    }

    public UsersPage goToUsersPage() {
        driver.findElement(menuItemUsers).click();
        return new UsersPage(driver);
    }

    public Page logout(Class<? extends Page> expectedPageClass) {
        driver.findElement(menuItemLogout).click();
        Page targetPage = null;
        try {
            targetPage = expectedPageClass.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception ex) {
            throw new RuntimeException("Exception when using reflection: " + ex.getMessage());
        }
        return targetPage;
    }
}
