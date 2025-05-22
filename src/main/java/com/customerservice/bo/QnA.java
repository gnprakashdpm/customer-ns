package com.customerservice.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QnA {
    private String question;
    private String answer;
}
