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
import java.util.concurrent.TimeUnit;

public class HotelReservationController {

    private final Connection connection;


    public HotelReservationController(Connection connection) {
        this.connection = connection;

    }

    public HotelReservation bookRoom(int roomId, int guestId, Date checkInDate, Date checkOutDate, double totalPrice) {
        try {
            Room room = getRoomById(roomId);
            if (room == null || !room.isDateAvailable(checkInDate, checkOutDate)) {
                throw new RuntimeException("Room is not available for the selected dates.");
            }

            String query = "INSERT INTO hotelreservation (room_id, guest_id, check_in, check_out, total_price) VALUES (?, ?, ?, ?, ?)";
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
                room.addReservation(reservation);
                return reservation;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Room getRoomById(int roomId) {
        Room room = null;
        try {
            String query = "SELECT * FROM room WHERE room_num = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                room = new Room();
                room.setRoomId(resultSet.getInt("room_id"));
                room.setType(RoomType.valueOf(resultSet.getString("room_type").toUpperCase()));
                room.setReservations(getReservationsForRoom(roomId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    private List<HotelReservation> getReservationsForRoom(int roomId) {
        List<HotelReservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM hotelreservation WHERE room_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, roomId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                HotelReservation reservation = new HotelReservation(
                        resultSet.getInt("reservation_id"),
                        getRoomById(resultSet.getInt("room_id")),
                        getUserById(resultSet.getInt("guest_id")),
                        resultSet.getDate("check_in"),
                        resultSet.getDate("check_out"),
                        resultSet.getDouble("total_price")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
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
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
