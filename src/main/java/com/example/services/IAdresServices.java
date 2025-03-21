package com.example.services;

import com.example.dto.dtoEntity.AdresRequestDto;
import com.example.dto.dtoEntity.AdresResponseDto;

import java.util.List;

public interface IAdresServices {

    AdresResponseDto save(AdresRequestDto dto);

    List<AdresResponseDto> findAll();

    AdresResponseDto findById(Long id);

    void deleteById(Long id);

    AdresResponseDto update(Long id, AdresRequestDto dto);
}
