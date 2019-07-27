package com.charlie.test.model;

import lombok.Data;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long modified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long creator;
    private String tag;
}
