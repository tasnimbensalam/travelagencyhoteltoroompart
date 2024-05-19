package controllers;



import models.Airline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;

public class AirlineController {
	 public Airline createAirline(String name, int convention) {
	        String sql = "INSERT INTO Airline (name, convention) VALUES (?,?)";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, name);  
	            pstmt.setInt(2, convention);

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                return new Airline(name,convention, new ArrayList<>());
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public List<Airline> getAllAirlines() {
	        List<Airline> airlines = new ArrayList<>();
	        String sql = "SELECT * FROM Airline";
	        try (Connection conn = Connexion.obtenirConnexion();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                String name = rs.getString("name");
	                int airlineId = rs.getInt("airline_id");
	                int convention = rs.getInt("convention");
	                airlines.add(new Airline(airlineId,name, convention, new ArrayList<>()));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return airlines;
	    }

	    public Airline getAirlineById(String airlineId) {
	        String sql = "SELECT * FROM Airline WHERE airline_id = ?";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, airlineId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	                String name = rs.getString("name");
	                int convention = rs.getInt("convention");
	                int Id = rs.getInt("airline_id");
	                return new Airline(Id,name, convention, new ArrayList<>());
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public Airline updateAirline(String airlineId, String name, Integer convention) {
	        Airline existingAirline = getAirlineById(airlineId);
	        if (existingAirline == null) {
	            return null;
	        }

	        
	        String newName = (name != null) ? name : existingAirline.getName();

	       
	        Integer newConvention = (convention != null) ? convention : existingAirline.getConvention();

	        String sql = "UPDATE Airline SET name = ?, convention = ? WHERE airline_id = ?";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, newName);
	            pstmt.setInt(2, newConvention);
	            pstmt.setString(3, airlineId);

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                return new Airline(newName,  newConvention, existingAirline.getFlights());
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


	    public boolean deleteAirline(String airlineId) {
	        String sql = "DELETE FROM Airline WHERE airline_id = ?";
	        try (Connection conn =Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, airlineId);
	            int affectedRows = pstmt.executeUpdate();

	            return affectedRows > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }  
}
