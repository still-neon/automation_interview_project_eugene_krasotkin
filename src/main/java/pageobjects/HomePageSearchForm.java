package pageobjects;

import base.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;


public class HomePageSearchForm extends BasePage {
	private static final String URL = "https://www.priceline.com/";
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


	public HomePageSearchForm(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void loadThePage() {
		driver.get(URL);
	}

	public void clickTab(SearchFormTabs tab) {
		waitAndClick(tab.getTabButton());
	}

	public void selectDestinationSettings(DestinationSettings destinationSettings) {
		WebElement destinationSettingsButton = destinationSettings.getDestinationSettingsButton();
		if (!destinationSettingsButton.isSelected()) {
			waitAndClick(destinationSettingsButton);
		}
	}

	public void enterDeparting(String departing) {
		typeAndSelect(departureInput, departing);
	}

	public void enterArrival(String arrival) {
		typeAndSelect(arrivalInput,arrival);
	}

	public void enterDates(String departureDate, String arrivalDate) {
		waitAndClick(dateInput);
		waitAndClick(getStartDate(departureDate));
		waitAndClick(getStartDate(arrivalDate));
	}

	private WebElement getStartDate(String date) {
		return calendarCard.findElement(By.xpath(String.format(".//div[@aria-label='%s']", date)));
	}

	public void selectTravelers(Map<TravelersGroup, Integer> travelersGroupsInfo) {
		waitAndClick(travelerSelectionButton);
		for (Map.Entry<TravelersGroup, Integer> travelersGroupInfo : travelersGroupsInfo.entrySet()) {
			selectTraveler(travelersGroupInfo.getKey(), travelersGroupInfo.getValue());
		}
		waitAndClick(travelerSelectionDoneButton);
	}

	public void selectCabinClass(CabinClasses cabinClass) {
		waitAndClick(cabinClassSelect);
		WebElement option = cabinClassSelect.findElement(By.xpath(String.format("//option[normalize-space()='%s']", cabinClass.getOption())));
		waitAndClick(option);
	}

	public void pressSearchButton() {
		waitAndClick(searchButton);
	}

	private void typeAndSelect(WebElement dropDownElement, String text) {
		dropDownElement.sendKeys(text);
		By searchableValue = By.xpath(String.format("//div[contains(text(),'%s')]", text));
		waitForElementPresent(searchableValue);
		WebElement elementForSelection = driver.findElement(searchableValue);
		waitAndClick(elementForSelection);
	}

	private void selectTraveler(TravelersGroup travelersGroup, int number) {
		WebElement groupPlusButton = travelerSelectionButton.findElement(By.xpath(String.format("//span[text()='%s']//parent::div//button[contains(@id,'plus-button')]", travelersGroup.getGroup())));

		for (int i = 1; i < number; i++) {
			waitAndClick(groupPlusButton);
		}
	}

	@Getter
	public enum SearchFormTabs {
		FLIGHTS(flightsTab);

		private WebElement tabButton;

		SearchFormTabs(WebElement tabButton) {
			this.tabButton = tabButton;
		}
	}

	@Getter
	public enum DestinationSettings {
		ROUND_TRIP(roundTripRadioButton);

		private WebElement destinationSettingsButton;

		DestinationSettings(WebElement destinationSettingsButton) {
			this.destinationSettingsButton = destinationSettingsButton;
		}
	}

	@Getter
	public enum CabinClasses {
		PREMIUM_ECONOMY("Premium Economy");

		private String option;

		CabinClasses(String option) {
			this.option = option;
		}
	}

	@Getter
	public enum TravelersGroup {
		ADULTS("Adults");

		private String group;

		TravelersGroup(String group) {
			this.group = group;
		}
	}
}