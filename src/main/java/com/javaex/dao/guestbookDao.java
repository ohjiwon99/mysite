package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.guestbookVo;

public class guestbookDao {// DB관련

	// 필드-없음
	// 생성자
	// 메소드-gs
	// 메소드-일반

	// 전체가져오기
	public List<guestbookVo> guestSelect() {
		List<guestbookVo> guestList = new ArrayList<guestbookVo>();

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select no ";
			query += "        name, ";
			query += "        password, ";
			query += "        content, ";
			query += "        reg_date ";
			query += " from   guestbook ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery(); // select문 빼고 나머지는 executeUpdate()씀!

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String pw = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				// db에서 가져온 데이터 vo로 묶기
				guestbookVo GuestbookVo = new guestbookVo(no, name, pw, content, reg_date);
				guestList.add(GuestbookVo);

			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
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
		}
		return guestList;

	}

	/************************************
	 * //등록
	 ***********************************/

	public int guestInsert(guestbookVo GuestbookVo) {
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " insert into guestbook  ";
			query += " values(null, ?,?,?,now()) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, GuestbookVo.getName());
			pstmt.setString(2, GuestbookVo.getPassword());
			pstmt.setString(3, GuestbookVo.getContent());

			// 실행
			count = pstmt.executeUpdate(); // select문 빼고 나머지는 executeUpdate()씀!

			// 4.결과처리
			System.out.println("1건 등록되었습니다.");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
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
		}
		return count;
	}

	/************************************
	 * //삭제
	 ***********************************/
	public int guestDelete(guestbookVo GuestbookVo) {
		int count = -1;
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/guestbook_db";
			conn = DriverManager.getConnection(url, "guestbook", "guestbook");

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			// 비밀번호 일치 여부 확인
			String query1 = "";
			query1 += " select name ";
			query1 += " from guestbook ";
			query1 += " where no=? ";
			query1 += " and password=? ";

			// 바인딩
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, GuestbookVo.getNo());
			pstmt.setString(2, GuestbookVo.getPassword());

			// 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {// 일치하는 데이터가 있다면 삭제/이동한 위치에 레코드가 있으면 true반환, 아니면 false반환
				String query2 = "";
				query2 += " delete from guestbook ";
				query2 += " where no=? ";
				query2 += " and password=? ";

				// 바인딩
				pstmt2 = conn.prepareStatement(query2);
				pstmt2.setInt(1, GuestbookVo.getNo());
				pstmt2.setString(2, GuestbookVo.getPassword());

				count = pstmt2.executeUpdate(); // select문 빼고 나머지는 executeUpdate()씀!
				System.out.println("1건 삭제되었습니다.");
			} else {// 일치하는 데이터가 없다면
				System.out.println("비밀번호가 다릅니다.");

			}

			// 4.결과처리

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
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
		}

		return count;
	}

}// Dao끝
