package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChoiceController implements Initializable {
	public Stage choiceStage;
	@FXML
	private Button choiceStudent;
	@FXML
	private Button choiceTuition;
	@FXML
	private Button choiceBody;
//	@FXML
//	private Button choiceScore;
	@FXML
	private Button choiceLogin;
//	@FXML
//	private Button choiceClose;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0. 학생관리 버튼을 눌렀을때 처리하는 함수
		choiceStudent.setOnAction(e -> {
			handleBtnChoiceStudent();
		});
		// 0. 학비관리 버튼을 눌렀을때 처리하는 함수
		choiceTuition.setOnAction(e -> {
			handleBtnChoiceTuition();
		});
		// 0. 신체관리 버튼을 눌렀을때 처리하는 함수
		choiceBody.setOnAction(e -> {
			handleBtnBody();
		});
		// 0. 로그인창 버튼을 눌렀을때 처리하는 함수
		choiceLogin.setOnAction(e -> {
			handleBtnChoiceLogin();
		});
//		// 0. 관리닫기 버튼을 눌렀을때 처리하는 함수
//		choiceClose.setOnAction(e -> {
//			choiceStage.close();
//		});
	}
	
	// 0. 학생관리 버튼을 눌렀을때 처리하는 함수
	private void handleBtnChoiceStudent() {
		try {
			Stage studentStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/student.fxml"));
			Parent root = loader.load();
			StudentController studentController = loader.getController();
			studentController.studentStage = studentStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			studentStage.setTitle("HYHBALLET 학생관리");
			studentStage.setResizable(false);
			studentStage.setScene(scene);
			choiceStage.close();
			studentStage.show();
			LoginController.callAlert("학생관리:학생관리 창입니다.");
		} catch (Exception e) {
			LoginController.callAlert("학생관리실패:학생관리 창실패");
		}
	}

	// 0. 학비관리 버튼을 눌렀을때 처리하는 함수
	private void handleBtnChoiceTuition() {
		try {
			Stage tuitionStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuition.fxml"));
			Parent root = loader.load();
			TuitionController tuitionController = loader.getController();
			tuitionController.tuitionStage = tuitionStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			tuitionStage.setTitle("HYHBALLET 학비관리");
			tuitionStage.setResizable(false);
			tuitionStage.setScene(scene);
			choiceStage.close();
			tuitionStage.show();
			LoginController.callAlert("학비관리:학비관리 창입니다.");
		} catch (Exception e) {
			LoginController.callAlert("학비관리실패:학비관리 창실패");
		}

	}
	
	// 0. 신체관리 버튼을 눌렀을때 처리하는 함수
	private void handleBtnBody() {
		try {
			Stage bodyStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/bodyfit.fxml"));
			Parent root = loader.load();
			BodyController bodyController = loader.getController();
			bodyController.bodyStage = bodyStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			bodyStage.setTitle("HYHBALLET 신체관리");
			bodyStage.setResizable(false);
			bodyStage.setScene(scene);
			choiceStage.close();
			bodyStage.show();
			LoginController.callAlert("신체관리:신체관리 창입니다.");
		} catch (Exception e) {
			LoginController.callAlert("신체관리실패:신체관리 창실패");
		}
	}

	// 0. 로그인창 버튼을 눌렀을때 처리하는 함수
	private void handleBtnChoiceLogin() {
		try {
			Stage loginStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/login.fxml"));
			Parent root = loader.load();
			LoginController loginController = loader.getController();
			loginController.loginStage = loginStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			loginStage.setTitle("HYHBALLET 로그인");
			loginStage.setResizable(false);
			loginStage.setScene(scene);
			choiceStage.close();
			loginStage.show();
			LoginController.callAlert("로그인창:로그인창 입니다.");
		} catch (Exception e) {
			LoginController.callAlert("로그인창 실패:로그인창 실패");
		}
	}

}
