package vux.codejava.response;

import java.util.List;

import vux.codejava.entity.SiteInfo;

public class SiteInfoResponse {

	private List<SiteInfo> content;
	private int pageNo;
    private int pageSize;
    private long totalItems;
    private int totalPages;
	public List<SiteInfo> getContent() {
		return content;
	}
	public void setContent(List<SiteInfo> content) {
		this.content = content;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
    
    
}
