import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
//import io.restassured.response.Response;
//io.restassured.matcher.RestAssuredMatchers.*
//import io.restassured.RestAssured;


public class JsonplaceholderRestAPITest {
    static {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // Test 1
    @Test
    public void test1_OmnisLaborumOdio() {
        given().when().get("/albums").then().statusCode(200)
                .body("title", hasItem("omnis laborum odio"));
    }

    // Test 2
    @Test
    public void test2_200comments() {
        given().when().get("/comments").then().statusCode(200)
                .body("size()", greaterThanOrEqualTo(200));
    }

    // Test 3
    @Test
    public void test3_GetUsers() {
        given().when().get("/users").then().statusCode(200)
                .body("find {it.name == 'Ervin Howell'}.username", equalTo("Antonette"))
                .body("find {it.name == 'Ervin Howell'}.address.zipcode", equalTo("90566-7771"));
    }

    // Test 4
    @Test
    public void test4_Biz() {
        given().when().get("/comments").then().statusCode(200)
                .body("email.findAll {it.toString().endsWith('.biz')}.size()", greaterThan(0));
    }

    // Test 5
    @Test
    public void test5_Post() {
        String requestBody = "{\n"
                + "\"userId\": 11,\n"
                + "\"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n"
                + "\"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\n"
                + "reprehenderit molestiae ut ut quas totam\\nostrum rerum est autem sunt rem eveniet architecto\"\n"
                + "}";
        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("userId", equalTo(11))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("body", containsString("suscipit recusandae consequuntur expedita et cum"));
    }

}