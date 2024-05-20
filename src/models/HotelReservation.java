package models;

import java.util.Date;

public class HotelReservation {
    private int rev_id;
    private Room room;
    private User guest;
    private Date check_in;
    private Date check_out;
    private Double totPrice;

    public HotelReservation(int reservationId, Room room, User guest, Date checkInDate, Date checkOutDate, double price) {
        this.rev_id = reservationId;
        this.room = room;
        this.guest = guest;
        this.check_in = checkInDate;
        this.check_out = checkOutDate;
        this.totPrice = price;
    }

    // Getters and setters for the attributes
    public int getRevId() {
        return rev_id;
    }

    public void setRevId(int reservationId) {
        this.rev_id = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public Double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(Double totPrice) {
        this.totPrice = totPrice;
    }

    public Date getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Date check_in) {
        this.check_in = check_in;
    }

    public Date getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Date check_out) {
        this.check_out = check_out;
    }
    public boolean overlaps(Date checkIn, Date checkOut) {
        return !(checkOut.before(check_in) || checkIn.after(check_out));
    }
}
