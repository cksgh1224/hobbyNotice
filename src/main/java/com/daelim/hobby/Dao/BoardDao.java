package com.daelim.hobby.Dao;
////int category=(board.getBdCategory().equals("Team"))?0:(board.getBdCategory().equals("Tip"))?1:
//(board.getBdCategory().equals("FreeBoard"))?2:null;  카테고리 DB컬럼에 저장 방식.
import java.beans.PropertyVetoException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale.Category;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.daelim.hobby.Vo.VOBoard;
import com.daelim.hobby.Vo.VOComment;
import com.mchange.v2.c3p0.ComboPooledDataSource;

//  Category Team==0   Tip==1 FreeBoard==2 else Null


@Repository
public class BoardDao {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://61.84.24.83:3306/hobby33?useSSL=false&serverTimezone=UTC";
	private String userid = "hobby33";
	private String userpw = "hobby33!!";
	private ComboPooledDataSource dataSource;
    public final  Map<Integer, String> categorys = new HashMap<Integer,String>() {
        {
            put(0, "Team");
            put(1, "Tip");
            put(2, "FreeBoard");
        }
    };
    //-------------------김정태 추가----------------
    @Inject
	private SqlSession sqlSession;

	private static String namespace = "com.hobby.mappers.board";
	//------------------------------------------
	private JdbcTemplate template;
	public BoardDao() {  //ComboPool을 이용하여 DB접근 

		dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass(driver);
			dataSource.setJdbcUrl(url);
			dataSource.setUser(userid);
			dataSource.setPassword(userpw);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		
		template = new JdbcTemplate();
		template.setDataSource(dataSource);
	}
	
	

//	public int memberRemove(VOBoard member) {
//		int result = 0;
//		
//		final String sql = "DELETE from member_ WHERE memId = ? AND memPw=?";
//		result = template.update(sql,member.getMemId(),member.getMemPw());
//		
//		
//		return result;
//		
//	}
	public List<VOBoard> boardRead() {
		List<VOBoard> boards = null;
		
		//각 게시판 마다의 댓글 수를 포함한 정보 select
		final String sql = "select m.*,count(c.dno) dnoCount from board as m LEFT join `comment` as c  ON  m.bdCno=c.dno group by m.bdCno";	
		// select * from mainboard
		//select m.*,count(C.dno) from mainboard as m LEFT join `comment` as c  ON  m.count=c.dno
		boards = template.query(sql, new Object[]{}, new RowMapper<VOBoard>() {

			@Override
			public VOBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				VOBoard board = new VOBoard();
				board.setBdName(rs.getString("bdName"));
				board.setBdTitle(rs.getString("bdTitle"));
				board.setBdContent(rs.getString("bdContent"));
				board.setBdHit(rs.getInt("bdHit"));
				board.setBdDate(rs.getTimestamp("bdDate"));
				board.setBdCno(rs.getInt("bdCno"));
				board.setBdCategory(categorys.get(rs.getInt("bdCategory")));
				
				return board;
				
			}
			
		});
		
		if(boards.isEmpty()) {
			System.out.println("board is null");
			return null;
		}
		return boards;
		
	}
	public List<VOBoard> boardRead(String category) {
		List<VOBoard> boards = null;
		String sql="";
		if(category.equals("Team")) {
			 sql = "SELECT * FROM board WHERE bdCategory=0";
		}
		else if(category.equals("Tip")) {
			sql = "SELECT * FROM board WHERE bdCategory=1";
		}
		else if(category.equals("FreeBoard")) {
			sql = "SELECT * FROM board WHERE bdCategory=2";
		}
		else {
			System.out.println("select에러");
			return null;
		}
		
		boards = template.query(sql, new Object[]{}, new RowMapper<VOBoard>() {

			@Override
			public VOBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				VOBoard board = new VOBoard();
				board.setBdName(rs.getString("bdName"));
				board.setBdTitle(rs.getString("bdTitle"));
				board.setBdContent(rs.getString("bdContent"));
				board.setBdHit(rs.getInt("bdHit"));
				board.setBdDate(rs.getTimestamp("bdDate"));
				board.setBdCno(rs.getInt("bdCount"));
				board.setBdCategory(categorys.get(rs.getInt("bdCategory")));
				
				return board;
			}
			
		});
		
		if(boards.isEmpty()) {
			System.out.println("board is null");
			return null;
		}
		return boards;
		
	}
	


	public int boardInsert(final VOBoard board) {
		int result = 0;
//		HttpServletRequest request = getCurrentRequest();
//		HttpSession session = request.getSession();     로그인 기능 구현되면 그때 사용
//		VOMember member = (VOMember) session.getAttribute("member");
//		if(member!=null) {  //세션이 없으면 로그인 상태가 아님
//			final String sql = "INSERT INTO mainboard (name, title,content,category) values (?,?,?,?)";
//			// 카테고리가 Team이면 0 Tip이면 1 FreeBoard면 3 아니면 null
//			int category=(board.getCategory().equals("Team"))?0:(board.getCategory().equals("Tip"))?1:
//				(board.getCategory().equals("FreeBoard"))?2:null;
//			result = template.update(sql,"testId",board.getTitle(),board.getContent(),category);
//			
//			
//		}
//		else {
//			System.out.println("Not Found Session");
//			return 0;
//		}
		final String sql = "INSERT INTO board (bdName, bdTitle,bdContent,bdCategory) values (?,?,?,?)";
		// 카테고리가 Team이면 0 Tip이면 1 FreeBoard면 3 아니면 null
		int category=(board.getBdCategory().equals("Team"))?0:(board.getBdCategory().equals("Tip"))?1:
			(board.getBdCategory().equals("FreeBoard"))?2:null;
		result = template.update(sql,"testId",board.getBdTitle(),board.getBdContent(),category);
		
		
		return result;
	}
	public static HttpServletRequest getCurrentRequest() {// 로그인 세션 확인을 위한 메소드
		 
	       ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
	               .currentRequestAttributes();
	 
	       HttpServletRequest hsr = sra.getRequest();
	     
	       return hsr;
	   }



	public VOBoard detailRead(int cnt) {
		
	List<VOBoard> boards = null;
		
		final String sql = "SELECT * FROM board where bdCno=?";
		
		boards = template.query(sql, new Object[]{cnt}, new RowMapper<VOBoard>() {

			@Override
			public VOBoard mapRow(ResultSet rs, int rowNum) throws SQLException {
				VOBoard board = new VOBoard();
				board.setBdName(rs.getString("bdName"));
				board.setBdTitle(rs.getString("bdTitle"));
				board.setBdContent(rs.getString("bdContent"));
				board.setBdHit(rs.getInt("bdHit"));
				board.setBdDate(rs.getTimestamp("bdDate"));
				board.setBdCno(rs.getInt("bdCno"));
				board.setBdLike(rs.getInt("bdLike"));
				board.setBdCategory(categorys.get(rs.getInt("bdCategory")));
				
				return board;
			}
			
		});
		
		if(boards.isEmpty()) {
			System.out.println("board is null");
			return null;
		}
		return boards.get(0);
	}
	public List<VOComment> detailComment(int cnt) {  // 게시글마다 댓글 출
		List<VOComment> comments = null;
		incrementHit(cnt);
		final String sql = "SELECT * FROM `comment` where dno=?";
		comments = template.query(sql, new Object[]{cnt}, new RowMapper<VOComment>() {

			@Override
			public VOComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				VOComment comment = new VOComment();
				
				comment.setComm_name(rs.getString("comm_name"));
				
				comment.setComm_content(rs.getString("comm_content"));
				
				comment.setComm_regDate(rs.getTimestamp("comm_regDate"));
				
				
				return comment;
			}
			
		});
		
		if(comments.isEmpty()) {
			System.out.println("board is null");
			return null;
		}
		return comments;
		
	}



	public int insertComment(VOComment comment) {
		
		int result = 0;
//		HttpServletRequest request = getCurrentRequest(); //요청값 가져오기
//		HttpSession session = request.getSession();  // 세션 불러오기
//		VOMember member = (VOMember) session.getAttribute("member");  // 혹시 몰라서 세션 체크 한번 더
		
//		if(member!=null) {  //세션이 없으면 로그인 상태가 아님
//			final String sql = "INSERT INTO comment (name,content,dno) values (?,?,?)";
//			// 카테고리가 Team이면 0 Tip이면 1 FreeBoard면 3 아니면 null
//			result = template.update(sql,member.getMemId(),comment.getContent(),comment.getDno());
//			
//			
//		}
//		else {
//			System.out.println("Not Found Session");
//			return 0;
//		}
		final String sql = "INSERT INTO comment (comm_name,comm_content,dno) values (?,?,?)";
		
		result = template.update(sql,"testId",comment.getComm_content(),comment.getDno());
		
		return result;
	}



	public int boardUpdate(VOBoard board,int cnt) {
		int result=0;
		int category=(board.getBdCategory().equals("Team"))?0:(board.getBdCategory().equals("Tip"))?1:
			(board.getBdCategory().equals("FreeBoard"))?2:null; // 카테고리 저장
		final String sql = "UPDATE board SET bdTitle = ? , bdContent = ? , bdCategory = ? WHERE bdCno = ?";
		System.out.println(sql);
		result = template.update(sql,board.getBdTitle(),board.getBdContent(),category,cnt);
		
		return result;
	}



	public int boardDelete(int cnt) {
		int result = 0;
		
		final String sql = "DELETE from board WHERE bdCno=?";
		result = template.update(sql,cnt);
		
		
		return result;
	}
	public void incrementHit(int cnt) { //조회수 증가 메소드
		int result=0;
		final String sql = "UPDATE board SET bdHit = bdHit+1 WHERE bdCno = ?";
		result = template.update(sql,cnt);
		
	}
	//-----------------------김정태 추가-----------------------------------------
	// 게시물 검색	
	public List<VOBoard> listPageSearch(int displayPost, int postNum, String searchType, String keyword)
			throws Exception {
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		  
		data.put("displayPost", displayPost);
		data.put("postNum", postNum);
		  
		data.put("searchType", searchType);
		data.put("keyword", keyword);
		  
		return sqlSession.selectList(namespace + ".listPageSearch", data);
	}
	//검색된 게시물 총 갯수(0605추가)
	
	public int searchCount(String keyword) throws Exception {
		int test=sqlSession.selectOne(namespace + ".searchCount", keyword);
		
		System.out.println("test:"+test);
		return sqlSession.selectOne(namespace + ".searchCount", keyword);
		
	}
	
}
