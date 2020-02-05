import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pageobjects.HomePageSearchForm;
import pageobjects.SearchResultsPage;

import java.util.HashMap;
import java.util.Map;

import static pageobjects.HomePageSearchForm.*;
import static pageobjects.HomePageSearchForm.CabinClasses.*;
import static pageobjects.HomePageSearchForm.TravelersGroup.*;
import static pageobjects.HomePageSearchForm.DestinationSettings.*;
import static pageobjects.HomePageSearchForm.SearchFormTabs.*;

public class FlightSearcher {
	private WebDriver driver;
	private HomePageSearchForm homePageSearchForm;
	private SearchResultsPage searchResultsPage;

	@BeforeMethod
	public void setUp() {
		FirefoxOptions options = new FirefoxOptions();
		System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
		options.addArguments("start-maximized");
		driver = new FirefoxDriver(options);

		homePageSearchForm = new HomePageSearchForm(driver);
		searchResultsPage = new SearchResultsPage(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void search() {
		openFlightsTab();
		enterFlightData();
		pressSearch();
		filterTheResults();
		processTheResults();
	}

	@Step("1. Click on the Flights tab")
	private void openFlightsTab() {
		homePageSearchForm.loadThePage();
		homePageSearchForm.clickTab(FLIGHTS);
	}

	@Step("2. Select destination settings" +
			"3. Enter departing, arrival, dates" +
			"4. Select travelers" +
			"5. Select cabin class")
	private void enterFlightData() {
		Map<TravelersGroup, Integer> map = new HashMap<TravelersGroup, Integer>();
		map.put(ADULTS, 2);
		homePageSearchForm.selectDestinationSettings(ROUND_TRIP);
		homePageSearchForm.enterDeparting("San Francisco, CA (SFO)");
		homePageSearchForm.enterArrival("New York City, NY (NYC)");
		homePageSearchForm.enterDates("April 20, 2020", "April 25, 2020");
		homePageSearchForm.selectTravelers(map);
		homePageSearchForm.selectCabinClass(PREMIUM_ECONOMY);
	}

	@Step("6. Press Search button")
	private void pressSearch() {
		homePageSearchForm.pressSearchButton();
	}

	@Step("7. Wait results loading" +
			"8. Filter results")
	private void filterTheResults() {
		//searchResultsPage.wait()
		searchResultsPage.selectStops();
		searchResultsPage.selectAirlines();

	}

	@Step("9. Print and save result")
	private void processTheResults() {

	}
}