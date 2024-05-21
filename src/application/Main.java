package application;





import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/auth/Feedback.fxml"));
            Scene scene = new Scene(root);

            String cssFile = String.valueOf(getClass().getResource("/views/css/welcome.css"));
            String cssFile2 = String.valueOf(getClass().getResource("/views/css/general.css"));
            scene.getStylesheets().add(cssFile);
            scene.getStylesheets().add(cssFile2);

            primaryStage.initStyle(StageStyle.TRANSPARENT);

            primaryStage.setScene(scene);
            primaryStage.show();
        
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 