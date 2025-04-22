package com.person.services.impl;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.AdresRequestDto;
import com.person.dto.dtoEntity.AdresResponseDto;
import com.person.entites.Adres;
import com.person.repository.AdresRepository;
import com.person.services.IAdresServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public BaseResponse save(AdresRequestDto dto) {

        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Adres adres = new Adres();
        BeanUtils.copyProperties(dto, adres);
        Adres dbAdres = adresRepository.save(adres);
        BeanUtils.copyProperties(dbAdres, adresResponseDto);

        log.info("Adres kayıt edildi");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(adresResponseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Adres Kayıt Başarılı");
        return baseResponse;
    }

    @Override
    public BaseResponse findAll() {

        List<AdresResponseDto> adresResponseDtoList = new ArrayList<>();
        List<Adres> adresList = adresRepository.findAll();

        for (Adres adres : adresList) {
            AdresResponseDto adresResponseDto = new AdresResponseDto();
            BeanUtils.copyProperties(adres, adresResponseDto);
            adresResponseDtoList.add(adresResponseDto);
        }

        log.info("Adresler çekildi");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(adresResponseDtoList);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Adres Listesi Çekildi");
        return baseResponse;

    }

    @Override
    public BaseResponse findById(Long id) {
        AdresResponseDto adresResponseDto = new AdresResponseDto();
        Optional<Adres> optional = adresRepository.findById(id);

        if (optional.isPresent()) {
            Adres dbAdres = optional.get();
            BeanUtils.copyProperties(dbAdres, adresResponseDto);
        }

        log.info("Adres bulundu");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(adresResponseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Girilen Id'ye ait adres bulundu");


        return baseResponse;

    }

    @Override
    public void deleteById(Long id) {
        log.info("Adres silindi");
        adresRepository.deleteById(id);

    }

    @Override
    public BaseResponse update(Long id, AdresRequestDto dto) {
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
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(adresResponseDto);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Adres Güncelleme Başarılı");
            return baseResponse;
        }
        return null;
    }
}
