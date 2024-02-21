package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.tribes.ChannelSender;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class userController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 생성자-기본생성자사용

	// 메소드-gs

	// 메소드-일반
	protected void doGet(HttpServletRequest request, 
			             HttpServletResponse response)
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
			
			//String action2 = request.getParameter("action");
			//System.out.println(action2);

		} else if ("loginForm".equals(action)) {
			System.out.println("loginForm:로그인폼");

			// 로그인 완료 폼
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			

		} else {
			System.out.println("action값을 다시 확인해주세요");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
