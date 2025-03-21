package com.example.dto.dtoQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelDateRequestDto {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
