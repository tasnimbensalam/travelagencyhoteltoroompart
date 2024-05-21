package service;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Hotel;

import java.io.IOException;

public class HotelDetailViewService {

    @FXML
    private Label hotelNameLabel;

    @FXML
    private Label hotelCityLabel;

    @FXML
    private VBox roomsVBox;

    private RoomsViewService roomsViewService;

    public void setSelectedHotel(Hotel hotel) {
        hotelNameLabel.setText(hotel.getName());
        hotelCityLabel.setText(hotel.getCity());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/auth/RoomsView.fxml"));
            VBox roomsRoot = loader.load();
            roomsViewService = loader.getController();
            roomsViewService.setSelectedHotel(hotel);

            roomsVBox.getChildren().clear();
            roomsVBox.getChildren().add(roomsRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
