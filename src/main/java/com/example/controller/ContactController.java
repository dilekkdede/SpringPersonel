package com.example.controller;

import com.example.dto.dtoEntity.ContactRequestDto;
import com.example.dto.dtoEntity.ContactResponseDto;
import com.example.services.IContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/contact")

public class ContactController {

    @Autowired
    private IContactServices contactServices;

    @PostMapping(path = "/save")
    public ContactResponseDto save(@RequestBody ContactRequestDto dto) {
        return contactServices.save(dto);
    }

    @GetMapping(path = "/get-all")
    public List<ContactResponseDto> findAll() {
        return contactServices.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public ContactResponseDto findById(@PathVariable(name = "id") Long id) {
        return contactServices.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    void deleteById(@PathVariable(name = "id") Long id) {
        contactServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public ContactResponseDto update(@PathVariable(name = "id") Long id, @RequestBody ContactRequestDto dto) {
        return contactServices.update(id, dto
        );

    }


}
