package models;

import java.util.Date;

public class HotelReservation {
    private int rev_id;
    private Hotel hotel;
	private User guest;
    private Date check_in;
    private Date check_out;
    private Double TotPrice;
 
    public HotelReservation(int reservationId, Room room, User guest, Date checkInDate, Date checkOutDate) {
        this.rev_id = reservationId;
        this.guest = guest;
        this.check_in = checkInDate;
        this.check_out = checkOutDate;
    }

  
    public int getRevId() {
        return rev_id;
    }

    public void setRevId(int reservationId) {
        this.rev_id = reservationId;
    }



    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }



	public Double getTotPrice() {
		return TotPrice;
	}


	public void setTotPrice(Double totPrice) {
		TotPrice = totPrice;
	}
	 public int getRev_id() {
			return rev_id;
		}


		public void setRev_id(int rev_id) {
			this.rev_id = rev_id;
		}


		public Hotel getHotel() {
			return hotel;
		}


		public void setHotel(Hotel hotel) {
			this.hotel = hotel;
		}


		public Date getCheck_in() {
			return check_in;
		}


		public void setCheck_in(Date check_in) {
			this.check_in = check_in;
		}


		public Date getCheck_out() {
			return check_out;
		}


		public void setCheck_out(Date check_out) {
			this.check_out = check_out;
		}

}

