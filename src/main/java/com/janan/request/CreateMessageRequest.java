package com.janan.request;

import lombok.Data;

@Data
public class CreateMessageRequest {

    private Long senderId;
    private Long ProjectId;
    private String content;
}
