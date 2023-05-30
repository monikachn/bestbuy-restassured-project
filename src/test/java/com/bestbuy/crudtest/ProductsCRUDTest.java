package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBase {
    static String name = "Acer-Chromebook" + TestUtils.getRandomValue();
    static String type = "laptop";
    static double price = 1669.89;
    static double shipping = 5.99;
    static String upc = TestUtils.getRandomValue();
    static String description = "The stylish Chromebook";
    static String manufacturer = "Acer";
    static String model = TestUtils.getRandomValue();
    static String url = "https://pisces.bbystatic.com/" + TestUtils.getRandomValue() + ".jpg;maxHeight=640;maxWidth=550";
    static String image = TestUtils.getRandomValue();
    static int id;

    @Test
    public void test001() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .header("Accept", "application/json")
                .when()
                .body(productPojo)
                .post()
                .then().log().body().statusCode(201);
        id = response.extract().path("id");
    }

    @Test
    public void test002() {
        int pId = given()
                .pathParams("id", id)
                .when()
                .get("/{id}")
                .then().statusCode(200)
                .extract()
                .path("id");
        Assert.assertEquals(pId, id);
    }

    @Test
    public void test003() {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName("Iphone Pro 14");
        productPojo.setType("Mobile");
        productPojo.setPrice(999);
        productPojo.setShipping(5.22);
        productPojo.setUpc(TestUtils.getRandomValue());
        productPojo.setDescription("The Stylish Phone");
        productPojo.setManufacturer("Apple");
        productPojo.setModel(TestUtils.getRandomValue());
        productPojo.setUrl("https://pisces.bbystatic.com/.jpg;maxHeight=640;maxWidth=550");
        productPojo.setImage("https://pisces.bbystatic.com");
        Response response = given()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .when()
                .body(productPojo)
                .put("/{id}");
        response.then().log().body().statusCode(200);
    }

    @Test
    public void test004() {
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