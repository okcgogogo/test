package com.charlie.test.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long modified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;
    private String tag;
}
