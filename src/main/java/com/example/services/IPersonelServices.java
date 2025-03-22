package com.example.services;

import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.PersonelDtoDogumGunu;
import com.example.dto.dtoQuery.PersonelDtoIdIn;
import com.example.entites.Personel;

import java.util.Date;
import java.util.List;

public interface IPersonelServices {

    PersonelResponseDto save(PersonelRequestDto dto);

    List<PersonelResponseDto> findAll();

    PersonelResponseDto findById(Long id);

    void deleteById(Long id);

    PersonelResponseDto update(Long id, PersonelRequestDto dto);

    List<Personel> personelListesi();

    List<Personel> personelListsiID2ve11(long id2,long id11);

    List<Personel> personelListesistatus(int status);

    List<Personel> personelListesiLikeKullanimi(String isim);

    List<Personel> personelListesiBolumBilgisi(String bolum);

    List<Personel> personelListesiBolumBilgisiUpper(String bolum);

    List<Personel> personelListesiBolumBilgisiLower(String bolum);

    List<Personel> personelListesiDogumGunu(PersonelDtoDogumGunu dto);

    List<Personel> personelListesiDogumGunuNull();

    List<Personel> personelListesiDogumGunuNotNull();

    int personelListesiCount();

    List<Personel> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn);

    int personelListesiCountvestatus(int status);



    //JPA QUERYS

    List<Personel> personelListesiJpa();

    List<Personel> personelListesiid2veyaid11jpa(long id2 , long id11);

    List<Personel> personelListesiStatusJpa(int status);

}
