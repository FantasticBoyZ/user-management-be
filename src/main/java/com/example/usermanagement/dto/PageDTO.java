package com.example.usermanagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private Integer offset;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalSize;
    private Integer numOfElement;
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageDTO(List<T> list, Integer pageIndex, Integer pageSize) {
        if (list.isEmpty()) {
            this.empty = true;
            this.offset = 0;
            this.content = list;
            this.totalSize = list.size();
            this.totalPage = 0;
            this.numOfElement = 0;
            this.first = false;
            this.last = false;
        } else {
            this.empty = false;
            this.totalPage = list.size() % pageSize == 0 ? list.size() / pageSize : (list.size() / pageSize) + 1;
            this.totalSize = list.size();
            if(pageIndex == 0){
                this.offset = 0;
                this.first = true;
            }else{
                this.first = false;
            }

            if(pageIndex > totalPage - 1 || pageIndex < 0){
                this.numOfElement = 0;
                this.first = false;
                this.last = false;
            }else if(pageIndex == totalPage-1){
                this.numOfElement = list.size() - pageIndex*pageSize;
                this.offset = pageIndex*pageSize;
                this.content = list.subList(offset, list.size());
                this.first = false;
                this.last = true;
            }else {
                this.first = true;
                this.last = true;
                this.numOfElement = pageSize;
                this.offset = pageIndex * pageSize;
                this.content = list.subList(offset, offset + pageSize);

            }

        }
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }
}
