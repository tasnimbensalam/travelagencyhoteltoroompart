package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Aircraft;

import connexion.Connexion;


public class AircraftController {
	
	
	 public Aircraft createAircraft(String model, int economic_cap, int business_cap,int first_cap) {
	        String sql = "INSERT INTO Aircraft (model, economic_cap,business_cap,first_cap) VALUES (?,?,?,?)";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, model);
	           
	            pstmt.setInt(2, economic_cap);
	            pstmt.setInt(3, business_cap);
	            pstmt.setInt(4, first_cap);

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                return new Aircraft(model, economic_cap,  business_cap, first_cap);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public List<Aircraft> getAllAircrafts() {
	        List<Aircraft> aircrafts = new ArrayList<>();
	        String sql = "SELECT * FROM aircraft";
	        try (Connection conn = Connexion.obtenirConnexion();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(sql)) {

	            while (rs.next()) {
	                String model = rs.getString("model");
	                int aircraftId = rs.getInt("aircraft_id");

	                int economic_cap = rs.getInt("economic_cap");
	                int business_cap = rs.getInt("business_cap");
	                int first_cap = rs.getInt("first_cap");
	                aircrafts.add(new Aircraft(aircraftId ,model, economic_cap,  business_cap, first_cap));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return aircrafts;
	    }

	    public Aircraft getAircraftById(int aircraftId) {
	        String sql = "SELECT * FROM Aircraft WHERE aircraft_id = ?";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, aircraftId);
	            ResultSet rs = pstmt.executeQuery();

	            if (rs.next()) {
	            	String model = rs.getString("model");
		               
	                int economic_cap = rs.getInt("economic_cap");
	                int business_cap = rs.getInt("business_cap");
	                int first_cap = rs.getInt("first_cap");
	                return new Aircraft(aircraftId,model, economic_cap,  business_cap, first_cap);
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public Aircraft Aircraftupdate(int aircraftId, String model, Integer economic_cap, Integer business_cap, Integer first_cap) {
	        Aircraft existingAircraft = getAircraftById(aircraftId);
	        if (existingAircraft == null) {
	            return null;
	        }

	        String newModel = (model != null) ? model : existingAircraft.getModel();
	        Integer newEconomy = (economic_cap != null) ? economic_cap : existingAircraft.getEconomic_cap();
	        Integer newBusiness = (business_cap != null) ? business_cap : existingAircraft.getBusiness_cap();
	        Integer newFirst = (first_cap != null) ? first_cap : existingAircraft.getFirst_cap();

	        String sql = "UPDATE Aircraft SET model = ?, economic_cap = ?, business_cap = ?, first_cap = ? WHERE aircraft_id = ?";
	        try (Connection conn = Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setString(1, newModel);
	            pstmt.setInt(2, newEconomy);
	            pstmt.setInt(3, newBusiness);
	            pstmt.setInt(4, newFirst);
	            pstmt.setInt(5, aircraftId);

	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                return new Aircraft(aircraftId, newModel, newEconomy, newBusiness, newFirst);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


	    public boolean deleteAircraft(int aircraftId) {
	        String sql = "DELETE FROM Aircraft WHERE aircraft_id = ?";
	        try (Connection conn =Connexion.obtenirConnexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            pstmt.setInt(1, aircraftId);
	            int affectedRows = pstmt.executeUpdate();

	            return affectedRows > 0;

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
}
