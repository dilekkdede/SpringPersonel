package com.example.entites;


import lombok.*;
import org.springframework.lang.UsesJava7;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "personel")
public class Personel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "bolum")
    private String bolum;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "birth_day")
    private Date birthDate;

}
