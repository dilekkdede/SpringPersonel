package com.person.services;

import com.person.dto.dtoEntity.UnitRequestDto;
import com.person.dto.dtoEntity.UnitResponseDto;

import java.util.List;

public interface IUnitServices {

    UnitResponseDto save(UnitRequestDto dto);

    List<UnitResponseDto> findAll();

    UnitResponseDto findById(Long id);

    void deleteById(Long id);

    UnitResponseDto update(Long id, UnitRequestDto dto);
}
