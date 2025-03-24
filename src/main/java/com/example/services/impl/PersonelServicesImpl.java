package com.example.services.impl;

import com.example.dto.dtoBase.PersonBaseResponse;
import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.*;
import com.example.entites.City;
import com.example.entites.Personel;
import com.example.entites.Unit;
import com.example.repository.*;
import com.example.services.IPersonelServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class PersonelServicesImpl implements IPersonelServices {

    private static SimpleDateFormat dF = new SimpleDateFormat("yyyy/M/dd", Locale.ENGLISH);

    private static Calendar calendar = Calendar.getInstance();  // STATİC YAPMAMIZIN NEDENİ BİR KERE TANIMLAYIP DEFALARCA KULLANIYORUZ.DEĞİŞMİYOR

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelNativeRepository personelNativeRepository;

    @Autowired
    private PersonelJPARepository personelJPARepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public PersonBaseResponse save(PersonelRequestDto dto) {

        PersonBaseResponse response = new PersonBaseResponse();

        Optional<City> city = cityRepository.findById(dto.getCityId());
        if (city.isEmpty()) {
            response.setMessage("Şehir bulunamadı!");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        }

        Optional<Unit> unit = unitRepository.findById(dto.getUnitId());
        if(unit.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Unit Bulunamadı!");
            return response;

        }
        if(dto.getFirstName()==null) {
            response.setMessage("İsim alanı boş olamaz!");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        Personel personel = new Personel();
        BeanUtils.copyProperties(dto, personel);

        Personel dbPersonel = personelRepository.save(personel);
        BeanUtils.copyProperties(dbPersonel, personelResponseDto);

        response.setData(personelResponseDto);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Personel kayıt edildi");
        return response;


    }

    @Override
    public List<PersonelResponseDto> findAll() {

        List<PersonelResponseDto> personelDtoList = new ArrayList<PersonelResponseDto>();
        List<Personel> personelList = personelRepository.findAll();

        for (Personel personel : personelList) {
            PersonelResponseDto personelResponseDto = new PersonelResponseDto();
            BeanUtils.copyProperties(personel, personelResponseDto);
            personelDtoList.add(personelResponseDto);
        }
        log.info("Personel List: " + personelList.size());
        return personelDtoList;
    }

    @Override
    public PersonelResponseDto findById(Long id) {
        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        Optional<Personel> optional = personelRepository.findById(id);
        if (optional.isPresent()) {
            Personel dbPersonel = optional.get();
            BeanUtils.copyProperties(dbPersonel, personelResponseDto);
        }

        log.info("Personel not found with id: " + id);
        return personelResponseDto;
    }

    @Override
    public void deleteById(Long id) {
        personelRepository.deleteById(id);
        log.info("Personel deleted: " + id);
    }

    @Override
    public PersonelResponseDto update(Long id, PersonelRequestDto dto) {
        PersonelResponseDto response = new PersonelResponseDto();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isPresent()) {
            Personel dbPersonel = optional.get();
            dbPersonel.setFirstName(dto.getFirstName());
            dbPersonel.setLastName(dto.getLastName());
            dbPersonel.setUserName(dto.getUserName());
            dbPersonel.setCreateBy(dbPersonel.getUserName());
            dbPersonel.setDescription(dto.getDescription());
            dbPersonel.setStatus(dto.getStatus());
            dbPersonel.setCreateDate(dto.getCreateDate());
            dbPersonel.setBolum(dto.getBolum());
            dbPersonel.setCityId(dto.getCityId());
            dbPersonel.setUnitId(dto.getUnitId());
            dbPersonel.setBirthDate(dto.getBirthDate());

            Personel updatedPersonel = personelRepository.save(dbPersonel);
            BeanUtils.copyProperties(updatedPersonel, response);

            return response;
        }

        return null;
    }

    @Override
    public List<Personel> personelListesi() {
        List<Personel> personelList = personelNativeRepository.personelListesi();
        return personelList;
    }

    @Override
    public List<Personel> personelListsiID2ve11(long id2, long id11) {
        List<Personel> personelList = personelNativeRepository.persnelListsiID2ve11(id2, id11);
        return personelList;
    }

    @Override
    public List<Personel> personelListesistatus(int status) {

        List<Personel> personelList = personelNativeRepository.personelListesistatus(status);
        return personelList;
    }

    @Override
    public List<Personel> personelListesiLikeKullanimi(String isim) {
        List<Personel> personelList = personelNativeRepository.personelListesiLikeKullanimi(isim);
        return personelList;
    }

    @Override
    public List<Personel> personelListesiBolumBilgisi(String bolum) {
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisi(bolum);
        return personelList;
    }

    @Override
    public List<Personel> personelListesiBolumBilgisiUpper(String bolum) {
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiUpper(bolum);
        return personelList;
    }

    @Override
    public List<Personel> personelListesiBolumBilgisiLower(String bolum) {
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiLower(bolum);
        return personelList;
    }

    @Override
    public List<Personel> personelListesiDogumGunu(PersonelDtoDogumGunu dto) {
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunu(dto.getDogumGunu());
        return personelList;
    }

    @Override
    public List<Personel> personelListesiDogumGunuNull() {
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNull();
        return personelList;
    }

    @Override
    public List<Personel> personelListesiDogumGunuNotNull() {
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNotNull();
        return personelList;
    }

    @Override
    public int personelListesiCount() {
        int count = personelNativeRepository.personelListesiCount();
        return count;
    }

    @Override
    public List<Personel> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn) {
        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> personelList = personelNativeRepository.personelListesiInKullanimi(idList);

        return personelList;
    }

    @Override
    public int personelListesiCountvestatus(int status) {
        int count = personelNativeRepository.personelListesiCountvestatus(status);
        return count;
    }
    //JPA QUERYS

    @Override
    public List<Personel> personelListesiJpa() {
        List<Personel> liste = personelJPARepository.personelListesi();
        return liste;
    }

    @Override
    public List<Personel> personelListesiid2veyaid11jpa(long id2, long id11) {
        List<Personel> list = personelJPARepository.personelListesiid2veyaid11jpa(id2, id11);
        return list;
    }

    @Override
    public List<Personel> personelListesiStatusJpa(int status) {
        List<Personel> list = personelJPARepository.personelListesiStatusJpa(status);
        return list;
    }

    @Override
    public List<Personel> personelListesiLikeJpa(String karakter) {
        List<Personel> list = personelJPARepository.personelListesiLikeJpa(karakter);
        return list;
    }

    @Override
    public List<Personel> personelListesibolumJpa(String bolum) {
        List<Personel> list = personelJPARepository.personelListesibolumJpa(bolum);
        return list;
    }

    @Override
    public List<Personel> personelListesibolumUpperJpa(String bolum) {
        List<Personel> list = personelJPARepository.personelListesibolumUpperJpa(bolum);
        return list;
    }

    @Override
    public List<Personel> personelListesibolumLowerJpa(String bolum) {
        List<Personel> list = personelJPARepository.personelListesibolumLowerJpa(bolum);
        return list;
    }

    @Override
    public List<Personel> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto) {
        List<Personel> list = personelJPARepository.personelListesiDogumGunuJpa(dto.getDogumGunu());
        return list;
    }

    @Override
    public List<Personel> personelListesiDogumGunuIsNotNullJpa() {
        List<Personel> list = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        return list;
    }

    @Override
    public int personelListesiCountJpa() {
        int liste = personelJPARepository.personelListesiCountJpa();
        return liste;
    }

    @Override
    public List<Personel> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn) {

        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> response = personelJPARepository.personelListesiInKullanimiJpa(idList);
        return response;
    }

    @Override
    public List<Personel> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn) {
        List<String> bosListe = new ArrayList<>();
        for (PersonelDtoCreateByIn str : dtoIn) {
            bosListe.add(str.getCreateBy());
        }

        List<Personel> liste = personelJPARepository.personelListesiInCreateByJpa(bosListe);
        return liste;
    }

    @Override
    public List<Personel> personelListesiIsimVeSoyisimHaricDigerleriNull() {
        List<Personel> response = new ArrayList<>();
        List<Personel> islem = personelJPARepository.personelListesi();

        if (islem != null) {
            for (Personel personel : islem) {
                personel.setBirthDate(null);
                personel.setCreateBy(null);
                personel.setCreateDate(null);
                personel.setCreateBy(null);
                personel.setBolum(null);
                personel.setUnitId(null);
                personel.setDescription(null);
                personel.setUserName(null);
                personel.setCityId(null);
                response.add(personel);

            }

        }
        return response;
    }

    @Override
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum() {
        List<PersonelDtoAdSoyadBolum> response = new ArrayList<>();

        List<Personel> personelList = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        if (personelList != null) {
            for (Personel personel : personelList) {
                PersonelDtoAdSoyadBolum personelDto = new PersonelDtoAdSoyadBolum();
                personelDto.setAd(personel.getFirstName());
                personelDto.setSoyad(personel.getLastName());
                personelDto.setBolum(personel.getBolum());
                personelDto.setAdSoyad(personel.getFirstName() + " " + personel.getLastName());
                response.add(personelDto);
            }

        }
        return response;
    }

    @Override
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4() {
        List<PersonelDtoAdSoyadBolum> response = new ArrayList<>();

        List<Personel> personelList = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        if (personelList != null) {
            for (Personel personel : personelList) {
                PersonelDtoAdSoyadBolum personelDto = new PersonelDtoAdSoyadBolum();
                if (personel.getFirstName().length() <= 4) {
                    personelDto.setAd(personel.getFirstName());
                    personelDto.setSoyad(personel.getLastName());
                    personelDto.setBolum(personel.getBolum());
                    personelDto.setAdSoyad(personel.getFirstName() + " " + personel.getLastName());
                    response.add(personelDto);
                }

            }

        }
        return response;
    }

    @Override
    public List<Personel> personelListesiBirthDayNotOlanaSistemTarihiSetleme() {
        List<Personel> response = new ArrayList<>();

        List<Personel> personelListesi = personelJPARepository.personelListesi();
        if (personelListesi != null) {
            for (Personel personel : personelListesi) {
                if (personel.getBirthDate() == null) {
                    Date newDate = new Date();
                    personel.setBirthDate(newDate);

                }

                response.add(personel);
            }
        }


        return response;

    }

    @Override
    public List<Personel> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException {

        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> response = personelJPARepository.personelListesiIkiTarihAraliginiDondurme(dateBas, dateSon);
        return response;

    }

    @Override
    public List<Personel> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException {
        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        return personelList;


    }

    @Override
    public List<Personel> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException {

        Date dateBas = new Date(); // SİSTEM TARİHİ ALMA
        int eklenecekGun = 14;


        calendar.setTime(dateBas);
        calendar.add(Calendar.DAY_OF_YEAR, eklenecekGun);
        Date dateSon = calendar.getTime();

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        return personelList;
    }


}
