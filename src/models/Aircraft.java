package models;

	public class Aircraft {
		
	private int aircraft_id;
	private String model;
	private int economic_cap;
	private int business_cap;
	private int first_cap;
	
	
	public Aircraft(int aircraft_id, String model, int economic_cap, int business_cap, int first_cap) {
		super();
		this.aircraft_id = aircraft_id;
		this.model = model;
		this.economic_cap = economic_cap;
		this.business_cap = business_cap;
		this.first_cap = first_cap;
	}
	public Aircraft(String model, int economic_cap, int business_cap, int first_cap) {
		super();
		this.model = model;
		this.economic_cap = economic_cap;
		this.business_cap = business_cap;
		this.first_cap = first_cap;
	}
	public int getAircraft_id() {
		return aircraft_id;
	}
	public void setAircraft_id(int aircraft_id) {
		this.aircraft_id = aircraft_id;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	public int getEconomic_cap() {
		return economic_cap;
	}
	public void setEconomic_cap(int economic_cap) {
		this.economic_cap = economic_cap;
	}
	public int getBusiness_cap() {
		return business_cap;
	}
	public void setBusiness_cap(int business_cap) {
		this.business_cap = business_cap;
	}
	public int getFirst_cap() {
		return first_cap;
	}
	public void setFirst_cap(int first_cap) {
		this.first_cap = first_cap;
	}
	public String getModel() {
		return model;
	}
	
	
	}
