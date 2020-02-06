package base;


import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public abstract class BasePage {
	private WebDriverWait wait;
	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
	}

	protected void waitAndClick(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	protected void waitForElementPresent(By locator) {
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	protected void waitForVisibility(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected boolean isElementOnPage(String locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	protected void switchToCurrentTab(String pageTextId) {
		Set<String> windowHandlers = driver.getWindowHandles();
		for (String windowHandler : windowHandlers) {
			driver.switchTo().window(windowHandler);
			if (driver.getPageSource().contains(pageTextId)) {
				break;
			}
		}
	}
}