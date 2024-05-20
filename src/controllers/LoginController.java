package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML
    private Hyperlink btn_signup;

    @FXML
    private TextField email_field;

    @FXML
    private TextField password_field;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Login button action
        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, email_field.getText(), password_field.getText());
            }
        });

        // Sign-up button action
        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/views/customer/Signup.fxml", "Sign up", null);
            }
        });
    }
    @FXML
    private Button closeBtn;

    @FXML
    private void handleCloseButtonAction() {
        // Fermeture de l'application JavaFX
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }
}
