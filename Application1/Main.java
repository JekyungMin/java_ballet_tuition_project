package Application;

import Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage loginStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/login.fxml"));
		Parent root = loader.load();
		LoginController loginController = loader.getController();
		loginController.loginStage = loginStage;
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toString());
		loginStage.setTitle("HYHBALLET ·Î±×ÀÎ");
		loginStage.setResizable(false);
		loginStage.setScene(scene);
		loginStage.show();
	}
}
