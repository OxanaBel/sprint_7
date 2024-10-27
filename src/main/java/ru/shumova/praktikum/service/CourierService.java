package ru.shumova.praktikum.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shumova.praktikum.dto.*;

import static ru.shumova.praktikum.util.RequestHelper.*;

public class CourierService {
    @Step("Создание курьера")
    public Response createCourier(CourierCreateRequest request) {
        return getRequestSpec().body(request).when().post(COURIER_API_PATH);
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierLoginRequest request) {
        return getRequestSpec().body(request).when().post(COURIER_API_LOGIN_PATH);
    }

    @Step("Удаление курьера")
    public void deleteCourier(CourierDeleteRequest request) {
        getRequestSpec().body(request).when().delete(COURIER_API_PATH);
    }
}
