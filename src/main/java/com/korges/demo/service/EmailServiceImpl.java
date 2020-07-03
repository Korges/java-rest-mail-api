package com.korges.demo.service;

import com.korges.demo.model.entity.Email;
import com.korges.demo.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final EmailRepository emailRepository;

    public Email save(Email email) {
        return emailRepository.save(email);
    }

    public List<Email> findAll() {
        return emailRepository.findAll();
    }

    public Optional<Email> findById(String id) {
        return emailRepository.findById(id);
    }
}