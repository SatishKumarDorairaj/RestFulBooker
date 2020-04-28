package com.herokuapp.restful.booker.commonUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.herokuapp.restful.booker.BookingDetailsJson.Booking;
import io.restassured.response.Response;

import java.io.IOException;

public class BookingUtils {

    ObjectMapper objectMapper = new ObjectMapper();

    public enum URL {
        CREATE_BOOKING("booking"),
        GET_ALL_BOOKING("booking"),
        GET_BOOKING("booking/{ID}"),
        UPDATE_BOOKING("booking/{ID}");

        private String key;

        URL(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }


    public String generateBookingJson(Booking booking) {
        try {
            return objectMapper.writeValueAsString(booking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Booking getBookingDetailsObjectFromBookingResponse(Response response) {
        Booking booking = null;
        try {
            booking = objectMapper.readValue(response.getBody().asString(), Booking.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booking;
    }
}
