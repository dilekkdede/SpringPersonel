package com.person.services;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.CityRequestDto;
import com.person.dto.dtoEntity.CityResponseDto;

import java.util.List;

public interface ICityServices {

    BaseResponse save(CityRequestDto dto);

    BaseResponse findAll();

    CityResponseDto findById(Long id);

    void deleteById(Long id);

    CityResponseDto update(Long id, CityRequestDto dto);
}
