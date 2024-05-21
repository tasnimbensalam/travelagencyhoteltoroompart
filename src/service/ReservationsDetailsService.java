package service;

import controllers.HotelReservationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.HotelReservation;
import models.Room;
import connexion.Connexion;

import java.util.List;

public class ReservationsDetailsService {

    private final HotelReservationController reservationService = new HotelReservationController(Connexion.obtenirConnexion());

    @FXML
    private TextField roomIdField;
    @FXML
    private ListView<HotelReservation> reservationsListView;


    @FXML
    public void initialize() {
    	 reservationsListView.setCellFactory(param -> new ListCell<HotelReservation>() {
    	        @Override
    	        protected void updateItem(HotelReservation reservation, boolean empty) {
    	            super.updateItem(reservation, empty);
    	            if (empty || reservation == null) {
    	                setText(null);
    	            } else {
    	                String details = "Reservation ID: " + reservation.getRevId() +
    	                                 ", Room ID: " + reservation.getRoom().getRoomNum() +
    	                                 ", User ID: " + reservation.getUserId() +
    	                                 ", Check-In: " + reservation.getCheck_in() +
    	                                 ", Check-Out: " + reservation.getCheck_out() +
    	                                 ", Total Price: " + reservation.getTotPrice();
    	                setText(details);
    	            }
    	        }
    	    });

    	    loadAllReservations();
    }

    @FXML
    public void loadReservations() {
        try {
            int roomId = Integer.parseInt(roomIdField.getText());
            List<HotelReservation> reservations = reservationService.getReservationsForRoom(roomId);
            if (reservations.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Reservations", "No reservations found for the given room ID.");
            } else {
                reservationsListView.getItems().setAll(reservations);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid room ID.");
        }
    }
    public void loadAllReservations() {
        List<HotelReservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "No Reservations", "No reservations found.");
        } else {
            reservationsListView.getItems().setAll(reservations);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
