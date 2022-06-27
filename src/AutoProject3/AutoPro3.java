package AutoProject3;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AutoPro3 {

    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "/Users/sabinahuseynly/Documents/BrowserDriver/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get("https://www.cars.com/");

//2. Verify the default selected options for the dropdowns:
        Select select0 = new Select(driver.findElement(By.xpath("//select[@name='stock_type']")));
        WebElement firstSelectedOption = select0.getFirstSelectedOption();
        Assert.assertEquals(firstSelectedOption.getText(), "New & used cars");

//        Select select1 = new Select (driver.findElement(By.xpath("//select[@id='makes']")));
//        WebElement firstSelectedOption1 = select1.getFirstSelectedOption();
//        Assert.assertEquals(firstSelectedOption1.getText(),"All makes");
//
//        Select select2 = new Select (driver.findElement(By.xpath("//select[@id='models']")));
//        WebElement firstSelectedOption2 = select2.getFirstSelectedOption();
//        Assert.assertEquals(firstSelectedOption2.getText(),"All models");
//
//        Select select3 = new Select (driver.findElement(By.xpath("//select[@id='make-model-max-price']")));
//        WebElement firstSelectedOption3 = select3.getFirstSelectedOption();
//        Assert.assertEquals(firstSelectedOption3.getText(),"No max price");

//        Select select4 = new Select (driver.findElement(By.xpath("//select[@id='make-model-maximum-distance']")));
//        WebElement firstSelectedOption4 = select4.getFirstSelectedOption();
//        Assert.assertEquals(firstSelectedOption4, "20 miles");

//3. in the new/used dropdown box, verify that the expected values are the following:
        Select select = new Select(driver.findElement(By.xpath("//select[@name='stock_type']")));
        List<WebElement> options = select.getOptions();
        List<String> allNewUsed = new ArrayList<>();
        for (WebElement option : options) {
            allNewUsed.add(option.getText());
        }
        System.out.println(allNewUsed);
//4. Choose used cars from new/used dropdown
        new Select(driver.findElement(By.xpath("//select[@name='stock_type']"))).selectByVisibleText("Used cars");
//5.Choose Tesla from Make dropdown
        new Select(driver.findElement(By.xpath("//select[@id='makes']"))).selectByVisibleText("Tesla");
        new Select(driver.findElement(By.xpath("//select[@name='models[]']"))).selectByVisibleText("All models");
//6. Verify Models dropdown contains the following
        Select selectM = new Select(driver.findElement(By.xpath("//select[@name='models[]']")));
        List<WebElement> optionM = select.getOptions();
        List<String> allModels = new ArrayList<>();
        for (WebElement option : optionM) {
            allModels.add(option.getText());
        }
        System.out.println(allModels);
//7,8,9.choose MOdel S/ $100,000/ 50miles
        new Select(driver.findElement(By.xpath("//select[@name='models[]']"))).selectByVisibleText("Model S");
        new Select(driver.findElement(By.xpath("//select[@id='make-model-max-price']"))).selectByVisibleText("$100,000");
        new Select(driver.findElement(By.xpath("//select[@id='make-model-maximum-distance']"))).selectByVisibleText("50");
//10 Enter 22182 ZIP snf click search
        driver.findElement(By.xpath("//input[@id='make-model-zip']")).sendKeys(Keys.chord("22182") + Keys.ENTER);

//11. in the next search results page, verify that there are 20 results on the page and each search result title contains "Tesla Model S"
        List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"vehicle-cards-container\"]"));
        Assert.assertEquals(20, elements.size());
        for (WebElement element : elements) {
            Assert.assertTrue(element.getText().contains("Tesla Model S"));
        }
        elements.get(elements.size() - 1).click();
        Assert.assertTrue(driver.getTitle().contains("Tesla Model S"));

//12. choose lowest price from sort by dropdown and verify that all the results are sorted in ascending order of the price:
        driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]")).click();
            new Select(driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]"))).selectByVisibleText("Lowest Price");

                List<WebElement> priceList = driver.findElements(By.xpath("//*[@class=\"price-section price-section-vehicle-card\"])"));
                List<Double> actualList = new ArrayList<>();
                for (WebElement element : priceList) {
                   actualList.add(Double.valueOf(element.getText().replace("$","").replace(",", "")));

                List<WebElement> priceList2 = driver.findElements(By.xpath("//*[@class=\"price-section price-section-vehicle-card\"])"));
                List<Double> expectedList = new ArrayList<>();
                    for (WebElement element2 : priceList2) {
                 expectedList.add(Double.valueOf(element.getText().replace("$","").replace(",", "")));

                    Collections.sort(actualList);
                    Assert.assertEquals(actualList,expectedList);
                }
                System.out.println();

//13. choose the highest Mileage from Sort by dropdown and verify that all the result are sorted in descending order of the mileage
                    driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]")).click();
                    new Select(driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]"))).selectByVisibleText("Highest mileage");

                 List<WebElement> actualMileage = driver.findElements(By.xpath("//*[@class='mileage']"));
                 List<Double> actualMileageList = new ArrayList<>();
                 for(WebElement webElement : actualMileage){
                     actualMileageList.add(Double.valueOf((webElement.getText().replace(",","").replace("mi.",""))));
                  List<WebElement> expectedMileage = driver.findElements(By.xpath("//*[@class='mileage']"));
                  List<Double>expectedMileageList = new ArrayList<>();
                  for(WebElement webElement1 : expectedMileage){
                      expectedMileageList.add(Double.valueOf((webElement1.getText().replace(",","").replace("mi.",""))));
                      Collections.sort(actualMileageList);
                      Assert.assertEquals(actualMileageList,expectedMileageList);
                  }
                     System.out.println();
                 }
  // Choose Oldest year from Sort by dropdown and verify that all the results are sorted in ascending order of the year
                    driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]")).click();
                    new Select(driver.findElement(By.xpath("//*[@id=\"sort-dropdown\"]"))).selectByVisibleText("Oldest year");
                List<WebElement> oldest = driver.findElements(By.xpath("//*[@data-linkname=\"vehicle-listing\"]"));
                List<Double> actualYear = new ArrayList<>();
                for(WebElement webElement : oldest){
                    actualYear.add(Double.valueOf((webElement.getText().substring(0,4))));
                 List<Double> expectedYear = new ArrayList<>(actualYear);
                 Collections.sort(actualYear);
                 Assert.assertEquals(actualYear, expectedYear);
                }
                    System.out.println();
            }
             driver.close();
        }
    }


