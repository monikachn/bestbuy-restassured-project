package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoresAssertionTest extends TestBase {
    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
    }

    //Verify the if the total is equal to 1561
    @Test
    public void test001() {
        response.body("total", equalTo(1561));
    }

    //Verify the if the stores of limit is equal to 10
    @Test
    public void test002() {
        response.body("limit", equalTo(10));
    }

    //Check the single ‘Name’ in the Array list (Inver Grove Heights)
    @Test
    public void testCheckSingleName() {
        response.body("data.name", hasItem("Inver Grove Heights"));
    }

    //Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)
    @Test
    public void testCheckMultipleNames() {
        response.body("data.name", hasItems("Roseville", "Burnsville", "Maplewood"));

    }

    //Verify the storied=7 inside storeservices of the third store of second services
    // Verify the state = MN of forth store
    //8. Verify the store name = Rochester of 9th store
    //9. Verify the storeId = 11 for the 6th store
    //10. Verify the serviceId = 4 for the 7th store of forth service
    @Test
    public void testVerifyStoriedThirdstoreOfSecondServices() {
        response.body("data[2].services[1].storeservices.storeId", equalTo(7))
                .body("data[3].state", equalTo("MN"))
                .body("data[8].name", is("Rochester"))
                .body("data[5].id", is(11))
                .body("data[6].services[3].storeservices.serviceId", is(4))
        ;
    }

    //Check hash map values ‘createdAt’ inside storeservices map where store name = Roseville

    @Test
    public void test007() {
        response.body("data[2].services.storeservices.createdAt", hasItem("2016-11-17T17:57:09.417Z"));
    }

}
