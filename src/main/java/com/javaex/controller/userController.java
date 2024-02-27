package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class userController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 생성자-기본생성자사용

	// 메소드-gs

	// 메소드-일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// user 실행되는지 확인
		System.out.println("userController");
		// http://localhost:8080/mysite/user

		// user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action);

		if ("joinform".equals(action)) {
			System.out.println("user>joinform");
			// http://localhost:8080/mysite/user?action=joinform

			// 회원가입 폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");

			/******************************************************
			 * 회원가입
			 ******************************************************/

		} else if ("join".equals(action)) {
			System.out.println("user>join");
			// http://localhost:8080/mysite/user?action=join&
			// id=aaaa&pw=123&name=오지원&gerder=male

			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			System.out.println(id);
			System.out.println(password);
			System.out.println(name);
			System.out.println(gender);

			// 데이터를 vo묶어준다
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);

			// dao를 vo로 실행
			UserDao userDao = new UserDao();

			userDao.insertUser(userVo);

			// 회원가입 완료 폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOK.jsp");

			/******************************************************
			 * 로그인폼
			 ******************************************************/

			// String action2 = request.getParameter("action");
			// System.out.println(action2);

		} else if ("loginForm".equals(action)) {
			System.out.println("user>loginForm:로그인폼");

			// 로그인 완료 폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			System.out.println("user>login:로그인");

			String id = request.getParameter("id");
			String password = request.getParameter("pw");

			UserVo userVo = new UserVo(id, password);

			UserDao userDao = new UserDao();
			UserVo authUser = userDao.selectUserByIdPw(userVo); // id pw
			// no name

			if (authUser != null) {// 로그인성공
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				WebUtil.redirect(request, response, "/mysite/main");

			} else {// 로그인 실패
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite/user?action=loginForm");
			}
		} else if ("logout".equals(action)) {
			System.out.println("logout");

			HttpSession session = request.getSession();
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite/main");

			/******************************************************
			 * 회원정보수정
			 ******************************************************/
	/*	} else if ("modifyform".equals(action)) {
	         System.out.println("user>modifyform");

	         WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

	      } else if ("logout".equals(action)) {
	         System.out.println("user>logout");
	         
	         HttpSession session = request.getSession();
	         session.invalidate();
	         
	         
	         WebUtil.redirect(request, response, "/mysite3/main");
	         
	         
	      }else if ("update".equals(action)) {
	            System.out.println("user>update");
	            
	            int no = Integer.parseInt(request.getParameter("no"));
	            String id = request.getParameter("id");
	            String password = request.getParameter("pw");
	            String name = request.getParameter("name");
	            String gender = request.getParameter("gender");

	            // vo로 묶기
	            UserVo userVo = new UserVo(no, id, password, name, gender);
	            System.out.println(userVo.toString());

	            // db관련 업무
	            UserDao userDao = new UserDao();

	            // db에 저장
	            userDao.updateUser(userVo);
	            
	            HttpSession session = request.getSession();
	            session.setAttribute("authUser", userVo);

	            WebUtil.redirect(request, response, "/mysite3/main");*/
	            
		} else if ("modifyForm".equals(action)) {
			System.out.println("user>joinForm>modifyForm:회원정보수정폼");

			// 회원정보수정 폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			UserVo userVo = new UserVo(id, password, name, gender);

			UserDao userDao = new UserDao();
			UserVo authUser = userDao.selectUserByIdPw(userVo); // id pw 이거 한번 더 확인!!!! 수정!ㄴ
			// no name

			if (authUser != null) {// 로그인 수정
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				WebUtil.redirect(request, response, "/mysite/main");

			} else {// 로그인 실패
				System.out.println("로그인 실패");

				WebUtil.redirect(request, response, "/mysite/user?action=loginForm");
			}
		} else if ("logout".equals(action)) {
			System.out.println("logout");

			HttpSession session = request.getSession();
			session.invalidate();

			WebUtil.redirect(request, response, "/mysite/main");
		}

		else {
			System.out.println("action값을 다시 확인해주세요");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	// http://localhost:8080/mysite/main 메인
	// http://localhost:8080/mysite/user?action=joinform 회원가입
	// http://localhost:8080/mysite/user?action=loginForm 로그인
	// http://localhost:8080/mysite/user?action=logout 로그아웃

}
