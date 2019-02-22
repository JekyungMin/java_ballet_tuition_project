package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import Model.Tuition;

public class TuitionDAO {
	public static ArrayList<Tuition> tuiArrayList = new ArrayList<>();
	public static ArrayList<String> tuiNameAndParty = new ArrayList<>();

	public static TreeMap<String, Integer> tuiSum = new TreeMap<String, Integer>();
	public static LinkedHashMap<String, Integer> tuiOprice = new LinkedHashMap<String, Integer>();
	public static TreeMap<String, Integer> tuiIprice = new TreeMap<String, Integer>();
	public static LinkedHashMap<String,Integer> tuiTotal = new LinkedHashMap<>();

	public static String selectSum;
	public static String selectOprice;
	public static String selectIprice;
	public static String selectTotal;
	
	// 1. 학비정보를 등록하는 함수
	public static int insertTuitionIntoBalletDB(Tuition tuition) {
		StringBuffer insertTuition = new StringBuffer();
		insertTuition.append("insert into tuitiontbl ");
		insertTuition.append("(studentId,individual,opus,pprice,iprice,oprice,total,payDate) ");
		insertTuition.append("values ");
		insertTuition.append("(?,?,?,?,?,?,?,?) ");

		// 1.1 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 1.2 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertTuition.toString());
			// 1.3 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, tuition.getStudentId());
			ps.setString(2, tuition.getIndividual());
			ps.setString(3, tuition.getOpus());
			ps.setInt(4, tuition.getPprice());
			ps.setInt(5, tuition.getIprice());
			ps.setInt(6, tuition.getOprice());
			ps.setInt(7, tuition.getTotal());
			ps.setString(8, tuition.getPayDate());

			// 1.4 실제데이터를 연결한 쿼리문을 실행한다.
			// executeUpdate() => 데이터베이스에 저장할때 사용하는 쿼리문
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
	// 2. 데이터베이스에서 2019년 2월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> get2019FebruaryTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2019-02-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2019년 1월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> get2019JanuaryTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2019-01-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}

	// 2. 데이터베이스에서 2018년 12월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getDecemberTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-12-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}

	// 2. 데이터베이스에서 2018년 11월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getNovemberTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-11-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}

	// 2. 데이터베이스에서 2018년 10월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getOctoberTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-10-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}

	// 2. 데이터베이스에서 2018년 9월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getSeptemberTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-09-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 8월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getAugustTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-08-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 7월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getJulyTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-07-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 6월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getJuneTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-06-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 5월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getMayTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-05-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 4월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getAprilTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-04-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 3월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getMarchTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-03-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 2월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getFebruaryTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-02-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}
	
	// 2. 데이터베이스에서 2018년 1월 학비관리를 가져오는 함수
	public static ArrayList<Tuition> getJanuaryTuitionFromBalletDb() {
		// 2.1 데이터베이스의 스튜던트 테이블과 튜이션테이블을 이너조인해서 데이터를 가져오는 쿼리문
		StringBuffer selectTuition = new StringBuffer();
		selectTuition.append("select studenttbl.studentID, studenttbl.name, studenttbl.party, ");
		selectTuition.append("tuitiontbl.individual, tuitiontbl.opus, tuitiontbl.pprice, tuitiontbl.iprice, ");
		selectTuition.append("tuitiontbl.oprice, tuitiontbl.total, tuitiontbl.payDate ");
		selectTuition.append("from studenttbl inner join tuitiontbl on studenttbl.studentID = tuitiontbl.studentID ");
		selectTuition.append("where tuitiontbl.payDate like '2018-01-%%' order by studenttbl.age desc");
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectTuition.toString());
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Tuition tuition = new Tuition(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				tuiArrayList.add(tuition);
			}
		} catch (Exception e) {
			StudentController.callAlert("getTuitionFromBalletDB실패:확인바람");
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
		return tuiArrayList;
	}

	// 학비추가창에서 아이디를 조회했을때 발레데이터베이스의 스튜던트테이블에서 아이디 조건에 맞는 학생이름과 그룹레슨종류를 가져오는 함수
	public static ArrayList<String> getNameAndPartyWhenStudentIdSearched(String studentId) {
		String getNameAndParty = "select name, party from studenttbl where studentId='" + studentId + "'";
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(getNameAndParty);
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				tuiNameAndParty.add(rs.getString(1));
				tuiNameAndParty.add(rs.getString(2));
			}
		} catch (Exception e) {
			StudentController.callAlert("getNameAndPartyWhenStudentIdSearched실패:확인바람");
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

		return tuiNameAndParty;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 달의 총수업료를 가져오는 함수
	public static TreeMap<String, Integer> getSelectedSum(String choiceStart, String choiceEnd) {
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
						selectSum = "select sum(total), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectSum = "select sum(total), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectSum);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiSum.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 총수업료가 등록되지 않았습니다.");
		}
		return tuiSum;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 달의 총작품비를 가져오는 함수
	public static LinkedHashMap<String, Integer> getSelectedOprice(String choiceStart, String choiceEnd) {
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
						selectOprice = "select sum(oprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectOprice = "select sum(oprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectOprice);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiOprice.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 총작품비가 등록되지 않았습니다.");
		}
		return tuiOprice;
	}

	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 달의 총개인레슨비를 가져오는 함수
	public static TreeMap<String, Integer> getSelectedIprice(String choiceStart, String choiceEnd) {
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
						selectIprice = "select sum(iprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%'";
					} else {
						selectIprice = "select sum(iprice), date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' ";
					}
					ps = connection.prepareStatement(selectIprice);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiIprice.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 총개인레슨비가 등록되지 않았습니다.");
		}
		return tuiIprice;
	}
	
	// 시작월과 끝월을 선택하여 그 기간동안의 선택된 학생의 총수업료를 가져오는 함수
	public static LinkedHashMap<String, Integer> getSelectedTotal(String choiceStart, String choiceEnd, String studentId) {
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
						selectTotal = "select total, date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-0" + j + "-%%' and studentId ='" + studentId + "'";
					} else {
						selectTotal = "select total, date_format(payDate, '%Y-%m') from tuitiontbl where payDate like '"
								+ i + "-" + j + "-%%' and studentId ='" + studentId +"'";
					}
					ps = connection.prepareStatement(selectTotal);
					rs = ps.executeQuery();
					while (rs.next()) {
						tuiTotal.put(rs.getString(2), rs.getInt(1));
					}
				}
			}
			if (rs == null) {
				StudentController.callAlert("알림:시작월이 끝월보다 늦습니다.");
				return null;
			}
		} catch (Exception e) {
			StudentController.callAlert("알림:선택하신 달에 총수업료가 등록되지 않았습니다.");
		}
		return tuiTotal;
	}


	// 3. 학비정보를 삭제하는 함수
	public static int deleteTuitionFromBalletDB(String studentId, String payDate) {
		// 3.1 데이터베이스의 튜이션테이블에 있는 데이터를 삭제하는 쿼리문
		String deleteTuition = "delete from tuitiontbl where studentId=? and payDate=? ";
		// 3.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 3.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteTuition);
			// 3.4 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, studentId);
			ps.setString(2, payDate);
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

	// 4. 학비정보를 수정하는 함수
	public static int updateTuitionSet(Tuition tuition, Tuition selectedTuition) {
		// 4.1 데이터베이스의 튜이션테이블을 수정하는 쿼리문
		StringBuffer updateTuition = new StringBuffer();
		updateTuition.append("update tuitiontbl set ");
		updateTuition.append(
				"individual=?,opus=?,pprice=?,iprice=?,oprice=?,total=?,payDate=? where studentId=? and payDate=? ");
		// 4.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 4.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateTuition.toString());
			// 4.4 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, tuition.getIndividual());
			ps.setString(2, tuition.getOpus());
			ps.setInt(3, tuition.getPprice());
			ps.setInt(4, tuition.getIprice());
			ps.setInt(5, tuition.getOprice());
			ps.setInt(6, tuition.getTotal());
			ps.setString(7, tuition.getPayDate());
			ps.setString(8, tuition.getStudentId());
			ps.setString(9, selectedTuition.getPayDate());

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
