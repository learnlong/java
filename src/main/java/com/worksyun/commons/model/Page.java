package com.worksyun.commons.model;

import java.util.List;

/**
 * 
* 分页
* @author lt
 */
public class Page<T> {
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