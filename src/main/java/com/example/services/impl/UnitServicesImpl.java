package com.example.services.impl;

import com.example.dto.dtoEntity.UnitRequestDto;
import com.example.dto.dtoEntity.UnitResponseDto;
import com.example.entites.Unit;
import com.example.repository.UnitRepository;
import com.example.services.IUnitServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UnitResponseDto save(UnitRequestDto dto) {

        UnitResponseDto responseDto = new UnitResponseDto();
        Unit unit = new Unit();
        BeanUtils.copyProperties(dto, unit);

        Unit dbUnit = unitRepository.save(unit);
        BeanUtils.copyProperties(dbUnit, responseDto);
        log.info("Unit kayıt edildi");
        return responseDto;
    }

    @Override
    public List<UnitResponseDto> findAll() {

        List<UnitResponseDto> responseList = new ArrayList<>();
        List<Unit> units = unitRepository.findAll();

        for (Unit unit : units) {
            UnitResponseDto responseDto = new UnitResponseDto();
            BeanUtils.copyProperties(unit, responseDto);
            responseList.add(responseDto);
        }
        log.info("Unitler çekildi");
        return responseList;
    }

    @Override
    public UnitResponseDto findById(Long id) {
        UnitResponseDto responseDto = new UnitResponseDto();
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            Unit dbUnit = unitOptional.get();
            BeanUtils.copyProperties(dbUnit, responseDto);
        }
        log.info("Unit bulundu");
        return responseDto;
    }

    @Override
    public void deleteById(Long id) {

        log.info("Unit silindi");
        unitRepository.deleteById(id);
    }

    @Override
    public UnitResponseDto update(Long id, UnitRequestDto dto) {
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
            return responseDto;


        }
        return null;
    }
}
