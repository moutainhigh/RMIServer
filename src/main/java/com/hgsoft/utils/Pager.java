package com.hgsoft.utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
@Component
@Scope("prototype")
public class Pager implements Serializable {

	private static final long serialVersionUID = 1L;
	private int currentPage = 1;
	private int pageSize = 10;
	private long totalSize = 0;
	private int totalPage = 1;

	private boolean hasFirst;
	private boolean hasPrevious;
	private boolean hasNext;
	private boolean hasLast;
	
	private int begin;
	private int end;
	
	public int getBegin() {
		return (currentPage-1)*pageSize+1;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}


	public int getEnd() {
		return pageSize*currentPage+1;
	}


	public void setEnd(int end) {
		this.end = end;
	}

	private List<Map<String, Object>> resultList;
	
	public void setResultList(List<Map<String, Object>> resultList) {
		this.resultList = resultList;
	}
	

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		try {
			int page = Integer.parseInt(currentPage);
			if (page > 0)
				this.currentPage = page;
		} catch (Exception e) {
		}
	}

	public boolean getHasFirst() {
		return currentPage == 1 ? false : true;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public boolean getHasLast() {
		return currentPage == getTotalPage() ? false : true;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	public boolean getHasNext() {
		return getHasLast() ? true : false;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean getHasPrevious() {
		return getHasFirst() ? true : false;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		try {
			int size = Integer.parseInt(pageSize);
			if (size > 0 && size < 1000)
				this.pageSize = size;
		} catch (Exception e) {
		}
	}

	public int getTotalPage() {
		totalPage = (int) (totalSize / pageSize);
		if (totalSize % pageSize != 0)
			totalPage++;
		totalPage = totalPage == 0 ? 1 : totalPage;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		if (totalSize > -1)
			this.totalSize = totalSize;
		currentPage = currentPage > getTotalPage() ? getTotalPage()
				: currentPage;
	}
}
