package com.example.services.impl;

import com.example.dto.dtoEntity.ContactRequestDto;
import com.example.dto.dtoEntity.ContactResponseDto;
import com.example.entites.Contact;
import com.example.repository.ContactRepository;
import com.example.services.IContactServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ContactResponseDto save(ContactRequestDto dto) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        Contact contact = new Contact();
        BeanUtils.copyProperties(dto, contact);
        Contact dbContact = contactRepository.save(contact);
        BeanUtils.copyProperties(dbContact, contactResponseDto);
        log.info("Contact kayıt edildi");
        return contactResponseDto;

    }

    @Override
    public List<ContactResponseDto> findAll() {
        List<ContactResponseDto> contactResponseDtos = new ArrayList<>();
        List<Contact> contactList = contactRepository.findAll();

        for (Contact contact : contactList) {
            ContactResponseDto contactResponseDto = new ContactResponseDto();
            BeanUtils.copyProperties(contact, contactResponseDto);
            contactResponseDtos.add(contactResponseDto);
        }
        log.info("Contact çekildi");
        return contactResponseDtos;
    }

    @Override
    public ContactResponseDto findById(Long id) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        Optional<Contact> optinol = contactRepository.findById(id);
        if (optinol.isPresent()) {
            Contact dbContact = optinol.get();
            BeanUtils.copyProperties(dbContact, contactResponseDto);
        }
        log.info("Contact bulundu");
        return contactResponseDto;
    }

    @Override
    public void deleteById(Long id) {

        log.info("Contact silindi");
        contactRepository.deleteById(id);

    }

    @Override
    public ContactResponseDto update(Long id, ContactRequestDto dto) {
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
            return contactResponseDto;
        }
        return null;
    }
}
