package com.customerservice.bo;

import java.util.List;
import lombok.Data;

@Data
public class IntentSummary {
    private String whom;
    private List<String> queries;
    private int queryCount;
	public String getWhom() {
		return whom;
	}
	public void setWhom(String whom) {
		this.whom = whom;
	}
	public List<String> getQueries() {
		return queries;
	}
	public void setQueries(List<String> queries) {
		this.queries = queries;
	}
	public int getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
}
