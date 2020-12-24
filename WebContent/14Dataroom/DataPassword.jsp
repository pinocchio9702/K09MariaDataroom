<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../common/boardHead.jsp"/>

<body>
<div class="container">
	<div class="row">		 
		<jsp:include page="../common/boardTop.jsp" />
	</div>
	<div class="row">		
		<jsp:include page="../common/boardLeft.jsp" />
		<div class="col-9 pt-3">
		<!-- ### 게시판의 body 부분  start ### -->
			<c:choose>
				<c:when test="${param.mode eq 'edit' }">
					<c:set var='modeView' value='수정' />
				</c:when>
				<c:otherwise>
					<c:set var='modeView' value='삭제' />
				</c:otherwise>
			</c:choose>
			<h3>게시판 - <small>${modeView }를 위한 패스워드 검증</small></h3>
			<script>
				/*
				연습문제] 글쓰기 폼에 빈값이 있는경우 서버로 전송되지
					않드록 아래 validate()함수를 완성하시오.
					모든 값이 입력되었다면 WriteProc.jsp로 submit되어야 한다.
				*/
				   function checkValidate(fm){
				      if(fm.pass.value==""){
				         alert("비밀번호를 입력하세요."); 
				         fm.pass.focus(); 
				         return false; 
				      }
				   }
			
			</script>
				<div class="row mt-3 mr-1">
				<table class="table table-bordered table-striped">
				
				<!-- 
					파일 업로드를 위해서는 반드시 enctype을 선언해야 한다. 
					해당 선언문장이 없으면 파일은 서버로 전송되지 않는다.
				 -->
				<form name="writeFrm" method="post" action="../DataRoom/DataPassword"
					onsubmit="return checkValidate(this);">
				<!-- 패스워드 검증을 위해 idx, mode는 웹서버로 전송되어야 하므로
					hidden폼에 값을 저장한다. -->
				<input type="hid-den" name="idx" value="${param.idx }"/>
				<input type="hid-den" name="mode" value="${param.mode }"/>
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th class="text-center" 
							style="vertical-align:middle;">패스워드</th>
						<td>
							<input type="password" class="form-control" name="pass"
								style="width:200px;"/>
						</td>
					</tr>
				</tbody>
				</table>
			</div>
			<div class="row mb-3">
				<div class="col text-right">
					<button type="submit" class="btn btn-danger">전송하기</button>
					<button type="reset" class="btn btn-dark">Reset</button>
					<button type="button" class="btn btn-warning" onclick="location.href='./DataRoom/DataList?nowPage=${param.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}';">리스트보기</button>
				</div>
				</form>
			</div>
			
		<!-- ### 게시판의 body 부분  end ### -->		
		</div>
	</div>
	<div class="row border border-dark border-bottom-0 border-right-0 border-left-0"></div>
	<jsp:include page="../common/boardBottom.jsp" />
 
</div>
</body>
</html>
