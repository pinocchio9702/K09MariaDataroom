<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CommonLink.jsp</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
	<h2>공통 링크</h2>
	<table border="1" width="90%">
		<tr>
			<td>
				<% if(session.getAttribute("USER_ID")==null){ %>
					<a href="../06Session/Login.jsp">로그인</a>
				<%} else{ %>
					<a href="../06Session/Logout.jsp">로그아웃</a>
				<%} %>
				&nbsp;&nbsp;&nbsp;
				<a href="../08Board1/BoardList.jsp">회원제게시판 1[PageX]</a>
				&nbsp;&nbsp;&nbsp;
				<a href="../08Board2/BoardList.jsp">회원제게시판 2[Page0]</a>
				&nbsp;&nbsp;&nbsp;
				<a href="../DateRoom/DataList">자료실(Mode12)</a>
			</td>
		</tr>
	</table>
</body>
</html>