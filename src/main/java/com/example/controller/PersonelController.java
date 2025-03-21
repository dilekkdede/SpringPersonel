package com.example.controller;

import com.example.dto.dtoEntity.PersonelRequestDto;
import com.example.dto.dtoEntity.PersonelResponseDto;
import com.example.entites.Personel;
import com.example.services.IPersonelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingEnumeration;
import java.util.List;

@RestController
@RequestMapping("/rest/api/personel")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PersonelController {

    @Autowired
    private IPersonelServices personelServices;


    @PostMapping(path = "/save")
    public PersonelResponseDto save(@RequestBody PersonelRequestDto personel) {
        return personelServices.save(personel);
    }

    @GetMapping(path = "/list")
    public List<PersonelResponseDto> findAll() {
        return personelServices.findAll();
    }

    @GetMapping(path = "/get-id/{id}")
    public PersonelResponseDto findById(@PathVariable(name = "id") Long id) {
        return personelServices.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteById(@PathVariable(name = "id") Long id) {
        personelServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public PersonelResponseDto updatePersonel(@PathVariable(name = "id") Long id, @RequestBody PersonelRequestDto personel) {
        return personelServices.update(id, personel);
    }

    @GetMapping(path = "/personel-listesi")
    public List<Personel> personelListesi() {
        return personelServices.personelListesi();
    }

    @GetMapping(path = "/personel-listesi-id2-id11/{id2}/{id11}")
    public List<Personel> persnelListsiID2ve11(@PathVariable(name = "id2") long id2, @PathVariable(name = "id11") long id11) {
        return personelServices.persnelListsiID2ve11(id2, id11);
    }


}
