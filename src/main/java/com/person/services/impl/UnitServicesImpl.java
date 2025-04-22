package com.person.services.impl;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.UnitRequestDto;
import com.person.dto.dtoEntity.UnitResponseDto;
import com.person.entites.Unit;
import com.person.repository.UnitRepository;
import com.person.services.IUnitServices;
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
public class UnitServicesImpl implements IUnitServices {

    @Autowired
    private UnitRepository unitRepository;


    @Override
    public BaseResponse save(UnitRequestDto dto) {

        UnitResponseDto responseDto = new UnitResponseDto();
        Unit unit = new Unit();
        BeanUtils.copyProperties(dto, unit);

        Unit dbUnit = unitRepository.save(unit);
        BeanUtils.copyProperties(dbUnit, responseDto);
        log.info("Unit kayıt edildi");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(responseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Unit kayıt edildi");
        return baseResponse;

    }

    @Override
    public BaseResponse findAll() {

        List<UnitResponseDto> responseList = new ArrayList<>();
        List<Unit> units = unitRepository.findAll();

        for (Unit unit : units) {
            UnitResponseDto responseDto = new UnitResponseDto();
            BeanUtils.copyProperties(unit, responseDto);
            responseList.add(responseDto);
        }
        log.info("Unitler çekildi");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(responseList);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Unitler Listesi Başarılı");
        return baseResponse;

    }

    @Override
    public BaseResponse findById(Long id) {
        UnitResponseDto responseDto = new UnitResponseDto();
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            Unit dbUnit = unitOptional.get();
            BeanUtils.copyProperties(dbUnit, responseDto);
        }
        log.info("Unit bulundu");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(responseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Girilen Id'ye ait Unit bulundu");

        return baseResponse;
    }

    @Override
    public void deleteById(Long id) {

        log.info("Unit silindi");
        unitRepository.deleteById(id);
    }

    @Override
    public BaseResponse update(Long id, UnitRequestDto dto) {
        UnitResponseDto responseDto = new UnitResponseDto();
        Optional<Unit> optinol = unitRepository.findById(id);
        if (optinol.isPresent()) {

            Unit dbUnit = optinol.get();
            dbUnit.setName(dto.getName());
            dbUnit.setCode(dto.getCode());
            dbUnit.setStatus(dto.getStatus());
            dbUnit.setCreateDate(dto.getCreateDate());

            Unit updatedUnit = unitRepository.save(dbUnit);
            BeanUtils.copyProperties(updatedUnit, responseDto);
            log.info("Unit güncellendi");

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(responseDto);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Unit Güncelleme başarılı");

            return baseResponse;


        }
        return null;
    }
}
