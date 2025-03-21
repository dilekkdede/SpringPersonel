package com.example.services.impl;

import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.entites.Personel;
import com.example.repository.PersonelRepository;
import com.example.services.IPersonelServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PersonelServicesImpl implements IPersonelServices {

    @Autowired
    private PersonelRepository personelRepository;

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
        List<Personel> personelList = personelRepository.personelListesi();
        return personelList;
    }


}
