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
public class AdresResponseDto {
    private Long id;

    private Integer personelId;

    private String description;


    private Integer status;


    private Date createDate;
}
