package models;

public class FeedbackHotel {
	
	
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

	
}
