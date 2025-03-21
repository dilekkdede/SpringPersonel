package com.example.services.impl;

import com.example.dto.dtoEntity.CityRequestDto;
import com.example.dto.dtoEntity.CityResponseDto;
import com.example.entites.City;
import com.example.repository.CityRepository;
import com.example.services.ICityServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityServicesImpl implements ICityServices {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public CityResponseDto save(CityRequestDto dto) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        City city = new City();
        BeanUtils.copyProperties(dto, city);
        City dbCity = cityRepository.save(city);
        BeanUtils.copyProperties(dbCity, cityResponseDto);
        return cityResponseDto;
    }

    @Override
    public List<CityResponseDto> findAll() {

        List<CityResponseDto> cityResponseDtos = new ArrayList<>();
        List<City> cityList = cityRepository.findAll();

        for (City city : cityList) {
            CityResponseDto cityResponseDto = new CityResponseDto();
            BeanUtils.copyProperties(city, cityResponseDto);
            cityResponseDtos.add(cityResponseDto);
        }
        return cityResponseDtos;
    }

    @Override
    public CityResponseDto findById(Long id) {
        CityResponseDto cityResponseDto = new CityResponseDto();
        Optional<City> optional = cityRepository.findById(id);
        if (optional.isPresent()) {
            City dbCity = optional.get();
            BeanUtils.copyProperties(dbCity, cityResponseDto);
        }
        return cityResponseDto;


    }

    @Override
    public void deleteById(Long id) {

        City findCity = cityRepository.findById(id).get();
        cityRepository.delete(findCity);

    }

    @Override
    public CityResponseDto update(Long id, CityRequestDto dto) {
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

            return cityResponseDto;

        }
        return null;
    }


}
