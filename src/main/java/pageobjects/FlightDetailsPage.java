package pageobjects;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageobjects.HomePageSearchForm.TravelersGroup;
import org.apache.commons.io.FileUtils;

import java.io.File;

import java.io.IOException;
import java.util.*;

import static pageobjects.HomePageSearchForm.TravelersGroup.*;


public class FlightDetailsPage extends BasePage {
	@FindBy(xpath = "//button[@id='continue-price']")
	private WebElement continueButton;
	private static final String PATH = System.getProperty("user.dir");
	private static final String FS = System.getProperty("file.separator");
	private static final String OUTPUT_FILE_FORMAT = PATH + FS + "src" + FS + "main" + FS + "resources" + FS + "screenshots" + FS;

	public FlightDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void printFlightInfo(Map<TravelersGroup, Integer> travelersGroups) {
		for (Map.Entry<String, String> flightInfo : getFlightInfo(travelersGroups).entrySet()) {
			System.out.println(flightInfo.getValue() + " - " + flightInfo.getKey());
		}
	}

	public void makePageScreenshot() {
		File screenshot = ((TakesScreenshot) driver).
				getScreenshotAs(OutputType.FILE);
		String path = OUTPUT_FILE_FORMAT + screenshot.getName();

		try {
			FileUtils.copyFile(screenshot, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitForPageLoaded() {
		switchToCurrentTab("Departure Information");

		waitForVisibility(continueButton);
	}

	private Map<String, String> getFlightInfo(Map<TravelersGroup, Integer> travelersGroups) {
		Map<String, String> flightInfo = new LinkedHashMap<String, String>();

		List<WebElement> departureFlightInfo = driver.findElements(By.xpath("//div[contains(text(),'Departure Information')]//parent::div//div[contains(text(),'Flight')]"));

		List<WebElement> returnFlightInfo = driver.findElements(By.xpath("//div[contains(text(),'Return Information')]//parent::div//div[contains(text(),'Flight')]"));

		for (WebElement departureFlight : departureFlightInfo) {
			flightInfo.put(departureFlight.getText(), "Departure Flight Number");
		}

		for (WebElement returnFlight : returnFlightInfo) {
			flightInfo.put(returnFlight.getText(), "Return Flight Number");
		}

		Double priceTotal = Double.valueOf(continueButton.getText().substring(continueButton.getText().indexOf('$') + 1, continueButton.getText().indexOf('/'))) * travelersGroups.get(ADULTS);

		flightInfo.put(String.valueOf(priceTotal).concat("$"), "Total Trip Fare");

		return flightInfo;
	}
}