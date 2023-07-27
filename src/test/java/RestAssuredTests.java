import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

public class RestAssuredTests {
    private int bookingId;
    private ResponseBookingId bookingId2;
    @BeforeMethod
    public void setup(){
        RestAssured.baseURI="http://restful-booker.herokuapp.com";
        RestAssured.requestSpecification=new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
    }
    @Test(priority = 2)
    public void getAllBookingId(){
        Response response= RestAssured.given().log().all().get("/booking");
        response.then().statusCode(200);
        response.prettyPrint();
        ResponseBookingId[] bookingList = RestAssured.given().when().get("booking/").as(ResponseBookingId[].class);
        bookingId2 = bookingList[0];

    }
    @Test(priority = 1)
    public void createNewBooking(){
        BookingDates bookingDates = new BookingDates()
                .builder()
                .checkIn(LocalDate.of(2022,01,01))
                .checkOut(LocalDate.of(2023,12,31))
                .build();
        CreateBooking booking = new CreateBooking()
                .builder()
                .firstname("Olha")
                .lastname("Borzanova")
                .totalPrice(100)
                .depositPaid(true)
                .additionalNeeds("Breakfast")
                .bookingdates(bookingDates)
                .build();
        Response response = RestAssured
                .given()
                .body(booking)
                .when()
                .post("/booking");
        response.then().statusCode(200);
        response.prettyPrint();
        bookingId = response.as(ResponseBooking.class).getBookingId();
    }
    @Test(priority = 3)
    public void updateTotalPriceBooking() {
        JSONObject totalPriceUpdate = new JSONObject();
        totalPriceUpdate.put("totalPrice", 121);
        Response response = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .body(totalPriceUpdate.toString())
                .when()
                .patch("/booking/" + bookingId);

        response.prettyPrint();
        response.then().statusCode(200);
    }
    @Test(priority = 4)
    public void updateFirstNameAndAdditionalNeeds(){
        CreateBooking booking = RestAssured
                .given()
                .when()
                .get("/booking/" + bookingId2.getBookingId())
                .as(CreateBooking.class);
        booking.setFirstname("Anna");
        booking.setAdditionalNeeds("Dinner");

        Response response = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .body(booking)
                .when()
                .put("/booking/" + bookingId2.getBookingId());

        response.prettyPrint();
        response.then().statusCode(405);
    }
    @Test(priority = 5)
    public void deleteBooking(){
        Response response = RestAssured
                .given()
                .auth()
                .preemptive()
                .basic("admin", "password123")
                .delete("/booking/" + bookingId);
        response.prettyPrint();
        response.then().statusCode(201);

    }

}
