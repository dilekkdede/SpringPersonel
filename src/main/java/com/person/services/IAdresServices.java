package com.person.services;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.AdresRequestDto;

public interface IAdresServices {

    BaseResponse save(AdresRequestDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, AdresRequestDto dto);
}
