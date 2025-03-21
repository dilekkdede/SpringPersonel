package com.example.services;

import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.entites.Personel;

import java.util.List;

public interface IPersonelServices {

    PersonelResponseDto save(PersonelRequestDto dto);

    List<PersonelResponseDto> findAll();

    PersonelResponseDto findById(Long id);

    void deleteById(Long id);

    PersonelResponseDto update(Long id, PersonelRequestDto dto);

    List<Personel> personelListesi();

    List<Personel> persnelListsiID2ve11(long id2,long id11);


}
