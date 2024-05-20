package controllers;

import models.Aircraft;
import models.Airline;
import models.Flight;
import connexion.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

	public Flight createFlight(String flight_num, String origin, String destination, Date d_depart, Date d_arrival, Time t_depart, Time t_arrival, Airline airline, Aircraft aircraft, Boolean availability) {
	    String sql = "INSERT INTO flight (flight_num, origin, destination, d_depart, d_arrival, t_depart, t_arrival, airline_id, aircraft_id, availability) VALUES (?,?,?,?,?,?,?,?,?,?)";
	    try (Connection conn = Connexion.obtenirConnexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pstmt.setString(1, flight_num);
	        pstmt.setString(2, origin);
	        pstmt.setString(3, destination);
	        pstmt.setDate(4, d_depart);
	        pstmt.setDate(5, d_arrival);
	        pstmt.setTime(6, t_depart);
	        pstmt.setTime(7, t_arrival);
	        pstmt.setInt(8, airline.getAirline_id());
	        pstmt.setInt(9, aircraft.getAircraft_id());
	        pstmt.setBoolean(10, availability);

	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    int flightId = generatedKeys.getInt(1);
	                    System.out.println("Flight created successfully with ID: " + flightId);
	                    return new Flight(flightId, flight_num, origin, destination, d_depart, d_arrival, t_depart, t_arrival, airline, aircraft, availability);
	                } else {
	                    throw new SQLException("Creating flight failed, no ID obtained.");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flight";
        try (Connection conn = Connexion.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int flightId = rs.getInt("flight_id");
                String flight_num = rs.getString("flight_num");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                Date d_depart = rs.getDate("d_depart");
                Date d_arrival = rs.getDate("d_arrival");
                Time t_depart = rs.getTime("t_depart");
                Time t_arrival = rs.getTime("t_arrival");
                int airlineId = rs.getInt("airline_id");
              
                int aircraftId = rs.getInt("aircraft_id");
                Boolean availability = rs.getBoolean("availability");
               
                AirlineController airlineController = new AirlineController();
                Airline airline = airlineController.getAirlineById(String.valueOf(airlineId));
                
                AircraftController aircraftController = new AircraftController();
                Aircraft aircraft = aircraftController.getAircraftById(aircraftId);
                flights.add(new Flight(flightId, flight_num, origin, destination, d_depart, d_arrival, t_depart, t_arrival, airline,aircraft, availability));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public Flight getFlightById(int flightId) {
        String sql = "SELECT * FROM flight WHERE flight_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, flightId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String flight_num = rs.getString("flight_num");
                String origin = rs.getString("origin");
                String destination = rs.getString("destination");
                Date d_depart = rs.getDate("d_depart");
                Date d_arrival = rs.getDate("d_arrival");
                Time t_depart = rs.getTime("t_depart");
                Time t_arrival = rs.getTime("t_arrival");
                int aircraftId = rs.getInt("aircraft_id");
                int airlineId = rs.getInt("airline_id");
                Boolean availability = rs.getBoolean("availability");
                AirlineController airlineController = new AirlineController();
                
                Airline airline = airlineController.getAirlineById(String.valueOf(airlineId));
                
                AircraftController aircraftController = new AircraftController();
                Aircraft aircraft = aircraftController.getAircraftById(aircraftId);
                return new Flight(flightId, flight_num, origin, destination, d_depart, d_arrival, t_depart, t_arrival,airline, aircraft, availability);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Flight updateFlight(int flightId, String flight_num, String origin, String destination, Date d_depart, Date d_arrival, Time t_depart, Time t_arrival,Airline airline, Aircraft aircraft, Boolean availability) {
        Flight existingFlight = getFlightById(flightId);
        if (existingFlight == null) {
            return null;
        }

        String newFlightNum = (flight_num != null) ? flight_num : existingFlight.getFlight_num();
        String newOrigin = (origin != null) ? origin : existingFlight.getOrigin();
        String newDestination = (destination != null) ? destination : existingFlight.getDestination();
        Date newDDepart = (d_depart != null) ? d_depart : existingFlight.getD_depart();
        Date newDArrival = (d_arrival != null) ? d_arrival : existingFlight.getD_arrival();
        Time newTDepart = (t_depart != null) ? t_depart : existingFlight.getT_depart();
        Time newTArrival = (t_arrival != null) ? t_arrival : existingFlight.getT_arrival();
        Aircraft newAircraft = (aircraft != null) ? aircraft : existingFlight.getAircraft();
        Boolean newAvailability = (availability != null) ? availability : existingFlight.getAvailability();
       Airline newAirline =(airline!= null) ? airline : existingFlight.getAirline();

        String sql = "UPDATE Flight SET flight_num = ?, origin = ?, destination = ?, d_depart = ?, d_arrival = ?, t_depart = ?, t_arrival = ?, aircraft_id = ?, availability = ? WHERE flight_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newFlightNum);
            pstmt.setString(2, newOrigin);
            pstmt.setString(3, newDestination);
            pstmt.setDate(4, newDDepart);
            pstmt.setDate(5, newDArrival);
            pstmt.setTime(6, newTDepart);
            pstmt.setTime(7, newTArrival);
            pstmt.setInt(8, newAircraft.getAircraft_id());
            pstmt.setBoolean(9, newAvailability);
            pstmt.setInt(10, flightId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return new Flight(flightId, newFlightNum, newOrigin, newDestination, newDDepart, newDArrival, newTDepart, newTArrival,newAirline, newAircraft, newAvailability);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteFlight(int flightId) {
        String sql = "DELETE FROM Flight WHERE flight_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, flightId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
}
