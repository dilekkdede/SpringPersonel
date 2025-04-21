package com.person.services;

import com.person.dto.dtoEntity.ContactRequestDto;
import com.person.dto.dtoEntity.ContactResponseDto;

import java.util.List;

public interface IContactServices {

    ContactResponseDto save(ContactRequestDto dto);

    List<ContactResponseDto> findAll();

    ContactResponseDto findById(Long id);

    void deleteById(Long id);

    ContactResponseDto update(Long id, ContactRequestDto dto);
}
