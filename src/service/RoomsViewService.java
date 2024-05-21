package service;

import controllers.RoomController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import models.Hotel;
import models.Room;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsViewService {

    @FXML
    private VBox roomsVBox;

    private Hotel selectedHotel;

    public void setSelectedHotel(Hotel hotel) {
        this.selectedHotel = hotel;
        loadRooms();
    }

    @FXML
    public void initialize() {
        // This will be called automatically when the FXML is loaded
    }

    private void loadRooms() {
        if (selectedHotel != null) {
            List<Room> rooms = RoomController.getAllRooms()
                                             .stream()
                                             .filter(room -> room.getHotel_id() == selectedHotel.getHotel_id())
                                             .collect(Collectors.toList());

            roomsVBox.getChildren().clear();

            for (Room room : rooms) {
                Button roomButton = new Button("Room " + room.getRoomNum() + " (" + room.getType() + ")");
                roomButton.setOnAction(event -> openReservationView(room));
                roomsVBox.getChildren().add(roomButton);
            }
        }
    }

    private void openReservationView(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/auth/MakeReservation.fxml"));
            Parent root = loader.load();

            MakeReservationViewService reservationController = loader.getController();
            reservationController.setSelectedRoom(room);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
