package com.person.services.impl;

import com.person.dto.CityDto;
import com.person.dto.CitySaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.City;
import com.person.enums.RecordStatus;
import com.person.repository.CityRepository;
import com.person.services.ICityServices;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CityServicesImpl implements ICityServices {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BaseResponse save(CitySaveDto dto) {

        BaseResponse response = new BaseResponse();

        City city = new City();
        city.setName(dto.getName());
        city.setCode(dto.getCode());
        city.setStatus(RecordStatus.ACTIVE.getValue());
        city.setCreateDate(new Date());

        City dbCity = cityRepository.save(city);


        CityDto cityDto = modelMapper.map(dbCity, CityDto.class);
        response.setData(cityDto);
        response.setStatus(201);
        response.setMessage("City Success");
        return response;

    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();
        List<City> cityList = cityRepository.findAll();

        List<CityDto> dtoList = modelMapper.map(cityList, new TypeToken<List<CityDto>>() {
        }.getType());

        response.setData(dtoList);
        response.setStatus(200);
        response.setMessage("City findAll Success");
        return response;
    }

    @Override
    public BaseResponse findById(Long id) {

        BaseResponse response = new BaseResponse();
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent()) {
            CityDto cityDto = modelMapper.map(city.get(), CityDto.class);
            response.setData(cityDto);
            response.setStatus(200);
            response.setMessage("City findById success");

        }
        return response;


    }

    @Override
    public BaseResponse deleteById(Long id) {

        BaseResponse baseResponse = new BaseResponse();
        cityRepository.deleteById(id);
        log.info("şehir silindi");

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("City başarılı bir şekilde silindi");
        return baseResponse;

    }

    @Override
    public BaseResponse update(Long id, CitySaveDto dto) {
        BaseResponse response = new BaseResponse();

        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            city.get().setName(dto.getName());
            city.get().setCode(dto.getCode());
        }

        City dbCity = cityRepository.save(city.get());
        CityDto cityDto = modelMapper.map(dbCity, CityDto.class);

        response.setData(cityDto);
        response.setStatus(201);
        response.setMessage("City update Success");


        return response;
    }


}
