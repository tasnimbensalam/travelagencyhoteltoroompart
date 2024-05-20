package models;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
	private int hotel_id ;
	private String name;
	private int convention;
    private String Country;
    private String city;
    private String address;
    private String email ;
    private int phoneNumber;
    private List<Room> rooms;
    private List<FeedbackHotel> feedbacks;
    private List<HotelReservation> reservations;
    
    public Hotel() {}

  
    
    public Hotel(String name,int convention,String country, String city, String address, String email,int phoneNumber) {
		
		this.name = name;
		this.convention = convention;
		Country = country;
		this.city = city;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.rooms = new ArrayList<>();
	    this.reservations = new ArrayList<>();
	    this.feedbacks = new ArrayList<>();
	}
    


	public Hotel(int hotel_id,String name,int convention, String country, String city, String address, String email,
			int phoneNumber, List<Room> rooms, List<FeedbackHotel> feedbacks, List<HotelReservation> reservations) {
		super();
		this.hotel_id = hotel_id;
		this.name = name;
		this.convention = convention;
		Country = country;
		this.city = city;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.rooms = rooms;
		this.feedbacks = feedbacks;
		this.reservations = reservations;
	}




	public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getRooms() {
        return rooms;
    }

  
    public void addReservation(HotelReservation reservation) {
        reservations.add(reservation);
    }

    public List<HotelReservation> getReservations() {
        return reservations;
    }

   
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getConvention() {
		return convention;
	}

	public void setConvention(int convention) {
		this.convention = convention;
	}

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}

	public List<FeedbackHotel> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<FeedbackHotel> feedbacks) {
		this.feedbacks = feedbacks;
	}
}

