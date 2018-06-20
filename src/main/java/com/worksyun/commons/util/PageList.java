package com.worksyun.commons.util;

import java.util.List;

/**
 * 
* 分页
* @author lt
 */
public class PageList<T> {
	private Integer total=0;
	
	private List<T> rows;



	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}