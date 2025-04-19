package com.example.services.impl;

import com.example.dto.dtoEntity.AdresRequestDto;
import com.example.dto.dtoEntity.AdresResponseDto;
import com.example.entites.Adres;
import com.example.repository.AdresRepository;
import com.example.services.IAdresServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdresServicesImpl implements IAdresServices {

    @Autowired
    private AdresRepository adresRepository;

    @Override
    public AdresResponseDto save(AdresRequestDto dto) {

        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Adres adres = new Adres();
        BeanUtils.copyProperties(dto, adres);
        Adres dbAdres = adresRepository.save(adres);
        BeanUtils.copyProperties(dbAdres, adresResponseDto);

        log.info("Adres kayıt edildi");
        return adresResponseDto;
    }

    @Override
    public List<AdresResponseDto> findAll() {

        List<AdresResponseDto> adresResponseDtoList = new ArrayList<>();
        List<Adres> adresList = adresRepository.findAll();

        for (Adres adres : adresList) {
            AdresResponseDto adresResponseDto = new AdresResponseDto();
            BeanUtils.copyProperties(adres, adresResponseDto);
            adresResponseDtoList.add(adresResponseDto);
        }

        log.info("Adresler çekildi");
        return adresResponseDtoList;
    }

    @Override
    public AdresResponseDto findById(Long id) {
        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Optional<Adres> optional = adresRepository.findById(id);

        if (optional.isPresent()) {
            Adres dbAdres = optional.get();
            BeanUtils.copyProperties(dbAdres, adresResponseDto);
        }

        log.info("Adres bulundu");
        return adresResponseDto;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Adres silindi");
        adresRepository.deleteById(id);

    }

    @Override
    public AdresResponseDto update(Long id, AdresRequestDto dto) {
        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Optional<Adres> optional = adresRepository.findById(id);
        if (optional.isPresent()) {

            Adres dbAdres = optional.get();
            dbAdres.setPersonelId(dto.getPersonelId());
            dbAdres.setDescription(dto.getDescription());
            dbAdres.setStatus(dto.getStatus());
            dbAdres.setStatus(dto.getStatus());
            dbAdres.setCreateDate(dto.getCreateDate());

            Adres updatedAdres = adresRepository.save(dbAdres);
            BeanUtils.copyProperties(updatedAdres, adresResponseDto);

            log.info("Adres güncellendi");
            return adresResponseDto;
        }
        return null;
    }
}
