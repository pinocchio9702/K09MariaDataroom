<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<c:when test="${param.mode=='edit' }">
		<c:set var="moveUrl" value="/DataRoom/DataEdit?idx=${param.idx }&nowPage=${requestScope.nowPage }" />
	</c:when>
	<c:otherwise>
		<c:set var="moveUrl" value="/DataRoom/DataDelete?idx=${param.idx }&nowPage=${nowPage }" />
	</c:otherwise>
</c:choose>

<!-- 
	패스워드 검증을 마친 후 결과에 따라 alert() 메시지를 띄어주고
	mode값에 따라 수정 혹은 삭제 페이지로 이동한다.
			-> edit : 수정페이지로 이동한다.
			-> delete : 삭제는 이미 앞에서 패스워드 검증이 끝났으므로
				추가적인 확인없이 즉시 삭제 처리한다.
 -->
<script> 
	<c:choose>
		<c:when test="${PASS_CORRECT }">
			alert("패스워드 검증완료");
			location.href="<c:url value='${moveUrl}' />";
		</c:when>
		<c:otherwise>
			alert("패스워드 검증실패, 뒤로 이동");
			history.back();
		</c:otherwise>
	</c:choose>
</script>