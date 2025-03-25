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
        if (unit.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Unit Bulunamadı!");
            return response;

        }
        if (dto.getFirstName() == null) {
            response.setMessage("İsim alanı boş olamaz!");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        if (dto.getLastName() == null) {
            response.setMessage("Soyisim alanı boş olamaz!");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        if (dto.getUserName() == null) {
            response.setMessage("Kullanıcı ismi alanı boş olamaz!");
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
    public List<PersonBaseResponse> findAll() {
        List<PersonBaseResponse> responseList = new ArrayList<>();

        List<PersonelResponseDto> personelDtoList = new ArrayList<>();
        List<Personel> personelList = personelRepository.findAll();

        for (Personel personel : personelList) {
            PersonelResponseDto personelResponseDto = new PersonelResponseDto();
            BeanUtils.copyProperties(personel, personelResponseDto);
            personelDtoList.add(personelResponseDto);
        }

        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelDtoList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Personel listesi");

        responseList.add(response);

        return responseList;
    }


    @Override
    public PersonBaseResponse findById(Long id) {
        PersonBaseResponse response = new PersonBaseResponse();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen Id değerine ait kayıt bulunamadı.");
            return response;
        }


        Personel dbPersonel = optional.get();
        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        BeanUtils.copyProperties(dbPersonel, personelResponseDto);

        response.setData(personelResponseDto);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen Id bilgileri: " + id);

        return response;
    }


    @Override
    public PersonBaseResponse deleteById(Long id) {
        PersonBaseResponse response = new PersonBaseResponse();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen Id değerine ait kayıt bulunamadı.");
            return response;
        }

        personelRepository.deleteById(id);

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Personel başarıyla silindi.");
        response.setData(null);
        return response;
    }


    @Override
    public PersonBaseResponse update(Long id, PersonelRequestDto dto) {

        PersonBaseResponse response = new PersonBaseResponse();

        PersonelResponseDto personResponseDto = new PersonelResponseDto();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Güncellenecek Id Bulunamadı");
            return response;
        }
        if (dto.getFirstName() == null || dto.getLastName() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("İsim ve soyisim alanları boş girilemez");
            return response;
        }

        Optional<Unit> unitOptional = unitRepository.findById(dto.getUnitId());
        if (unitOptional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen Unit Id geçersiz");
            return response;
        }

        Optional<City> cityOptional = cityRepository.findById(dto.getCityId());
        if (cityOptional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen City Id geçersiz");
            return response;
        }

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
            BeanUtils.copyProperties(updatedPersonel, personResponseDto);

            response.setData(personResponseDto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Güncelleme işlemi başarılı");
            return response;
        }

        return null;
    }

    //QUERYIMPL

    @Override
    public List<PersonBaseResponse> personelListesi() {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesi();
        PersonBaseResponse response = new PersonBaseResponse();

        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListsiID2ve11(long id2, long id11) {
        List<PersonBaseResponse> responseList = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.persnelListsiID2ve11(id2, id11);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen Id bilgileri");
        responseList.add(response);
        return responseList;
    }

    @Override
    public List<PersonBaseResponse> personelListesistatus(int status) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesistatus(status);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen status bilgileri");
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiLikeKullanimi(String isim) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiLikeKullanimi(isim);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiBolumBilgisi(String bolum) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisi(bolum);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiBolumBilgisiUpper(String bolum) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiUpper(bolum);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiBolumBilgisiLower(String bolum) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiLower(bolum);
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiDogumGunu(PersonelDtoDogumGunu dto) {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunu(dto.getDogumGunu());
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiDogumGunuNull() {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNull();
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<PersonBaseResponse> personelListesiDogumGunuNotNull() {
        List<PersonBaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNotNull();
        PersonBaseResponse response = new PersonBaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
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
