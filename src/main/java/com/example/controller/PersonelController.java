package com.example.controller;

import com.example.dto.dtoBase.PersonBaseResponse;
import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.*;
import com.example.entites.Personel;
import com.example.services.IPersonelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingEnumeration;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/api/personel")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PersonelController {

    @Autowired
    private IPersonelServices personelServices;


    @PostMapping(path = "/save")
    public PersonBaseResponse save(@RequestBody PersonelRequestDto personel) {
        return personelServices.save(personel);
    }

    @GetMapping(path = "/list")
    public List<PersonBaseResponse> findAll() {
        return personelServices.findAll();
    }

    @GetMapping(path = "/get-id/{id}")
    public PersonBaseResponse findById(@PathVariable(name = "id") Long id) {
        return personelServices.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public PersonBaseResponse deleteById(@PathVariable(name = "id") Long id) {
        return personelServices.deleteById(id);
    }


    @PutMapping(path = "/update/{id}")
    public PersonBaseResponse updatePersonel(@PathVariable(name = "id") Long id, @RequestBody PersonelRequestDto personel) {
        return personelServices.update(id, personel);
    }

    //@uery işlemleri

    @GetMapping(path = "/personel-listesi")
    public List<Personel> personelListesi() {
        return personelServices.personelListesi();
    }

    @GetMapping(path = "/personel-listesi-id2-id11/{id2}/{id11}")
    public List<Personel> personelListsiID2ve11(@PathVariable(name = "id2") long id2, @PathVariable(name = "id11") long id11) {
        return personelServices.personelListsiID2ve11(id2, id11);
    }


    @GetMapping(path = "/personel-listesi-status/{status}")
    public List<Personel> personelListsistatus(@PathVariable(name = "status") int status) {
        return personelServices.personelListesistatus(status);
    }

    @GetMapping(path = "/personel-listesi-like-kullanimi/{isim}")
    public List<Personel> personelListsistatus(@PathVariable(name = "isim") String isim) {
        return personelServices.personelListesiLikeKullanimi(isim);
    }

    @GetMapping(path = "/personel-listesi-bolum/{bolum}")
    public List<Personel> personelListesiBolumBilgisi(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisi(bolum);
    }

    @GetMapping(path = "/personel-listesi-bolum-upper/{bolum}")
    public List<Personel> personelListesiBolumBilgisiUpper(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisiUpper(bolum);
    }

    @GetMapping(path = "/personel-listesi-bolum-lower/{bolum}")
    public List<Personel> personelListesiBolumBilgisiLower(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisiLower(bolum);
    }

    @PostMapping(path = "/personel-listesi-dogum-gunu")
    public List<Personel> personelListesiDogumGunu(@RequestBody PersonelDtoDogumGunu dto) {
        return personelServices.personelListesiDogumGunu(dto);
    }

    @GetMapping(path = "/personel-listesi-dogum-gunu-null")
    public List<Personel> personelListesiDogumGunuNull() {
        return personelServices.personelListesiDogumGunuNull();
    }

    @GetMapping(path = "/personel-listesi-dogum-gunu-not-null")
    public List<Personel> personelListesiDogumGunuNotNull() {
        return personelServices.personelListesiDogumGunuNotNull();
    }

    @GetMapping(path = "personel-listesi-count")
    public int personelListesiCount() {
        return personelServices.personelListesiCount();
    }


    @PostMapping(path = "personel-listesi-id-in")
    public List<Personel> personelListesiInKullanimi(@RequestBody List<PersonelDtoIdIn> dtoIdIn) {
        return personelServices.personelListesiInKullanimi(dtoIdIn);
    }

    @GetMapping(path = "personel-listesi-count-status/{status}")
    public int personelListesiCountvestatus(@PathVariable(name = "status") int status) {
        return personelServices.personelListesiCountvestatus(status);
    }


    //JPA QUERYS

    @GetMapping(path = "/personel-listesi-jpa")
    public List<Personel> personelListesiJpa() {
        return personelServices.personelListesiJpa();
    }

    @GetMapping(path = "/personel-listesi-id-jpa/{id2}/{id11}")
    public List<Personel> personelListesiid2veyaid11jpa(@PathVariable(name = "id2") long id2, @PathVariable(name = "id11") long id11) {
        return personelServices.personelListesiid2veyaid11jpa(id2, id11);
    }

    @GetMapping(path = "/personel-listesi-status-jpa/{status}")
    public List<Personel> personelListesiStatusJpa(@PathVariable(name = "status") int status) {
        return personelServices.personelListesiStatusJpa(status);
    }

    @GetMapping(path = "/personel-listesi-like-jpa/{karakter}")
    public List<Personel> personelListesiLikeJpa(@PathVariable(name = "karakter") String karakter) {
        return personelServices.personelListesiLikeJpa(karakter);
    }

    @GetMapping(path = "/personel-listesi-bolum-jpa/{bolum}")
    public List<Personel> personelListesiBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumJpa(bolum);
    }

    @GetMapping(path = "/personel-listesi-upper-bolum-jpa/{bolum}")
    public List<Personel> personelListesiUpperBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumUpperJpa(bolum);
    }


    @GetMapping(path = "/personel-listesi-lower-bolum-jpa/{bolum}")
    public List<Personel> personelListesiLowerBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumLowerJpa(bolum);
    }

    @PostMapping(path = "/personel-listesi-dogum-gunu-jpa")
    public List<Personel> personelListesiDogumGunuJpa(@RequestBody PersonelDtoDogumGunu dto) {
        return personelServices.personelListesiDogumGunuJpa(dto);
    }

    @GetMapping(path = "/personel-listesi-dogum-not-null-jpa")
    public List<Personel> personelListesiDogumGunuIsNotNullJpa() {
        return personelServices.personelListesiDogumGunuIsNotNullJpa();
    }

    @GetMapping(path = "/personel-listesi-count-jpa")
    public int personelListesiCountJpa() {
        return personelServices.personelListesiCountJpa();
    }

    @PostMapping(path = "/personel-listesi-in-jpa")
    public List<Personel> personelListesiInKullanimiJpa(@RequestBody List<PersonelDtoIdIn> dto) {
        return personelServices.personelListesiInKullanimiJpa(dto);
    }

    @PostMapping(path = "/personel-listesi-in-createBy-jpa")
    public List<Personel> personelListesiInCreateByJpa(@RequestBody List<PersonelDtoCreateByIn> dto) {
        return personelServices.personelListesiInCreateByJpa(dto);
    }

    @GetMapping(path = "/personel-listesi-isim-ve-soyisim-haric-null")
    public List<Personel> personelListesiIsimVeSoyisimHaricDigerleriNull() {
        return personelServices.personelListesiIsimVeSoyisimHaricDigerleriNull();
    }

    @GetMapping(path = "/personel-listesi-ad-soyad-bolum-jpa")
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum() {
        return personelServices.personelListesiAdSoyadBolum();
    }

    @GetMapping(path = "/personel-listesi-ad-soyad-bolum-kucuk-esit-jpa")
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4() {
        return personelServices.personelListesiAdSoyadBolumKucukEsit4();
    }

    @GetMapping(path = "/personel-listesi-birth-day-not-olana-sistem-tarihini-setleme-jpa")
    public List<Personel> personelListesiBirthDayNotOlanaSistemTarihiSetleme() {
        return personelServices.personelListesiBirthDayNotOlanaSistemTarihiSetleme();
    }

    @PostMapping(path = "/personel-listesi-tarih-araligi-dondurme")
    public List<Personel> personelListesiIkiTarihAraliginiDondurme(@RequestBody PersonelDtoTarihAraligi dto) throws ParseException {
        return personelServices.personelListesiIkiTarihAraliginiDondurme(dto);
    }

    @PostMapping(path = "/personel-listesi-tarih-araligi-createDate-dondurme")
    public List<Personel> personelListesiIkiTarihAraligindakiCreateDate(@RequestBody PersonelDtoTarihAraligi dto) throws ParseException {
        return personelServices.personelListesiIkiTarihAraligindakiCreateDate(dto);
    }

    @GetMapping(path = "/personel-listesi-createDate-with-sistem-tarihi")
    public List<Personel> personelIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException {
        return personelServices.personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi();
    }


}
