package ru.shumova.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.shumova.praktikum.dto.GetOrderListRequest;
import ru.shumova.praktikum.service.OrderService;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    OrderService orderService = new OrderService();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderListTest() {
        GetOrderListRequest request = new GetOrderListRequest().setPage(0);
        orderService.getOrdersList(request).then()
                .statusCode(200).body("orders", notNullValue());
    }
}
