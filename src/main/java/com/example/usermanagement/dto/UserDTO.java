package com.example.usermanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NonNull
    private Long id;
    @NonNull
    private String username;
    private String password;
    @NonNull
    private String fullName;
    private String description;
    @NonNull
    private Date dateOfBirth;
    @NonNull
    private String address;
    private Date createDate;
    private Long creatorId;
    private String creatorName;
    private Date updateDate;
    private Long updaterId;
    private String updaterName;
    private Long sectionId;
    private String sectionName;
    private Long roleId;
    private String roleName;
}
