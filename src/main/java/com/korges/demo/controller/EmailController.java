package com.korges.demo.controller;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import com.korges.demo.service.EmailFacadeService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/emails")
@RestController
public class EmailController {
    private final EmailFacadeService emailFacadeService;

    @GetMapping
    public List<Email> findAll() {
        return emailFacadeService.findAll();
    }

    @GetMapping("/{id}")
    public Either<Error, Email> findById(@PathVariable("id") String id) {
        return emailFacadeService.findById(id);
    }

    @PostMapping
    public Email save(@RequestBody EmailInputDTO email) {
        return emailFacadeService.save(email);
    }

    // TODO
    @PostMapping("/{id}/send")
    public Email send(@PathVariable("id") String id) {
        Either<Error, Email> s = emailFacadeService.send(id);

        return s.get();
    }

}