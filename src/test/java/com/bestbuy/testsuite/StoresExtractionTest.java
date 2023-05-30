package com.bestbuy.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StoresExtractionTest {
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

    // 1) Extract the value of limit
    @Test
    public void test001() {
        int limit = response.extract().path("limit");

        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of limit is : " + limit);
        System.out.println("------------------End of Test---------------------------");
    }

    @Test
    public void test002() {
        int total = response.extract().path("total");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of total is : " + total);
        System.out.println("------------------End of Test---------------------------");
    }

    @Test
    //Extract the name of 5th store
    public void test003() {
        String nameOfStore = response.extract().path("data[4].name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of total is : " + nameOfStore);
        System.out.println("------------------End of Test---------------------------");
    }

    //Extract the names of all the store
    @Test
    public void test004() {
        List<String> nameOfAllStore = response.extract().path("data.name");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of total is : " + nameOfAllStore);
        System.out.println("------------------End of Test---------------------------");
    }

    //Extract the storeId of all the store
    @Test
    public void test005() {
        List<Integer> storeId = response.extract().path("data.id");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of total is : " + storeId);
        System.out.println("------------------End of Test---------------------------");
    }

    //Print the size of the data list
    @Test
    public void test006() {
        List<String> dataSize = response.extract().path("data");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The size of the data is : " + dataSize.size());
        System.out.println("------------------End of Test---------------------------");
    }

    @Test
    public void test007() {
        //Get all the value of the store where store name = St Cloud
        List<HashMap<String, ?>> valves = response.extract().path("data.findAll{it.name == 'St Cloud'}");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The values of store name 'St Cloud' are: " + valves);
        System.out.println("------------------End of Test---------------------------");
    }

    //Get the address of the store where store name = Rochester
    @Test
    public void test008() {
        List<HashMap<String, ?>> address = response.extract().path("data.findAll{it.name == 'Rochester'}.address");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The address of store name 'Rochester' are: " + address);
        System.out.println("------------------End of Test---------------------------");
    }

    //Get all the services of 8th store
    @Test
    public void test009() {
        List<HashMap<String, ?>> listOfServices = response.extract().path("data[7].services");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The services of '8th store' are: " + listOfServices);
        System.out.println("------------------End of Test---------------------------");
    }

    //10. Get storeservices of the store where service name = Windows Store
    @Test
    public void test010() {
        List<HashMap<String, ?>> serviceName = response.extract().path("data.find{it.services}.services.findAll{it.name ='Windows Store'}.storeservices");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println(" Storeservices of the store where service name = Windows Store : " + serviceName);
        System.out.println("------------------End of Test---------------------------");

    }

    //11. Get all the storeId of all the store
    @Test
    public void test011() {
        List<Integer> storeIds = response.extract().path("data.services.storeservices.storeId");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("List Of store Id is: " + storeIds);
        System.out.println("------------------End of Test---------------------------");

    }

    //12. Get id of all the store
    @Test
    public void test0012() {
        List<Integer> storeId = response.extract().path("data.id");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The value of total is : " + storeId);
        System.out.println("------------------End of Test---------------------------");
    }

    //13. Find the store names Where state = ND
    @Test
    public void test013() {
        String stateND = response.extract().path("data[7].state");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The store names Where state  : " + stateND);
        System.out.println("------------------End of Test---------------------------");
    }

    //14. Find the Total number of services for the store where store name = Rochester
    @Test
    public void test014() {
        List<HashMap<String, ?>> values = response.extract().path("data.findAll{it.name == 'Rochester'}.services");

        System.out.println("------------------StartingTest---------------------------");
        System.out.println("The Total number of services for  the store name 'Rochester' are: " + values);
        System.out.println("------------------End of Test---------------------------");
    }

    //15. Find the createdAt for all services whose name = “Windows Store”
    @Test
    public void test0015() {
        List<HashMap<String, ?>> services = response.extract().path("data.services*.find{it.name=='Windows Store'}.storeservices.createdAt");
        System.out.println("Get the createdAt for all services whose name = Windows Store" + services);
    }

    //16. Find the name of all services Where store name = “Fargo”
    @Test
    public void test016() {
        List<HashMap<String, ?>> servicesFargo = response.extract().path("data.findAll{it.name == 'Fargo'}.services");
        System.out.println("------------------StartingTest---------------------------");
        System.out.println("name of all services Where store name = “Fargo” : " + servicesFargo);
        System.out.println("------------------End of Test---------------------------");
    }

    //Find the zip of all the store
    @Test
    public void test017() {
        List<HashMap<String, ?>> zipCode = response.extract().path("data.zip");
        System.out.println("zip of all store " + zipCode);
    }

    //18. Find the zip of store name = Roseville
    @Test
    public void test0018() {
        List<HashMap<String, ?>> zipCodeName = response.extract().path("data.findAll{it.name=='Roseville'}.zip");
        System.out.println("Find the zip of store name = Roseville " + zipCodeName);
    }

    //19. Find the storeservices details of the service name = Magnolia Home Theater
    @Test
    public void test0019() {
        List<HashMap<String, ?>> storeServices =  response.extract().path("data.services*.findAll{it.name=='Magnolia Home Theater'}.storeservices");
        System.out.println("Find the storeservices details of the service name = Magnolia Home Theater "+storeServices);
    }
    //20. Find the lat of all the stores
    @Test
    public void test0020(){
        List<?>latName  = response.extract().path("data.lat");
        System.out.println("Find the lat of all the stores "+latName);
    }


    }

