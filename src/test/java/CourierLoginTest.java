import api.BaseApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import api.CourierApi;
import org.example.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest extends BaseApi {

    private CourierApi courierApi;

    @Before
    public void setUp() {
        super.setupRequestSpecification();
        this.courierApi = new CourierApi(requestSpecification);
    }


    @Test
    @DisplayName("Проверка авторизации курьера (успешно)")
    @Description("Успешная проверка логина курьера")
    public void checkLoginCourier(){
        courierApi.setCourier(new Courier("ya", "1234", "saske"));
        courierApi.createCourier();
        courierApi.loginCourier()
                .then().statusCode(SC_OK)
                .and()
                .assertThat().body("id", notNullValue());
    }


    @Test
    @DisplayName("Проверка авторизации курьера без пароля")
    @Description("Проверка, что нельзя залогиниться без ввода пароля")
    public void checkLoginCourierWithoutPassword(){
        courierApi.setCourier(new Courier("ya", ""));
        courierApi.loginCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Проверка авторизации курьера без логина")
    @Description("Проверка, что нельзя залогиниться без ввода логина")
    public void checkLoginCourierWithoutLogin(){
        courierApi.setCourier(new Courier("", "1234"));
        courierApi.loginCourier()
                .then().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Проверка авторизации курьера с некорректным логином и паролем")
    @Description("Проверка, что нельзя залогиниться с некорректным логином и паролем")
    public void checkLoginPasswordCourierIsIncorrect(){
        courierApi.setCourier(new Courier("null", "null"));
        courierApi.loginCourier()
                .then().statusCode(SC_NOT_FOUND)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void dataClean(){
        courierApi.deleteCourier();
    }
}