package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Room {
    private int room_num;
    private RoomType type;
    private int isAvailable;
    private double price;
    private int hotel_id;
    private List<HotelReservation> reservations;
    public Room(int roomNumber, RoomType type, double pricePerNight, int hotel_id) {
        this.room_num = roomNumber;
        this.type = type;
        this.isAvailable = 1; 
        this.price = pricePerNight;
        this.setHotel_id(hotel_id);
    }

    public int getRoomNum() {
        return room_num;
    }

    public void setRoomNum(int roomNumber) {
        this.room_num = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int isAvailable() {
        return isAvailable;
    }

    public void setAvailable(int available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double pricePerNight) {
        this.price = pricePerNight;
    }

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public void setReservations(List<HotelReservation> reservations) {
		this.reservations = reservations;
	}
	 public List<HotelReservation> getReservations() {
	        return reservations;
	    }
	 public boolean isDateAvailable(Date checkIn, Date checkOut) {
	        for (HotelReservation reservation : reservations) {
	            if (reservation.overlaps(checkIn, checkOut)) {
	                return false;
	            }
	        }
	        return true;
	    }
	  public void addReservation(HotelReservation reservation) {
	        reservations.add(reservation);
	  }
	 

	    public Room(int room_num, double price) {
	        this.room_num = room_num;
	        this.price = price;
	        this.isAvailable = 1;
	        this.reservations = new ArrayList<>();
	    }

	    public Room() {
			
		}

		

	    public boolean isAvailable(Date checkIn, Date checkOut) {
	        for (HotelReservation reservation : reservations) {
	            if (!(checkOut.before(reservation.getCheck_in()) || checkIn.after(reservation.getCheck_out()))) {
	                return false;
	            }
	        }
	        return true;
	    }

}
