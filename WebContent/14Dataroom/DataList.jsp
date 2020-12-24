<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<jsp:include page="../common/boardHead.jsp" />

<body>
	<div class="container">
		<div class="row">
			<jsp:include page="../common/boardTop.jsp" />
		</div>
		<div class="row">
			<jsp:include page="../common/boardLeft.jsp" />
			<div class="col-9 pt-3">
				<!-- ### 게시판의 body 부분  start ### -->
				<h3>
					자료실 - <small>Model2방식의 Servlet게시판</small>
				</h3>
				<h4>${test }</h4>
				<div class="row">
					<!-- 검색부분 -->
					<form class="form-inline ml-auto">
						<div class="form-group">
							<select name="searchColumn" class="form-control">
								<option value="title">제목</option>
								<option value="name">작성자</option>
								<option value="content">내용</option>
							</select>
						</div>
						<div class="input-group">
							<input type="text" name="searchWord" class="form-control" />
							<div class="input-group-btn">
								<button type="submit" class="btn btn-warning">
									<i class='fa fa-search' style='font-size: 20px'></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row mt-3">
					<!-- 게시판리스트부분 -->
					<table class="table table-bordered table-hover table-striped">
						<colgroup>
							<col width="60px" />
							<col width="*" />
							<col width="160px" />
							<col width="120px" />
							<col width="80px" />
							<col width="60px" />
						</colgroup>
						<thead>
							<tr style="background-color: rgb(133, 133, 133);"
								class="text-center text-white">
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회수</th>
								<th>첨부</th>
							</tr>
						</thead>
						<tbody>
						<!-- 
							ListCtrl 서블릿에서 request영역에 저장한 ResultSet을
							JSTL과 EL을 통해 화면에 출력한다.
							<chose>
								<when => lists컬렉션에 아무값도 없을때
								<otherwise => ResultSet결과가 있을때(즉 출력할
												레코드가 있을때)
						 -->
							<c:choose>
								<c:when test="${empty lists }">
									<tr>
										<td colspan="6" align="center" height="100">등록된 게시물이
											없습니다.</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${lists }" var="row" varStatus="loop">
										<!-- 리스트반복 -->
										<tr>
											<td class="text-center">${map.totalCount - (((map.nowPage-1) * map.pageSize) + 
												loop.index) }
											</td>
											<td><a
												href="../DataRoom/DataView?idx=${row.idx }&nowPage=${map.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}">
													${row.title } </a></td>
											<td class="text-center">${row.name }</td>
											<td class="text-center">${row.postdate }</td>
											<td class="text-center">${row.visitcount }</td>
											<td class="text-center"><c:if
													test="${not empty row.attachedfile }">
													<a
														href="./Download?filename=${row.attachedfile }&idx=${row.idx}">
														<img src="../images/disk.png" width="20">
													</a>
												</c:if></td>
										</tr>
										<!-- 리스트 반복 -->
						</tbody>
						</c:forEach>
						</c:otherwise>
						</c:choose>
					</table>
				</div>
				<div class="row">
					<div class="col text-right">
						<!-- 각종 버튼 부분 -->
						<!-- <button type="button" class="btn">Basic</button> -->
						<button type="button" class="btn btn-primary"
							onclick="location.href='../DataRoom/DataWrite?nowPage=${map.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}';">글쓰기</button>
						<!-- <button type="button" class="btn btn-secondary">수정하기</button>
					<button type="button" class="btn btn-success">삭제하기</button>
					<button type="button" class="btn btn-info">답글쓰기</button>
					<button type="button" class="btn btn-warning">리스트보기</button>
					<button type="button" class="btn btn-danger">전송하기</button>
					<button type="button" class="btn btn-dark">Reset</button>
					<button type="button" class="btn btn-light">Light</button>
					<button type="button" class="btn btn-link">Link</button> -->
					</div>
				</div>
				<div class="row mt-3">
					<div class="col">
						<!-- 페이지번호 부분 -->
						<ul class="pagination justify-content-center">
							${map.pagingImg }
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div
			class="row border border-dark border-bottom-0 border-right-0 border-left-0"></div>
		<jsp:include page="../common/boardBottom.jsp" />

	</div>
</body>
</html>