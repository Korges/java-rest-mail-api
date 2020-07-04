package com.korges.demo.service;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.service.persistence.EmailPersistenceService;
import com.korges.demo.service.sender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailFacadeServiceImpl implements EmailFacadeService {
    private final EmailPersistenceService emailPersistenceService;
    private final EmailSenderService emailSenderService;

    @Override
    public Email save(EmailInputDTO emailDTO) {
        Email email = Email.builder()
                .header(emailDTO.getHeader())
                .message(emailDTO.getMessage())
                .status(EmailStatus.PENDING)
                .build();

        return emailPersistenceService.save(email);
    }

    @Override
    public List<Email> findAll() {
        return emailPersistenceService.findAll();
    }

    @Override
    public Optional<Email> findById(String id) {
        return emailPersistenceService.findById(id);
    }

    @Override
    public Email send(String id) {
        return emailSenderService.send(null);
    }

}
