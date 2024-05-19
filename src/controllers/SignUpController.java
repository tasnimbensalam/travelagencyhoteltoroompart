package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_login;

    @FXML
    private TextField fullname_field;

    @FXML
    private TextField cin_field;

    @FXML
    private TextField passport_field;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField password_field;

    @FXML
    private TextField address_field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    		btn_signup.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (!fullname_field.getText().isEmpty() && 
	                    !cin_field.getText().isEmpty() && 
	                    !passport_field.getText().isEmpty() && 
	                    !phone_field.getText().isEmpty() && 
	                    !email_field.getText().isEmpty() && 
	                    !password_field.getText().isEmpty() && 
	                    !address_field.getText().isEmpty()) {
					DBUtils.signUpUser(event, fullname_field.getText(), cin_field.getText(), passport_field.getText(), phone_field.getText(), email_field.getText(), password_field.getText(), address_field.getText());
					DBUtils.changeScene(event, "/views/auth/signUp.fxml", "Logged !", email_field.getText());
				}
				else {
					System.out.println("Please fill out all the fields.");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill out all the fields with your information to Sign in !" );
					alert.show();
				}
					
				
			}
		} );
	

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/views/auth/logIn.fxml", "Log in", null);
            }
        });
    }
}
