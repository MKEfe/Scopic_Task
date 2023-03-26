package com.efe.tests;

import com.efe.utilities.Driver;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegioJetTest {


    //div[@class='page-layout max-h-screen overflow-y-auto py-4 lg:py-5']
    @Test
    public void testJegioJet() throws InterruptedException {

        // Navigate to RegioJet website
        Driver.getDriver().get("https://regiojet.com");
        WebElement cookies = Driver.getDriver().findElement(By.xpath("//button[normalize-space()='ALLOW ALL']"));
        cookies.click();

        Thread.sleep(2000);


        // Select Ostrava and Brno as departure and arrival cities
        WebElement fromCity =  Driver.getDriver().findElement(By.xpath("(//form[@action='#']/div/div/div/div/div)[1]"));
        fromCity.click();
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys("Ostrava").perform();
        action.sendKeys(Keys.ENTER).perform();


        WebElement toCity =  Driver.getDriver().findElement(By.xpath("(//form[@action='#']/div/div/div/div/div)[3]"));
        toCity.click();
        Actions action1 = new Actions(Driver.getDriver());
        action1.sendKeys("Brno").perform();
        action1.sendKeys(Keys.ENTER).perform();


        // Select Monday as the departure day

        WebElement pickDay =  Driver.getDriver().findElement(By.xpath("(//form[@action='#']/div/div/div/div/div)[4]"));
        pickDay.click();
        //WebElement monday = Driver.getDriver().findElement(By.xpath("//td[@aria-label='Select departure date: Monday, April 3, 2023']"));
        WebElement monday = Driver.getDriver().findElement(By.xpath("//td[contains(@aria-label, 'Monday') and @aria-disabled='false']"));
        Thread.sleep(2000);
        monday.click();

        //Search the results
        WebElement search = Driver.getDriver().findElement(By.xpath("//button[@data-id='search-btn']"));
        search.click();

        //Get the departure and arrrival Details

        //List<WebElement> departureArrivalTimes = Driver.getDriver().findElements(By.xpath("//h2[@class='h3']"));
        List<WebElement> departureArrivalTimes = Driver.getDriver().findElements(By.xpath("//div[@class='flex flex-wrap items-center gap-y-0.5 gap-1.5']//h2[@class='h3']"));
        List<WebElement> prices = Driver.getDriver().findElements(By.xpath("//button[contains (@data-id, 'connection-card-price')]"));
        List<WebElement> travelLenghts = Driver.getDriver().findElements(By.xpath("//span[contains(@aria-label, 'Journey length')]"));
        List<WebElement> isDirect = Driver.getDriver().findElements(By.xpath("//span[@class='text-13 lg:text-14 pl-0.5']"));

        // Print the Travel Details to the console
        ArrayList <String> arrivaltimes = new ArrayList<>();
        ArrayList <String> travelTimes = new ArrayList<>();
        ArrayList <Double> priceList = new ArrayList<>();
        int count = 1;
        int i;
        for (i=0; i<departureArrivalTimes.size(); ++i) {
            System.out.println(count + ". Travel Option Details: " );
            System.out.println("Depature-Arrival Time: "+ departureArrivalTimes.get(i).getText());
            System.out.println("Price: "+ prices.get(i).getText());
            System.out.println("Travel Lenght: "+ travelLenghts.get(i).getText());
            assertTrue(isDirect.get(i).getText().contains("Direct"));

            //GET Travel Lenghts of each travel to find the shortest time
            travelTimes.add(travelLenghts.get(i).getText());

            //GET Price of each travel to find the lowest price.
            priceList.add(Double.valueOf(prices.get(i).getText().replace("from ","").replace("€", "")));
            count++;
        }

        // Assert the given conditions are satisfied,

        for (WebElement element: departureArrivalTimes.toArray(new WebElement[0])){
            element.click();
            Thread.sleep(500);

            // Assertion for the departure stations are from "Ostrava"
            WebElement departure= Driver.getDriver().findElement(By.xpath("//span[@class='pr-0.5']"));
            assertTrue(departure.getText().contains("Ostrava"));

            // Assertion for the arrival stations are "Brno"
            WebElement arrival= Driver.getDriver().findElement(By.xpath("//div[@class='cardOpenTransfer font-bold sm:text-14']"));
            assertTrue(arrival.getText().contains("Brno"));

            // GET arrival times for finding the soonest arrival time.
            WebElement arrivalTime= Driver.getDriver().findElement(By.xpath("//div[@class='cardOpenTransfer self-center font-bold sm:text-14'][2]"));
            arrivaltimes.add(arrivalTime.getText());

            // Get number of stops= Unable find any information for stop count.

            element.click();
        }

        //Create 3 cases which will choose optimal connection, based on the following criteria:
        System.out.println("a) the soonest arrival time to Brno starting from midnight  = "
                + Collections.min(arrivaltimes));
        System.out.println("b) the shortest time spent with travelling – sitting in the train = " + Collections.min(travelTimes));
        System.out.println("c) the lowest price of the journey  = €" + Collections.min(priceList));

        Driver.getDriver().quit();

    }
/*
NOTE: number of stops: cannot find related information .
 */
    }


