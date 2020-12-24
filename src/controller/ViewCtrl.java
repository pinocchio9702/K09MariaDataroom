package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewCtrl extends HttpServlet{

	/*
	서블릿이 요청을 받을때 doGet(), doPost()를 통해 처리하게되지만,
	service()는 위 두가지 방식의 요청을 동시에 처리할 수 있다.
	*/
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//게시물의 일련번호
		String idx = req.getParameter("idx");
		
		DataroomDAO dao = new DataroomDAO();
		//일련번호에 해당하는 출력할 게시물을 가져온다.
		DataroomDTO dto = dao.selectView(idx);
		//조회수를 증가시킨다.
		dao.updateVisitCount(idx);
		
		//게시물의 줄바꿈 처리를 위해 replace()를 사용한다.
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
		//커넥션 풀에 객체 반납
		dao.close();
		
		//request영역에 DTO객체 저장
		req.setAttribute("dto", dto);
		
		req.getRequestDispatcher("/14Dataroom/DataView.jsp").forward(req, resp);
		
	}
}
