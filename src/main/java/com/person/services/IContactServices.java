package com.person.services;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.ContactRequestDto;

public interface IContactServices {

    BaseResponse save(ContactRequestDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    void deleteById(Long id);

    BaseResponse update(Long id, ContactRequestDto dto);
}
