package kr.co.wallet.www.vo;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 7;
	private Criteria cri;
	
	public void setDisplayPageNum(int displayPageNum){
		this.displayPageNum = displayPageNum;
	}
	
	public void setCri(Criteria cri){
		this.cri = cri;
	}
	
	public Criteria getCri(){
		return cri;
	}
	
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
		calcDate();
	}
	
	private void calcDate(){ //ceil 함수는 올림 함수 즉 0.1 ~ 9 = 1 -> 1 * 10
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum); 
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		endPage = endPage > tempEndPage ? tempEndPage : endPage;
		
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}
	
	public String makeQuery(int page){		// contentid와 contentType 도 넣게 변경,,, 상세정보 페이지 좋아요 기능 추가
		UriComponents uriComponents = 
		
		UriComponentsBuilder.newInstance()
			.queryParam("page", page)
			.queryParam("perPageNum", cri.getPerPageNum())
			.build();
		
		return uriComponents.toUriString();
	}
	
	public int getStartPage(){
		return startPage;
	}
	
	public int getEndPage(){
		return endPage;
	}
	
	public boolean getPrev(){
		return prev;
	}
	
	public boolean getNext(){
		return next;
	}
}
