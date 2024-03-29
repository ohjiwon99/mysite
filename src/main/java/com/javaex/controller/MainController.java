package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;

@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 생성자-기본생성자사용

	// 메소드-gs

	// 메소드-일반
	protected void doGet(HttpServletRequest request, 
			             HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("MainController");
		//http://localhost:8080/mysite/MainController
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
		//http://localhost:8080/mysite/MainController

	}

	protected void doPost(HttpServletRequest request, 
			              HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
