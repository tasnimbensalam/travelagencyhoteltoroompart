package service;

import controllers.HotelReservationController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import models.Room;
import models.HotelReservation;
import connexion.Connexion;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

public class ReservationHotelViewService {

    private final HotelReservationController reservationService = new HotelReservationController(Connexion.obtenirConnexion());
    private Room selectedRoom;

    @FXML
    private Pane mainPane;

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
    public void initialize() {
        // Listen for changes to the date pickers
        checkInDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
        checkOutDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalPrice());
    }

    public void setSelectedRoom(Room room) {
        this.selectedRoom = room;
        roomIdField.setText(String.valueOf(room.getRoomNum()));
        roomIdField.setEditable(false); 
    }

    private void calculateTotalPrice() {
        if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null && selectedRoom != null) {
            Date checkInDate = Date.valueOf(checkInDatePicker.getValue());
            Date checkOutDate = Date.valueOf(checkOutDatePicker.getValue());

            long diffInMillies = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            double totalPrice = diffInDays * selectedRoom.getPrice();
            totalPriceField.setText(String.valueOf(totalPrice));
        }
    }

    @FXML
    public void bookRoom() {
        try {
            int roomId = Integer.parseInt(roomIdField.getText());
            int guestId = Integer.parseInt(guestIdField.getText());
            Date checkInDate = Date.valueOf(checkInDatePicker.getValue());
            Date checkOutDate = Date.valueOf(checkOutDatePicker.getValue());
            double totalPrice = Double.parseDouble(totalPriceField.getText());

            HotelReservation reservation = reservationService.bookRoom(roomId, guestId, checkInDate, checkOutDate, totalPrice);
            if(reservation ==null) {
            	Alert alertt = new Alert(Alert.AlertType.ERROR);
            	 alertt.setHeaderText("ALREADY BOOKED");
            	return ;
            }
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
