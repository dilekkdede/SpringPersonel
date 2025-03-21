package com.example.dto.dtoQuery;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String createBy;

    private String description;

    private Integer status;

    private Date createDate;

    private String bolum;

    private String cityName;

    private String unitName;

    private Date birthDate;

    private String adresDescrition;

    public PersonelInfoDto(Long id, String firstName, String lastName, String userName, String unitName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.unitName = unitName;
    }


    public PersonelInfoDto(Long id, String firstName, String lastName, String cityName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cityName = cityName;
    }

    public PersonelInfoDto(Long id, String firstName, String lastName, Date createDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
    }

    public PersonelInfoDto(Long id, String firstName, String lastName, int status, String createBy) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.createBy = createBy;
    }

    public PersonelInfoDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

    }

}
