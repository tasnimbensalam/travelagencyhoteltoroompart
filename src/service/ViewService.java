package service;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import models.Room;

import java.io.IOException;

public class ViewService {

    public void loadReservationView(Room room, Pane mainPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/auth/ReservationView.fxml"));
            Pane reservationPane = loader.load();

            ReservationHotelViewService controller = loader.getController();
            controller.setSelectedRoom(room);

            mainPane.getChildren().setAll(reservationPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadView(String fxmlFile, Pane mainPane) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + fxmlFile));
            Pane newPane = loader.load();
            mainPane.getChildren().setAll(newPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
