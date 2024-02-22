package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.guestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.guestbookVo;

@WebServlet("/gbc")
public class guestbookController extends HttpServlet {
	// 필드
	private static final long serialVersionUID = 1L;

	// 생성자-기본생성자사용

	// 메소드-gs

	// 메소드-일반
	protected void doGet(HttpServletRequest request,
			             HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("guestbookController.goGet");//출력 실행 확인

		String action = request.getParameter("action");
		System.out.println(action);//출력 실행 확인
		// http://localhost:8080/guestbook/gbc?action=insert
		
		if("alform".equals(action)) { //메인화면
			System.out.println("alform:메인화면");
			
			//DB관련
			guestbookDao GuestbookDao = new guestbookDao();
			
			//리스트 불러오기
			List<guestbookVo> guestList = GuestbookDao.guestSelect();
			
			//데이터 담기(리스트 주소를 request에 담기)
			request.setAttribute("guestList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/addList.jsp");
			
		}else if("insert".equals(action)) {//등록
			System.out.println("alform:등록");
			
			//값 저장하기
			String name= request.getParameter("name");
			String pw= request.getParameter("pw");
			String content= request.getParameter("content");
			String reg_date = request.getParameter("reg_date");
			
			//Vo로 묶기
			guestbookVo guestVo = new guestbookVo(name, pw, content, reg_date);
			
			//DB관련연결
			guestbookDao GuestDao = new guestbookDao();
			
			//Dao메소드로 연결
			GuestDao.guestInsert(guestVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/guestbook/gbc?action=alform");
			
			
		}else if("dform".equals(action)) {
			System.out.println("dform:삭제폼");
			int no = Integer.parseInt(request.getParameter("no"));
//			
			request.setAttribute("no", no);

//			//리다이렉트
			WebUtil.forward(request, response, "/WEB-INF/deleteForm.jsp");
		}else if("delete".equals(action) ) {
			//받은 값을 저장
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			
			//Vo로 묶기
			guestbookVo GuestVo = new guestbookVo(no,pw);
			
			//DB관련
			guestbookDao GuestbookDao = new guestbookDao();
			
			//Dao메소드쓰기
			GuestbookDao.guestDelete(GuestVo);
			
			WebUtil.redirect(request, response, "/guestbook/gbc?action=alform");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
