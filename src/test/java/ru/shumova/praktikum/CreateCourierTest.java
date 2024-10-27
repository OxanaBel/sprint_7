package ru.shumova.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.shumova.praktikum.dto.CourierCreateRequest;
import ru.shumova.praktikum.dto.CourierDeleteRequest;
import ru.shumova.praktikum.dto.CourierLoginRequest;
import ru.shumova.praktikum.service.CourierService;

import static org.hamcrest.Matchers.is;

public class CreateCourierTest {
    CourierService courierService = new CourierService();

    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Подготовка тестовой информации")
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(20);
        password = RandomStringUtils.randomAlphabetic(20);
        firstName = RandomStringUtils.randomAlphabetic(20);
    }

    @Test
    @DisplayName("Создание курьера")
    public void createCourierTest() {
        CourierCreateRequest request = new CourierCreateRequest(login, password, firstName);
        courierService.createCourier(request).then().statusCode(201).body("ok", is(true));
        finalizeTest();
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    public void createExistedLoginCourierTest() {
        CourierCreateRequest request = new CourierCreateRequest(login, password, firstName);
        courierService.createCourier(request);
        courierService.createCourier(request).then().statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера. Запрос без логина")
    public void createCourierWithoutLoginTest() {
        CourierCreateRequest request = new CourierCreateRequest(null, password, firstName);
        courierService.createCourier(request).then().statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера. Запрос без пароля")
    public void createCourierWithoutPasswordTest() {
        CourierCreateRequest request = new CourierCreateRequest(login, null, firstName);
        courierService.createCourier(request).then().statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Step("Удаление тестовой информации")
    public void finalizeTest() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(login, password);
        Integer id = courierService.loginCourier(loginRequest).then().extract().body().path("id");
        CourierDeleteRequest deleteRequest = new CourierDeleteRequest(String.valueOf(id));
        courierService.deleteCourier(deleteRequest);
    }
}
