
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class smartbuyDiscount {

	public WebDriver driver;
	SoftAssert softassertProcess = new SoftAssert();

	@BeforeTest()

	public void doBeforeStart() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.get("https://smartbuy-me.com/smartbuystore/");

		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();

		driver.manage().window().maximize();

	}

	@Test()
	public void discount() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath(
				"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
				.click();
		driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
		driver.navigate().back();

		String the_actual_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();

		//
		String[] the_updated_actual_price = the_actual_price.split("JOD");

		String The_Final_actual_price = the_updated_actual_price[0].trim();

		String updated_the_actual_price = The_Final_actual_price.replace(",", ".");

		Double final_actualPrice = Double.parseDouble(updated_the_actual_price);
		System.out.println("final_actualPrice" + final_actualPrice);
		//

		String price_before_discount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
//
		String[] the_updated_price_before_discount = price_before_discount.split("JOD");

		String The_Final_price_before_discount = the_updated_price_before_discount[0].trim();

		String updated_price_before_discount = The_Final_price_before_discount.replace(",", ".");

		Double final_price_before_discount = Double.parseDouble(updated_price_before_discount);
		System.out.println("final_price_before_discount    " + final_price_before_discount);
		//

		String ratio_of_discount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();
		//
		String[] the_updated_ratio_of_discount = ratio_of_discount.split("%");

		String The_Final_ratio_of_discount = the_updated_ratio_of_discount[0].trim();

		Double The_updated_Final_ratio_of_discount = Double.parseDouble(The_Final_ratio_of_discount);

		//

		Double expected_price = Math.floor((The_updated_Final_ratio_of_discount / 100) * (final_price_before_discount))
				* 1000;

		Double final_expected_price = final_actualPrice - expected_price;

		System.out.println("final_expected_price    " + final_expected_price);

		softassertProcess.assertEquals(final_expected_price, final_actualPrice);

		softassertProcess.assertAll();

	}

}
