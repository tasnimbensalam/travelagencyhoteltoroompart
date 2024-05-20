package models;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class FeedbackHotel {
	
	private int feedback_id;
	private User client;
    private Hotel hotel;
    private String comment;
    private Double rates; 
    

    public Double getRates() {
		return rates;
	}
	public void setRates(Double rates) {
		this.rates = rates;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String commentaire) {
		this.comment = commentaire;
	}
	public int getFeedback_id() {
		return feedback_id;
	}
	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}

	public StringProperty clientNameProperty() {
        return new SimpleStringProperty(client.getFullName());
    }

    public StringProperty hotelNameProperty() {
        return new SimpleStringProperty(hotel.getName());
    }
}
