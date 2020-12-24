<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 파일명 : isLogin.jsp --%>
<%

/*

	해당 페이지처럼 jsp코드를 포함한 파일을 include할 때는
	지시어를 통해 기술하는 것이 좋다. 액션태그를 사용하는 경우
	먼저 컴파일된 후 페이지에 삽입되므로 해당 변수를 원하는 페이지에서 
	사용하지 못하는 문제가 있을 수 있다.
*/
//글쓴기 페이지 진입전 로그인 되었는지 확인
if(session.getAttribute("USER_ID") == null){
%>
	<script type="text/javascript">
	/*
	만약 로그인이 되지 않았다면 로그인 페이지로 이동시킨다.
	*/
		alert("로그인 후 이용해주십시요.");
		location.href="../06Session/Login.jsp";
	</script>
<%

	/*
	JS코드보다 JSP코드가 우선순위가 높으므로 만약 if문 뒤에 
	JSP코드가 존재하면 위의 JS코드가 실행되지 않을수 있다.
	그러므로 if문을 벗어나지 못하도록 return을 걸어줘야 한다.
	*/
	return;
}
%>