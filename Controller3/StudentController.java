package Controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StudentController implements Initializable {
	public Stage studentStage;
	@FXML
	private TextField stuTxtSearch;
	@FXML
	private Button stuBtnSearch;
	@FXML
	private Button stuBtnImageChange;
	@FXML
	private Button stuBtnAdd;
	@FXML
	private Button stuBtnEdit;
	@FXML
	private Button stuBtnDelete;
	@FXML
	private Button stuBtnBack;
	@FXML
	private Button stuBtnClose;
	@FXML
	private TableView<Student> stuTableView;
	@FXML
	private ImageView stuImageView;

	private ObservableList<Student> stuListData = FXCollections.observableArrayList();
	private ObservableList<String> stuPartyList = FXCollections.observableArrayList();
	ArrayList<Student> stuArrayList;

	private Student selectedStudent;
	private int selectedStudentIndex;

	private File selectedFile = null;
	private String fileName = "";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 0.테이블뷰 세팅
		stuTableViewSet();

		// 0. 콤보박스 (반종류) 세팅
		stuPartyList.addAll("초등전공", "중등전공", "고등전공");

		// 0. 학생추가 버튼을 눌렀을때 처리하는 함수
		stuBtnAdd.setOnAction(e -> {
			handleStuBtnAddAction();
		});

		// 0. 학생수정 버튼을 눌렀을떄 처리하는 함수
		stuBtnEdit.setOnAction(e -> {
			handleStuBtnEditAction();
		});
		// 0. 학생삭제 버튼을 눌렀을때 처리하는 함수
		stuBtnDelete.setOnAction(e -> {
			handleStuBtnDeleteAction();
		});

		// 0. 테이블뷰를 한번 클릭했을때 함수
		stuTableView.setOnMouseClicked(e -> {
			handleStuTableViewClickAction(e);
		});

		// 0. 검색을 눌렀을때 처리하는 함수
		stuBtnSearch.setOnAction(e -> {
			handleStuBtnSearchAction();
		});

		// 0. 검색텍스트 필드에서 엔터를 눌렀을때 처리하는 함수
		stuTxtSearch.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleStuBtnSearchAction();
			}
		});

		// 0. 뒤로가기 버튼을 눌렀을때 처리하는 함수
		stuBtnBack.setOnAction(e -> {
			handleStuBtnBackAction();
		});

		// 0. 종료버튼을 눌렀을때 처리하는 함수
		stuBtnClose.setOnAction(e -> {
			studentStage.close();
		});
	}

	// 0.테이블뷰 세팅
	private void stuTableViewSet() {
		TableColumn tcStudentId = stuTableView.getColumns().get(0);
		tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		tcStudentId.setStyle("-fx-alignment: CENTER");
		TableColumn tcName = stuTableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER");
		TableColumn tcAge = stuTableView.getColumns().get(2);
		tcAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		tcAge.setStyle("-fx-alignment: CENTER");
		TableColumn tcPhone = stuTableView.getColumns().get(3);
		tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		tcPhone.setStyle("-fx-alignment: CENTER");
		TableColumn tcAddress = stuTableView.getColumns().get(4);
		tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcAddress.setStyle("-fx-alignment: CENTER");
		TableColumn tcParty = stuTableView.getColumns().get(5);
		tcParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tcParty.setStyle("-fx-alignment: CENTER");
		TableColumn tcImage = stuTableView.getColumns().get(6);
		tcImage.setCellValueFactory(new PropertyValueFactory<>("image"));
		tcImage.setStyle("-fx-alignment: CENTER");
		stuTableView.setItems(stuListData);

		stuArrayList = StudentDAO.getStudentFromBalletDB();
		for (Student student : stuArrayList) {
			stuListData.add(student);
		}
	}

	// 0. 학생추가 버튼을 눌렀을때 처리하는 함수
	private void handleStuBtnAddAction() {
		try {
			Stage addStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(studentStage);
			addStage.setTitle("학생추가창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/studentadd.fxml"));
			Parent root = loader.load();
			// ******************************ID등록*********************************
			TextField addTxtStudentId = (TextField) root.lookup("#addTxtStudentId");
			TextField addTxtName = (TextField) root.lookup("#addTxtName");
			TextField addTxtAge = (TextField) root.lookup("#addTxtAge");
			TextField addTxtPhone = (TextField) root.lookup("#addTxtPhone");
			TextField addTxtAddress = (TextField) root.lookup("#addTxtAddress");
			ComboBox<String> addCmbParty = (ComboBox) root.lookup("#addCmbParty");
			addCmbParty.setItems(stuPartyList);
			TextField addTxtImage = (TextField) root.lookup("#addTxtImage");
			addTxtImage.setEditable(false);
			Button addBtnImage = (Button) root.lookup("#addBtnImage");
			Button addBtnExample = (Button) root.lookup("#addBtnExample");
			Button addBtnAdd = (Button) root.lookup("#addBtnAdd");
			Button addBtnClose = (Button) root.lookup("#addBtnClose");
			ImageView addImageView = (ImageView) root.lookup("#addImageView");
			// ****************************addBtnImage 이벤트*****************************
			addBtnImage.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
				selectedFile = fileChooser.showOpenDialog(addStage);
				if (selectedFile != null) {
					fileName = selectedFile.getName();
				}
				addImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				stuImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				addTxtImage.setText(fileName);

			});
			// ****************************addBtnExample 이벤트*****************************
			addBtnExample.setOnAction(e -> {
				addTxtStudentId.setText("mjk7485");
				addTxtName.setText("민제경");
				addTxtAge.setText("11");
				addTxtPhone.setText("019-3572-7485");
				addTxtAddress.setText("푸르지오 105동 1202호");
				addCmbParty.getSelectionModel().select("초등전공");
			});
			// ****************************addBtnAdd 이벤트*****************************
			addBtnAdd.setOnAction(e -> {
				Student student = new Student(addTxtStudentId.getText(), addTxtName.getText(),
						Integer.parseInt(addTxtAge.getText()), addTxtPhone.getText(), addTxtAddress.getText(),
						addCmbParty.getSelectionModel().getSelectedItem(), fileName);

				int count = StudentDAO.insertStudentIntoBalletDB(student);
				if (count != 0) {
					stuListData.add(student);
					callAlert("추가:추가되었습니다.");
				}
				addTxtStudentId.clear();
				addTxtName.clear();
				addTxtAge.clear();
				addTxtPhone.clear();
				addTxtAddress.clear();
				addCmbParty.getSelectionModel().clearSelection();
				stuImageView.setImage(new Image(getClass().getResource("../images/basicImage.jpg").toString()));
				selectedFile = null;
				fileName = null;
			});
			// ****************************addBtnClose 이벤트***************************
			addBtnClose.setOnAction(e -> {
				addStage.close();
			});
			Scene scene = new Scene(root);
			addStage.setResizable(false);
			addStage.setScene(scene);
			addStage.show();
		} catch (Exception e) {
			callAlert("학생추가창 오류 :학생추가창 오류가 발생했습니다.");
		}
	}

	// 0. 학생수정 버튼을 눌렀을떄 처리하는 함수
	private void handleStuBtnEditAction() {
		selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
		selectedStudentIndex = stuTableView.getSelectionModel().getSelectedIndex();
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			//editStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(studentStage);
			editStage.setTitle("학생수정창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/studentedit.fxml"));
			Parent root = loader.load();
			// ******************************ID등록*********************************
			TextField editTxtStudentId = (TextField) root.lookup("#editTxtStudentId");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtAge = (TextField) root.lookup("#editTxtAge");
			TextField editTxtPhone = (TextField) root.lookup("#editTxtPhone");
			TextField editTxtAddress = (TextField) root.lookup("#editTxtAddress");
			ComboBox<String> editCmbParty = (ComboBox) root.lookup("#editCmbParty");
			editCmbParty.setItems(stuPartyList);
			TextField editTxtImage = (TextField) root.lookup("#editTxtImage");
			editTxtImage.setEditable(false);
			Button editBtnImage = (Button) root.lookup("#editBtnImage");
			Button editBtnExample = (Button) root.lookup("#editBtnExample");
			Button editBtnEdit = (Button) root.lookup("#editBtnEdit");
			Button editBtnClose = (Button) root.lookup("#editBtnClose");
			ImageView editImageView = (ImageView) root.lookup("#editImageView");
			// ****************************수정화면 초기화*********************************
			editTxtStudentId.setText(selectedStudent.getStudentId());
			editTxtName.setText(selectedStudent.getName());
			editTxtAge.setText(String.valueOf(selectedStudent.getAge()));
			editTxtPhone.setText(selectedStudent.getPhone());
			editTxtAddress.setText(selectedStudent.getAddress());
			editCmbParty.getSelectionModel().select(selectedStudent.getParty());
			editTxtImage.setText(selectedStudent.getImage());
			editImageView
					.setImage(new Image(getClass().getResource("../images/" + selectedStudent.getImage()).toString()));
			editTxtStudentId.setDisable(true);
			editTxtName.setDisable(true);
			// ****************************editBtnExample 이벤트*****************************
			editBtnExample.setOnAction(e -> {
				// editTxtStudentId.setText("yjy5014");
				// editTxtName.setText("유진이");
				editTxtAge.setText("14");
				editTxtPhone.setText("019-6414-5142");
				editTxtAddress.setText("월드메르디앙 101동 201호");
				editCmbParty.getSelectionModel().select("중등전공");
			});
			// ****************************editBtnImage 이벤트*****************************
			editBtnImage.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
				selectedFile = fileChooser.showOpenDialog(editStage);
				if (selectedFile != null) {
					fileName = selectedFile.getName();
				}
				editImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				stuImageView.setImage(new Image(getClass().getResource("../images/" + fileName).toString()));
				editTxtImage.setText(fileName);

			});
			// ****************************editBtnEdit 이벤트*****************************
			editBtnEdit.setOnAction(e -> {
				Student student = new Student(editTxtStudentId.getText(), editTxtName.getText(),
						Integer.parseInt(editTxtAge.getText()), editTxtPhone.getText(), editTxtAddress.getText(),
						editCmbParty.getSelectionModel().getSelectedItem(), editTxtImage.getText());

				int count = StudentDAO.updateStudentSet(student, selectedStudent);
				if (count != 0) {
					stuListData.set(selectedStudentIndex, student);
					callAlert("수정:수정되었습니다.");
					editStage.close();
				} else {
					return;
				}
			});
			// ****************************editBtnClose 이벤트***************************
			editBtnClose.setOnAction(e -> {
				editStage.close();
			});
			Scene scene = new Scene(root);
			editStage.setResizable(false);
			editStage.setScene(scene);
			editStage.show();
		} catch (Exception e) {
			callAlert("학생수정 오류 :학생선택 후 수정해주세요.");
		}
	}

	// 0. 학생삭제 버튼을 눌렀을때 처리하는 함수
	private void handleStuBtnDeleteAction() {
		try {
			selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
			int count = StudentDAO.deleteStudentFromDB(selectedStudent.getStudentId());
			if (count != 0) {
				stuListData.remove(selectedStudent);
				stuArrayList.remove(selectedStudent);
				callAlert("삭제:삭제되었습니다.");
			} else {
				return;
			}
		} catch (Exception e) {
			callAlert("학생삭제 오류:학생선택 후 삭제해주세요.");
		}
	}

	// 0. 테이블뷰를 한번 클릭했을때 함수
	private void handleStuTableViewClickAction(MouseEvent e) {
		try {
			selectedStudent = stuTableView.getSelectionModel().getSelectedItem();
			selectedStudentIndex = stuTableView.getSelectionModel().getSelectedIndex();
			if (e.getClickCount() == 1) {
				stuImageView.setImage(
						new Image(getClass().getResource("../images/" + selectedStudent.getImage()).toString()));
			}
		} catch (Exception e1) {
		}

	}

	// 0. 검색을 눌렀을때 처리하는 함수
	private void handleStuBtnSearchAction() {
		for (Student student : stuListData) {
			if (stuTxtSearch.getText().trim().equals(student.getName())) {
				stuTableView.getSelectionModel().select(student);
			}
		}
	}

	// 0. 뒤로가기 버튼을 눌렀을때 처리하는 함수
	private void handleStuBtnBackAction() {
		try {
			stuArrayList.clear();
			stuListData.clear();
			Stage choiceStage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/choice.fxml"));
			Parent root = loader.load();
			ChoiceController choiceController = loader.getController();
			choiceController.choiceStage = choiceStage;
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../Application/app.css").toString());
			choiceStage.setTitle("HYHBALLET 메뉴선택");
			choiceStage.setResizable(false);
			choiceStage.setScene(scene);
			studentStage.close();
			choiceStage.show();

		} catch (Exception e) {
		}
	}

	// 기타 : 알림창
	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("알림창");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));
		alert.showAndWait();
	}

}
