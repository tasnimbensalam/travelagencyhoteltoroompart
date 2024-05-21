package models;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class Flight {
    private int flight_id;
    private String flight_num;
    private String origin;
    private String destination;
    private Date d_depart;
    private Date d_arrival;
    private Time t_depart;
    private Time t_arrival;
    private Airline airline;
    private Aircraft aircraft;
    private Boolean availability;
    
	private HashMap<String ,Integer> availableSeats;
	
    public Flight(String flight_num, String origin, String destination, Date d_depart, Date d_arrival, Time t_depart,
			Time t_arrival, Airline airline, Aircraft aircraft, Boolean availability) {
	
		this.flight_num = flight_num;
		this.origin = origin;
		this.destination = destination;
		this.d_depart = d_depart;
		this.d_arrival = d_arrival;
		this.t_depart = t_depart;
		this.t_arrival = t_arrival;
		this.airline = airline;
		this.aircraft = aircraft;
		this.availability = availability;
		this.availableSeats = new HashMap<>();
        this.availableSeats.put("economy", aircraft.getEconomic_cap());
        this.availableSeats.put("business", aircraft.getBusiness_cap());
        this.availableSeats.put("first", aircraft.getFirst_cap());
    }
	
    
 

	
  
    
    public Flight(int flight_id, String flight_num, String origin, String destination, Date d_depart, Date d_arrival, Time t_depart, Time t_arrival,Airline airline, Aircraft aircraft, Boolean availability) {
        this.flight_id = flight_id;
        this.flight_num = flight_num;
        this.origin = origin;
        this.destination = destination;
        this.d_depart = d_depart;
        this.d_arrival = d_arrival;
        this.t_depart = t_depart;
        this.t_arrival = t_arrival;
        this.setAirline(airline);
        this.aircraft = aircraft;
        this.availability = availability;
		this.availableSeats = new HashMap<>();
        this.availableSeats.put("economy", aircraft.getEconomic_cap());
        this.availableSeats.put("business", aircraft.getBusiness_cap());
        this.availableSeats.put("first", aircraft.getFirst_cap());
    }

	public Flight() {
		this.availableSeats = new HashMap<>();
		
	}




	public int getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}

	public String getFlight_num() {
		return flight_num;
	}

	public void setFlight_num(String flight_num) {
		this.flight_num = flight_num;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getD_depart() {
		return d_depart;
	}

	public void setD_depart(Date d_depart) {
		this.d_depart = d_depart;
	}

	public Date getD_arrival() {
		return d_arrival;
	}

	public void setD_arrival(Date d_arrival) {
		this.d_arrival = d_arrival;
	}

	public Time getT_depart() {
		return t_depart;
	}

	public void setT_depart(Time t_depart) {
		this.t_depart = t_depart;
	}

	public Time getT_arrival() {
		return t_arrival;
	}

	public void setT_arrival(Time t_arrival) {
		this.t_arrival = t_arrival;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}




	public int getAvailableCapacity() {
		int sum = 0;
		for (int cap : this.availableSeats.values())
		{
			sum += cap ;
		}		
		return sum;
	}

   
}
