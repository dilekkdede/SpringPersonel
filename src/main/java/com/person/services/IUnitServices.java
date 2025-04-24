package com.person.services;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.UnitRequestDto;

public interface IUnitServices {

    BaseResponse save(UnitRequestDto dto);

  BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, UnitRequestDto dto);
}
