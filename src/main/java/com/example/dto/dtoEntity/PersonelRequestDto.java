package com.example.dto.dtoEntity;

import com.example.entites.Adres;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelRequestDto {

    private Long id;

    private String firstName;


    private String lastName;


    private String userName;


    private String createBy;


    private String description;

    private Integer status;


    private Date createDate;

    private String bolum;


    private Long cityId;


    private Long unitId;

    private Date birthDate;

    private AdresRequestDto adres;

}
