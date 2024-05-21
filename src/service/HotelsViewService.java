package service;

import controllers.HotelController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Hotel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HotelsViewService {

    @FXML
    private VBox hotelsVBox;

    @FXML
    private TextField searchField;

    private List<Hotel> allHotels;

    @FXML
    public void initialize() {
        allHotels = HotelController.getAllHotels();
        displayHotels(allHotels);
    }

    @FXML
    private void handleSearch() {
        String searchQuery = searchField.getText().toLowerCase();
        List<Hotel> filteredHotels = allHotels.stream()
                .filter(hotel -> hotel.getName().toLowerCase().contains(searchQuery))
                .collect(Collectors.toList());
        displayHotels(filteredHotels);
    }

    private void displayHotels(List<Hotel> hotels) {
        hotelsVBox.getChildren().clear();
        for (Hotel hotel : hotels) {
            Button hotelButton = new Button(hotel.getName() + " (" + hotel.getCity() + ")");
            hotelButton.setOnAction(event -> openHotelDetailView(hotel));
            hotelsVBox.getChildren().add(hotelButton);
        }
    }

    private void openHotelDetailView(Hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/auth/HotelDetailsView.fxml"));
            Parent root = loader.load();

            HotelDetailViewService hotelDetailController = loader.getController();
            hotelDetailController.setSelectedHotel(hotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
