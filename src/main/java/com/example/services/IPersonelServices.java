package com.example.services;

import com.example.dto.dtoBase.PersonBaseResponse;
import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.*;
import com.example.entites.Personel;

import java.text.ParseException;
import java.util.List;

public interface IPersonelServices {

    PersonBaseResponse save(PersonelRequestDto dto);

    List<PersonBaseResponse> findAll();

    PersonBaseResponse findById(Long id);

    PersonBaseResponse deleteById(Long id);

    PersonBaseResponse update(Long id, PersonelRequestDto dto);

    List<PersonBaseResponse> personelListesi();

    List<PersonBaseResponse> personelListsiID2ve11(long id2, long id11);

    List<PersonBaseResponse> personelListesistatus(int status);

    List<PersonBaseResponse> personelListesiLikeKullanimi(String isim);

    List<PersonBaseResponse> personelListesiBolumBilgisi(String bolum);

    List<PersonBaseResponse> personelListesiBolumBilgisiUpper(String bolum);

    List<PersonBaseResponse> personelListesiBolumBilgisiLower(String bolum);

    List<PersonBaseResponse> personelListesiDogumGunu(PersonelDtoDogumGunu dto);

    List<PersonBaseResponse> personelListesiDogumGunuNull();

    List<PersonBaseResponse> personelListesiDogumGunuNotNull();

    int personelListesiCount();

    List<Personel> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn);

    int personelListesiCountvestatus(int status);


    //JPA QUERYS

    List<Personel> personelListesiJpa();

    List<Personel> personelListesiid2veyaid11jpa(long id2, long id11);

    List<Personel> personelListesiStatusJpa(int status);

    List<Personel> personelListesiLikeJpa(String karakter);

    List<Personel> personelListesibolumJpa(String bolum);

    List<Personel> personelListesibolumUpperJpa(String bolum);

    List<Personel> personelListesibolumLowerJpa(String bolum);

    List<Personel> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto);

    List<Personel> personelListesiDogumGunuIsNotNullJpa();

    int personelListesiCountJpa();

    List<Personel> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn);

    List<Personel> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn);

    List<Personel> personelListesiIsimVeSoyisimHaricDigerleriNull();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4();

    List<Personel> personelListesiBirthDayNotOlanaSistemTarihiSetleme();

    List<Personel> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException;

    List<Personel> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException;

    List<Personel> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException;
}
