package service;

import java.util.List;

import controllers.UsersController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.RoomType;
import models.User;

public class UsersViewService {

    private UsersController usersController;

    @FXML
    private TextField address_field;

    @FXML
    private TextField cin_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField fullname_field;

    @FXML
    private TextField passport_field;

    @FXML
    private TextField password_field;

    @FXML
    private TextField phone_field;

    @FXML
    private ListView<String> userListView;

    @FXML
    private TextField UserIdField;

    @FXML
    private TextField searchField;

    public UsersViewService() {
        usersController = new UsersController();
    }

    @FXML
    private void initialize() {
        refreshUserList();
    }

    private void refreshUserList() {
        userListView.getItems().clear();
        for (User u : UsersController.getAllUsers()) {
            userListView.getItems().add(u.getUserId() + " - " + u.getFullName() + " - " + u.getCin() + " - " + u.getPassport() + " - " + u.getEmail() + " - " + u.getPassword() + " - " + u.getAddress() + " - " + u.getRole() + " - " + u.getAccountStatus());
        }
    }

    @FXML
    private void handleAddUser() {
        String fullname = fullname_field.getText();
        String cin = cin_field.getText();
        String passport = passport_field.getText();
        String phone = phone_field.getText();
        String email = email_field.getText();
        String password = password_field.getText();
        String address = address_field.getText();

        if (fullname.isEmpty() || cin.isEmpty() || passport.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            showAlert("Input Error", "Empty Fields", "All fields must be filled.");
            return;
        }

        UsersController.handleAddUser(fullname, cin, passport, phone, email, password, address);
        refreshUserList();
    }

    @FXML
    private void handleUpdateUser() {
        int userId;
        try {
            userId = Integer.parseInt(UserIdField.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid ID", "User ID must be a valid integer.");
            return;
        }

        String fullname = fullname_field.getText();
        String cin = cin_field.getText();
        String passport = passport_field.getText();
        String phone = phone_field.getText();
        String email = email_field.getText();
        String password = password_field.getText();
        String address = address_field.getText();
        String accountStatus = "Active"; // Assuming a default value for account status

        if (fullname.isEmpty() || cin.isEmpty() || passport.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
            showAlert("Input Error", "Empty Fields", "All fields must be filled.");
            return;
        }

        User updatedUser = UsersController.updateUser(userId, fullname, cin, passport, phone, email, password, address, accountStatus);
        if (updatedUser != null) {
            refreshUserList();
        } else {
            showAlert("Update Error", "Update Failed", "Failed to update user details.");
        }
    }

    @FXML
    private void handleDeleteUser() {
        int userId;
        try {
            userId = Integer.parseInt(UserIdField.getText());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid ID", "User ID must be a valid integer.");
            return;
        }

        boolean isDeleted = UsersController.deleteUser(userId);
        if (isDeleted) {
            refreshUserList();
        } else {
            showAlert("Deletion Error", "Deletion Failed", "Failed to delete the user.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText();
        List<User> searchResults = usersController.searchUsers(query);
        userListView.getItems().clear();
        for (User u : searchResults) {
            userListView.getItems().add(u.getUserId() + " - " + u.getFullName() + " - " + u.getCin() + " - " + u.getPassport() + " - " + u.getPassport() + " - " + u.getPhone() + " - " + u.getPassword() + " - " + u.getAddress() + " - " + u.getRole() + " - " + u.getAccountStatus());
        }
    }
    
    @FXML
    private void handleUsersSelection(MouseEvent event) {
        String selectedUserString = userListView.getSelectionModel().getSelectedItem();
        if (selectedUserString != null) {
            String[] parts = selectedUserString.split(" - ");
            if (parts.length == 9) {
                int userId = Integer.parseInt(parts[0]);
                String fullName = parts[1];
                String cin = parts[2];
                String phone = parts[3];
                String email = parts[4];
                String password = parts[5];
                String address = parts[6];
                String passport = parts[7];
                String accountStatus = parts[8];

                UserIdField.setText(String.valueOf(userId));
                fullname_field.setText(fullName);
                cin_field.setText(cin);
                phone_field.setText(phone);
                passport_field.setText(passport);
                email_field.setText(email);
                password_field.setText(password);
                address_field.setText(address);
            } else {
                System.err.println("Invalid format for selected user string.");
            }
        }
    }




}
