package com.example.services;

import com.example.dto.dtoEntity.ContactRequestDto;
import com.example.dto.dtoEntity.ContactResponseDto;

import java.util.List;

public interface IContactServices {

    ContactResponseDto save(ContactRequestDto dto);

    List<ContactResponseDto> findAll();

    ContactResponseDto findById(Long id);

    void deleteById(Long id);

    ContactResponseDto update(Long id, ContactRequestDto dto);
}
