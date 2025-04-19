package com.example.controller;

import com.example.dto.dtoEntity.CityRequestDto;
import com.example.dto.dtoEntity.CityResponseDto;
import com.example.services.ICityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/city")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CityController {


    @Autowired
    private ICityServices cityService;


    @PostMapping(path = "/save")
    public CityResponseDto save(@RequestBody CityRequestDto dto) {
        return cityService.save(dto);
    }

    @GetMapping(path = "/get-all")
    public List<CityResponseDto> findAll() {
        return cityService.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public CityResponseDto findById(@PathVariable(name = "id") Long id) {
        return cityService.findById(id);

    }

    @DeleteMapping(path = "/delete/{id}")
    void deleteById(@PathVariable(name = "id") Long id) {
        cityService.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public CityResponseDto update(@PathVariable(name = "id") Long id, @RequestBody CityRequestDto dto) {
        return cityService.update(id, dto);
    }


}
