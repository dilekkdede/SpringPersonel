package com.person.services.impl;

import com.person.dto.UserSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.User;
import com.person.repository.UserRepository;
import com.person.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseResponse save(UserSaveDto dto) {
        return null;
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
