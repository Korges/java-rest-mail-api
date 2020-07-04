package com.korges.demo.controller;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.service.EmailFacadeService;
import com.korges.demo.service.persistence.EmailPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/emails")
@RestController
public class EmailController {
    private final EmailFacadeService emailPersistenceService;

    @GetMapping
    public List<Email> findAll() {
        return emailPersistenceService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Email> findById(@PathVariable("id") String id) {
        return emailPersistenceService.findById(id);
    }

    @PostMapping
    public Email save(@RequestBody EmailInputDTO email) {
        return emailPersistenceService.save(email);
    }

}