package com.customerservice.bo;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Data
public class FunctionalityDetail {
    private String date;
    private int dateQueryCount = 0;
    private List<UserQueryDetail> questionDetails = new ArrayList<>();

    public FunctionalityDetail(LocalDate date) {
        this.date = date.toString();
    }

    public void addQuestion(String user, String question, String answer, String keywords, String potentialQuestions) {
        // Increment total queries for this date
        this.dateQueryCount++;

        // Find or create the UserQueryDetail for the user
        UserQueryDetail userDetail = questionDetails.stream()
            .filter(detail -> detail.getUser().equals(user))
            .findFirst()
            .orElseGet(() -> {
                UserQueryDetail newDetail = new UserQueryDetail(user);
                questionDetails.add(newDetail);
                return newDetail;
            });

        // Increment query count for the user
        userDetail.incrementQueryCount();

        // Add main QnA
        userDetail.getQna().add(new QnA(question, answer));

        // Add optional metadata entries
        if (keywords != null && !keywords.isEmpty()) {
            userDetail.getQna().add(new QnA("keywords", keywords));
        }

        if (potentialQuestions != null && !potentialQuestions.isEmpty()) {
            userDetail.getQna().add(new QnA("potentialQuestions", potentialQuestions));
        }
    }

}
