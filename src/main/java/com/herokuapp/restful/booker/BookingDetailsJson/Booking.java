package com.herokuapp.restful.booker.BookingDetailsJson;


public class Booking {

    public Booking(String firstName, String lastname, int totalprice, boolean depositpaid, String additionalNeeds, String CheckIndate, String checkoutDate) {
        this.firstname = firstName;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.additionalneeds = additionalNeeds;
        bookingdates = new Bookingdates(CheckIndate, checkoutDate);
    }

    private String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    public Bookingdates getBookingdates() {
        return bookingdates;
    }

    public void setBookingDates(Bookingdates bookingDates) {
        this.bookingdates = bookingDates;
    }

    private String lastname;
    private int totalprice;
    private boolean depositpaid;
    private String additionalneeds;
    private Bookingdates bookingdates;

}



