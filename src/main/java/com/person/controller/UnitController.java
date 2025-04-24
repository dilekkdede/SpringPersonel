package com.person.controller;


import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.UnitRequestDto;
import com.person.dto.dtoEntity.UnitResponseDto;
import com.person.services.IUnitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/unit")
@CrossOrigin(allowedHeaders = "*", origins = "*")

public class UnitController {

    @Autowired
    private IUnitServices unitServices;


    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody UnitRequestDto unit) {
        return unitServices.save(unit);
    }

    @GetMapping(path = "/get-all")
    public BaseResponse findAll() {
        return unitServices.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return unitServices.findById(id);

    }

    @DeleteMapping(path = "/delete/{id}")
    BaseResponse deleteById(@PathVariable(name = "id") Long id) {

        return unitServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse update(@PathVariable(name = "id") Long id, @RequestBody UnitRequestDto dto) {
        return unitServices.update(id, dto);
    }

}
