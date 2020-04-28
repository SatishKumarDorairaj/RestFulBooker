package com.herokuapp.restful.booker.here.testScripts;

import com.herokuapp.restful.booker.here.BookingDetailsJson.Booking;
import com.herokuapp.restful.booker.here.commonUtils.RestAssuredUtils;
import com.herokuapp.restful.booker.here.commonUtils.BookingUtils;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class BookingTestScripts extends BaseTest {
    BookingUtils bookingUtils = new BookingUtils();
    RestAssuredUtils restAssuredUtils = new RestAssuredUtils();

    private String bookingId;

    Response response;

    void validateBookingResponse(Response response, String firstname, String lastName, String additionalNeeds, int totslPrice, boolean isDepositPaid, String checkIn, String checkOut) {
        Booking bookingResponse = bookingUtils.getBookingDetailsObjectFromBookingResponse(response);
        Assert.assertEquals(bookingResponse.getFirstname(), firstname, "Failed while validating First name");
        Assert.assertEquals(bookingResponse.getLastname(), lastName, "Failed while validating second name");
        Assert.assertEquals(bookingResponse.getAdditionalneeds(), additionalNeeds, "Failed while validating additional needs ");
        Assert.assertEquals(bookingResponse.getTotalprice(), totslPrice, "Failed while validating total price");
        Assert.assertEquals(bookingResponse.isDepositpaid(), isDepositPaid, "Failed while validating deposit paid");
        Assert.assertEquals(bookingResponse.getBookingdates().getCheckin(), checkIn, "Failed while validating Check in");
        Assert.assertEquals(bookingResponse.getBookingdates().getCheckout(), checkOut, "Failed while validating Checkout");
        System.out.println("Successfully validated all the fields in the response");
    }

    @Test(priority = 0)
    void createBooking() {
        Booking booking = new Booking("Jim", "Carey", 111, true, "Breakfast", "2018-01-01", "2019-01-01");
        String bookingDetailsJson = bookingUtils.generateBookingJson(booking);
        response = restAssuredUtils.post(environmentProperties.getHostName() + BookingUtils.URL.CREATE_BOOKING.getKey(), bookingDetailsJson);
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to create booking");
    }

    @Test(priority = 1)
    void assertBookingUsingBookingId() {
        bookingId = response.jsonPath().get("bookingid").toString();
        System.out.println("booking Id is : " + bookingId);
        response = restAssuredUtils.get(environmentProperties.getHostName() + BookingUtils.URL.GET_BOOKING.getKey().replace("{ID}", bookingId));
        Assert.assertEquals(response.getStatusCode(), 200, "Failed while trying to get Booking");
        validateBookingResponse(response, "Jim", "Carey", "Breakfast", 111, true, "2018-01-01", "2019-01-01");
    }


    @Test(priority = 2)
    void updateCompleteBooking() {
        Booking booking = new Booking("JimJim", "careyCarey", 111, true, "Breakfast", "2018-01-01", "2019-01-01");
        String bookingDetailsJson = bookingUtils.generateBookingJson(booking);
        response = restAssuredUtils.put(environmentProperties.getHostName() + BookingUtils.URL.UPDATE_BOOKING.getKey().replace("{ID}", bookingId), bookingDetailsJson);
        Assert.assertEquals(response.getStatusCode(), 200, "failed while Completely Updating Booking");
        validateBookingResponse(response, "JimJim", "careyCarey", "Breakfast", 111, true, "2018-01-01", "2019-01-01");
    }

    @Test(priority = 3)
    void partialUpdatebooking() {
        JSONObject bookingJson = new JSONObject();
        bookingJson.put("firstname", "JimCarey1");
        bookingJson.put("lastname", "JimCarey2");
        response = restAssuredUtils.patch(environmentProperties.getHostName() + BookingUtils.URL.UPDATE_BOOKING.getKey().replace("{ID}", bookingId), bookingJson.toString());
        Assert.assertEquals(response.getStatusCode(), 200, "failed while Partially Updating Booking");
        validateBookingResponse(response, "JimCarey1", "JimCarey2", "Breakfast", 111, true, "2018-01-01", "2019-01-01");
    }


    @Test(priority = 5)
    void deleteAllBookings() {
        response = restAssuredUtils.get(environmentProperties.getHostName() + BookingUtils.URL.GET_ALL_BOOKING.getKey());
        JSONArray jsonArray = new JSONArray(response.getBody().asString());
        System.out.println(" " + jsonArray.length() + "  Bookings are present in the system");
        ArrayList<String> bookingIds = new ArrayList<>();
        jsonArray.forEach(eachBookingObject -> {
            bookingId = ((JSONObject) eachBookingObject).get("bookingid").toString();
            response = restAssuredUtils.delete(environmentProperties.getHostName() + BookingUtils.URL.GET_BOOKING.getKey().replace("{ID}", bookingId));
            if (response.getStatusCode() != 201)
                bookingIds.add(bookingId);
        });
        Assert.assertEquals(bookingIds.isEmpty(), true, "Failed while deleting some of the booking Ids...please check!!! " + bookingIds.toString());
    }

    @Test(priority = 6)
    void updateForInvalidBookingId() {
        int randomBookingId = Math.abs(ThreadLocalRandom.current().nextInt());
        Booking booking = new Booking("JimJim", "careyCarey", 111, true, "Breakfast", "2018-01-01", "2019-01-01");
        String bookingDetailsJson = bookingUtils.generateBookingJson(booking);
        response = restAssuredUtils.put(environmentProperties.getHostName() + BookingUtils.URL.UPDATE_BOOKING.getKey().replace("{ID}", String.valueOf(randomBookingId)), bookingDetailsJson);
        Assert.assertEquals(response.getStatusCode(), 405, "failed while Completely Updating Booking");
    }

    @Test(priority = 7)
    void invalidTypeForFirstname() {
        Booking booking = new Booking("Jim", "Carey", 111, true, "Breakfast", "2018-01-01", "2019-01-01");
        String bookingDetailsJson = bookingUtils.generateBookingJson(booking);
        JSONObject modifiedBookingJson = new JSONObject(bookingDetailsJson);
        modifiedBookingJson.put("firstname", false);
        System.out.println(modifiedBookingJson.toString(4));
        response = restAssuredUtils.post(environmentProperties.getHostName() + BookingUtils.URL.CREATE_BOOKING.getKey(), modifiedBookingJson.toString());
        Assert.assertEquals(response.getStatusCode(), 400, "Failed to validating negative testcases for  create booking");
    }

    @Test(priority = 8)
    void additionalKeysUsedForCreateBooking() {
        Booking booking = new Booking("Jim", "Carey", 111, true, "Breakfast", "2018-01-01", "2019-01-01");
        String bookingDetailsJson = bookingUtils.generateBookingJson(booking);
        JSONObject modifiedBookingJson = new JSONObject(bookingDetailsJson);
        modifiedBookingJson.put("CorruptedKey", false);
        System.out.println(modifiedBookingJson.toString(4));
        response = restAssuredUtils.post(environmentProperties.getHostName() + BookingUtils.URL.CREATE_BOOKING.getKey(), modifiedBookingJson.toString());
        Assert.assertEquals(response.getStatusCode(), 200, "Failed to validating negative testcases for  create booking");

        bookingId = response.jsonPath().get("bookingid").toString();
        response = restAssuredUtils.get(environmentProperties.getHostName() + BookingUtils.URL.GET_BOOKING.getKey().replace("{ID}", bookingId));
        Assert.assertEquals(response.getStatusCode(), 200, "Failed while trying to get Booking");
        validateBookingResponse(response, "Jim", "Carey", "Breakfast", 111, true, "2018-01-01", "2019-01-01");

        Assert.assertEquals(response.jsonPath().get("CorruptedKey") == null, true, "Failed while validating corrupted key");
    }


}
