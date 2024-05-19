package service;

import controllers.AircraftController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Aircraft;

import java.util.List;

public class AircraftViewService {
    @FXML
    private TextField aircraftIdField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField economicCapField;
    @FXML
    private TextField businessCapField;
    @FXML
    private TextField firstCapField;
    @FXML
    private ListView<String> aircraftListView;

    private AircraftController aircraftController;

    public AircraftViewService() {
        aircraftController = new AircraftController();
    }

    @FXML
    private void initialize() {
        refreshAircraftList();
    }

    @FXML
    private void handleAddAircraft() {
        try {
            String model = modelField.getText();
            int economicCap = Integer.parseInt(economicCapField.getText());
            int businessCap = Integer.parseInt(businessCapField.getText());
            int firstCap = Integer.parseInt(firstCapField.getText());

            Aircraft aircraft = aircraftController.createAircraft(model, economicCap, businessCap, firstCap);
            if (aircraft != null) {
                refreshAircraftList();
                clearFields();
            } else {
                showAlert("Error", "Unable to add aircraft.", "An error occurred while adding the aircraft.");
            }
        } catch (Exception e) {
            showAlert("Invalid Input", "Please ensure all fields are filled correctly.", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateAircraft() {
        try {
            int aircraftId = Integer.parseInt(aircraftIdField.getText());
            String model = modelField.getText();
            Integer economicCap = Integer.parseInt(economicCapField.getText());
            Integer businessCap = Integer.parseInt(businessCapField.getText());
            Integer firstCap = Integer.parseInt(firstCapField.getText());

            Aircraft aircraft = aircraftController.Aircraftupdate(aircraftId, model, economicCap, businessCap, firstCap);
            if (aircraft != null) {
                refreshAircraftList();
                clearFields();
            } else {
                showAlert("Error", "Unable to update aircraft.", "An error occurred while updating the aircraft.");
            }
        } catch (Exception e) {
            showAlert("Invalid Input", "Please ensure all fields are filled correctly.", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteAircraft() {
        try {
            int aircraftId = Integer.parseInt(aircraftIdField.getText());
            boolean success = aircraftController.deleteAircraft(aircraftId);
            if (success) {
                refreshAircraftList();
                clearFields();
            } else {
                showAlert("Error", "Unable to delete aircraft.", "An error occurred while deleting the aircraft.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid aircraft ID.", "Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleGetAircraftById() {
        try {
            int aircraftId = Integer.parseInt(aircraftIdField.getText());
            Aircraft aircraft = aircraftController.getAircraftById(aircraftId);
            if (aircraft != null) {
                modelField.setText(aircraft.getModel());
                economicCapField.setText(String.valueOf(aircraft.getEconomic_cap()));
                businessCapField.setText(String.valueOf(aircraft.getBusiness_cap()));
                firstCapField.setText(String.valueOf(aircraft.getFirst_cap()));
            } else {
                showAlert("Not Found", "Aircraft Not Found", "No aircraft found with the provided ID.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid aircraft ID.", "Error: " + e.getMessage());
        }
    }

    private void refreshAircraftList() {
        aircraftListView.getItems().clear();
        for (Aircraft aircraft : aircraftController.getAllAircrafts()) {
            aircraftListView.getItems().add(aircraft.getAircraft_id() + " - " + aircraft.getModel());
        }
    }

    private void clearFields() {
        aircraftIdField.clear();
        modelField.clear();
        economicCapField.clear();
        businessCapField.clear();
        firstCapField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
