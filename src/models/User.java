package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	 private int user_id;
	    private String fullName;
	    private String cin;
	    private String passport;
	    private String phone;
	    private String email;
	    private String password;
	    private String address;
	    private String role;
	    private String account_status;
	    public User()
	    {}

		public User(int userId, String fullName, String cin, String passport, String phone, String email, String password, String address, String role, String account_status) {
			this.user_id = userId;
			this.fullName = fullName;
			this.cin = cin;
			this.passport = passport;
			this.phone = phone;
			this.email = email;
			this.password = password;
			this.address = address;
			this.account_status = account_status;
			this.setRole(role);
		}
		
		public User(String fullName, String cin, String passport, String phone, String email, String password, String address, String role, String account_status) {
			this.fullName = fullName;
			this.cin = cin;
			this.passport = passport;
			this.phone = phone;
			this.email = email;
			this.password = password;
			this.address = address;
			this.account_status = account_status;
			this.setRole(role);
		}
		
		
		public int getUserId() {
			return user_id;
		}
		public void setUserId(int userId) {
			this.user_id = userId;
		}
		
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		
		public String getCin() {
			return cin;
		}
		public void setCin(String cin) {
			this.cin = cin;
		}
		
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		
		public String getPassport() {
			return passport;
		}
		public void setPassport(String passport) {
			this.passport = passport;
		}

		public String getAccountStatus() {
			return account_status;
		}

		public void setAccountStatus(String account_status) {
			this.account_status = account_status;
		}
		 public StringProperty fullNameProperty() {
		        return new SimpleStringProperty(fullName);
		    }
		
		@Override
		public String toString() {
			return "User [fullName=" + fullName + ", cin=" + cin + ", passport=" + passport + ", phone=" + phone
					+ ", email=" + email + ", password=" + password + ", address=" + address + ", role=" + role
					+ ", account_status=" + account_status + "]";
		}
	
}