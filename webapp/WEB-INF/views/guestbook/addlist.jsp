<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.javaex.vo.guestbookVo"%>

<%
List<guestbookVo> guestList =(List<guestbookVo>)request.getAttribute("guestList");
System.out.println(guestList);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<form>
		<table border="1" width="540px">
			<tr>
				<td>이름</td>
				<td><input type="text" name=""></td>
				<td>비밀번호</td>
				<td><input type="password" name=""></td>
			</tr>
			<tr>
				<td colspan="4"><textarea cols="72" rows="5"></textarea></td>
			</tr>
			<tr>
				<td colspan="4"><button type="">등록</button></td>
				<input type="text" name="action" value="insert">
			</tr>
		</table>
	</form>
	<br>


	<% for(int i = 0; i<guestList.size(); i++ ){
	%>
	
	<table border="1" width="540px">
		<tr>
			<td>[<%= guestList.get(i).getNo() %>]</td>
			<td><%= guestList.get(i).getName() %></td>
			<td><%=guestList.get(i).getReg_date() %></td>
			<td><a href="/guestbook/gbc?action=dform&no=<%=guestList.get(i).getNo() %>">삭제</a></td>
		</tr>
		<tr>
			<td colspan="4"><%=guestList.get(i).getContent() %></td>
		</tr>
	</table>
	<br>
	<%
	} 
	%>
</body>
</html>