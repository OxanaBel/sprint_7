package ru.shumova.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.shumova.praktikum.dto.CourierCreateRequest;
import ru.shumova.praktikum.dto.CourierDeleteRequest;
import ru.shumova.praktikum.dto.CourierLoginRequest;
import ru.shumova.praktikum.service.CourierService;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CourierService courierService = new CourierService();

    private String login;
    private String password;

    @Before
    @Step("Подготовка тестовой информации")
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(20);
        password = RandomStringUtils.randomAlphabetic(20);
        String firstName = RandomStringUtils.randomAlphabetic(20);
        CourierCreateRequest request = new CourierCreateRequest(login, password, firstName);
        courierService.createCourier(request);
    }

    @Test
    @DisplayName("Логин курьера")
    public void loginCourierTest() {
        CourierLoginRequest request = new CourierLoginRequest(login, password);
        courierService.loginCourier(request).then().statusCode(200).body("id", notNullValue());
    }

    @Test
    @DisplayName("Логин курьера без поля 'Login'")
    public void loginWithoutLoginTest() {
        CourierLoginRequest request = new CourierLoginRequest(StringUtils.EMPTY, password);
        courierService.loginCourier(request).then()
                .statusCode(400).body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера без поля 'Password'")
    public void loginWithoutPasswordTest() {
        CourierLoginRequest request = new CourierLoginRequest(login, StringUtils.EMPTY);
        courierService.loginCourier(request).then()
                .statusCode(400).body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера с неверным полем 'Login'")
    public void loginWithWrongLoginTest() {
        String randomLogin = RandomStringUtils.random(20);
        CourierLoginRequest request = new CourierLoginRequest(randomLogin, password);
        courierService.loginCourier(request).then()
                .statusCode(404).body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера с неверным полем 'Password'")
    public void loginWithWrongPasswordTest() {
        String randomPass = RandomStringUtils.random(20);
        CourierLoginRequest request = new CourierLoginRequest(login, randomPass);
        courierService.loginCourier(request).then()
                .statusCode(404).body("message", is("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление тестовой информации")
    public void finalizeTest() {
        CourierLoginRequest loginRequest = new CourierLoginRequest(login, password);
        int id = courierService.loginCourier(loginRequest).then().extract().body().path("id");
        CourierDeleteRequest deleteRequest = new CourierDeleteRequest(String.valueOf(id));
        courierService.deleteCourier(deleteRequest);
    }
}
