package models;

import java.util.List;


public class Airline {
	private int airline_id;
    private String name;
    private int convention;
    private List<Flight> flights;
    

    public Airline(String name, int convention, List<Flight> flights) {
	
		this.name = name;

		this.convention = convention;
		this.flights = flights;
	}
    

	public Airline( int airline_id,String name, int convention, List<Flight> flights) {
		super();
		this.name = name;
		this.airline_id = airline_id;
		this.convention = convention;
		this.flights = flights;
	}


	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

  

    public List<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

	public int getConvention() {
		return convention;
	}

	public void setConvention(int convention) {
		this.convention = convention;
	}

	public int getAirline_id() {
		return airline_id;
	}

	public void setAirline_id(int airline_id) {
		this.airline_id = airline_id;
	}


   
}
