package ru.shumova.praktikum.service;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shumova.praktikum.dto.CancelOrderRequest;
import ru.shumova.praktikum.dto.CreateOrderRequest;
import ru.shumova.praktikum.dto.GetOrderListRequest;

import static ru.shumova.praktikum.util.RequestHelper.*;

public class OrderService {
    @Step("Получение списка заказов")
    public Response getOrdersList(GetOrderListRequest request) {
        return getRequestSpec().body(request).when().get(ORDERS_API_PATH);
    }

    @Step("Создание заказа")
    public Response createOrder(CreateOrderRequest request) {
        return getRequestSpec().body(request).when().post(ORDERS_API_PATH);
    }

    @Step("Отмена заказа")
    public void cancelOrder(CancelOrderRequest request) {
        getRequestSpec().body(request).when().put(ORDERS_API_CANCEL_PATH);
    }
}
