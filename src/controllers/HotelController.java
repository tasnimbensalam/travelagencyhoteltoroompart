package controllers;


import models.Hotel;
import connexion.Connexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelController {

    public void createHotel(String name, int convention, String country, String city, String address, String email, int phoneNumber) {
        String sql = "INSERT INTO hotel (name, convention, country, city, address, email, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, convention);
            pstmt.setString(3, country);
            pstmt.setString(4, city);
            pstmt.setString(5, address);
            pstmt.setString(6, email);
            pstmt.setInt(7, phoneNumber);

            pstmt.executeUpdate();
            System.out.println("Hotel ajouté avec succès !");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        try (Connection conn = Connexion.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int hotelId = rs.getInt("hotel_id");
                String name = rs.getString("name");
                int convention = rs.getInt("convention");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String address = rs.getString("address");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phoneNumber");

                Hotel hotel = new Hotel(name,convention, country, city, address, email, phoneNumber);
                hotel.setHotel_id(hotelId);
                hotels.add(hotel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public Hotel getHotelById(int hotelId) {
        String sql = "SELECT * FROM hotel WHERE hotel_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hotelId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int convention = rs.getInt("convention");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String address = rs.getString("address");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phoneNumber");

                Hotel hotel = new Hotel(name, convention,country, city, address, email, phoneNumber);
                hotel.setHotel_id(hotelId);
                return hotel;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public Hotel updateHotel(int HotelId, String name, Integer convention, String country, String city, String address, String email, int phoneNumber) {
        Hotel existingHotel = getHotelById(HotelId);
        if (existingHotel == null) {
            return null;
        }

        String newName = (name != null) ? name : existingHotel.getName();
        Integer newConvention = (convention != null) ? convention : existingHotel.getConvention();
        String newCountry = (country != null) ? country : existingHotel.getCountry();
        String newCity = (city != null) ? city : existingHotel.getCity();
        String newAddress = (address != null) ? address : existingHotel.getAddress();
        String newEmail = (email != null) ? email : existingHotel.getEmail();
        int newPhoneNumber = (phoneNumber != -1) ? phoneNumber : existingHotel.getPhoneNumber();

        String sql = "UPDATE Hotel SET name = ?, convention = ?, country = ?, city = ?, address = ?, email = ?, phoneNumber = ? WHERE hotel_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setInt(2, newConvention);
            pstmt.setString(3, newCountry);
            pstmt.setString(4, newCity);
            pstmt.setString(5, newAddress);
            pstmt.setString(6, newEmail);
            pstmt.setInt(7, newPhoneNumber);
            pstmt.setInt(8, HotelId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return new Hotel(newName,newConvention, newCountry, newCity,newAddress, newEmail, newPhoneNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Boolean deleteHotel(int hotelId) {
        String sql = "DELETE FROM hotel WHERE hotel_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, hotelId);
            pstmt.executeUpdate();
            System.out.println("Hotel supprimé avec succès !");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return false ;
        
    }
}
