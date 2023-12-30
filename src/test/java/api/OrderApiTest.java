package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.Order;

public class OrderApiTest {

    protected final static String ORDERS_ENDPOINT = "/orders";
    private Order order;
    private final RequestSpecification requestSpecification;

    public OrderApiTest(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Step("Создать заказ и получить track")
    public Response createOrder(){
        Response response =
                requestSpecification
                        .given()
                        .body(order)
                        .when()
                        .post(ORDERS_ENDPOINT);
        return response;
    }

    @Step("Получить список заказов")
    public Response getOrderList(){
        Response response =
                requestSpecification
                        .given()
                        .get(ORDERS_ENDPOINT);
        return response;
    }
}