package com.bestbuy.crudtest;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class StoresCRUDTest extends TestBase {
    static String name="City corner store";
    static String type="BigBox";
    static String address="13513 Ridgedale Dr";
    static String address2="Stepny Green";
    static String city="Hopkins";
    static String state="MN";
    static String zip="55305";
    static double lat=44.9696;
    static  double lng=99.449;
    static String hours="Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int id;
    @Test
    public void test001(){
        Map<String,Object> services = new HashMap<>();
        services.put("name","Geek Squad Services");
        services.put("id","01");
        StorePojo storePojo=new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        storePojo.setServices(services);

        ValidatableResponse response= given()
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .body(storePojo)
                .post()
                .then().log().body().statusCode(201);
        id=response.extract().path("id");
    }
    @Test
    public void test002(){
        int sId=given()
                .pathParams("id",id)
                .when()
                .get("/{id}")
                .then().statusCode(200)
                .extract()
                .path("id");
        Assert.assertEquals(sId,id);
    }
    @Test
    public void test003(){
        StorePojo storePojo=new StorePojo();
        storePojo.setName("CityFoods store");
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours("Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8");
        Response response= given()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .when()
                .body(storePojo)
                .patch("/{id}");
        response.then().log().body().statusCode(200);
    }
    @Test
    public void test004(){
        given()
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);

        given()
                .pathParam("id", id)
                .when()
                .get("/{id}")
                .then().statusCode(404);
    }
}
