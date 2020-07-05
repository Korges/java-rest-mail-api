package com.korges.demo.controller;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.Error;
import com.korges.demo.service.EmailFacadeService;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public Either<Error, Email> update(@PathVariable("id") String id, @RequestBody EmailInputDTO email) {
        return emailFacadeService.update(id, email);
    }

    // TODO
    @PostMapping("/send/{id}")
    public Either<Error, Email> send(@PathVariable("id") String id) {
        return emailFacadeService.send(id);
    }

    @PostMapping("/send/all")
    public List<Either<Error, Email>> sendAllPending() {
        return emailFacadeService.sendAllPending();
    }

}