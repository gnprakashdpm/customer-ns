package com.customerservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryAnalyticsResponse {
    private String functionality;
    private int functionalityQueryCount;
    private List<FunctionalityDetail> details = new ArrayList<>();

    public QueryAnalyticsResponse(String functionality) {
        this.functionality = functionality;
    }

    public void incrementQueryCount() {
        functionalityQueryCount++;
    }
}
