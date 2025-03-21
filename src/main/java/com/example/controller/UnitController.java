package com.example.controller;


import com.example.dto.dtoEntity.UnitRequestDto;
import com.example.dto.dtoEntity.UnitResponseDto;
import com.example.services.IUnitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/unit")
public class UnitController {

    @Autowired
    private IUnitServices unitServices;


    @PostMapping(path = "/save")
    public UnitResponseDto save(@RequestBody UnitRequestDto unit) {
        return unitServices.save(unit);
    }

    @GetMapping(path = "/get-all")
    public List<UnitResponseDto> findAll() {
        return unitServices.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public UnitResponseDto findById(@PathVariable(name = "id") Long id) {
        return unitServices.findById(id);

    }

    @DeleteMapping(path = "/delete/{id}")
    void deleteById(@PathVariable(name = "id") Long id) {
        unitServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public UnitResponseDto update(@PathVariable(name = "id") Long id, @RequestBody UnitRequestDto dto) {
        return unitServices.update(id, dto);
    }

}
