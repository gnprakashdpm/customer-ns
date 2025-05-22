package com.customerservice.bo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserQueryDetail {
    private String user;
    private int userQueryCount;
    private List<QnA> qna = new ArrayList<>();

    public UserQueryDetail(String user) {
        this.user = user;
        this.userQueryCount = 0;
    }

    public void incrementQueryCount() {
        this.userQueryCount++;
    }

    // Getters and setters
}

