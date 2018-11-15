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

    public ApplicationsPage goToApplicationPage() {
        driver.findElement(menuItemApplications).click();
        return new ApplicationsPage(driver);
    }

    public ProfilePage goToProfilePage() {
        driver.findElement(menuItemProfile).click();
        return new ProfilePage(driver);
    }

    public UsersPage goToUsersPage() {
        driver.findElement(menuItemUsers).click();
        return new UsersPage(driver);
    }

    public LoginPage goToUrl(String url) {
        driver.get(url);
        return new LoginPage(driver);
    }

    public LoginPage logout() {
        driver.findElement(menuItemLogout).click();
        return new LoginPage(driver);
    }
}
