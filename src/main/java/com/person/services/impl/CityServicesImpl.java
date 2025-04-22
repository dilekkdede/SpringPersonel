package com.person.services.impl;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.CityRequestDto;
import com.person.dto.dtoEntity.CityResponseDto;
import com.person.entites.City;
import com.person.enums.RecordStatus;
import com.person.repository.CityRepository;
import com.person.services.ICityServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityServicesImpl implements ICityServices {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public BaseResponse save(CityRequestDto dto) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        City city = new City();
        BeanUtils.copyProperties(dto, city);
        city.setStatus(RecordStatus.ACTIVE.getValue());
        city.setCreateDate(new Date());
        City dbCity = cityRepository.save(city);
        BeanUtils.copyProperties(dbCity, cityResponseDto);
        log.info("Şehir kayıt edildi");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(HttpStatus.CREATED.value());
        baseResponse.setData(cityResponseDto);
        baseResponse.setMessage("Şehir başarılı bir şekilde kayıt edildi");
        return baseResponse;
    }

    @Override
    public BaseResponse findAll() {

        List<CityResponseDto> cityResponseDtos = new ArrayList<>();
        List<City> cityList = cityRepository.findAll();

        for (City city : cityList) {
            CityResponseDto cityResponseDto = new CityResponseDto();
            BeanUtils.copyProperties(city, cityResponseDto);
            cityResponseDtos.add(cityResponseDto);
        }
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(cityResponseDtos);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Şehirler Listesi");
        log.info("şehir listesi çekildi");
        return baseResponse;
    }

    @Override
    public BaseResponse findById(Long id) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        Optional<City> optional = cityRepository.findById(id);
        if (optional.isPresent()) {
            City dbCity = optional.get();
            BeanUtils.copyProperties(dbCity, cityResponseDto);
        }
        log.info("Şehir bulundu");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(cityResponseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Girilen Id'ye ait şehir bulundu");
        return baseResponse;


    }

    @Override
    public void deleteById(Long id) {

        City findCity = cityRepository.findById(id).get();
        log.info("Şehir silindi");
        cityRepository.delete(findCity);

    }

    @Override
    public BaseResponse update(Long id, CityRequestDto dto) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        Optional<City> optional = cityRepository.findById(id);
        if (optional.isPresent()) {
            City dbCity = optional.get();
            dbCity.setName(dto.getName());
            dbCity.setCode(dto.getCode());
            dbCity.setStatus(dto.getStatus());
            dbCity.setCreateDate(dto.getCreateDate());

            City updatedCity = cityRepository.save(dbCity);
            BeanUtils.copyProperties(updatedCity, cityResponseDto);

            log.info("Şehir güncellendi");
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setData(cityResponseDto);
            baseResponse.setMessage("Şehir güncelleme başarılı");
            return baseResponse;


        }
        return null;
    }


}
