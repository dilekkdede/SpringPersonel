package com.example.dto.dtoEntity;

import lombok.*;

import java.io.Serializable;


@Data
@ToString
@EqualsAndHashCode
public class Bdto implements Serializable {

    private Long id;
    private String name;
    private String code;
    private Adto a;

}
