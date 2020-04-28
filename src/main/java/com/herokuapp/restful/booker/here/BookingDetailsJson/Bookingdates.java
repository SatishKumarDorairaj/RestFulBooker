package com.herokuapp.restful.booker.here.BookingDetailsJson;

public class Bookingdates {

    Bookingdates(){
        super();
    }
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

