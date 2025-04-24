package com.person.services;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.CityRequestDto;

public interface ICityServices {

    BaseResponse save(CityRequestDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, CityRequestDto dto);
}
