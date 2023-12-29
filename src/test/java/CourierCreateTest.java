import api.BaseApiTest;
import api.CourierApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierCreateTest extends BaseApiTest {

    private CourierApiTest courierApi;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.courierApi = new CourierApiTest(requestSpecification);
    }


    @Test
    @DisplayName("Проверка создания курьера (успешно)")
    @Description("Успешная проверка создания курьера")
    public void checkCreateCourier(){
        courierApi.setCourier(new Courier("ya", "1234", "saske"));
        courierApi.createCourier()
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("ok", is(true));
    }


    @Test
    @DisplayName("Проверка создания курьера с дублирующими данными")
    @Description("Проверка, что нельзя создать дубль курьера")
    public void checkCreateDuplicateCourier(){
        courierApi.setCourier(new Courier("ya", "1234", "saske"));
        courierApi.createCourier();
        courierApi.createCourier()
                .then().statusCode(SC_CONFLICT)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Проверка создания курьера без пароля и имени")
    @Description("Проверка, что нельзя создать курьера без пароля и имени")
    public void checkCreateCourierWithoutPassName(){
        courierApi.setCourier(new Courier("ya", "", ""));
        courierApi.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Проверка создания курьера без логина и имени")
    @Description("Проверка, что нельзя создать курьера без логина и имени")
    public void checkCreateCourierWithoutLoginName(){
        courierApi.setCourier(new Courier("", "1234", ""));
        courierApi.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Проверка создания курьера без пароля и логина")
    @Description("Проверка, что нельзя создать курьера без пароля и логина")
    public void checkCreateCourierWithoutLoginPass(){
        courierApi.setCourier(new Courier("", "", "saske"));
        courierApi.createCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void dataClean(){
        courierApi.deleteCourier();
    }
}