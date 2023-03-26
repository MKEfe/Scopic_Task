package com.efe.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegioJetTest_API {

/*
    @BeforeAll
    public static void init(){
        //save baseurl inside this variable so that we dont need to type each http method.
        baseURI = "https://brn-ybus-pubapi.sa.cz/restapi/routes/search/simple?tariffs=REGULAR&toLocationType=CITY&toLocationId=10202002&fromLocationType=CITY&fromLocationId=10202000&departureDate=2023-04-03";
    } */

    @DisplayName("RegioJet API Test")
    @Test
    public void test(){
        String url = "https://brn-ybus-pubapi.sa.cz/restapi/routes/search/simple?tariffs=REGULAR&toLocationType=CITY&toLocationId=10202002&fromLocationType=CITY&fromLocationId=10202000&departureDate=2023-04-03";

        Response response = given().accept(ContentType.JSON)
                .when()
        .get(url).then().statusCode(200).extract().response();
       // response.prettyPrint();

        List<String> routeCount= response.path("routes");
        System.out.println("There is " + routeCount.size() + " route options available");

        for (int i = 0; i < routeCount.size(); i++) {

            System.out.println(i+". travel option details for:");
            String id = response.path("routes[" +i+"].id");
            String departureStation = (response.path("routes[" +i+"].departureStationId")).toString().replace("372825007", "Ostrava");
            String arrivalStation = (response.path("routes[" +i+"].arrivalStationId")).toString().replace("3088864001", "Brno");
            String departureTime = response.path("routes[" +i+"].departureTime");
            OffsetDateTime odt1 = OffsetDateTime.parse(departureTime);
            LocalDateTime ldt1 = odt1.toLocalDateTime();
            String arrivalTime = response.path("routes[" +i+"].arrivalTime");
            Float priceFrom = response.path("routes[" +i+"].priceFrom");
            String travelTime = response.path("routes[" +i+"].travelTime");

            System.out.println("Route id ="+id);
            System.out.println("Departure Station Name ="+ departureStation);
            System.out.println("Arrival Station Name ="+ arrivalStation);
            System.out.println("Departure Time ="+ldt1);
            System.out.println("Arrival Time ="+arrivalTime);
            System.out.println("Price ="+priceFrom);
            System.out.println("Travel Time ="+travelTime);
        }





    }

}
//.queryParam("{\"tariffs\":\"REGULAR\"}", "{\"toLocationType\":\"CITY\"}", "{\"toLocationId\":\"10202002\"}", "{\"fromLocationType\":\"CITY\"}", "{\"fromLocationId\":\"10202000\"}", "{\"departureDate\":\"2023-04-03\"}")
//                .log().all()