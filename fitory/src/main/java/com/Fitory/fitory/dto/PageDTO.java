package com.Fitory.fitory.dto;

public class PageDTO {
	private int startNo;
	private int endNo;
	private int perPageNum=15;
	private int pageBox=10;
	private Integer page; // Integer 웹에서 받은 페이지 정보(String)가 없으면 null인데 int는 null을 저장할 수 없다. 오류방지
	private Integer totalCount;
	private int endPage;
	private int startPage;
	private boolean prev;
	private boolean next;
	// 검색용 변수 2개 추가
	private String searchType;
	private String searchKeyword;

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	private void calcPage() {
		startNo = (this.page - 1) * perPageNum +1; 
		int tempEnd = (int) (Math.ceil(page / (double) this.pageBox) * this.pageBox);
		                                // 6/10=0.6  > 1   10
		                                // 11/10=1.1  > 2   20
		this.startPage = (tempEnd - this.pageBox) + 1;		
		if (tempEnd * this.perPageNum > this.totalCount) {			
			this.endPage = (int) Math.ceil(this.totalCount / (double) this.perPageNum);			
			if(endPage!=page) {
				this.endNo = startNo + this.perPageNum - 1;	
			}else if(endPage==page&&this.totalCount % perPageNum==0) {
				this.endNo = startNo + perPageNum-1;
			}else {
				this.endNo = startNo + this.totalCount % perPageNum - 1;
			}
		} else {			
			this.endPage = tempEnd;
			this.endNo = startNo + this.perPageNum - 1;
		}
		
		this.prev = this.startPage != 1;
		this.next = this.endPage * this.perPageNum < this.totalCount;
		
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcPage();// totalCount 전제게시물개수가 있어야지 페이지계산을 할 수 있기 때문에
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public int getStartNo() {

		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getPageBox() {
		return pageBox;
	}

	public void setPageBox(int pageBox) {
		this.pageBox = pageBox;
	}
	
}