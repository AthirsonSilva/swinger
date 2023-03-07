module com.app.swinger {
	requires java.desktop;

	opens com.app.swinger to javafx.fxml;
	exports com.app.swinger;
}