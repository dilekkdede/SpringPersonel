package com.example.dto.dtoQuery;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelStatusRequestDto {

    private int status;
    private String createBy;
}
