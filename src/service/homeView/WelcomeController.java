package service.homeView;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controllers.DBUtils;

public class WelcomeController implements Initializable {

    @FXML
    private Button customerLoginBtn;

    @FXML
    private Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Animation du label de bienvenue
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(welcomeLabel);
        scale.setDuration(Duration.millis(3000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.EASE_OUT);
        scale.setByX(0.5);
        scale.setByY(0.5);
        scale.play();
    }

    @FXML
    public void switchToCustomerLogin(ActionEvent event) {
        // Redirection vers la vue de connexion client
        DBUtils.changeScene(event, "/views/customer/Login.fxml", "Log in", null);
    }

    @FXML
    public void switchToAdminLogin(ActionEvent event) throws IOException {
        // Redirection vers la vue de connexion admin
        Parent root = FXMLLoader.load(getClass().getResource("/admin/Login-view.fxml"));
        Stage stage = (Stage) customerLoginBtn.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
