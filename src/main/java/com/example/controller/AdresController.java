package com.example.controller;


import com.example.dto.dtoEntity.AdresRequestDto;
import com.example.dto.dtoEntity.AdresResponseDto;
import com.example.services.IAdresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/adres")
@CrossOrigin(allowedHeaders = "*", origins = "*")

public class AdresController {

    @Autowired
    private IAdresServices adresService;

    @PostMapping(path = "/save")
    public AdresResponseDto save(@RequestBody AdresRequestDto dto) {
        return adresService.save(dto);
    }

    @GetMapping(path = "get-all")
    public List<AdresResponseDto> findAll() {
        return adresService.findAll();
    }

    @GetMapping(path = "/get-id/{id}")
    public AdresResponseDto findById(@PathVariable(name = "id") Long id) {
        return adresService.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        adresService.deleteById(id);
    }

    @PutMapping(path = "update/{id}")
    public AdresResponseDto updateAdres(@PathVariable(name = "id") Long id,@RequestBody AdresRequestDto dto) {
        AdresResponseDto updatedAdres = adresService.update(id, dto);
        return updatedAdres;

    }

}
