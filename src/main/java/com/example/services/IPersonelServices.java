package com.example.services;

import com.example.dto.dtoBase.PersonBaseResponse;
import com.example.dto.dtoEntity.Bdto;
import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.*;
import com.example.entites.B;
import com.example.entites.Personel;

import java.text.ParseException;
import java.util.List;

public interface IPersonelServices {

    String test();

    B saveB (B b);

    List<Bdto> findAllB();


    /// ////////////CRUD İŞLEMLERİ //////////////////////////
    PersonBaseResponse save(PersonelRequestDto dto);

    List<PersonBaseResponse> findAll();

    PersonBaseResponse findById(Long id);

    PersonBaseResponse deleteById(Long id);

    PersonBaseResponse update(Long id, PersonelRequestDto dto);

    /// ////////////////////////////////////////////////////////////////////////////////////////


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

    PersonBaseResponse personelListesiCount();

    List<PersonBaseResponse> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn);

    PersonBaseResponse personelListesiCountvestatus(int status);


    //JPA QUERYS

    List<PersonBaseResponse> personelListesiJpa();

    List<PersonBaseResponse> personelListesiid2veyaid11jpa(long id2, long id11);

    List<PersonBaseResponse> personelListesiStatusJpa(int status);

    List<PersonBaseResponse> personelListesiLikeJpa(String karakter);

    List<PersonBaseResponse> personelListesibolumJpa(String bolum);

    List<PersonBaseResponse> personelListesibolumUpperJpa(String bolum);

    List<PersonBaseResponse> personelListesibolumLowerJpa(String bolum);

    List<PersonBaseResponse> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto);

    List<PersonBaseResponse> personelListesiDogumGunuIsNotNullJpa();

    PersonBaseResponse personelListesiCountJpa();

    List<PersonBaseResponse> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn);

    List<PersonBaseResponse> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn);

    List<PersonBaseResponse> personelListesiIsimVeSoyisimHaricDigerleriNull();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4();

    List<PersonBaseResponse> personelListesiBirthDayNotOlanaSistemTarihiSetleme();

    List<PersonBaseResponse> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException;

    List<PersonBaseResponse> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException;

    List<PersonBaseResponse> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException;
}
