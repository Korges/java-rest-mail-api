package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;

import java.util.List;
import java.util.Optional;

public interface EmailService {

    Email save(EmailInputDTO email);
    List<Email> findAll();
    Optional<Email> findById(String id);

}