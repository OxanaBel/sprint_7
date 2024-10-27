package ru.shumova.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.shumova.praktikum.dto.CancelOrderRequest;
import ru.shumova.praktikum.dto.CreateOrderRequest;
import ru.shumova.praktikum.service.OrderService;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    OrderService orderService = new OrderService();
    private final String[] color;

    private CreateOrderRequest request;
    private String track;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] colours() {
        return new Object[][] {
                new String[][]{{"GREY"}},
                new String[][]{{"BLACK"}},
                new String[][]{{"BLACK", "GREY"}},
                new String[][]{{}},
        };
    }

    @Before
    @Step("Подготовка тестовой информации")
    public void setUp() {
        request = new CreateOrderRequest()
                .setFirstName(randomString())
                .setLastName(randomString())
                .setAddress(randomString())
                .setMetroStation(randomInteger())
                .setPhone(randomString())
                .setRentTime(randomInteger())
                .setDeliveryDate(LocalDate.now().toString())
                .setComment(randomString())
                .setColor(color);
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderWIthDifferentColorsTest() {
        Response response = orderService.createOrder(request);
        response.then().statusCode(201).body("track", notNullValue());
        track = response.then().extract().body().path("track").toString();
    }

    @After
    public void finalizeTest() {
        orderService.cancelOrder(new CancelOrderRequest(track));
    }

    private String randomString() {
        return RandomStringUtils.randomAlphabetic(20);
    }

    private Integer randomInteger() {
        return RandomUtils.nextInt();
    }
}
