package service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import controllers.FlightController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Flight;

public class SearchFlightService {

    @FXML
    private TextField originField;

    @FXML
    private TextField destinationField;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private TextField adultsField;

    @FXML
    private TextField childrenField;

    @FXML
    private TextField infantsField;

 

    @FXML
    private TableView<Flight> flightsTableView;

    @FXML
    private TableColumn<Flight, String> flightNumColumn;
    @FXML
    private TableColumn<Flight, String> originColumn;
    @FXML
    private TableColumn<Flight, String> destinationColumn;
    @FXML
    private TableColumn<Flight, Date> departureDateColumn;


    private FlightController flightController;

    public SearchFlightService() {
        flightController = new FlightController();
    }

    @FXML
    private void initialize() {
   

        flightNumColumn.setCellValueFactory(new PropertyValueFactory<>("flight_num"));
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("d_depart"));
       
    }

    @FXML
    private void searchFlights() {
        try {
            String origin = originField.getText();
            String destination = destinationField.getText();
            Date departureDate = Date.valueOf(departureDatePicker.getValue());
            int adults = Integer.parseInt(adultsField.getText());
            int children = Integer.parseInt(Optional.ofNullable(childrenField.getText()).orElse("0"));
            int infants = Integer.parseInt(Optional.ofNullable(infantsField.getText()).orElse("0"));
            int nbPersons = adults + children + infants;

            System.out.println("Searching flights with parameters: origin=" + origin + ", destination=" + destination + ", departureDate=" + departureDate + ", nbPersons=" + nbPersons);

            List<Flight> availableFlights = flightController.searchFlights(origin, destination, departureDate, nbPersons);

            System.out.println("Found " + availableFlights.size() + " flights.");

            flightsTableView.setItems(FXCollections.observableArrayList(availableFlights));
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the console for debugging
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please check your input and try again.");
            alert.showAndWait();
        }
    }

}
