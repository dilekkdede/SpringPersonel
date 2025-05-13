package com.person.services.impl;

import com.person.dto.UserSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.City;
import com.person.entites.User;
import com.person.enums.RecordStatus;
import com.person.repository.CityRepository;
import com.person.repository.UserRepository;
import com.person.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public BaseResponse save(UserSaveDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserName(dto.getUserName());
        user.setTcNo(dto.getTcNo());

        Optional<City> city = cityRepository.findById(dto.getCity().getId());
        if (city.isPresent()) {
            user.setCity(city.get());
        }

        user.setCreateBy("Dilek Dede");
        user.setCreateDate(new Date());
        user.setStatus(RecordStatus.ACTIVE.getValue());
        User newUser = userRepository.save(user);

        BaseResponse response = new BaseResponse();
        response.setData(newUser);
        response.setStatus(201);

        log.info("User saved with id " + newUser.getId());
        return response;
    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();

        List<User> listUser = userRepository.findAll();
        response.setData(listUser);
        response.setStatus(200);

        log.info("UserServiceImpl.findAll");
        return response;
    }
}
