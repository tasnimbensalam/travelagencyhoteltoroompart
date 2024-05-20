package service;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controllers.FeedbackController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableCell;
import models.FeedbackHotel;
import models.Hotel;
import models.User;

public class FeedbackHotelService implements Initializable {

    @FXML
    private ComboBox<User> clientComboBox;

    @FXML
    private ComboBox<Hotel> hotelComboBox;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private TextField ratesTextField;

    @FXML
    private Button submitButton;

    @FXML
    private TableView<FeedbackHotel> feedbackTable;

    @FXML
    private TableColumn<FeedbackHotel, String> clientColumn;

    @FXML
    private TableColumn<FeedbackHotel, String> hotelColumn;

    @FXML
    private TableColumn<FeedbackHotel, String> commentColumn;

    @FXML
    private TableColumn<FeedbackHotel, Double> ratesColumn;

    @FXML
    private TableColumn<FeedbackHotel, Void> actionColumn;

    private FeedbackController feedbackController;
    private ObservableList<FeedbackHotel> feedbackList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        feedbackController = new FeedbackController();
        populateComboBoxes();
        setupTable();
        loadFeedbacks();
    }

    private void populateComboBoxes() {
        List<User> users = feedbackController.getAllUsers();
        clientComboBox.getItems().addAll(users);

        List<Hotel> hotels = feedbackController.getAllHotels();
        hotelComboBox.getItems().addAll(hotels);
    }

    private void setupTable() {
        clientColumn.setCellValueFactory(cellData -> cellData.getValue().clientNameProperty());
        hotelColumn.setCellValueFactory(cellData -> cellData.getValue().hotelNameProperty());
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        ratesColumn.setCellValueFactory(new PropertyValueFactory<>("rates"));

        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(deleteButton);

            {
                deleteButton.setOnAction(event -> {
                    FeedbackHotel feedback = getTableView().getItems().get(getIndex());
                    handleDeleteButtonAction(feedback);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }



    private void loadFeedbacks() {
        feedbackList = FXCollections.observableArrayList(feedbackController.getAllFeedbacks());
        feedbackTable.setItems(feedbackList);
    }

    @FXML
    private void handleSubmitButtonAction() {
        User selectedClient = clientComboBox.getValue();
        Hotel selectedHotel = hotelComboBox.getValue();
        String comment = commentTextArea.getText();
        Double rates = Double.valueOf(ratesTextField.getText());

        FeedbackHotel feedback = new FeedbackHotel();
        feedback.setClient(selectedClient);
        feedback.setHotel(selectedHotel);
        feedback.setComment(comment);
        feedback.setRates(rates);

        feedbackController.saveFeedback(feedback);
        loadFeedbacks();
        clearForm();
    }

  
    private void handleDeleteButtonAction(FeedbackHotel feedback) {
        feedbackController.deleteFeedback(feedback.getFeedback_id());
        loadFeedbacks();
    }

    private void clearForm() {
        clientComboBox.setValue(null);
        hotelComboBox.setValue(null);
        commentTextArea.clear();
        ratesTextField.clear();
    }
}
