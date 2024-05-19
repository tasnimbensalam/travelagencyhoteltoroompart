package models;

import java.util.Date;

public class FlightReservation {
    private int flightrev_id;
    private Flight flight;
    private User passenger;
    private Date reservationDate;
    private int priceTotal;

	public FlightReservation(int flightrev_id, Flight flight, User passenger, Date reservationDate, int priceTotal) {
	
		this.flightrev_id = flightrev_id;
		this.flight = flight;
		this.passenger = passenger;
		this.reservationDate = reservationDate;
		this.priceTotal = priceTotal;
	}

	public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }


	
	public int getFlightrev_id() {
		return flightrev_id;
	}

	public void setFlightrev_id(int flightrev_id) {
		this.flightrev_id = flightrev_id;
	}

	public int getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(int priceTotal) {
		this.priceTotal = priceTotal;
	}
}

