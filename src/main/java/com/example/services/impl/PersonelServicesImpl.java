package com.example.services.impl;

import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.dto.dtoQuery.PersonelDtoCreateByIn;
import com.example.dto.dtoQuery.PersonelDtoDogumGunu;
import com.example.dto.dtoQuery.PersonelDtoIdIn;
import com.example.entites.Personel;
import com.example.repository.PersonelJPARepository;
import com.example.repository.PersonelNativeRepository;
import com.example.repository.PersonelRepository;
import com.example.services.IPersonelServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonelServicesImpl implements IPersonelServices {

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelNativeRepository personelNativeRepository;

    @Autowired
    private PersonelJPARepository personelJPARepository;

    @Override
    public PersonelResponseDto save(PersonelRequestDto dto) {

        PersonelResponseDto personelResponseDto = new PersonelResponseDto();
        Personel personel = new Personel();
        BeanUtils.copyProperties(dto, personel);

        Personel dbPersonel = personelRepository.save(personel);
        BeanUtils.copyProperties(dbPersonel, personelResponseDto);

        return personelResponseDto;


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
       int liste=personelJPARepository.personelListesiCountJpa();
        return liste;
    }

    @Override
    public List<Personel> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn) {

        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> response= personelJPARepository.personelListesiInKullanimiJpa(idList);
        return response;
    }

    @Override
    public List<Personel> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn) {
        List<String> bosListe= new ArrayList<>();
        for(PersonelDtoCreateByIn str:dtoIn){
            bosListe.add(str.getCreateBy());
        }

        List<Personel> liste=personelJPARepository.personelListesiInCreateByJpa(bosListe);
        return liste;
    }


}
