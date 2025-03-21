package com.example.dto.dtoQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelQueryResponseDto {
    private Long id;

    private String firstName;

    private String lastName;

    private Date createDate;

    private String description;

    private Integer status;

    private String bolum;

    private String userName;

    private String createBy;

    private Long cityId;
    private Long unitId;



}
