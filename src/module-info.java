module TravelAgency {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	  exports service;
	  exports controllers;
	  exports service.homeView;
	  
	  opens models to javafx.base;
	  opens controllers to javafx.graphics,javafx.fxml, javafx.base;
	  opens service to javafx.graphics,javafx.fxml;
	  opens service.homeView to javafx.graphics,javafx.fxml, javafx.base;
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
