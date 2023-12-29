import api.BaseApiTest;
import api.OrderApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class OrderListTest extends BaseApiTest {

    private OrderApiTest orderApi;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.orderApi = new OrderApiTest(requestSpecification);
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Успешное получение списка заказов")
    public void checkOrderList(){
        orderApi.getOrderList()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}