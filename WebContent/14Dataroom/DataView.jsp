<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
					자료실 - <small>View(상세보기)</small>
				</h3>

				<div class="row mt-3 mr-1">
					<table class="table table-bordered">
						<colgroup>
							<col width="20%" />
							<col width="30%" />
							<col width="20%" />
							<col width="30%" />
						</colgroup>
						<tbody>
							<tr>
								<th class="text-center table-active align-middle">작성자</th>
								<td>${dto.name }</td>
								<th class="text-center table-active align-middle">작성일</th>
								<td>${dto.postdate }</td>
							</tr>
							<tr>
								<th class="text-center table-active align-middle">다운로드 수</th>
								<td>${dto.downcount }</td>
								<th class="text-center table-active align-middle">조회수</th>
								<td>${dto.visitcount }</td>
							</tr>
							<tr>
								<th class="text-center table-active align-middle">제목</th>
								<td colspan="3">${dto.title }</td>
							</tr>
							<tr>
								<th class="text-center table-active align-middle">내용</th>
								<td colspan="3" class="align-middle" style="height: 200px;">
								<!-- textarea에 입력시 엔터키를 사용하면
								\r\n -->
									${dto.content }
								</td>
							</tr>
							<tr>
								<th class="text-center table-active align-middle">첨부파일</th>
								<td colspan="3">
									<c:if test="${not empty dto.attachedfile }">
										${dto.attachedfile }
										<a href="./Download?filename=${dto.attachedfile }&idx=${dto.idx}">
											[다운로드]
										</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="row mb-3">
					<div class="col-6">
					
					<!-- 
						what is param???
					 -->
						<button type="button" class="btn btn-secondary" onclick="location.href='./DataPassword?idx=${param.idx}&mode=edit&nowPage=${param.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}';">수정하기</button>
						<button type="button" class="btn btn-success" onclick="location.href='./DataPassword?idx=${dto.idx}&mode=delet&nowPage=${param.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}';">삭제하기</button>
					
					
					
					</div>
					<div class="col-6 text-right pr-5">
						<button type="button" class="btn btn-warning"
							onclick="location.href='./DataList?&nowPage=${param.nowPage}&searchColumn=${param.searchColumn}&searchWord=${param.searchWord}';">리스트보기</button>
					</div>
				</div>


				<!-- ### 게시판의 body 부분  end ### -->
			</div>
		</div>
		<div
			class="row border border-dark border-bottom-0 border-right-0 border-left-0"></div>
		<jsp:include page="../common/boardBottom.jsp" />

	</div>
</body>
</html>