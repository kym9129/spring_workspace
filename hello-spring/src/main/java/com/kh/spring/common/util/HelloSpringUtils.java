package com.kh.spring.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloSpringUtils {

	public static String getRenamedFilename(String originalFilename) {
		
		//test.jpg
		
		//확장자 추출
		int beginIndex = originalFilename.lastIndexOf("."); //4
		String ext = originalFilename.substring(beginIndex); //.jpg
		
		//년월일_난수 format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
		DecimalFormat df = new DecimalFormat("000"); //정수부 3자리
		
		return sdf.format(new Date()) + df.format(Math.random() * 1000) + ext;
	}
	/**
	 * <nav aria-label="Page navigation example">
		  <ul class="pagination">
		    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
		    <li class="page-item"><a class="page-link" href="#">1</a></li>
		    <li class="page-item"><a class="page-link" href="#">2</a></li>
		    <li class="page-item"><a class="page-link" href="#">3</a></li>
		    <li class="page-item"><a class="page-link" href="#">Next</a></li>
		  </ul>
		</nav>
	 */
	public static Map<String, Object> getPageBar(int cPage, int numPerPage, int totalContents, String url) {
		Map<String, Object> pageBar = new HashMap<>();
		int totalPage = (int)Math.ceil((double)totalContents / numPerPage);
		int pageBarSize = 5;
		
		int pageStart = (cPage - 1) / pageBarSize * pageBarSize + 1;
		int pageEnd = pageStart + pageBarSize - 1;
		
		int pageNo = pageStart;
//		log.debug("totalPage={}", totalPage);
//		log.debug("pageStart={}, pageEnd={}", pageStart, pageEnd);
//		log.debug("cPage={}", cPage);
		
		/**
		 * 이전 1 2 3 4 5 다음
		 * 이전 6 7 8 9 10 다음
		 * 이전 11 
		 */
		pageBar.put("url", url);
		
		//1. 이전
		pageBar.put("pageNo", pageNo);
		
		//2. pageNo
		List<Integer> pageNoList = new ArrayList<>();
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			pageNoList.add(pageNo);
			pageNo++;
		}
		int lastPageNo = pageNo-1;
		pageBar.put("pageNoList", pageNoList);
		pageBar.put("totalPage", totalPage);
		pageBar.put("lastPageNo", lastPageNo);
		pageBar.put("cPage", cPage);
		
		//3. 다음
		//url + pageNo
		
		return pageBar;
	}
	
	

}
