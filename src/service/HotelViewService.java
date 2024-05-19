package service;


import controllers.HotelController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import models.Hotel;


public class HotelViewService {
	
	    @FXML
	    private TextField IdField;
	    @FXML
	    private TextField nameField;
	    
	    @FXML
	    private TextField conventionField;
	    
	    @FXML
	    private TextField countryField;
	    
	    @FXML
	    private TextField cityField;
	    @FXML
	    private TextField addressField;
	    @FXML
	    private TextField emailField;
	    @FXML
	    private TextField phoneNumberField;
	    

	    @FXML
	    private ListView<String> hotelListView;

	    private HotelController hotelController;

	    public HotelViewService() {
	        hotelController = new HotelController();
	    }

	    private void clearFields() {
	        nameField.clear();
	        IdField.clear();
	        conventionField.clear();
	        countryField.clear();
	        cityField.clear();
	        addressField.clear();
	        emailField.clear();
	        phoneNumberField.clear();
	    }
	    
	    @FXML
	    private void initialize() {
	        refreshHotelList();
	    }

	    @FXML
	    private void handleAddHotel() {
	        try {
	            String name = nameField.getText();
	            String country = countryField.getText();
	            String city = cityField.getText();
	            String address = addressField.getText();
	            String email = emailField.getText();

	            int phone = Integer.parseInt(phoneNumberField.getText());
	            int convention = Integer.parseInt(conventionField.getText());

	            System.out.println("Name: " + name);
	            System.out.println("Country: " + country);
	            System.out.println("City: " + city); 
	            System.out.println("Address: " + address);
	            System.out.println("Email: " + email);
	            System.out.println("Phone: " + phone);
	            System.out.println("Convention: " + convention);

	            hotelController.createHotel(name, convention, country, city, address, email, phone);
	            refreshHotelList();
	            clearFields();
	        } catch (NumberFormatException e) {
	            showAlert("Invalid Input", "Invalid Convention or Phone Number Value", "Please enter valid integer values for the convention and phone number fields.");
	        }
	    }


	    @FXML
	    private void handleUpdateHotel() {
	      
	            int HotelId = Integer.parseInt(IdField.getText());
	            String name = nameField.getText();
	            String country = countryField.getText();
	            String city = cityField.getText();
	            String adress = addressField.getText();
	            String email = emailField.getText();
	            
	            int phone = Integer.parseInt(phoneNumberField.getText());
	            int convention = Integer.parseInt(conventionField.getText());

	            hotelController.updateHotel(HotelId, name, convention,country,city,adress,email,phone);
	            refreshHotelList();
	            clearFields();

	    }

	    @FXML
	    private void handleDeleteHotel() {
	
	        
	        int hotelId = Integer.parseInt(IdField.getText());
	        boolean success = hotelController.deleteHotel(hotelId);
	        if (success) {
	            refreshHotelList();
	            clearFields();
	        } else {
	            showAlert("Deletion Failed", "Hotel Not Found", "No Hotel found with the provided ID.");
	        }
	    }

	    @FXML
	    private void handleGetHotelById() {
	    	int hotelId = Integer.parseInt(IdField.getText());
	        Hotel Hotel = hotelController.getHotelById(hotelId);
	        if (Hotel != null) {
	            nameField.setText(Hotel.getName());
	            conventionField.setText(String.valueOf(Hotel.getConvention()));
	            countryField.setText(Hotel.getCountry());
	            cityField.setText(Hotel.getCity());
	            addressField.setText(Hotel.getAddress());
	            emailField.setText(Hotel.getEmail());
	            phoneNumberField.setText(String.valueOf(Hotel.getPhoneNumber()));
	        } else {
	            showAlert("Not Found", "Hotel Not Found", "No Hotel found with the provided ID.");
	        }
	    }

	    private void refreshHotelList() {
	        hotelListView.getItems().clear();
	        for (Hotel hotel : hotelController.getAllHotels()) {
	            hotelListView.getItems().add(hotel.getName() + " - " + hotel.getHotel_id());
	        }
	    }


	    private void showAlert(String title, String header, String content) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle(title);
	        alert.setHeaderText(header);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
}
