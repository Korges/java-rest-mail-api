package com.korges.demo.controller;

import com.korges.demo.model.dto.input.EmailInputDTO;
import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import com.korges.demo.model.enums.Error;
import com.korges.demo.service.EmailFacadeService;
import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/emails")
@RestController
public class EmailController {
    private final EmailFacadeService emailFacadeService;

    /**
     * See all email in the system
     * @return List<Email>
     */
    @GetMapping
    public List<Email> findAll() {
        return emailFacadeService.findAll();
    }

    /**
     * See email by it's id
     * @param id String
     * @return
     */
    @GetMapping("/{id}")
    public Either<Error, Email> findById(@PathVariable("id") String id) {
        return emailFacadeService.findById(id);
    }

    /**
     * Check status of the email
     * @param id String
     * @return
     */
    @GetMapping("/{id}/status")
    public Either<Error, EmailStatus> findEmailStatus(@PathVariable("id") String id) {
        return emailFacadeService.findEmailStatus(id);
    }

    /**
     * Create new email
     * @param email EmailInputDTO
     * @return
     */
    @PostMapping
    public Email save(@RequestBody EmailInputDTO email) {
        return emailFacadeService.save(email);
    }

    /**
     * Update email
     * @param id String
     * @param email EmailInputDTO
     * @return
     */
    @PutMapping("/{id}")
    public Either<Error, Email> update(@PathVariable("id") String id, @RequestBody EmailInputDTO email) {
        return emailFacadeService.update(id, email);
    }

    /**
     * Send email
     * @param id String
     * @return
     */
    @PostMapping("/send/{id}")
    public Either<Error, Email> send(@PathVariable("id") String id) {
        return emailFacadeService.send(id);
    }

    /**
     * Send all pending email
     * @return
     */
    @PostMapping("/send/all")
    public List<Either<Error, Email>> sendAllPending() {
        return emailFacadeService.sendAllPending();
    }

}