package service;

import controllers.HotelReservationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.HotelReservation;
import models.Room;
import java.sql.Date;
import java.util.concurrent.TimeUnit;
import connexion.Connexion;

public class MakeReservationViewService {

    private final HotelReservationController reservationController = new HotelReservationController(Connexion.obtenirConnexion());
    private Room selectedRoom;

    @FXML
    private TextField roomIdField;
    @FXML
    private TextField guestIdField;
    @FXML
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;
    @FXML
    private TextField totalPriceField;
    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label roomTypeLabel;
    @FXML
    private Label roomPriceLabel;

    @FXML
    public void initialize() {
        // Any additional initialization if needed
    }

    public void setSelectedRoom(Room room) {
        this.selectedRoom = room;
        roomIdField.setText(String.valueOf(room.getRoomNum()));
        roomIdField.setEditable(false);
        displayRoomDetails();
    }

    private void displayRoomDetails() {
        if (selectedRoom != null) {
            String hotelName = getHotelNameById(selectedRoom.getHotel_id());
            hotelNameLabel.setText("Hotel: " + hotelName);
            roomTypeLabel.setText("Type: " + selectedRoom.getType().toString());
            roomPriceLabel.setText("Price: $" + selectedRoom.getPrice() + " per night");
        }
    }

    private String getHotelNameById(int hotelId) {
        return "Hotel Name Placeholder"; // Replace with actual implementation
    }

    @FXML
    public void bookRoom() {
        try {
            int roomId = Integer.parseInt(roomIdField.getText());
            int guestId = Integer.parseInt(guestIdField.getText());
            Date checkInDate = Date.valueOf(checkInDatePicker.getValue());
            Date checkOutDate = Date.valueOf(checkOutDatePicker.getValue());

            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            double roomPrice = selectedRoom.getPrice();
            double totalPrice = diffInDays * roomPrice;

            HotelReservation reservation = reservationController.bookRoom(roomId, guestId, checkInDate, checkOutDate, totalPrice);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Room Booked Successfully");
            alert.setContentText("Reservation ID: " + reservation.getRevId());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter valid room and guest IDs.");
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Booking Failed");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
