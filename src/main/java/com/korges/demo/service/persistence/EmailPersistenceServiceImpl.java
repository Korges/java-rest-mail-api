package com.korges.demo.service.persistence;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailPersistenceServiceImpl implements EmailPersistenceService {
    private final EmailRepository emailRepository;

    public Email save(EmailInputDTO emailDTO) {
        Email email = Email.builder()
                .header(emailDTO.getHeader())
                .message(emailDTO.getMessage())
                .status(EmailStatus.PENDING)
                .build();

        return emailRepository.save(email);
    }

    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    public Optional<Email> findById(String id) {
        return emailRepository.findById(id);
    }

}