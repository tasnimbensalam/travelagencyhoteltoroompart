package models;

public class Room {
    private int room_num;
    private RoomType type;
    private int isAvailable;
    private double price;
    private int hotel_id;

    public Room(int roomNumber, RoomType type, double pricePerNight, int hotel_id) {
        this.room_num = roomNumber;
        this.type = type;
        this.isAvailable = 1; 
        this.price = pricePerNight;
        this.setHotel_id(hotel_id);
    }

    public int getRoomNum() {
        return room_num;
    }

    public void setRoomNum(int roomNumber) {
        this.room_num = roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int isAvailable() {
        return isAvailable;
    }

    public void setAvailable(int available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double pricePerNight) {
        this.price = pricePerNight;
    }

	public int getHotel_id() {
		return hotel_id;
	}

	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
}
