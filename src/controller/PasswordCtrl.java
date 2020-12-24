package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordCtrl extends HttpServlet{

		/*
		상세보기 페이지에서 수정/삭제 버튼을 눌러서 패스워드 검증폼으로
		집입할때의 요청 처리
		 */
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.getRequestDispatcher("/14Dataroom/DataPassword.jsp")
				.forward(req, resp);
		}
		
		/*
		패스워드를 입력한 후 submit 했을때 요청처리
		 */
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			//전송된 3가지 값을 가져와 변수에 저장한다.
			String idx = req.getParameter("idx");
			String mode = req.getParameter("mode");
			String pass = req.getParameter("pass");
			
			//패스워드 검정을 위해 model호출.
			DataroomDAO dao = new DataroomDAO();
			boolean isCorrect = dao.isCorrectPassword(pass, idx);
			dao.close();
			
			//결과값을 리퀘스트 영역에 저장
			req.setAttribute("PASS_CORRECT", isCorrect);
			
			req.getRequestDispatcher("/14Dataroom/PassMessage.jsp")
				.forward(req, resp);
		}
}
