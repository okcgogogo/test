package com.charlie.test;

public enum CommentTypeEnum {

    QUESTION(1,"回复问题"),
    COMMENT(2,"回复评论"),
    ;
    private Integer type;
    private String typeDesc;

    CommentTypeEnum(Integer type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentType: CommentTypeEnum.values()) {
            if (commentType.type == type) {
                return true;
            }
        }

        return false;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }
}
