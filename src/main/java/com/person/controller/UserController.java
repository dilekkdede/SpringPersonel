package com.person.controller;

import com.person.dto.UserSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.CityRequestDto;
import com.person.services.ICityServices;
import com.person.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/user")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {


    @Autowired
    private UserService service;


    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody UserSaveDto dto) {
        return service.save(dto);
    }

    @GetMapping(path = "/get-all")
    public BaseResponse findAll() {
        return service.findAll();
    }
}
