package com.example.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String code;
    private String description;
    private Long parentId;
    private String parentName;
    private Long creatorId;
    private String creatorName;
    private Long updaterId;
    private String updaterName;
    private Date createDate;
    private Date updateDate;
}
