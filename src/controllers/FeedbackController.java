package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connexion.Connexion;
import models.FeedbackHotel;
import models.Hotel;
import models.User;

public class FeedbackController {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullName(rs.getString("fullName"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotel";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setHotel_id(rs.getInt("hotel_id"));
                hotel.setName(rs.getString("name"));
                hotels.add(hotel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    public List<FeedbackHotel> getAllFeedbacks() {
        List<FeedbackHotel> feedbacks = new ArrayList<>();
        String sql = "SELECT f.feedback_id, f.comment, f.rates, u.fullName as clientName, h.name as hotelName " +
                     "FROM feedbackhotel f " +
                     "JOIN user u ON f.client_id = u.user_id " +
                     "JOIN hotel h ON f.hotel_id = h.hotel_id";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                FeedbackHotel feedback = new FeedbackHotel();
                feedback.setFeedback_id(rs.getInt("feedback_id"));
                feedback.setComment(rs.getString("comment"));
                feedback.setRates(rs.getDouble("rates"));

                User client = new User();
                client.setFullName(rs.getString("clientName"));
                feedback.setClient(client);

                Hotel hotel = new Hotel();
                hotel.setName(rs.getString("hotelName"));
                feedback.setHotel(hotel);

                feedbacks.add(feedback);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    public void saveFeedback(FeedbackHotel feedback) {
        String sql = "INSERT INTO feedbackhotel (client_id, hotel_id, comment, rates) VALUES (?, ?, ?, ?)";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, feedback.getClient().getUserId());
            pstmt.setInt(2, feedback.getHotel().getHotel_id());
            pstmt.setString(3, feedback.getComment());
            pstmt.setDouble(4, feedback.getRates());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFeedback(int feedbackId) {
        String sql = "DELETE FROM feedbackhotel WHERE feedback_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, feedbackId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
