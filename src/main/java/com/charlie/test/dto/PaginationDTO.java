package com.charlie.test.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private Boolean showPre;
    private Boolean showNext;
    private Boolean showFirst;
    private Boolean showLast;
    private Integer page;
    private Integer totalPage;
    private Integer offset;
    private List<Integer> pages = new ArrayList<>();


    public void setPagination(int totalNums, Integer page, Integer size) {
        this.totalPage = totalNums % size == 0 ? totalNums / size : totalNums / size + 1;

        if (page <= 0) {
            page = 0;
        }

        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        this.offset = (page - 1) * size;

        pages.add(page);

        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                // 往list头部添加
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                // 往后添加
                pages.add(page + i);
            }
        }

        // 上一页
        if (page == 1) {
            showPre = false;
        } else {
            showPre = true;
        }

        // 下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
    }
}
