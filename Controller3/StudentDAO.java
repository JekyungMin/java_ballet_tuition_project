package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Model.Student;

public class StudentDAO {
	public static ArrayList<Student> stuArrayList = new ArrayList<>();

	// 1. 학생정보 등록하는 함수
	public static int insertStudentIntoBalletDB(Student student) {
		StringBuffer insertStudent = new StringBuffer();
		insertStudent.append("insert into studenttbl ");
		insertStudent.append("(studentId,name,age,phone,address,party,image) ");
		insertStudent.append("values ");
		insertStudent.append("(?,?,?,?,?,?,?) ");

		// 1.1 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 1.2 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(insertStudent.toString());
			// 1.3 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, student.getStudentId());
			ps.setString(2, student.getName());
			ps.setInt(3, student.getAge());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getAddress());
			ps.setString(6, student.getParty());
			ps.setString(7, student.getImage());

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

	// 2. 데이터베이스에서 전체내용을 모두 가져오는 함수
	public static ArrayList<Student> getStudentFromBalletDB() {
		// 2.1 데이터베이스의 스튜던트테이블 에 있는 데이터를 모두 가져오는 쿼리문
		String selectStudent = "select studentId, name, age, phone, address, party, image from studenttbl order by age desc ";
		// 2.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 2.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		// 2.4 쿼리문을 실행하고 나서 가져올 데이터를 담고있는 컬렉션프레임워크
		// ResultSet 을 만든다.
		ResultSet rs = null;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(selectStudent);
			// 2.5실제데이터를 연결한 쿼리문을 실행한다.
			// executeQuery() => 데이터베이스에서 결과를 가져올때 사용하는 쿼리문
			rs = ps.executeQuery();
			if (rs == null) {
				StudentController.callAlert("ResultSet 가져오기 실패:select 쿼리문 실패");
				return null;
			}
			while (rs.next()) {
				Student student = new Student(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
				stuArrayList.add(student);
			}
		} catch (Exception e) {
			StudentController.callAlert("getStudentFromBalletDB실패:확인바람");
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

		return stuArrayList;
	}
	
	// 3. 테이블뷰에서 선택한 레코드를 데이터베이스에서 삭제하는 함수
	public static int deleteStudentFromDB(String studentId) {
		// 3.1 데이터베이스의 스튜던트테이블에 있는 데이터를 삭제하는 쿼리문
		String deleteStudent = "delete from studenttbl where studentId = ? ";
		// 3.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 3.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(deleteStudent);
			// 3.4 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, studentId);
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
	
	// 4. 테이블뷰 데이터 수정시 데이터베이스 데이터를 수정하는 함수
	public static int updateStudentSet(Student student, Student selectedStudent) {
		// 4.1 데이터베이스의 스튜던트테이블을 수정하는 쿼리문
		StringBuffer updateStudent = new StringBuffer();
		updateStudent.append("update studenttbl set ");
		updateStudent.append(	"studentId=?,name=?, age=?,phone=?,address=?,party=?,image=? where studentId=? ");
		// 4.2 데이터베이스 Connection을 가져온다.
		Connection connection = null;
		// 4.3 쿼리문을 실행할 Statement를 만든다.
		PreparedStatement ps = null;
		int count = 0;
		try {
			connection = DBUtility.getConnection();
			ps = connection.prepareStatement(updateStudent.toString());
			// 4.4 쿼리문에 실제데이터를 연결한다.
			ps.setString(1, student.getStudentId());
			ps.setString(2, student.getName());
			ps.setInt(3, student.getAge());
			ps.setString(4, student.getPhone());
			ps.setString(5, student.getAddress());
			ps.setString(6, student.getParty());
			ps.setString(7, student.getImage());
			ps.setString(8, selectedStudent.getStudentId());

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
