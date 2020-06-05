package com.daelim.hobby.Service;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daelim.hobby.Dao.BoardDao;
import com.daelim.hobby.Vo.VOBoard;
import com.daelim.hobby.Vo.VOComment;



@Service
public class BoardService {
	@Autowired
	BoardDao dao;
	
	
	public List<VOBoard> read() {
		
		List<VOBoard> boards =dao.boardRead();
		return boards;
		
	}
	public List<VOBoard> read(String category) {
		
		List<VOBoard> boards =dao.boardRead(category);
		return boards;
		
	}
	public int insert(VOBoard board) {
		int result=dao.boardInsert(board);
		
		return result;
	}
	public VOBoard detailView(int cnt) {
		VOBoard board=dao.detailRead(cnt);
		
		return board;
		
	}
	public List<VOComment> detailComment(int cnt) {
		List<VOComment> comments = dao.detailComment(cnt);
		
		return comments;
		
	}
	public int insertComment(VOComment comment) {
		int result = dao.insertComment(comment);
		
		return result;
		
		
	}
	public int insertUpload(MultipartFile uploadfile,String path) {
	    String saveName = getCurrentDate("yyyyMMddHHmm") + "_" + uploadfile.getOriginalFilename();
	    File saveFile = new File(path,saveName); 
	    File filePath = new File(path);
	    
	    if(!filePath.exists()) {
	    	filePath.mkdirs();   // 만약에 path 해당하는 경로가 없으면 폴더를 만듬
	    }
	    try {
	    	uploadfile.transferTo(saveFile); // 파일 저장 
	    }
	    catch(Exception e) {
	    	System.out.println("Error:"+e);
	    }

		return 0;
	}
	public static String getCurrentDate(String format) {  // 이미지파일이름 중복방지
	       String dtStr = "";
	       SimpleDateFormat sdf = new SimpleDateFormat(format);
	       Date dt1 = new Date();

	       dtStr = sdf.format(dt1);

	       return dtStr;
	}

}
