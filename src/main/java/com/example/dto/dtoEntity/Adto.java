package com.example.dto.dtoEntity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
public class Adto implements Serializable {

    private Long id;

    private String name;
}
