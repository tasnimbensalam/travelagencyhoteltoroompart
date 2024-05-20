package service;

import controllers.AircraftController;
import controllers.AirlineController;
import controllers.FlightController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Aircraft;
import models.Airline;
import models.Flight;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightViewService {
    @FXML
    private TextField flighIdField;
    @FXML
    private TextField flightNumField;
    @FXML
    private TextField flightIdField;
    @FXML
    private TextField originField;
    @FXML
    private TextField destinationField;
    @FXML
    private DatePicker dDepartField;
    @FXML
    private DatePicker dArrivalField;
    @FXML
    private TextField tDepartField;
    @FXML
    private TextField tArrivalField;
    @FXML
    private ComboBox<String> aircraftIdField;
    @FXML
    private ComboBox<String> airlineIdField;
    @FXML
    private CheckBox availabilityCheckBox;
    @FXML
    private ListView<String> flightListView;

    private FlightController flightController;
    private AirlineController airlineController;
    private AircraftController aircraftController;

    private Map<String, Integer> aircraftNameToIdMap = new HashMap<>();
    private Map<String, Integer> airlineNameToIdMap = new HashMap<>();
    private Map<Integer, String> aircraftIdToNameMap = new HashMap<>();
    private Map<Integer, String> airlineIdToNameMap = new HashMap<>();

    public FlightViewService() {
        flightController = new FlightController();
        airlineController = new AirlineController();
        aircraftController = new AircraftController();
    }

    @FXML
    private void initialize() {
        populateAircraftComboBox();
        populateAirlineComboBox();
        refreshFlightList();
    }

    private void populateAircraftComboBox() {
        List<Aircraft> aircrafts = aircraftController.getAllAircrafts();
        for (Aircraft aircraft : aircrafts) {
            String name = aircraft.getModel(); // Assume getName() method exists in Aircraft class
            int id = aircraft.getAircraft_id();
            aircraftIdToNameMap.put(id, name);
            aircraftNameToIdMap.put(name, id);
            aircraftIdField.getItems().add(name);
        }
    }

    private void populateAirlineComboBox() {
        List<Airline> airlines = airlineController.getAllAirlines();
        for (Airline airline : airlines) {
            String name = airline.getName(); // Assume getName() method exists in Airline class
            int id = airline.getAirline_id();
            airlineIdToNameMap.put(id, name);
            airlineNameToIdMap.put(name, id);
            airlineIdField.getItems().add(name);
        }
    }

    @FXML
    private void handleAddFlight() {
        String flightNum = flightNumField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        LocalDate departDate = dDepartField.getValue();
        LocalDate arrivalDate = dArrivalField.getValue();
        String tDepartText = tDepartField.getText();
        String tArrivalText = tArrivalField.getText();
        String airlineName = airlineIdField.getValue();
        String aircraftName = aircraftIdField.getValue();
        boolean availability = availabilityCheckBox.isSelected();

        if (flightNum.isEmpty() || origin.isEmpty() || destination.isEmpty() ||
            departDate == null || arrivalDate == null ||
            tDepartText.isEmpty() || tArrivalText.isEmpty() ||
            airlineName == null || aircraftName == null) {
            showAlert("Invalid Input", "Please ensure all fields are filled correctly.", "Some fields are empty.");
            return;
        }

        Date dDepart = Date.valueOf(departDate);
        Date dArrival = Date.valueOf(arrivalDate);
        Time tDepart = Time.valueOf(tDepartText);
        Time tArrival = Time.valueOf(tArrivalText);
        int airlineId = airlineNameToIdMap.get(airlineName);
        int aircraftId = aircraftNameToIdMap.get(aircraftName);

        Aircraft aircraft = aircraftController.getAircraftById(aircraftId);
        Airline airline = airlineController.getAirlineById(String.valueOf(airlineId));

        if (aircraft == null || airline == null) {
            showAlert("Invalid Input", "Aircraft or Airline ID is incorrect.", "Please check the IDs and try again.");
            return;
        }

        flightController.createFlight(flightNum, origin, destination, dDepart, dArrival, tDepart, tArrival, airline, aircraft, availability);
        refreshFlightList();
        clearFields();
    }

    @FXML
    private void handleUpdateFlight() {
        try {
            int flightId = Integer.parseInt(flightIdField.getText());
            String flightNum = flightNumField.getText();
            String origin = originField.getText();
            String destination = destinationField.getText();
            LocalDate departDate = dDepartField.getValue();
            LocalDate arrivalDate = dArrivalField.getValue();
            String tDepartText = tDepartField.getText();
            String tArrivalText = tArrivalField.getText();
            String airlineName = airlineIdField.getValue();
            String aircraftName = aircraftIdField.getValue();
            boolean availability = availabilityCheckBox.isSelected();

            Date dDepart = Date.valueOf(departDate);
            Date dArrival = Date.valueOf(arrivalDate);
            Time tDepart = Time.valueOf(tDepartText);
            Time tArrival = Time.valueOf(tArrivalText);
            int airlineId = airlineNameToIdMap.get(airlineName);
            int aircraftId = aircraftNameToIdMap.get(aircraftName);

            Aircraft aircraft = aircraftController.getAircraftById(aircraftId);
            Airline airline = airlineController.getAirlineById(String.valueOf(airlineId));

            if (aircraft == null || airline == null) {
                showAlert("Invalid Input", "Aircraft or Airline ID is incorrect.", "Please check the IDs and try again.");
                return;
            }

            flightController.updateFlight(flightId, flightNum, origin, destination, dDepart, dArrival, tDepart, tArrival, airline, aircraft, availability);
            refreshFlightList();
            clearFields();
        } catch (Exception e) {
            showAlert("Invalid Input", "Please ensure all fields are filled correctly.", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteFlight() {
        try {
            int flightId = Integer.parseInt(flightIdField.getText());
            boolean success = flightController.deleteFlight(flightId);
            if (success) {
                refreshFlightList();
                clearFields();
            } else {
                showAlert("Deletion Failed", "Flight Not Found", "No flight found with the provided ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid flight ID.", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleGetFlightById() {
        try {
            int flightId = Integer.parseInt(flightIdField.getText());
            Flight flight = flightController.getFlightById(flightId);
            if (flight != null) {
                flightNumField.setText(flight.getFlight_num());
                originField.setText(flight.getOrigin());
                destinationField.setText(flight.getDestination());
                dDepartField.setValue(flight.getD_depart().toLocalDate());
                dArrivalField.setValue(flight.getD_arrival().toLocalDate());
                tDepartField.setText(flight.getT_depart().toString());
                tArrivalField.setText(flight.getT_arrival().toString());
                aircraftIdField.setPromptText(aircraftIdToNameMap.get(flight.getAircraft().getAircraft_id()));
                airlineIdField.setPromptText(airlineIdToNameMap.get(flight.getAirline().getAirline_id()));
                availabilityCheckBox.setSelected(flight.getAvailability());
            } else {
                showAlert("Not Found", "Flight Not Found", "No flight found with the provided ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid flight ID.", "Error: " + e.getMessage());
        }
    }

    private void refreshFlightList() {
        flightListView.getItems().clear();
        for (Flight flight : flightController.getAllFlights()) {
            String aircraftName = aircraftIdToNameMap.get(flight.getAircraft().getAircraft_id());
            String airlineName = airlineIdToNameMap.get(flight.getAirline().getAirline_id());
            flightListView.getItems().add(
                flight.getFlight_id() + " - " + flight.getFlight_num() + " - " + 
                flight.getOrigin() + " - " + flight.getDestination() + " - " + 
                "Aircraft: " + aircraftName + " - " + "Airline: " + airlineName + " - " +
                flight.getD_depart() + " - " + flight.getD_arrival() + " - " + 
                flight.getT_depart() + " - " + flight.getT_arrival()
            );
        }
    }

    private void clearFields() {
        flightNumField.clear();
        originField.clear();
        destinationField.clear();
        dDepartField.setValue(null);
        dArrivalField.setValue(null);
        tDepartField.clear();
        tArrivalField.clear();
        aircraftIdField.setValue(null);
        airlineIdField.setValue(null);
        availabilityCheckBox.setSelected(false);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
