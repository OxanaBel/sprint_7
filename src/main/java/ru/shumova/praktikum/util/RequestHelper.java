package ru.shumova.praktikum.util;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestHelper {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String COURIER_API_PATH = "/api/v1/courier";
    public static final String COURIER_API_LOGIN_PATH = "/api/v1/courier/login";
    public static final String ORDERS_API_PATH = "/api/v1/orders";
    public static final String ORDERS_API_CANCEL_PATH = "/api/v1/orders/cancel";

    public static RequestSpecification getRequestSpec() {
        return given().baseUri(BASE_URL).contentType(ContentType.JSON);
    }
}
