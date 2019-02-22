package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import Model.Tuition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TuitionController implements Initializable {
	public Stage tuitionStage;
	@FXML
	private TextField tuiTxtSearch;
	@FXML
	private TextField tuiTxtSum;
	@FXML
	private Button tuiBtnSearch;
	@FXML
	private Button tuiBtnAdd;
	@FXML
	private Button tuiBtnEdit;
	@FXML
	private Button tuiBtnDelete;
	@FXML
	private Button tuiBtnLineSum;
	@FXML
	private Button tuiBtnScatterOprice;
	@FXML
	private Button tuiBtnAreaIprice;
	@FXML
	private Button tuiBtnSelectedTotal;
	@FXML
	private Button tuiBtnBarTotal;
	@FXML
	private Button tuiBtnPiePIOsum;
	@FXML
	private Button tuiBtnBack;
	@FXML
	private Button tuiBtnClose;
	@FXML
	private ComboBox<String> tuiCmbPayDate;
	@FXML
	private TableView<Tuition> tuiTableView;

	private ObservableList<Tuition> tuiListData = FXCollections.observableArrayList();
	private ObservableList<String> tuiPayDateList = FXCollections.observableArrayList();
	private ObservableList<String> tuiIndividualList = FXCollections.observableArrayList();
	private ObservableList<String> tuiOpusList = FXCollections.observableArrayList();
	ArrayList<Tuition> tuiArrayList = new ArrayList<>();
	ArrayList<String> tuiNameAndParty = new ArrayList<>();
	TreeMap<String, Integer> tuiSum = new TreeMap<>();
	LinkedHashMap<String, Integer> tuiOprice = new LinkedHashMap<>();
	TreeMap<String, Integer> tuiIprice = new TreeMap<>();
	LinkedHashMap<String, Integer> tuiTotal = new LinkedHashMap<>();

	ArrayList<Integer> tuiEntireIprice;
	ArrayList<Integer> tuiSelectedTotal;

	int monthlySum;

	private Tuition selectedTuition;
	private int selectedTuitionIndex;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// 0.테이블뷰 세팅
		tuiTableViewSet();

		// 0.콤보박스(납부월) 세팅
		tuiPayDateList.addAll("2019년 2월","2019년 1월", "2018년 12월", "2018년 11월", "2018년 10월", "2018년 9월", "2018년 8월", "2018년 7월",
				"2018년 6월", "2018년 5월", "2018년 4월", "2018년 3월", "2018년 2월", "2018년 1월");
		tuiCmbPayDate.setItems(tuiPayDateList);

		// 0.콤보박스(개인레슨 횟수) 세팅
		tuiIndividualList.addAll("0회", "1회", "2회", "3회", "4회", "5회", "6회");

		// 0.콤보박스(작품명) 세팅
		tuiOpusList.addAll("작품미선택", "르코르세르", "백조의호수", "파랑새", "라실피드", "라바야데르", "파라오의딸", "레이몬다", "지젤", "호두까기인형", "블랙스완",
				"코펠리아", "파키타", "할리퀸아드", "실비아", "카르멘", "고집쟁이딸", "세헤라자데", "인형요정", "돈키호테", "탈리스만", "카멜리아", "키트리", "에스메랄다");

		// 0. 검색을 눌렀을때 처리하는 함수
		tuiBtnSearch.setOnAction(e -> {
			handleTuiBtnSearchAction();
		});

		// 0. 검색텍스트 필드에서 엔터를 눌렀을때 처리하는 함수
		tuiTxtSearch.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				handleTuiBtnSearchAction();
			}
		});

		// 0. 학비추가 버튼을 눌렀을때 처리하는 함수
		tuiBtnAdd.setOnAction(e -> {
			handleTuiBtnAddAction();
		});

		// 0. 학비수정 버튼을 눌렀을때 처리하는 함수
		tuiBtnEdit.setOnAction(e -> {
			handleTuiBtnEditAction();
		});

		// 0. 학비삭제 버튼을 눌렀을때 처리하는 함수
		tuiBtnDelete.setOnAction(e -> {
			handleTuiBtnDeleteAction();
		});

		// 0. 월별총수업료 비교 버튼을 눌렀을때 처리하는 함수
		tuiBtnLineSum.setOnAction(e -> {
			handleTuiBtnLineSumAction();
		});

		// 0. 월별총작품비 비교 버튼을 눌렀을때 처리하는 함수
		tuiBtnScatterOprice.setOnAction(e -> {
			handleTuiBtnScatterOpriceAction();
		});

		// 0. 월별총개인레슨비 비교 버튼을 눌렀을때 처리하는 함수
		tuiBtnAreaIprice.setOnAction(e -> {
			handleTuiBtnAreaIpriceAction();
		});

		// 0. 월별선택학생총수업료 버튼을 눌렀을때 처리하는 함수
		tuiBtnSelectedTotal.setOnAction(e -> {
			handleTuiBtnSelectedTotalAction();
		});

		// 0. 당월학생별총수업비 버튼을 눌렀을때 처리하는 함수
		tuiBtnBarTotal.setOnAction(e -> {
			handleTuiBtnBarTotalAction();
		});

		// 0. 당월총레슨비비교 버튼을 눌렀을때 처리하는 함수
		tuiBtnPiePIOsum.setOnAction(e -> {
			handleTuiBtnPiePIOsumAction();
		});
		// 0. 테이블뷰를 두번클릭 했을때 처리하는 함수
		tuiTableView.setOnMouseClicked(e -> {
			handleTuiTableViewDoubleClickedAction(e);
		});
		// 0. 뒤로가기 버튼을 눌렀을때 처리하는 함수
		tuiBtnBack.setOnAction(e -> {
			handleTuiBtnBackAction();
		});

		// 0. 종료버튼을 눌렀을때 처리하는 함수
		tuiBtnClose.setOnAction(e -> {
			tuitionStage.close();
		});
	}

	// 0.테이블뷰 세팅
	private void tuiTableViewSet() {
		TableColumn tcStudentId = tuiTableView.getColumns().get(0);
		tcStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		tcStudentId.setStyle("-fx-alignment: CENTER");
		TableColumn tcName = tuiTableView.getColumns().get(1);
		tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcName.setStyle("-fx-alignment: CENTER");
		TableColumn tcParty = tuiTableView.getColumns().get(2);
		tcParty.setCellValueFactory(new PropertyValueFactory<>("party"));
		tcParty.setStyle("-fx-alignment: CENTER");
		TableColumn tcIndividual = tuiTableView.getColumns().get(3);
		tcIndividual.setCellValueFactory(new PropertyValueFactory<>("individual"));
		tcIndividual.setStyle("-fx-alignment: CENTER");
		TableColumn tcOpus = tuiTableView.getColumns().get(4);
		tcOpus.setCellValueFactory(new PropertyValueFactory<>("opus"));
		tcOpus.setStyle("-fx-alignment: CENTER");
		TableColumn tcPprice = tuiTableView.getColumns().get(5);
		tcPprice.setCellValueFactory(new PropertyValueFactory<>("pprice"));
		tcPprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcIprice = tuiTableView.getColumns().get(6);
		tcIprice.setCellValueFactory(new PropertyValueFactory<>("iprice"));
		tcIprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcOprice = tuiTableView.getColumns().get(7);
		tcOprice.setCellValueFactory(new PropertyValueFactory<>("oprice"));
		tcOprice.setStyle("-fx-alignment: CENTER");
		TableColumn tcTotal = tuiTableView.getColumns().get(8);
		tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		tcTotal.setStyle("-fx-alignment: CENTER");
		TableColumn tcPayDate = tuiTableView.getColumns().get(9);
		tcPayDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));
		tcPayDate.setStyle("-fx-alignment: CENTER");
		tuiTableView.setItems(tuiListData);

		// 콤보박스(납부월) 선택에 따라 데이터베이스에서 학비관리를 따로 가져온다.
		tuiCmbPayDate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				switch (newValue) {
				case "2019년 2월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.get2019FebruaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2019년 1월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.get2019JanuaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 12월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getDecemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 11월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getNovemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 10월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getOctoberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 9월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getSeptemberTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 8월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getAugustTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 7월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJulyTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 6월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJuneTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 5월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getMayTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 4월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getAprilTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 3월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getMarchTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 2월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getFebruaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				case "2018년 1월":
					if (tuiArrayList != null) {
						tuiArrayList.clear();
					}
					if (tuiListData != null) {
						tuiListData.clear();
					}
					tuiArrayList = TuitionDAO.getJanuaryTuitionFromBalletDb();
					for (Tuition tuition : tuiArrayList) {
						tuiListData.add(tuition);
					}
					setMonthlySum();
					break;
				}
			}
		});
	}

	// 0. 학비추가 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnAddAction() {
		try {
			Stage addStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			addStage.initOwner(tuitionStage);
			addStage.setTitle("학비추가창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionadd.fxml"));
			Parent root = loader.load();
			// ******************************ID등록*********************************
			TextField addTxtStudentId = (TextField) root.lookup("#addTxtStudentId");
			Button addBtnStudentId = (Button) root.lookup("#addBtnStudentId");
			TextField addTxtName = (TextField) root.lookup("#addTxtName");
			TextField addTxtParty = (TextField) root.lookup("#addTxtParty");
			ComboBox<String> addCmbIndividual = (ComboBox) root.lookup("#addCmbIndividual");
			addCmbIndividual.setItems(tuiIndividualList);
			ComboBox<String> addCmbOpus = (ComboBox) root.lookup("#addCmbOpus");
			addCmbOpus.setItems(tuiOpusList);
			TextField addTxtPprice = (TextField) root.lookup("#addTxtPprice");
			TextField addTxtIprice = (TextField) root.lookup("#addTxtIprice");
			TextField addTxtOprice = (TextField) root.lookup("#addTxtOprice");
			TextField addTxtTotal = (TextField) root.lookup("#addTxtTotal");
			Button addBtnTotal = (Button) root.lookup("#addBtnTotal");
			DatePicker addPayDate = (DatePicker) root.lookup("#addPayDate");
			Button addBtnAdd = (Button) root.lookup("#addBtnAdd");
			Button addBtnClose = (Button) root.lookup("#addBtnClose");
			addTxtPprice.setEditable(false);
			addTxtIprice.setEditable(false);
			addTxtOprice.setEditable(false);
			addTxtTotal.setEditable(false);
			// ****************************addBtnStudentId 이벤트*****************************
			// 학비추가 창에서 아이디 조회버튼을 눌렀을때 처리한다.
			addBtnStudentId.setOnAction(e -> {
				try {
					if (tuiNameAndParty != null) {
						tuiNameAndParty.clear();
					}
					tuiNameAndParty = TuitionDAO.getNameAndPartyWhenStudentIdSearched(addTxtStudentId.getText());
					addTxtName.setText(tuiNameAndParty.get(0));
					addTxtParty.setText(tuiNameAndParty.get(1));
				} catch (Exception e1) {
					StudentController.callAlert("알림:없는 아이디 입니다.");
				}
			});
			// *********************addTxtStudentId에서 엔터를 쳤을때 이벤트******************
			addTxtStudentId.setOnKeyPressed(e -> {
				try {
					if (e.getCode().equals(KeyCode.ENTER)) {
						if (tuiNameAndParty != null) {
							tuiNameAndParty.clear();
						}
						tuiNameAndParty = TuitionDAO.getNameAndPartyWhenStudentIdSearched(addTxtStudentId.getText());
						addTxtName.setText(tuiNameAndParty.get(0));
						addTxtParty.setText(tuiNameAndParty.get(1));
					}
				} catch (Exception e1) {
					StudentController.callAlert("알림:없는 아이디 입니다.");
				}
			});
			// *****************************학생추가창 학비세팅**********************************
			// 그룹레슨비 세팅
			addTxtParty.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "고등전공":
						addTxtPprice.setText("160000");
						break;
					case "중등전공":
						addTxtPprice.setText("130000");
						break;
					case "초등전공":
						addTxtPprice.setText("100000");
						break;
					}
				}
			});
			// 개인레슨비 세팅
			addCmbIndividual.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (addTxtParty.getText().equals("고등전공")) {
						switch (newValue) {
						case "0회":
							addTxtIprice.setText("0");
							break;
						case "1회":
							addTxtIprice.setText("40000");
							break;
						case "2회":
							addTxtIprice.setText("80000");
							break;
						case "3회":
							addTxtIprice.setText("120000");
							break;
						case "4회":
							addTxtIprice.setText("160000");
							break;
						case "5회":
							addTxtIprice.setText("200000");
							break;
						case "6회":
							addTxtIprice.setText("240000");
							break;
						}
					}
					if (addTxtParty.getText().equals("중등전공")) {
						switch (newValue) {
						case "0회":
							addTxtIprice.setText("0");
							break;
						case "1회":
							addTxtIprice.setText("30000");
							break;
						case "2회":
							addTxtIprice.setText("60000");
							break;
						case "3회":
							addTxtIprice.setText("90000");
							break;
						case "4회":
							addTxtIprice.setText("120000");
							break;
						case "5회":
							addTxtIprice.setText("150000");
							break;
						case "6회":
							addTxtIprice.setText("180000");
							break;
						}
					}
					if (addTxtParty.getText().equals("초등전공")) {
						switch (newValue) {
						case "0회":
							addTxtIprice.setText("0");
							break;
						case "1회":
							addTxtIprice.setText("20000");
							break;
						case "2회":
							addTxtIprice.setText("40000");
							break;
						case "3회":
							addTxtIprice.setText("60000");
							break;
						case "4회":
							addTxtIprice.setText("80000");
							break;
						case "5회":
							addTxtIprice.setText("100000");
							break;
						case "6회":
							addTxtIprice.setText("120000");
							break;
						}
					}
				}
			});
			// 작품비 세팅
			addCmbOpus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "작품미선택":
						addTxtOprice.setText("0");
						break;
					case "르코르세르":
						addTxtOprice.setText("48000");
						break;
					case "백조의호수":
						addTxtOprice.setText("49000");
						break;
					case "파랑새":
						addTxtOprice.setText("50000");
						break;
					case "라실피드":
						addTxtOprice.setText("51000");
						break;
					case "라바야데르":
						addTxtOprice.setText("52000");
						break;
					case "파라오의딸":
						addTxtOprice.setText("53000");
						break;
					case "레이몬다":
						addTxtOprice.setText("54000");
						break;
					case "지젤":
						addTxtOprice.setText("55000");
						break;
					case "호두까기인형":
						addTxtOprice.setText("56000");
						break;
					case "블랙스완":
						addTxtOprice.setText("57000");
						break;
					case "코펠리아":
						addTxtOprice.setText("58000");
						break;
					case "파키타":
						addTxtOprice.setText("59000");
						break;
					case "할리퀸아드":
						addTxtOprice.setText("60000");
						break;
					case "실비아":
						addTxtOprice.setText("61000");
						break;
					case "카르멘":
						addTxtOprice.setText("62000");
						break;
					case "고집쟁이딸":
						addTxtOprice.setText("63000");
						break;
					case "세헤라자데":
						addTxtOprice.setText("64000");
						break;
					case "인형요정":
						addTxtOprice.setText("65000");
						break;
					case "돈키호테":
						addTxtOprice.setText("66000");
						break;
					case "탈리스만":
						addTxtOprice.setText("67000");
						break;
					case "카멜리아":
						addTxtOprice.setText("68000");
						break;
					case "키트리":
						addTxtOprice.setText("69000");
						break;
					case "에스메랄다":
						addTxtOprice.setText("70000");
						break;
					}
				}
			});
			// 총수업비 세팅 => addBtnTotal 이벤트
			addBtnTotal.setOnAction(e -> {
				addTxtTotal.setText(String.valueOf(Integer.parseInt(addTxtPprice.getText())
						+ Integer.parseInt(addTxtIprice.getText()) + Integer.parseInt(addTxtOprice.getText())));
			});

			// ****************************addBtnAdd 이벤트*****************************
			addBtnAdd.setOnAction(e -> {
				Tuition tuition = new Tuition(addTxtStudentId.getText(), addTxtName.getText(), addTxtParty.getText(),
						addCmbIndividual.getSelectionModel().getSelectedItem(),
						addCmbOpus.getSelectionModel().getSelectedItem(), Integer.parseInt(addTxtPprice.getText()),
						Integer.parseInt(addTxtIprice.getText()), Integer.parseInt(addTxtOprice.getText()),
						Integer.parseInt(addTxtTotal.getText()), addPayDate.getValue().toString());

				int count = TuitionDAO.insertTuitionIntoBalletDB(tuition);
				if (count != 0) {
					tuiListData.add(tuition);
					StudentController.callAlert("추가:추가되었습니다.");
				}
				// **************************추가창 초기화******************************
//				addTxtStudentId.clear();
//				addTxtName.clear();
//				addTxtParty.clear();
//				addCmbIndividual.getSelectionModel().clearSelection();
//				addCmbOpus.getSelectionModel().clearSelection();
//				addTxtPprice.clear();
//				addTxtIprice.clear();
//				addTxtOprice.clear();
//				addTxtTotal.clear();
//				addPayDate.setValue(null);
			});
			// ****************************addBtnClose 이벤트***************************
			addBtnClose.setOnAction(e -> {
				addStage.close();
			});
			// ***************************************************************************
			Scene scene = new Scene(root);
			addStage.setResizable(false);
			addStage.setScene(scene);
			addStage.show();
		} catch (Exception e) {
			StudentController.callAlert("학비추가창 오류 :학비추가창 오류가 발생했습니다.");
		}

	}

	// 0. 학비수정 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnEditAction() {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		selectedTuitionIndex = tuiTableView.getSelectionModel().getSelectedIndex();
		try {
			Stage editStage = new Stage(StageStyle.UTILITY);
			// addStage.initModality(Modality.WINDOW_MODAL);
			editStage.initOwner(tuitionStage);
			editStage.setTitle("학비수정창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionedit.fxml"));
			Parent root = loader.load();
			// ******************************ID등록*********************************
			TextField editTxtStudentId = (TextField) root.lookup("#editTxtStudentId");
			TextField editTxtName = (TextField) root.lookup("#editTxtName");
			TextField editTxtParty = (TextField) root.lookup("#editTxtParty");
			ComboBox<String> editCmbIndividual = (ComboBox) root.lookup("#editCmbIndividual");
			editCmbIndividual.setItems(tuiIndividualList);
			ComboBox<String> editCmbOpus = (ComboBox) root.lookup("#editCmbOpus");
			editCmbOpus.setItems(tuiOpusList);
			TextField editTxtPprice = (TextField) root.lookup("#editTxtPprice");
			TextField editTxtIprice = (TextField) root.lookup("#editTxtIprice");
			TextField editTxtOprice = (TextField) root.lookup("#editTxtOprice");
			TextField editTxtTotal = (TextField) root.lookup("#editTxtTotal");
			Button editBtnTotal = (Button) root.lookup("#editBtnTotal");
			DatePicker editPayDate = (DatePicker) root.lookup("#editPayDate");
			Button editBtnEdit = (Button) root.lookup("#editBtnEdit");
			Button editBtnClose = (Button) root.lookup("#editBtnClose");
			editTxtStudentId.setDisable(true);
			editTxtName.setDisable(true);
			editTxtParty.setDisable(true);
//			editTxtStudentId.setEditable(false);
//			editTxtName.setEditable(false);
//			editTxtParty.setEditable(false);
			editTxtPprice.setEditable(false);
			editTxtIprice.setEditable(false);
			editTxtOprice.setEditable(false);
			editTxtTotal.setEditable(false);
			// ****************************수정화면 초기화*********************************
			editTxtStudentId.setText(selectedTuition.getStudentId());
			editTxtName.setText(selectedTuition.getName());
			editTxtParty.setText(selectedTuition.getParty());
			editCmbIndividual.getSelectionModel().select(selectedTuition.getIndividual());
			editCmbOpus.getSelectionModel().select(selectedTuition.getOpus());
			editTxtPprice.setText(String.valueOf(selectedTuition.getPprice()));
			editTxtIprice.setText(String.valueOf(selectedTuition.getIprice()));
			editTxtOprice.setText(String.valueOf(selectedTuition.getOprice()));
			editTxtTotal.setText(String.valueOf(selectedTuition.getTotal()));
			editPayDate.setValue(LocalDate.parse(selectedTuition.getPayDate()));
			// *****************************학생수정창 학비세팅**********************************
			// 그룹레슨비 세팅
			editTxtParty.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "고등전공":
						editTxtPprice.setText("160000");
						break;
					case "중등전공":
						editTxtPprice.setText("130000");
						break;
					case "초등전공":
						editTxtPprice.setText("100000");
						break;
					}
				}
			});
			// 개인레슨비 세팅
			editCmbIndividual.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (editTxtParty.getText().equals("고등전공")) {
						switch (newValue) {
						case "0회":
							editTxtIprice.setText("0");
							break;
						case "1회":
							editTxtIprice.setText("40000");
							break;
						case "2회":
							editTxtIprice.setText("80000");
							break;
						case "3회":
							editTxtIprice.setText("120000");
							break;
						case "4회":
							editTxtIprice.setText("160000");
							break;
						case "5회":
							editTxtIprice.setText("200000");
							break;
						case "6회":
							editTxtIprice.setText("240000");
							break;
						}
					}
					if (editTxtParty.getText().equals("중등전공")) {
						switch (newValue) {
						case "0회":
							editTxtIprice.setText("0");
							break;
						case "1회":
							editTxtIprice.setText("30000");
							break;
						case "2회":
							editTxtIprice.setText("60000");
							break;
						case "3회":
							editTxtIprice.setText("90000");
							break;
						case "4회":
							editTxtIprice.setText("120000");
							break;
						case "5회":
							editTxtIprice.setText("150000");
							break;
						case "6회":
							editTxtIprice.setText("180000");
							break;
						}
					}
					if (editTxtParty.getText().equals("초등전공")) {
						switch (newValue) {
						case "0회":
							editTxtIprice.setText("0");
							break;
						case "1회":
							editTxtIprice.setText("20000");
							break;
						case "2회":
							editTxtIprice.setText("40000");
							break;
						case "3회":
							editTxtIprice.setText("60000");
							break;
						case "4회":
							editTxtIprice.setText("80000");
							break;
						case "5회":
							editTxtIprice.setText("100000");
							break;
						case "6회":
							editTxtIprice.setText("120000");
							break;
						}
					}
				}
			});
			// 작품비 세팅
			editCmbOpus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					switch (newValue) {
					case "작품미선택":
						editTxtOprice.setText("0");
						break;
					case "르코르세르":
						editTxtOprice.setText("48000");
						break;
					case "백조의호수":
						editTxtOprice.setText("49000");
						break;
					case "파랑새":
						editTxtOprice.setText("50000");
						break;
					case "라실피드":
						editTxtOprice.setText("51000");
						break;
					case "라바야데르":
						editTxtOprice.setText("52000");
						break;
					case "파라오의딸":
						editTxtOprice.setText("53000");
						break;
					case "레이몬다":
						editTxtOprice.setText("54000");
						break;
					case "지젤":
						editTxtOprice.setText("55000");
						break;
					case "호두까기인형":
						editTxtOprice.setText("56000");
						break;
					case "블랙스완":
						editTxtOprice.setText("57000");
						break;
					case "코펠리아":
						editTxtOprice.setText("58000");
						break;
					case "파키타":
						editTxtOprice.setText("59000");
						break;
					case "할리퀸아드":
						editTxtOprice.setText("60000");
						break;
					case "실비아":
						editTxtOprice.setText("61000");
						break;
					case "카르멘":
						editTxtOprice.setText("62000");
						break;
					case "고집쟁이딸":
						editTxtOprice.setText("63000");
						break;
					case "세헤라자데":
						editTxtOprice.setText("64000");
						break;
					case "인형요정":
						editTxtOprice.setText("65000");
						break;
					case "돈키호테":
						editTxtOprice.setText("66000");
						break;
					case "탈리스만":
						editTxtOprice.setText("67000");
						break;
					case "카멜리아":
						editTxtOprice.setText("68000");
						break;
					case "키트리":
						editTxtOprice.setText("69000");
						break;
					case "에스메랄다":
						editTxtOprice.setText("70000");
						break;
					}
				}
			});
			// 총수업비 세팅 => editBtnTotal 이벤트
			editBtnTotal.setOnAction(e -> {
				editTxtTotal.setText(String.valueOf(Integer.parseInt(editTxtPprice.getText())
						+ Integer.parseInt(editTxtIprice.getText()) + Integer.parseInt(editTxtOprice.getText())));
			});

			// ****************************editBtnEdit 이벤트*****************************
			editBtnEdit.setOnAction(e -> {
				Tuition tuition = new Tuition(editTxtStudentId.getText(), editTxtName.getText(), editTxtParty.getText(),
						editCmbIndividual.getSelectionModel().getSelectedItem(),
						editCmbOpus.getSelectionModel().getSelectedItem(), Integer.parseInt(editTxtPprice.getText()),
						Integer.parseInt(editTxtIprice.getText()), Integer.parseInt(editTxtOprice.getText()),
						Integer.parseInt(editTxtTotal.getText()), editPayDate.getValue().toString());

				int count = TuitionDAO.updateTuitionSet(tuition, selectedTuition);
				if (count != 0) {
					tuiListData.set(selectedTuitionIndex, tuition);
					StudentController.callAlert("수정:수정되었습니다.");
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
			StudentController.callAlert("학비수정 오류 :선택후 수정해주세요.");
		}

	}

	// 0. 학비삭제 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnDeleteAction() {
		try {
			selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
			int count = TuitionDAO.deleteTuitionFromBalletDB(selectedTuition.getStudentId(),
					selectedTuition.getPayDate());
			if (count != 0) {
				tuiListData.remove(selectedTuition);
				tuiArrayList.remove(selectedTuition);
				StudentController.callAlert("삭제:삭제되었습니다.");
			} else {
				return;
			}
		} catch (Exception e) {
			StudentController.callAlert("학비삭제 오류:선택 후 삭제해주세요.");
		}
	}

	// 0. 월별총수업료 비교 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnLineSumAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
			choiceStage.setTitle("월 선택창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			choiceStage.setResizable(false);
			choiceStage.setScene(scene);
			choiceStage.show();
			// ******************************ID등록*********************************
			DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
			DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
			Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
			// **************************choiceBtnChart 이벤트*********************
			choiceBtnChart.setOnAction(e -> {
				if (tuiSum != null) {
					tuiSum.clear();
				}
				try {
					Stage lineStage = new Stage();
					lineStage.initOwner(choiceStage);
					lineStage.setTitle("월별 총수업료 비교");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionLineSum.fxml"));
					Parent root1 = loader1.load();
					// ID등록
					LineChart tuiLineSum = (LineChart) root1.lookup("#tuiLineSum");
					Button tuiLineSumClose = (Button) root1.lookup("#tuiLineSumClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("총수업료");

					tuiSum = TuitionDAO.getSelectedSum(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiSum.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer sum = tuiSum.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, sum));
					}

					series1.setData(obList1);
					tuiLineSum.setAnimated(false);
					tuiLineSum.getData().add(series1);
					Scene scene1 = new Scene(root1);
					lineStage.setScene(scene1);
					lineStage.show();

					tuiLineSumClose.setOnAction(e1 -> {
						lineStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("알림:선택하신 달에 총수업료가 등록되지 않았습니다.");

				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("알림:선택하신 달에총수업료가 등록되지 않았습니다.");

		}
	}

	// 0. 월별총작품비 비교 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnScatterOpriceAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
			choiceStage.setTitle("월 선택창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			choiceStage.setResizable(false);
			choiceStage.setScene(scene);
			choiceStage.show();
			// ******************************ID등록*********************************
			DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
			DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
			Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
			// **************************choiceBtnChart 이벤트*********************
			choiceBtnChart.setOnAction(e -> {
				if (tuiOprice != null) {
					tuiOprice.clear();
				}
				try {
					Stage scatterStage = new Stage();
					scatterStage.initOwner(choiceStage);
					scatterStage.setTitle("월별 총작품비 비교");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionScatterOprice.fxml"));
					Parent root1 = loader1.load();
					// ID등록
					ScatterChart tuiScatterOprice = (ScatterChart) root1.lookup("#tuiScatterOprice");
					Button tuiScatterOpriceClose = (Button) root1.lookup("#tuiScatterOpriceClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("총작품비");

					tuiOprice = TuitionDAO.getSelectedOprice(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiOprice.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer oprice = tuiOprice.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, oprice));
					}

					series1.setData(obList1);
					tuiScatterOprice.setAnimated(false);
					tuiScatterOprice.getData().add(series1);
					Scene scene1 = new Scene(root1);
					scatterStage.setScene(scene1);
					scatterStage.show();

					tuiScatterOpriceClose.setOnAction(e1 -> {
						scatterStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("알림:선택하신 달에 총작품비가 등록되지 않았습니다.");
				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("알림:선택하신 달에 총작품비가 등록되지 않았습니다.");
		}
	}

	// 0. 월별총개인레슨비 비교 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnAreaIpriceAction() {
		try {
			Stage choiceStage = new Stage();
			choiceStage.initOwner(tuitionStage);
			choiceStage.setTitle("월 선택창");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			choiceStage.setResizable(false);
			choiceStage.setScene(scene);
			choiceStage.show();
			// ******************************ID등록*********************************
			DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
			DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
			Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
			// **************************choiceBtnChart 이벤트*********************
			choiceBtnChart.setOnAction(e -> {
				if (tuiIprice != null) {
					tuiIprice.clear();
				}
				try {
					Stage areaStage = new Stage();
					areaStage.initOwner(choiceStage);
					areaStage.setTitle("월별 총개인레슨비 비교");
					FXMLLoader loader1 = new FXMLLoader(getClass().getResource("../View/tuitionAreaIprice.fxml"));
					Parent root1 = loader1.load();
					// ID등록
					AreaChart tuiAreaIprice = (AreaChart) root1.lookup("#tuiAreaIprice");
					Button tuiAreaIpriceClose = (Button) root1.lookup("#tuiAreaIpriceClose");
					ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

					XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
					series1.setName("총개인레슨비");

					tuiIprice = TuitionDAO.getSelectedIprice(choiceStart.getValue().toString(),
							choiceEnd.getValue().toString());

					Set<String> set = tuiIprice.keySet();
					Iterator<String> iterator = set.iterator();
					while (iterator.hasNext()) {
						String month = iterator.next();
						Integer iprice = tuiIprice.get(month);
						obList1.add(new XYChart.Data<String, Integer>(month, iprice));
					}

					series1.setData(obList1);
					tuiAreaIprice.setAnimated(false);
					tuiAreaIprice.getData().add(series1);
					Scene scene1 = new Scene(root1);
					areaStage.setScene(scene1);
					areaStage.show();

					tuiAreaIpriceClose.setOnAction(e1 -> {
						areaStage.close();
					});

				} catch (Exception e1) {
					StudentController.callAlert("알림:선택하신 달에 총개인레슨비가 등록되지 않았습니다.");
				}
			});
		} catch (Exception e) {
			// StudentController.callAlert("알림:선택하신 달에 총작품비가 등록되지 않았습니다.");
		}
	}

	// 0. 월별선택학생총수업료 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnSelectedTotalAction() {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		if (selectedTuition != null) {
			try {
				Stage choiceStage = new Stage();
				choiceStage.initOwner(tuitionStage);
				choiceStage.setTitle("월 선택창");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/monthChoice.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				choiceStage.setResizable(false);
				choiceStage.setScene(scene);
				choiceStage.show();
				// ******************************ID등록*********************************
				DatePicker choiceStart = (DatePicker) root.lookup("#choiceStart");
				DatePicker choiceEnd = (DatePicker) root.lookup("#choiceEnd");
				Button choiceBtnChart = (Button) root.lookup("#choiceBtnChart");
				// **************************choiceBtnChart 이벤트*********************
				choiceBtnChart.setOnAction(e -> {
					if (tuiTotal != null) {
						tuiTotal.clear();
					}
					try {
						Stage lineStage = new Stage();
						lineStage.initOwner(choiceStage);
						lineStage.setTitle(selectedTuition.getName() + "의 월별 총수업료");
						FXMLLoader loader1 = new FXMLLoader(
								getClass().getResource("../View/tuitionLineSelectedTotal.fxml"));
						Parent root1 = loader1.load();
						// ID등록
						LineChart tuiLineSelectedTotal = (LineChart) root1.lookup("#tuiLineSelectedTotal");
						Button tuiLineSelectedTotalClose = (Button) root1.lookup("#tuiLineSelectedTotalClose");
						ObservableList<XYChart.Data<String, Integer>> obList1 = FXCollections.observableArrayList();

						XYChart.Series<String, Integer> series1 = new XYChart.Series<String, Integer>();
						series1.setName(selectedTuition.getName() + "의 총수업료");

						tuiTotal = TuitionDAO.getSelectedTotal(choiceStart.getValue().toString(),
								choiceEnd.getValue().toString(), selectedTuition.getStudentId());

						Set<String> set = tuiTotal.keySet();
						Iterator<String> iterator = set.iterator();
						while (iterator.hasNext()) {
							String month = iterator.next();
							Integer total = tuiTotal.get(month);
							obList1.add(new XYChart.Data<String, Integer>(month, total));
						}

						series1.setData(obList1);
						tuiLineSelectedTotal.setAnimated(false);
						tuiLineSelectedTotal.getData().add(series1);
						Scene scene1 = new Scene(root1);
						lineStage.setScene(scene1);
						lineStage.show();

						tuiLineSelectedTotalClose.setOnAction(e1 -> {
							lineStage.close();
						});
					} catch (Exception e1) {
						StudentController.callAlert("알림:선택하신 달에 " + selectedTuition.getName() + "의 총수업료가 등록되지 않았습니다.");
					}
				});

			} catch (Exception e) {
				StudentController.callAlert("알림:학생선택후 눌러주시기 바랍니다.");
			}
		} else {
			StudentController.callAlert("알림:학생선택후 눌러주시기 바랍니다.");
		}

	}

	// 0. 당월학생별총수업비 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnBarTotalAction() {
		try {
			Stage barStage = new Stage();
			barStage.initOwner(tuitionStage);
			barStage.setTitle(tuiCmbPayDate.getSelectionModel().getSelectedItem()+" 학생별 총수업료 비교");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionBarTotal.fxml"));
			Parent root = loader.load();
			BarChart tuiBarTotal = (BarChart) root.lookup("#tuiBarTotal");
			Button tuiBarTotalClose = (Button) root.lookup("#tuiBarTotalClose");

			XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
			series1.setName("총수업료");
			ObservableList obList = FXCollections.observableArrayList();
			for (Tuition tuition : tuiArrayList) {
				obList.add(new XYChart.Data<String, Integer>(tuition.getName(), tuition.getTotal()));
			}
			series1.setData(obList);
			tuiBarTotal.setAnimated(false);
			tuiBarTotal.getData().add(series1);

			Scene scene = new Scene(root);
			barStage.setScene(scene);
			barStage.show();

			tuiBarTotalClose.setOnAction(e -> {
				barStage.close();
			});

		} catch (Exception e) {
			StudentController.callAlert("당월 학생별 총수업료 비교오류:바차트창 오류");
		}

	}

	// 0. 당월총레슨비비교 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnPiePIOsumAction() {
		try {
			Stage pieStage = new Stage();
			pieStage.initOwner(tuitionStage);
			pieStage.setTitle(tuiCmbPayDate.getSelectionModel().getSelectedItem() + "의 총레슨비 비교");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionPiePIOsum.fxml"));
			Parent root = loader.load();
			PieChart tuiPiePIOsum = (PieChart) root.lookup("#tuiPiePIOsum");
			Button tuiPiePIOsumClose = (Button) root.lookup("#tuiPiePIOsumClose");

			ObservableList obList = FXCollections.observableArrayList();
			int PpriceSum = 0;
			int IpriceSum = 0;
			int OpriceSum = 0;
			for (Tuition tuition : tuiArrayList) {
				PpriceSum = PpriceSum + tuition.getPprice();
				IpriceSum = IpriceSum + tuition.getIprice();
				OpriceSum = OpriceSum + tuition.getOprice();
			}
			LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
			linkedHashMap.put("총그룹레슨비(" + PpriceSum + "원)", PpriceSum);
			linkedHashMap.put("총개인레슨비(" + IpriceSum + "원)", IpriceSum);
			linkedHashMap.put("총작품비(" + OpriceSum + "원)", OpriceSum);
			Set<String> set = linkedHashMap.keySet();
			for (String priceName : set) {
				int price = linkedHashMap.get(priceName);
				obList.add(new PieChart.Data(priceName, price));
			}
			tuiPiePIOsum.setData(obList);

			Scene scene = new Scene(root);
			pieStage.setScene(scene);
			pieStage.show();

			tuiPiePIOsumClose.setOnAction(e1 -> {
				pieStage.close();
			});
		} catch (Exception e1) {
			StudentController.callAlert("알림:파이차트창 오류");
		}
	}

	// 0. 테이블뷰를 두번클릭 했을때 처리하는 함수
	private void handleTuiTableViewDoubleClickedAction(MouseEvent e) {
		selectedTuition = tuiTableView.getSelectionModel().getSelectedItem();
		if (e.getClickCount() == 2) {
			try {
				Stage pieStage = new Stage();
				pieStage.initOwner(tuitionStage);
				pieStage.setTitle(selectedTuition.getName() + "의 레슨비 비교");
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/tuitionPiePIOprice.fxml"));
				Parent root = loader.load();
				PieChart tuiPiePIOprice = (PieChart) root.lookup("#tuiPiePIOprice");
				Button tuiPiePIOpriceClose = (Button) root.lookup("#tuiPiePIOpriceClose");

				ObservableList obList = FXCollections.observableArrayList();
				LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
				linkedHashMap.put("그룹레슨비(" + selectedTuition.getPprice() + "원)", selectedTuition.getPprice());
				linkedHashMap.put("개인레슨비(" + selectedTuition.getIprice() + "원)", selectedTuition.getIprice());
				linkedHashMap.put("작품비(" + selectedTuition.getOprice() + "원)", selectedTuition.getOprice());
				Set<String> set = linkedHashMap.keySet();
				for (String priceName : set) {
					int price = linkedHashMap.get(priceName);
					obList.add(new PieChart.Data(priceName, price));
				}
				tuiPiePIOprice.setData(obList);

				Scene scene = new Scene(root);
				pieStage.setScene(scene);
				pieStage.show();

				tuiPiePIOpriceClose.setOnAction(e1 -> {
					pieStage.close();
				});
			} catch (Exception e1) {
				StudentController.callAlert("알림:파이차트창 오류");
			}
		}
	}

// 0. 검색을 눌렀을때 처리하는 함수
	private void handleTuiBtnSearchAction() {
		for (Tuition tuition : tuiListData) {
			if (tuiTxtSearch.getText().trim().equals(tuition.getName())) {
				tuiTableView.getSelectionModel().select(tuition);
			}
		}
	}

	// 0. 뒤로가기 버튼을 눌렀을때 처리하는 함수
	private void handleTuiBtnBackAction() {
		try {
			tuiArrayList.clear();
			tuiListData.clear();
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
			tuitionStage.close();
			choiceStage.show();

		} catch (Exception e) {
		}
	}

	// 0. 당월 총수업료 텍스트필드에 나타내기
	private void setMonthlySum() {
		monthlySum = 0;
		for (Tuition tuition : tuiArrayList) {
			monthlySum += tuition.getTotal();
		}
		tuiTxtSum.setText(String.valueOf(monthlySum));
	}

}
