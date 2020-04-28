package com.herokuapp.restful.booker.BookingDetailsJson;

public class Bookingdates {

    Bookingdates(String checkIn , String Checkout){
        this.checkin=checkIn;
        this.checkout=Checkout;

    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    private String checkin;


    private String checkout;

}

