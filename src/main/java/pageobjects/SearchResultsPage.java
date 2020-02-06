package pageobjects;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
			waitAndClick(stopsNoneButton.findElement(stop.getCheckBox()));;
		}
	}

	public void selectAirlines(Set<Airlines> airlines) {
		airlinesNoneButton.click();
		for (Airlines airline : airlines) {
			waitAndClick(airlinesNoneButton.findElement(airline.getCheckBox()));

		}
	}

	public void waitForResultsLoading() {
		switchToCurrentTab("Fewer Options");

		waitForVisibility(stopsNoneButton);
		waitForVisibility(airlinesNoneButton);
	}

	public void findMostExpensive() {
		String flightLocator = "//span[contains(text(),'View More Flights')]";

		while (isElementOnPage(flightLocator)) {
			waitForVisibility(stopsNoneButton);
			driver.findElement(By.xpath(flightLocator)).click();
		}

		String flightChooseButtonLocator = "//li[last()]/span/li//button[normalize-space()='Choose']";

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		if (isElementOnPage(flightChooseButtonLocator)) {
			WebElement mostExpensiveChooseButton = searchResultsList.findElement(By.xpath(flightChooseButtonLocator));
			waitAndClick(mostExpensiveChooseButton);
		}
	}

	public enum Stops {
		NONSTOP("nonstop"), UP_TO_1_STOP("up to 1 stop");

		private String stop;

		Stops(String stop) {
			this.stop = stop;
		}

		public By getCheckBox(){
			return  By.xpath(String.format(".//parent::div//parent::div//parent::div//span[text()='%s']//parent::*//preceding-sibling::div//div", stop));
		}
	}

	public enum Airlines {
		DELTA_AIR_LINES("Delta Air Lines");

		private String airline;

		Airlines(String airline) {
			this.airline = airline;
		}

		public By getCheckBox(){
			return  By.xpath(String.format(".//parent::div//parent::div//parent::div//span[text()='%s']//parent::*//preceding-sibling::div//div", airline));
		}
	}
}