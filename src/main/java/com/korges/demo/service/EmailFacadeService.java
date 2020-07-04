package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;

import java.util.List;
import java.util.Optional;

public interface EmailFacadeService {

    Email save(EmailInputDTO email);
    List<Email> findAll();
    Optional<Email> findById(String id);
    Email send(String id);

}
