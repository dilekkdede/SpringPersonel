package com.example.dto.dtoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitRequestDto {

    private Long id;

    private String name;

    private String code;

    private Integer status;

    private Date createDate;
}
