package pageobjects;

import base.BasePage;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import util.CustomSleeper;

import java.util.Set;


public class SearchResultsPage extends BasePage {
	@FindBy(xpath = "//button[@data-autobot-element='FLIGHTS_LISTINGS_FILTER_STOPS_NONE']")
	private WebElement stopsNoneButton;
	@FindBy(xpath = "//button[@data-autobot-element='FLIGHTS_LISTINGS_FILTER_AIRLINES_NONE']")
	private WebElement airlinesNoneButton;
	@FindBy(xpath = "//div[contains(@class,'Loader') and contains(text(),'Searching')]")
	private WebElement searchLoader;
	@FindBy(xpath = "//span[contains(text(),'View More Flights')]")
	private WebElement viewMoreFlightsButton;
	@FindBy(xpath = "//ul[contains(@class,'fly-search-listings')]")
	private WebElement searchResultsList;

	public SearchResultsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void selectStops(Set<Stops> stops) {
		stopsNoneButton.click();
		for (Stops stop : stops) {
			WebElement stopPlace = stopsNoneButton.findElement(By.xpath(String.format("//parent::div//parent::div//parent::div//span[text()='%s']//parent::*//preceding-sibling::div//div", stop.getStop())));
			waitAndClick(stopPlace);
		}
	}

	public void selectAirlines(Set<Airlines> airlines) {
		airlinesNoneButton.click();
		for (Airlines airline : airlines) {
			WebElement airlineName = stopsNoneButton.findElement(By.xpath(String.format("//parent::div//parent::div//parent::div//span[text()='%s']//parent::*//preceding-sibling::div//div", airline.getAirline())));
			waitAndClick(airlineName);

		}
	}

	public void waitForResultsLoading() {
		Set<String> windowHandlers = driver.getWindowHandles();
		for (String windowHandler : windowHandlers) {
			driver.switchTo().window(windowHandler);
			if (driver.getPageSource().contains("Fewer Options")) {

				break;
			}
		}
		waitForVisibility(stopsNoneButton);
		waitForVisibility(airlinesNoneButton);
	}

	public void findMostExpensive() {
		String flightLocator = "//span[contains(text(),'View More Flights')]";
		CustomSleeper.sleepTight(2000);
		while (isElementOnPage(flightLocator)) {
			waitForVisibility(stopsNoneButton);
			driver.findElement(By.xpath(flightLocator)).click();
			CustomSleeper.sleepTight(2000);
		}
		String flightChooseButtonLocator = "//li[last()]/span/li//button[normalize-space()='Choose']";

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		CustomSleeper.sleepTight(2000);
		if (isElementOnPage(flightChooseButtonLocator)) {
			WebElement mostExpensiveChooseButton = searchResultsList.findElement(By.xpath(flightChooseButtonLocator));
			waitAndClick(mostExpensiveChooseButton);
		}
	}

	private boolean isElementOnPage(String locator) {
		try {
			driver.findElement(By.xpath(locator));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@Getter
	public enum Stops {
		NONSTOP("nonstop"), UP_TO_1_STOP("up to 1 stop");

		private String stop;

		Stops(String stop) {
			this.stop = stop;
		}
	}

	@Getter
	public enum Airlines {
		DELTA_AIR_LINES("Delta Air Lines");

		private String airline;

		Airlines(String airline) {
			this.airline = airline;
		}
	}
}