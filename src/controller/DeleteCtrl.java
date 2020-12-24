package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.FileUtil;

public class DeleteCtrl extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String idx = req.getParameter("idx");
		String nowPage = req.getParameter("nowPage");
		
		req.setAttribute("nowPage", nowPage);
		
		DataroomDAO dao = new DataroomDAO();
		//첨부파일 삭제를 위해 기존의 게시물을 가져와서 DTO객체에 저장
		DataroomDTO dto = dao.selectView(idx);
		
		//게시물 삭제
		int sucOrFail = dao.delete(idx);
		if(sucOrFail == 1) {
			//게시물의 삭제가 완료되었다면 첨부파일도 삭제한다.
			String fileName = dto.getAttachedfile();
			//경로명, 파일명을 전달하여 물리적경로에 젖아된 파일을 삭제처리함
			FileUtil.deleteFile(req, "/Upload", fileName);
			
		}
		
		//Message.jsp에서 페이지 이동 및 알림창을 위한 속성저장
		req.setAttribute("WHEREIS", "DELETE");
		req.setAttribute("SUC_FAIL", sucOrFail);
		
		req.getRequestDispatcher("/14Dataroom/Message.jsp").forward(req, resp);
	}
}
