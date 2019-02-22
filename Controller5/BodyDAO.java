package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import Model.Body;

public class BodyDAO {
	public static ArrayList<Body> bodyArrayList = new ArrayList<>();
	public static ArrayList<String> bodyStudentId = new ArrayList<>();
	public static ArrayList<String> bodyNameAndAge = new ArrayList<>();
	public static TreeMap<String, Double> bodySelectedWeight = new TreeMap<>();
	public static LinkedHashMap<String, Double> bodySelectedMuscle = new LinkedHashMap<>();
	public static TreeMap<String, Double> bodySelectedFat = new TreeMap<>();

	public static String selectWeight;
	public static String selectMuscle;
	public static String selectFat;

	// 1. 신체정보를 등록하는 함수
	public static int insertBodyIntoBalletDB(Body body) {
		StringBuffer insertBody = new StringBuffer();
		insertBody.append("insert into bodytbl ");
		insertBody.append("(studentId,height,weight,muscle,fat,measureDate) ");
		insertBody.append("values ");
		insertBody.append("(?,?,?,?,?,?) ");

		Connection connection = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertBody.toString());
			ps.setString(1, body.getStudentId());
			ps.setDouble(2, body.getHeight());
			ps.setDouble(3, body.getWeight());
			ps.setDouble(4, body.getMuscle());
			ps.setDouble(5, body.getFat());
			ps.setString(6, body.getMeasureDate());

			// 1.4 실제데이터를 연결한 쿼리문을 실행한다.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("삽입실패:삽입쿼리문 실패");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("삽입실패:데이터베이스 삽입쿼리문 실패");
		} finally {
			// 1.5 자원객체를 닫는다.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("닫기실패:자원닫기실패");
			}
		}
		return count;
	}
	// 2. 데이터베이스에서 2019년 신체관리를 가져오는 함수
	public static ArrayList<Body> get2019BodyFromBalletDb(String studentId) {
		// 2.1 데이터베이스의 스튜던트 테이블과 바디테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2019-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb실패:확인바람");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return bodyArrayList;
	}

	// 2. 데이터베이스에서 2018년 신체관리를 가져오는 함수
	public static ArrayList<Body> get2018BodyFromBalletDb(String studentId) {
		// 2.1 데이터베이스의 스튜던트 테이블과 바디테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2018-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb실패:확인바람");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return bodyArrayList;
	}

	// 2. 데이터베이스에서 2017년 신체관리를 가져오는 함수
	public static ArrayList<Body> get2017BodyFromBalletDb(String studentId) {
		// 2.1 데이터베이스의 스튜던트 테이블과 바디테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectBody = new StringBuffer();
		selectBody.append("select studenttbl.studentID, studenttbl.name, studenttbl.age, bodytbl.height, ");
		selectBody.append("bodytbl.weight, bodytbl.muscle, bodytbl.fat, bodytbl.measureDate from studenttbl ");
		selectBody.append("inner join bodytbl on studenttbl.studentId = bodytbl.studentId ");
		selectBody.append("where bodytbl.measureDate like '2017-%%-%%' and studenttbl.studentId='" + studentId + "' ");
		selectBody.append("order by bodytbl.measureDate desc ");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectBody.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Body body = new Body(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4), rs.getDouble(5),
						rs.getDouble(6), rs.getDouble(7), rs.getString(8));
				bodyArrayList.add(body);
			}
		} catch (Exception e) {
			StudentController.callAlert("get2018BodyFromBalletDb실패:확인바람");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return bodyArrayList;
	}

	// 0. 신체관리창에서 이름을 검색 했을때 발레데이터베이스의 스튜던트테이블에서 이름에 맞는 학생아이디를 가져오는 함수
	public static ArrayList<String> getStudentIdWhenNameSearched(String nameSearch) {
		// String getStudentId = "select studenttbl.studentId from studenttbl inner join
		// bodytbl on studenttbl.studentId = bodytbl.studentId where studenttbl.name =
		// '"+nameSearch+"'";
		String getStudentId = "select studentId from studenttbl where name like '" + nameSearch + "' ";
		// 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getStudentId);
			// 실제데이터를 연결한 쿼리문을 실행한다.
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				bodyStudentId.add(rs.getString(1));
			}
		} catch (Exception e) {
			StudentController.callAlert("getStudentIdWhenNameSearched실패:확인바람");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return bodyStudentId;
	}

	// 0. 신체추가창에서 아이디를 조회했을때 발레데이터베이스의 스튜던트테이블에서 아이디 조건에 맞는 학생이름과 나이를 가져오는 함수
	public static ArrayList<String> getNameAndAgeWhenStudentIdSearched(String studentId) {
		String getNameAndAge = "select name, age from studenttbl where studentId= '" + studentId + "' ";
		// 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getNameAndAge);
			// 실제데이터를 연결한 쿼리문을 실행한다.
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				bodyNameAndAge.add(rs.getString(1));
				bodyNameAndAge.add(String.valueOf(rs.getInt(2)));
			}
		} catch (Exception e) {
			StudentController.callAlert("getNameAndAgeWhenStudentIdSearched실패:확인바람");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return bodyNameAndAge;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 학생의 체중을 가져오는 함수
	public static TreeMap<String, Double> getSelectedWeight(String choiceStart, String choiceEnd, String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectWeight = "select weight, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '"
								+ i + "-0" + j + "-%%' and studentId ='" + studentId + "'";
					} else {
						selectWeight = "select weight, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectWeight);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedWeight.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 체중정보가 등록되지 않았습니다.");
		}
		return bodySelectedWeight;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 학생의 근육량을 가져오는 함수
	public static LinkedHashMap<String, Double> getSelectedMuscle(String choiceStart, String choiceEnd,
			String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectMuscle = "select muscle, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-0" + j
								+ "-%%' and studentId ='" + studentId + "'";
					} else {
						selectMuscle = "select muscle, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectMuscle);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedMuscle.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 근육량 정보가 등록되지 않았습니다.");
		}
		return bodySelectedMuscle;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 학생의 체지방률을 가져오는 함수
	public static TreeMap<String, Double> getSelectedFat(String choiceStart, String choiceEnd, String studentId) {
		int startMonth = 0;
		int endMonth = 0;
		int startYear = 0;
		int endYear = 0;
		String[] splitedChoiceStart = choiceStart.split("-");
		String[] splitedChoiceEnd = choiceEnd.split("-");

		startYear = Integer.parseInt(splitedChoiceStart[0]);
		endYear = Integer.parseInt(splitedChoiceEnd[0]);

		if ((Integer.parseInt(splitedChoiceStart[1]) != 12) && (Integer.parseInt(splitedChoiceStart[1]) != 11)
				&& (Integer.parseInt(splitedChoiceStart[1]) != 10)) {
			startMonth = Integer.parseInt(splitedChoiceStart[1].substring(1));
		} else {
			startMonth = Integer.parseInt(splitedChoiceStart[1]);
		}

		if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
				&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
			endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
		} else {
			endMonth = Integer.parseInt(splitedChoiceEnd[1]);
		}

		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			int j = 0;
			int i = 0;
			for (i = startYear; i < endYear + 1; i++) {
				if (i > startYear) {
					startMonth = 1;
				}
				if (i < endYear) {
					endMonth = 12;
				} else {
					if ((Integer.parseInt(splitedChoiceEnd[1]) != 12) && (Integer.parseInt(splitedChoiceEnd[1]) != 11)
							&& (Integer.parseInt(splitedChoiceEnd[1]) != 10)) {
						endMonth = Integer.parseInt(splitedChoiceEnd[1].substring(1));
					} else {
						endMonth = Integer.parseInt(splitedChoiceEnd[1]);
					}
				}
				for (j = startMonth; j < endMonth + 1; j++) {
					if (j >= 1 && j <= 9) {
						selectFat = "select fat, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-0" + j
								+ "-%%' and studentId ='" + studentId + "'";
					} else {
						selectFat = "select fat, date_format(measureDate, '%Y-%m') from bodytbl where measureDate like '" + i + "-" + j
								+ "-%%' and studentId ='" + studentId + "'";
					}
					ps = connection.prepareStatement(selectFat);
					rs = ps.executeQuery();
					while (rs.next()) {
						bodySelectedFat.put(rs.getString(2), rs.getDouble(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 체지방률 정보가 등록되지 않았습니다.");
		}
		return bodySelectedFat;
	}

	// 3. 신체정보를 삭제하는 함수
	public static int deleteBodyFromBalletDB(String studentId, String measureDate) {
		// 3.1 데이터베이스의 바디테이블에 있는 데이터를 삭제하는 쿼리문
		String deleteBody = "delete from bodytbl where studentId=? and measureDate=? ";
		// 3.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 3.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteBody);
			// 3.4 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, studentId);
			ps.setString(2, measureDate);
			// 3.5 실제데이터를 연결한 쿼리문을 실행한다.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("delete실패:delete 쿼리문 실패");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("delete실패:데이터베이스 삭제실패");
		} finally {
			// 3.6 자원객체를 닫는다.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return count;
	}

	// 4. 신체정보를 수정하는 함수
	public static int updateBodySet(Body body, Body selectedBody) {
		// 4.1 데이터베이스의 튜이션테이블을 수정하는 쿼리문
		StringBuffer updateBody = new StringBuffer();
		updateBody.append("update bodytbl set ");
		updateBody.append("height=?,weight=?,muscle=?,fat=?,measureDate=? where studentId=? and measureDate=? ");
		// 4.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 4.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateBody.toString());
			// 4.4 쿼리문에 실제데이터를 연결한다.
			ps.setDouble(1, body.getHeight());
			ps.setDouble(2, body.getWeight());
			ps.setDouble(3, body.getMuscle());
			ps.setDouble(4, body.getFat());
			ps.setString(5, body.getMeasureDate());
			ps.setString(6, selectedBody.getStudentId());
			ps.setString(7, selectedBody.getMeasureDate());

			// 4.5 실제데이터를 연결한 쿼리문을 실행한다.
			count = ps.executeUpdate();
			if (count == 0) {
				StudentController.callAlert("update실패:update쿼리문 실패");
				return count;
			}
		} catch (Exception e) {
			StudentController.callAlert("update실패:데이터베이스 수정실패");
		} finally {
			// 4.6 자원객체를 닫는다.
			try {
				if (ps != null) {
					ps.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				StudentController.callAlert("자원닫기실패:실패");
			}
		}
		return count;
	}

}
