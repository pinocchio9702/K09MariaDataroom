package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;



public class DataroomDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;

	// 기본생성자에 DBCP(커넥션풀)을 통해 DB연결
	public DataroomDAO() {
		try {
			Context initctx = new InitialContext();
			Context ctx = (Context) initctx.lookup("java:comp/env");
			DataSource source = (DataSource) ctx.lookup("jdbc_mariadb");
			con = source.getConnection();

			System.out.println("DBCP연결 성공(인자생성자)");
		} catch (Exception e) {
			System.out.println("DBCP연결 실패(인자생성자)");
			e.printStackTrace();
		}
	}

	public DataroomDAO(ServletContext ctx) {
		try {
			Class.forName(ctx.getInitParameter("MariaJDBCDriver"));
			String id = ctx.getInitParameter("MariaUser");
			String pw = ctx.getInitParameter("MariaPass");
			con = DriverManager.getConnection(ctx.getInitParameter("MariaConnectURL"), id, pw);
			System.out.println("DB연결 성공(인자생성자)");
		} catch (Exception e) {
			System.out.println("DB연결 실패(인자생성자)");
			e.printStackTrace();
		}
	}

	// 게시물의 갯수를 카운트
	public int getTotalRecordCount(Map map) {
		// 게시물의 갯수는 최초 0으로 초기화
		int totalCount = 0;

		try {
			// 기본쿼리문(전체레코드를 대상으로 함)
			String sql = "SELECT COUNT(*) FROM dataroom";

			// JSP페이지에서 검색어를 입력한 경우 where절이 동적으로 추가된다.
			if (map.get("Word") != null) {
				sql += "   WHERE  " + map.get("Column") + " " + "  LIKE '%" + map.get("Word") + "%'";
			}

			System.out.println("query=" + sql);

			// 쿼리 실행후 결과값 반환
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			rs.next();
			totalCount = rs.getInt(1);
		} catch (Exception e) {
		}

		return totalCount;
	}

	// 게시물을 가져와서 ResultSet형태로 반환
	public List<DataroomDTO> selectList(Map<String, Object> map) {
		// 리스트 컬렉션을 생성
		List<DataroomDTO> dataroom = new Vector<DataroomDTO>();
		// 기본 쿼리문
		String sql = "SELECT * FROM dataroom  ";

		// 검색어가 있을경우 조건절 동작 추가
		if (map.get("Word") != null) {
			sql += "   WHERE  " + map.get("Column") + " " + "  LIKE '%" + map.get("Word") + "%'";
		}

		// 최근게시물을 항상 위로 노출해야 하므로 작성된 순서의 역순으로 정렬한다.
		sql += "   ORDER BY idx desc";

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			// 오라클이 반환해주는 Result의 갯수만큼 반복
			while (rs.next()) {
				// 하나의 레코드를 DTO객체에 저장하기 위해 새로운 객체생성
				DataroomDTO dto = new DataroomDTO();
				// setter()를 통해 각각의 컬럼에 데이터 저장
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));

				// DTO객체를 List컬렉션에 추가
				dataroom.add(dto);
				System.out.println("쿼리 토스 성공");
			}
		} catch (Exception e) {
			System.out.println("select시 예외발생");
			e.printStackTrace();
		}

		return dataroom;

	}

	public int insert(DataroomDTO dto) {
		int affected = 0;
		try {
			/*
			MariaDB에서는 Sequence대신 auto_increment를 사용하므로
			해당 쿼리는 삭제한다.
			 */
			String sql = "INSERT INTO dataroom ( " + " title, name, content, attachedfile, pass, downcount)  "
					+ "  VALUES ( " + "  ?,?,?,?,?,0)";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getAttachedfile());
			psmt.setString(5, dto.getPass());

			affected = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return affected;
	}

	// 조회수 증가
	public void updateVisitCount(String idx) {
		String sql = " UPDATE dataroom SET " + " visitcount=visitcount+1  " + "  WHERE idx=?  ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		} catch (Exception e) {
		}
	}

	// 자료실 게시물 상세보기
	public DataroomDTO selectView(String idx) {
		DataroomDTO dto = null;
		String sql = " SELECT * FROM dataroom  " + " WHERE idx=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			rs = psmt.executeQuery();
			if (rs.next()) {
				dto = new DataroomDTO();

				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));// 조회수추가

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	// 게시물의 일련번호, 패스워드를 통한 검증(수정, 삭제시 호출됨)
	public boolean isCorrectPassword(String pass, String idx) {
		boolean isCorr = true;
		try {
			String sql = "SELECT COUNT(*) FROM dataroom " + " WHERE pass=? AND idx=? ";
			psmt = con.prepareStatement(sql);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			// 자바에서 쿼리문을 받아서 오라클에 전달해줌
			// 오라클은 자바에 결과값을 반환해줌
			// delect, update, insert는 숫자를 반환
			// select는 레코드를 반환
			// 결과가 숫자일 경우 한번만 rs.next()해주면 된다.
			// 결과를 한번 읽어주는 역할을 하는게 next()이다.
			// 한행을 읽어준다.
			rs.next();
			if (rs.getInt(1) == 0) {
				// 패스워드 검증 실패(해당하는 게시물이 없음)
				isCorr = false;
			}
		} catch (Exception e) {
			isCorr = false;
			e.printStackTrace();
		}
		return isCorr;
	}

	public int delete(String idx) {
		int affected = 0;
		try {
			String query = " DELETE FROM dataroom  " + "  WHERE idx=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);

			affected = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("delecte중 예외발생");
			e.printStackTrace();
		}

		return affected;
	}

	public int update(DataroomDTO dto) {
		int affected = 0;

		try {
			String query = "UPDATE dataroom SET  " + "  title=?, name=?, content=? " + "  , attachedfile=?, pass=? "
					+ "  WHERE idx=?";

			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getAttachedfile());
			psmt.setString(5, dto.getPass());

			// 게시물을 수정을 위한 추가 부분
			psmt.setString(6, dto.getIdx());

			affected = psmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("update중 예외발생");
			e.printStackTrace();
		}

		return affected;
	}

	public List<DataroomDTO> selectListPage(Map map) {

		List<DataroomDTO> bbs = new Vector<DataroomDTO>();

		// 쿼리문이 아래와 같이 페이지처리 쿼리문으로 변경됨.
		String sql = "  " 
				+ "     SELECT * FROM dataroom ";

		if (map.get("Word") != null) {
			sql += " WHERE  " + map.get("Column") + " " + " LIKE '%" + map.get("Word") + "%' ";
		}
		sql += " " + "       ORDER BY idx DESC LIMIT ?, ? ";
		System.out.println("쿼리문 : " + sql);

		try {

			psmt = con.prepareStatement(sql);

			// JSP에서 계산한 페이지 범위값을 이용해 인파라미터를 설정함.
			psmt.setInt(1, Integer.parseInt(map.get("start").toString()));
			psmt.setInt(2, Integer.parseInt(map.get("end").toString()));

			rs = psmt.executeQuery();

			while (rs.next()) {
				DataroomDTO dto = new DataroomDTO();

				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setAttachedfile(rs.getString(6));
				dto.setDowncount(rs.getInt(7));
				dto.setPass(rs.getString(8));
				dto.setVisitcount(rs.getInt(9));

				bbs.add(dto);
			}

		} catch (Exception e) {
			System.out.println("update중 예외발생");
			e.printStackTrace();
		}

		return bbs;

	}

	// 파일 다운로드 횟수 증가
	public void downCountPlus(String idx) {
		String sql = "UPDATE dataroom SET  " + "  downcount=downcount+1  " + "  WHERE idx=?  ";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		} catch (Exception e) {

		}
	}

	public void close() {
		try {
			// 연결을 해제하는 것이 아니고 풀에 다시 반납한다.
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			System.out.println("자원반납시 예외발생");
		}
	}

}
