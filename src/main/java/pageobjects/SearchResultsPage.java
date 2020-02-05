package pageobjects;

import base.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;


public class SearchResultsPage extends BasePage{
	@FindBy(xpath = "//button[@data-autobot-element='FLIGHTS_LISTINGS_FILTER_STOPS_NONE']")
	private WebElement stopsNoneButton;
	@FindBy(xpath = "//button[@data-autobot-element='FLIGHTS_LISTINGS_FILTER_AIRLINES_NONE']")
	private WebElement airlinesNoneButton;





	public SearchResultsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public void selectStops(Set<Stops> stops) {
		stopsNoneButton.click();
		for(Stops stop: stops) {
			WebElement stopPlace = stopsNoneButton.findElement(By.xpath(String.format("//ancestor::div//span[normalize-space()='%s']//ancestor::label[contains(@for,'stops')]//input", stop.getStop())));
			waitAndClick(stopPlace);
		}
	}

	public void selectAirlines(Set<Airlines> airlines) {
		airlinesNoneButton.click();
		for(Airlines airline: airlines) {
			WebElement airlineName = stopsNoneButton.findElement(By.xpath(String.format("//ancestor::div//span[normalize-space()='%s']//ancestor::label[contains(@for,'airlines')]//input", airline.getAirline())));
			waitAndClick(airlineName);
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