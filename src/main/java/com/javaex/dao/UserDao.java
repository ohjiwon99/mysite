package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url, "web", "web");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}// getConnection()

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}// close()

	/**************************************
	 * 회원가입
	 ************************************/

	public int insertUser(UserVo userVo) {
		int count = -1;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " insert into users ";// 띄어쓰기
			query += " value(null,?,?,?,?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			// - 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;

	}

	/**************************************
	 * 로그인
	 ************************************/

	public /* (자료형쓰기) */UserVo selectUserByIdPw(UserVo userVo) {
		UserVo authuser = null;

		this.getConnection();

		// 3. SQL문 준비 / 바인딩 / 실행

		try {

			// SQL문 준비
			String query = "";
			query += " select no, ";// 띄어쓰기
			query += "        name ";
			query += " from   users ";
			query += " where id= ? ";
			query += " and password=?; ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw()); // 숫자 바꾸기 꼭!!!!!!!!!!!

			// - 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				authuser = new UserVo();

				authuser.setNo(no);
				authuser.setName(name);

			}
		} catch (

		SQLException e)

		{
			System.out.println("error:" + e);
		}

		this.close();
		return authuser;
	}

	/**************************************
	 * 회원정보수정
	 ************************************/

	public int updateUser(UserVo userVo) {
		int count = -1;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행

			// SQL문 준비
			String query = "";
			query += " update users ";// 띄어쓰기
			query += " set    id = ?, ";
			query += "        password =?, ";
			query += "        name = ?, ";
			query += "        gender =? ";
			query += " where  no= ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			pstmt.setInt(5, userVo.getNo());

			// - 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정 되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return count;
		// return modifyList;

	}
	/*
	 * // 수정 public int updateUser(UserVo userVo) { int count = -1;
	 * 
	 * this.getConnection();
	 * 
	 * try {
	 * 
	 * // 3. SQL문 준비 / 바인딩 / 실행 // SQL문 준비 String query = ""; query +=
	 * " update users "; query += " set password = ?, "; query += " name = ?, ";
	 * query += " gender = ? "; query += " where no = ? ";
	 * 
	 * // - 바인딩 pstmt = conn.prepareStatement(query); pstmt.setString(1,
	 * userVo.getPw()); pstmt.setString(2, userVo.getName()); pstmt.setString(3,
	 * userVo.getGender()); pstmt.setInt(4, userVo.getNo());
	 * 
	 * // - 실행 count = pstmt.executeUpdate();
	 * 
	 * // 4. 결과처리 System.out.println(count + "건 수정 되었습니다.");
	 * 
	 * } catch (SQLException e) { System.out.println("error:" + e); }
	 * 
	 * this.close();
	 * 
	 * return count; }
	 */
}
