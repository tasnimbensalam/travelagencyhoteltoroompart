package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connexion.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DBUtils {

	public static void changeScene(ActionEvent event, String fxmlFile, String title, String email) {
	    Parent root = null;
	    try {
	        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
	        root = loader.load();

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setTitle(title);
	        stage.setScene(new Scene(root, 680, 400));
	        stage.show();

	        if (fxmlFile.equals("/views/auth/logged.fxml")) { // logged in
	            LoggedInController loggedInController = loader.getController();
	            loggedInController.setUserInfo(email);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}



    public static void signUpUser(ActionEvent event, String fullname, String cin, String passport, String phone, String email, String password, String address) {
        Connection connection = null;
        PreparedStatement statementInsert = null;
        PreparedStatement statementCheckUserExist = null;
        ResultSet resultSet = null;

        try {
            connection = Connexion.obtenirConnexion();
            statementCheckUserExist = connection.prepareStatement("SELECT * FROM user WHERE email = ?"); // ? : typed email
            statementCheckUserExist.setString(1, email);
            resultSet = statementCheckUserExist.executeQuery();

            if (resultSet.isBeforeFirst()) { // checks if the set is empty, returns true/false
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this Email.");
                alert.show();
            } else { // insert the user
                statementInsert = connection.prepareStatement("INSERT INTO user(fullName, cin, passport, phone, email, password, address, role, account_status) VALUES(?,?,?,?,?,?,?,?,?)");
                statementInsert.setString(1, fullname);
                statementInsert.setString(2, cin);
                statementInsert.setString(3, passport);
                statementInsert.setString(4, phone);
                statementInsert.setString(5, email);
                statementInsert.setString(6, password);
                statementInsert.setString(7, address);
                statementInsert.setString(8, "User");
                statementInsert.setString(9, "Active");
                statementInsert.executeUpdate();

                changeScene(event, "/views/auth/logged.fxml", "Welcome", email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statementCheckUserExist != null) {
                try {
                    statementCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statementInsert != null) {
                try {
                    statementInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                Connexion.close(connection);
            }
        }
    }

    public static void logInUser(ActionEvent event, String email, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = Connexion.obtenirConnexion();
            preparedStatement = connection.prepareStatement("SELECT password FROM user WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) { // if the set is null
                System.out.println("User not found in DB!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided Credentials are incorrect.");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    // compare passwords
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "/views/auth/logged.fxml", "Welcome", email);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                Connexion.close(connection);
            }
        } 
    }
}
