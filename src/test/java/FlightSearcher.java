import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import pageobjects.FlightDetailsPage;
import pageobjects.HomePageSearchForm;
import pageobjects.SearchResultsPage;

import java.util.*;

import static pageobjects.HomePageSearchForm.*;
import static pageobjects.HomePageSearchForm.CabinClasses.*;
import static pageobjects.HomePageSearchForm.TravelersGroup.*;
import static pageobjects.HomePageSearchForm.DestinationSettings.*;
import static pageobjects.HomePageSearchForm.SearchFormTabs.*;
import static pageobjects.SearchResultsPage.Airlines.*;
import static pageobjects.SearchResultsPage.Stops.*;

public class FlightSearcher {
	private static final String DEPARTING = "San Francisco, CA (SFO)";
	private static final String ARRIVAL = "New York City, NY (NYC)";
	private static final String START_DATE = "April 20, 2020";
	private static final String END_DATE = "April 25, 2020";
	private static final Set<SearchResultsPage.Stops> STOPS = new HashSet<SearchResultsPage.Stops>(Arrays.asList(NONSTOP, UP_TO_1_STOP));
	private static final Set<SearchResultsPage.Airlines> AIRLINES = new HashSet<SearchResultsPage.Airlines>(Arrays.asList(DELTA_AIR_LINES));
	private static final Map<TravelersGroup, Integer> TRAVELERS_GROUPS;
	static {
		TRAVELERS_GROUPS = new HashMap<TravelersGroup, Integer>();
		TRAVELERS_GROUPS.put(ADULTS, 2);
	}
	private WebDriver driver;
	private HomePageSearchForm homePageSearchForm;
	private SearchResultsPage searchResultsPage;
	private FlightDetailsPage flightDetailsPage;


	@BeforeMethod
	public void setUp() {
		FirefoxOptions options = new FirefoxOptions();
		System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
		options.addArguments("start-maximized");
		driver = new FirefoxDriver(options);

		homePageSearchForm = new HomePageSearchForm(driver);
		searchResultsPage = new SearchResultsPage(driver);
		flightDetailsPage = new FlightDetailsPage(driver);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void search() {
		openFlightsTab();
		enterFlightData(ROUND_TRIP,DEPARTING,ARRIVAL,START_DATE, END_DATE,TRAVELERS_GROUPS,PREMIUM_ECONOMY);
		pressSearch();
		filterTheResults(STOPS, AIRLINES);
		processTheResults(TRAVELERS_GROUPS);
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
	private void enterFlightData(DestinationSettings destinationSettings, String departing, String arrival, String startDate, String endDate, Map<TravelersGroup, Integer> travelersGroups, CabinClasses cabinClasses) {
		homePageSearchForm.selectDestinationSettings(destinationSettings);
		homePageSearchForm.enterDeparting(departing);
		homePageSearchForm.enterArrival(arrival);
		homePageSearchForm.enterDates(startDate, endDate);
		homePageSearchForm.selectTravelers(travelersGroups);
		homePageSearchForm.selectCabinClass(cabinClasses);
	}

	@Step("6. Press Search button")
	private void pressSearch() {
		homePageSearchForm.pressSearchButton();
	}

	@Step("7. Wait results loading" +
			"8. Filter results")
	private void filterTheResults(Set<SearchResultsPage.Stops> stops, Set<SearchResultsPage.Airlines> airlines) {
		searchResultsPage.waitForResultsLoading();
		searchResultsPage.selectStops(stops);
		searchResultsPage.selectAirlines(airlines);
	}

	@Step("9. Print and save result")
	private void processTheResults(Map<TravelersGroup, Integer> travelersGroups) {
		searchResultsPage.findMostExpensive();
		flightDetailsPage.waitForPageLoaded();
		flightDetailsPage.printFlightInfo(travelersGroups);
		flightDetailsPage.makePageScreenshot();
	}
}