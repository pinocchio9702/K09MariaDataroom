package controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import util.FileUtil;

public class EditCtrl extends HttpServlet{

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			//일련번호
			String idx = req.getParameter("idx");
			
			/*
			서블릿에서 application내장객체를 얻어오기 위해 메소드 호출함.
			해당 내장객체는 DAO로 전달하여 초기화파라미터를 얻어오게 된다.
			*/
			ServletContext app = this.getServletContext();
			DataroomDAO dao = new DataroomDAO(app);
			
			//수정폼을 구성하기 위해 게시물의 내용을 가져온다.
			DataroomDTO dto = dao.selectView(idx);
			
			req.setAttribute("dto", dto);
			
			req.getRequestDispatcher("/14Dataroom/DataEdit.jsp")
				.forward(req, resp);
		}
		
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			req.setCharacterEncoding("UTF-8");
			
			//첨부파일 업로드
			MultipartRequest mr = FileUtil.upload(req, req.getServletContext().getRealPath("/Upload"));
			
			int sucOrFail;
			
			//멀티파트 객체가 정상적으로 생성되면 나머지 폼값을 받아온다.
			if(mr != null) {
				String idx = mr.getParameter("idx");
				String nowPage = mr.getParameter("nowPage");
				//첨부파일의 수정을 위해 hidden폼으로 작성한 기본파일명을 받는다.
				String originalfile = mr.getParameter("originalfile");
				
				//수정처리후 상세보기 페이지로 이동해야 하므로 영역에 속성을 저장한다.
				req.setAttribute("idx", idx);
				req.setAttribute("nowPage", nowPage);
				
				String name = mr.getParameter("name");
				String title = mr.getParameter("title");
				String content = mr.getParameter("content");
				String pass = mr.getParameter("pass");
				String attachedfile = mr.getParameter("attachedfile");
				
				/*
				수정폼에서 첨부한 파일이 있다면 기존파일은 삭제하고,
				없다면 기존 파일명으로 유지해야한다.
				*/
				if(attachedfile==null) {
					attachedfile = originalfile;
				}
				
				DataroomDTO dto = new DataroomDTO();
				dto.setAttachedfile(attachedfile);
				dto.setContent(content);
				dto.setTitle(title);
				dto.setName(name);
				dto.setPass(pass);
				dto.setIdx(idx);
				
				/*
				 레코드의 update가 성공이고 동시에 새로운 파일이 업로드 되었다면
				 기존의 파일은 삭제처리한다.
				 첨부한 파일이 없다면 기존파일은 유지된다.
				 */
				ServletContext app = this.getServletContext();
				DataroomDAO dao = new DataroomDAO();
				sucOrFail = dao.update(dto);
				
				if(sucOrFail == 1 && mr.getFilesystemName("attachedfile")!=null) {
					FileUtil.deleteFile(req, "/Upload", originalfile);
				}
				
				dao.close();
			}
			else {
				sucOrFail = -1;
			}
			
			//수정처리 이후에는 상세보기 페이지로 이동한다.
			req.setAttribute("SUC_FAIL", sucOrFail);
			req.setAttribute("WHEREIS", "UPDATE");
			
			req.getRequestDispatcher("/14Dataroom/Message.jsp").forward(req, resp);
			
		}
}
