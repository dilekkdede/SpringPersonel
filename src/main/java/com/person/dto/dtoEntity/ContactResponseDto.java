package com.person.dto.dtoEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponseDto {
    private Long id;
    private Long personelId;
    private Integer status;


    private String contact;

    private Date createDate;

    private String type;
}
