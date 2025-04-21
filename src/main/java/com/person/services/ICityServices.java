package com.person.services;

import com.person.dto.dtoEntity.CityRequestDto;
import com.person.dto.dtoEntity.CityResponseDto;

import java.util.List;

public interface ICityServices {

    CityResponseDto save(CityRequestDto dto);

    List<CityResponseDto> findAll();

    CityResponseDto findById(Long id);

    void deleteById(Long id);

    CityResponseDto update(Long id, CityRequestDto dto);
}
