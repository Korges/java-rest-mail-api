package com.korges.demo.controller;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.service.EmailService;
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
@RestController
@RequestMapping("/emails")
public class EmailController {
    private final EmailService emailService;

    @GetMapping
    public List<Email> findAll() {
        return emailService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Email> findById(@PathVariable("id") String id) {
        return emailService.findById(id);
    }

    @PostMapping
    public Email save(@RequestBody EmailInputDTO email) {
        return emailService.save(email);
    }

}