package br.com.devtools.apidevtools.core.controller;

import java.util.List;

public class FormGet<Model> {

	private List<Model> list;
	private Long page;
	private Long lastPage;
	private Integer numberRecords;
	private Long totalRecords;

	public List<Model> getList() {
		return list;
	}

	public void setList(List<Model> list) {
		this.list = list;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public Long getLastPage() {
		return lastPage;
	}

	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}

	public Long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getNumberRecords() {
		return numberRecords;
	}

	public void setNumberRecords(Integer numberRecords) {
		this.numberRecords = numberRecords;
	}
	
}
