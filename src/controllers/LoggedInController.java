package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoggedInController implements Initializable {
    
    @FXML
    private Button btn_logout;
    
    @FXML
    private Label label_logged;
    
    // logout action
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btn_logout.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) { // when user logs out => change scene
                System.out.println("Logout button clicked."); // Debugging
                DBUtils.changeScene(event, "/views/auth/logIn.fxml", "Log in", null);
            }
        });
        
    }
    
    // set user's custom information
    public void setUserInfo(String email) {
        label_logged.setText("Welcome" + email + "!");
        
    }

}
