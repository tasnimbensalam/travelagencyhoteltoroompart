package controllers;

import models.HotelReservation;
import models.Room;
import models.RoomType;
import models.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelReservationController {

    private final Connection connection;

    public HotelReservationController(Connection connection) {
        this.connection = connection;
    }

    public HotelReservation bookRoom(int roomId, int guestId, Date checkInDate, Date checkOutDate, double totalPrice) {
        try {
            Room room = getRoomById(roomId);
            if (room == null || !isRoomAvailable(roomId, checkInDate, checkOutDate)) {
                throw new RuntimeException("Room is not available for the selected dates.");
            }

            String query = "INSERT INTO hotelreservation (room_id, user_id, check_in, check_out, TotPrice) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, roomId);
            statement.setInt(2, guestId);
            statement.setDate(3, checkInDate);
            statement.setDate(4, checkOutDate);
            statement.setDouble(5, totalPrice);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int reservationId = generatedKeys.getInt(1);
                User guest = getUserById(guestId);
                HotelReservation reservation = new HotelReservation(reservationId, room, guest, checkInDate, checkOutDate, totalPrice);
                return reservation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room getRoomById(int roomId) {
        Room room = null;
        try {
            String query = "SELECT * FROM room WHERE room_num = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomNum(resultSet.getInt("room_num"));
                room.setType(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
                room.setPrice(resultSet.getDouble("price"));
                room.setReservations(getReservationsForRoom(roomId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }
    public List<HotelReservation> getAllReservations() {
        List<HotelReservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM hotelreservation";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                HotelReservation reservation = new HotelReservation(
                        resultSet.getInt("rev_id"),
                        getRoomBasicInfo(resultSet.getInt("room_id")),
                        getUserById(resultSet.getInt("user_id")),
                        resultSet.getDate("check_in"),
                        resultSet.getDate("check_out"),
                        resultSet.getDouble("TotPrice")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    public List<HotelReservation> getReservationsForRoom(int roomId) {
        List<HotelReservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM hotelreservation WHERE room_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                HotelReservation reservation = new HotelReservation(
                        resultSet.getInt("rev_id"),
                        getRoomBasicInfo(roomId),
                        getUserById(resultSet.getInt("user_id")),
                        resultSet.getDate("check_in"),
                        resultSet.getDate("check_out"),
                        resultSet.getDouble("TotPrice")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private Room getRoomBasicInfo(int roomId) {
        Room room = null;
        try {
            String query = "SELECT room_num, type, price FROM room WHERE room_num = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomNum(resultSet.getInt("room_num"));
                room.setType(RoomType.valueOf(resultSet.getString("type").toUpperCase()));
                room.setPrice(resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    private User getUserById(int userId) {
        User user = null;
        try {
            String query = "SELECT * FROM user WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("user_id"));
                user.setFullName(resultSet.getString("fullName"));
                // Set other user fields
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private boolean isRoomAvailable(int roomId, Date checkInDate, Date checkOutDate) {
        try {
            String query = "SELECT COUNT(*) FROM hotelreservation WHERE room_id = ? AND NOT (check_out <= ? OR check_in >= ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            statement.setDate(2, checkInDate);
            statement.setDate(3, checkOutDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
