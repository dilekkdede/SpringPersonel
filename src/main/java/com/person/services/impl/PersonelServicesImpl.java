package com.person.services.impl;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.*;
import com.person.dto.dtoQuery.*;
import com.person.entites.*;
import com.person.repository.*;
import com.person.services.IPersonelServices;
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

    @Autowired
    private AdresRepository adresRepository;


    /// CRUD İŞLEMLERİ////
    @Override
    public BaseResponse save(PersonelRequestDto dto) {

        BaseResponse response = new BaseResponse();


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

        Personel personel = new Personel();
        BeanUtils.copyProperties(dto, personel);

        Optional<City> city = cityRepository.findById(dto.getCity().getId());
        if (city.isEmpty()) {
            response.setMessage("Şehir bulunamadı!");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return response;
        } else {
            personel.setCity(city.get());
        }

        Optional<Unit> unit = unitRepository.findById(dto.getUnit().getId());
        if (unit.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Unit Bulunamadı!");
            return response;
        } else {
            personel.setUnit(unit.get());
        }

        if (dto.getAdres() != null) {
            Optional<Adres> findAdres = adresRepository.findById(dto.getAdres().getId());
            findAdres.ifPresent(personel::setAdres);
        }


        Personel dbPersonel = personelRepository.save(personel);

        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        BeanUtils.copyProperties(dbPersonel, personelResponseDto);

        if (dbPersonel.getAdres() != null) {
            AdresResponseDto adresResponseDto = new AdresResponseDto();
            BeanUtils.copyProperties(dbPersonel.getAdres(), adresResponseDto);
            personelResponseDto.setAdres(adresResponseDto);
        }

        if (dbPersonel.getCity() != null) {
            CityResponseDto cityResponseDto = new CityResponseDto();
            BeanUtils.copyProperties(dbPersonel.getCity(), cityResponseDto);
            personelResponseDto.setCity(cityResponseDto);
        }

        if (dbPersonel.getUnit() != null) {
            UnitResponseDto unitResponseDto = new UnitResponseDto();
            BeanUtils.copyProperties(dbPersonel.getUnit(), unitResponseDto);
            personelResponseDto.setUnit(unitResponseDto);
        }

        response.setData(personelResponseDto);
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Personel kayıt edildi");
        log.info("Personel kayıt edildi");
        return response;


    }

    @Override
    public List<BaseResponse> findAll() {
        List<BaseResponse> responseList = new ArrayList<>();
        List<PersonelResponseDto> personelDtoList = new ArrayList<>();
        List<Personel> personelList = personelRepository.findAll();

        for (Personel personel : personelList) {
            PersonelResponseDto personelResponseDto = new PersonelResponseDto();
            BeanUtils.copyProperties(personel, personelResponseDto);

            // Adres varsa DTO'ya çevir
            if (personel.getAdres() != null) {
                AdresResponseDto adresResponseDto = new AdresResponseDto();
                BeanUtils.copyProperties(personel.getAdres(), adresResponseDto);
                personelResponseDto.setAdres(adresResponseDto);
            }

            if (personel.getCity() != null) {
                CityResponseDto cityResponseDto = new CityResponseDto();
                BeanUtils.copyProperties(personel.getCity(), cityResponseDto);
                personelResponseDto.setCity(cityResponseDto);
            }


            if (personel.getUnit() != null) {
                UnitResponseDto unitResponseDto = new UnitResponseDto();
                BeanUtils.copyProperties(personel.getUnit(), unitResponseDto);
                personelResponseDto.setUnit(unitResponseDto);
            }

            log.info("Personeller çekildi");
            personelDtoList.add(personelResponseDto);
        }

        BaseResponse response = new BaseResponse();
        response.setData(personelDtoList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Personel listesi");

        responseList.add(response);

        return responseList;
    }


    @Override
    public BaseResponse findById(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen Id değerine ait kayıt bulunamadı.");
            return response;
        }


        Personel dbPersonel = optional.get();
        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Adres adres = dbPersonel.getAdres();

        CityResponseDto cityResponseDto = new CityResponseDto();
        City city = dbPersonel.getCity();
        BeanUtils.copyProperties(adres, adresResponseDto);
        BeanUtils.copyProperties(city, cityResponseDto);
        BeanUtils.copyProperties(dbPersonel, personelResponseDto);
        personelResponseDto.setAdres(adresResponseDto);
        personelResponseDto.setCity(cityResponseDto);

        response.setData(personelResponseDto);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen Id bilgileri: " + id);

        log.info("Personel bulundu");
        return response;
    }


    @Override
    public BaseResponse deleteById(Long id) {
        BaseResponse response = new BaseResponse();
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
        log.info("Personel silindi");
        return response;
    }


    @Override
    public BaseResponse update(Long id, PersonelRequestDto dto) {

        BaseResponse response = new BaseResponse();

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

        Optional<City> cityOptional = cityRepository.findById(dto.getCity().getId());
        if (cityOptional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen City Id geçersiz");
            return response;
        }

        Optional<Adres> adresOptional = adresRepository.findById(dto.getAdres().getId());
        Optional<City> cityOptional1 = cityRepository.findById(dto.getCity().getId());

        if (optional.isPresent()) {
            Personel dbPersonel = optional.get();
            dbPersonel.setFirstName(dto.getFirstName());
            dbPersonel.setLastName(dto.getLastName());
            dbPersonel.setUserName(dto.getUserName());
            dbPersonel.setCreateBy(dbPersonel.getUserName());
            dbPersonel.setAdres(adresOptional.get());
            dbPersonel.setCity(cityOptional1.get());
            dbPersonel.setDescription(dto.getDescription());
            dbPersonel.setStatus(dto.getStatus());
            dbPersonel.setCreateDate(dto.getCreateDate());
            dbPersonel.setBolum(dto.getBolum());
            dbPersonel.setBirthDate(dto.getBirthDate());

            Personel updatedPersonel = personelRepository.save(dbPersonel);


            if (dbPersonel.getAdres() != null) {
                AdresResponseDto adresResponseDto = new AdresResponseDto();
                BeanUtils.copyProperties(dbPersonel.getAdres(), adresResponseDto);
                personResponseDto.setAdres(adresResponseDto);
            }

            if (dbPersonel.getCity() != null) {
                CityResponseDto cityResponseDto = new CityResponseDto();
                BeanUtils.copyProperties(dbPersonel.getCity(), cityResponseDto);
                personResponseDto.setCity(cityResponseDto);
            }


            BeanUtils.copyProperties(updatedPersonel, personResponseDto);

            response.setData(personResponseDto);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Güncelleme işlemi başarılı");
            log.info("Personel günceellendi");
            return response;
        }

        return null;
    }


    /// ////////////QUERYIMPL

    @Override
    public List<BaseResponse> personelListesi() {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesi();
        BaseResponse response = new BaseResponse();

        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListsiID2ve11(long id2, long id11) {
        List<BaseResponse> responseList = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.persnelListsiID2ve11(id2, id11);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen Id bilgileri");
        responseList.add(response);
        return responseList;
    }

    @Override
    public List<BaseResponse> personelListesistatus(int status) {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesistatus(status);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen status bilgileri");
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiLikeKullanimi(String isim) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiLikeKullanimi(isim);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisi(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisi(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisiUpper(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiUpper(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisiLower(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiLower(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunu(PersonelDtoDogumGunu dto) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunu(dto.getDogumGunu());
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNull();
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuNotNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNotNull();
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCount() {
        BaseResponse response = new BaseResponse();
        int count = personelNativeRepository.personelListesiCount();
        response.setData(count);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Peronel sayısı: " + count);
        return response;

    }

    @Override
    public List<BaseResponse> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> personelList = personelNativeRepository.personelListesiInKullanimi(idList);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCountvestatus(int status) {
        int count = personelNativeRepository.personelListesiCountvestatus(status);
        BaseResponse response = new BaseResponse();
        response.setData(count);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }

    //JPA QUERYS

    @Override
    public List<BaseResponse> personelListesiJpa() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> liste = personelJPARepository.personelListesi();
        BaseResponse response = new BaseResponse();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiid2veyaid11jpa(long id2, long id11) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiid2veyaid11jpa(id2, id11);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiStatusJpa(int status) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiStatusJpa(status);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiLikeJpa(String karakter) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiLikeJpa(karakter);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumUpperJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumUpperJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumLowerJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumLowerJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiDogumGunuJpa(dto.getDogumGunu());
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuIsNotNullJpa() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCountJpa() {
        BaseResponse response = new BaseResponse();
        int liste = personelJPARepository.personelListesiCountJpa();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }

    @Override
    public List<BaseResponse> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> response = personelJPARepository.personelListesiInKullanimiJpa(idList);
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<String> bosListe = new ArrayList<>();
        for (PersonelDtoCreateByIn str : dtoIn) {
            bosListe.add(str.getCreateBy());
        }

        List<Personel> liste = personelJPARepository.personelListesiInCreateByJpa(bosListe);
        BaseResponse response = new BaseResponse();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiIsimVeSoyisimHaricDigerleriNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> response = new ArrayList<>();
        List<Personel> islem = personelJPARepository.personelListesi();

        if (islem != null) {
            for (Personel personel : islem) {
                personel.setBirthDate(null);
                personel.setCreateBy(null);
                personel.setCreateDate(null);
                personel.setCreateBy(null);
                personel.setBolum(null);
                personel.setDescription(null);
                personel.setUserName(null);
                //    personel.setCityId(null);
                response.add(personel);

            }
        }
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
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
    public List<BaseResponse> personelListesiBirthDayNotOlanaSistemTarihiSetleme() {
        List<BaseResponse> responseList = new ArrayList<>();
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
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        responseList.add(response1);
        return responseList;

    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException {

        List<BaseResponse> personelListesi = new ArrayList<>();
        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> response = personelJPARepository.personelListesiIkiTarihAraliginiDondurme(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;

    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException {
        List<BaseResponse> personelListesi = new ArrayList<>();
        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(personelList);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;


    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException {
        List<BaseResponse> personelListesi = new ArrayList<>();
        Date dateBas = new Date(); // SİSTEM TARİHİ ALMA
        int eklenecekGun = 14;

        calendar.setTime(dateBas);
        calendar.add(Calendar.DAY_OF_YEAR, eklenecekGun);
        Date dateSon = calendar.getTime();

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(personelList);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
    }


}
