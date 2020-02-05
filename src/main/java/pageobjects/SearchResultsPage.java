package pageobjects;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;


public class SearchResultsPage {
	@FindBy(id = "search-tab-flights")
	private static WebElement flightsTab;
	@FindBy(id = "trip-type-round-trip")
	private static WebElement roundTripRadioButton;
	@FindBy(xpath = "//input[contains(@id,'flight-departure-airport')]")
	private WebElement departureInput;
	@FindBy(xpath = "//input[contains(@id,'flight-arrival-airport')]")
	private WebElement arrivalInput;
	@FindBy(xpath = "//input[@placeholder='Departing – Returning']")
	private WebElement dateInput;
	@FindBy(xpath = "//div[@class='CalendarCard__Card-sc-1jxm5yu-0 dXbiom sc-kUaPvJ kMvfpG']")
	private WebElement calendarCard;
	@FindBy(id = "traveler-selection-readonly-input")
	private WebElement travelerSelectionButton;
	@FindBy(id = "traveler-selection-done-button")
	private WebElement travelerSelectionDoneButton;
	@FindBy(id = "cabin-class-select")
	private WebElement cabinClassSelect;
	@FindBy(xpath = "//button[@data-autobot-element-id='HOME_FLIGHTS_SUBMIT_BUTTON']")
	private WebElement searchButton;

	private WebDriver driver;
	private WebDriverWait wait;


	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
	}

	public void selectStops(Set<Stops> stops) {

	}

	public void selectAirlines(Set<Airlines> airlines) {

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