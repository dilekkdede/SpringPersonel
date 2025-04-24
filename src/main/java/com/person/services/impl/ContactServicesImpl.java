package com.person.services.impl;

import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoEntity.ContactRequestDto;
import com.person.dto.dtoEntity.ContactResponseDto;
import com.person.entites.Contact;
import com.person.repository.ContactRepository;
import com.person.services.IContactServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactServicesImpl implements IContactServices {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public BaseResponse save(ContactRequestDto dto) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        Contact contact = new Contact();
        BeanUtils.copyProperties(dto, contact);
        Contact dbContact = contactRepository.save(contact);
        BeanUtils.copyProperties(dbContact, contactResponseDto);
        log.info("Contact kayıt edildi");

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(contactResponseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("İletişim bilgisi kayıt edildi");
        return baseResponse;


    }

    @Override
    public BaseResponse findAll() {
        List<ContactResponseDto> contactResponseDtos = new ArrayList<>();
        List<Contact> contactList = contactRepository.findAll();

        for (Contact contact : contactList) {
            ContactResponseDto contactResponseDto = new ContactResponseDto();
            BeanUtils.copyProperties(contact, contactResponseDto);
            contactResponseDtos.add(contactResponseDto);
        }
        log.info("Contact çekildi");

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(contactResponseDtos);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("İletişim bilgileri listelendi");
        return baseResponse;
    }

    @Override
    public BaseResponse findById(Long id) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        Optional<Contact> optinol = contactRepository.findById(id);
        if (optinol.isPresent()) {
            Contact dbContact = optinol.get();
            BeanUtils.copyProperties(dbContact, contactResponseDto);
        }
        log.info("Contact bulundu");
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(contactResponseDto);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Girilen Id'ye ait iletişim bilgisi bulundu");
        return baseResponse;

    }

    @Override
    public BaseResponse deleteById(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        log.info("Contact silindi");
        contactRepository.deleteById(id);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Contact başarılı bir şekilde silindi");
        return baseResponse;

    }

    @Override
    public BaseResponse update(Long id, ContactRequestDto dto) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        Optional<Contact> optinol = contactRepository.findById(id);
        if (optinol.isPresent()) {
            Contact dbContact = optinol.get();
            dbContact.setPersonelId(dto.getPersonelId());
            dbContact.setStatus(dto.getStatus());
            dbContact.setCreateDate(dto.getCreateDate());
            dbContact.setContact(dto.getContact());
            dbContact.setType(dto.getType());

            Contact updatedContact = contactRepository.save(dbContact);
            BeanUtils.copyProperties(updatedContact, contactResponseDto);
            log.info("Contact güncellendi");

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(contactResponseDto);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("İletişim bilgisi güncellendi");
            return baseResponse;
        }
        return null;
    }
}
