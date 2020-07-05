package com.korges.demo.controller;

import com.korges.demo.model.dto.EmailInput;
import com.korges.demo.model.entity.Email;
import com.korges.demo.service.EmailFacadeService;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.korges.demo.controller.responsehandler.ResponseHandler.generateResponseEntity;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/emails")
@RestController
public class EmailController {
    private final EmailFacadeService emailFacadeService;

    /**
     * Find all emails in the system
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Email>> findAll() {
        log.info("[EmailController] - fetching all emails");

        return new ResponseEntity<>(emailFacadeService.findAll(), HttpStatus.OK);
    }

    /**
     * Find email by id
     * @param id String
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        log.info("[EmailController] - fetching email by given id: " + id);

        return generateResponseEntity(emailFacadeService.findById(id));
    }

    /**
     * Check status of the email
     * @param id String
     * @return ResponseEntity
     */
    @GetMapping("/{id}/status")
    public ResponseEntity<?> findEmailStatus(@PathVariable("id") String id) {
        log.info("[EmailController] - fetching email by given id: " + id);

        return generateResponseEntity(emailFacadeService.findEmailStatus(id));
    }

    /**
     * Create new email
     * @param email EmailInput
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Email> save(@RequestBody EmailInput email) {
        log.info("[EmailController] - saving new email: " + email);

        return new ResponseEntity<>(emailFacadeService.save(email), HttpStatus.CREATED);
    }

    /**
     * Update email
     * @param id String
     * @param email EmailInput
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody EmailInput email) {
        log.info("[EmailController] - updating email by given id: " + id + " with: " + email);

        return generateResponseEntity(emailFacadeService.update(id, email));
    }

    /**
     * Send email
     * @param id String
     * @return ResponseEntity
     */
    @PostMapping("/send/{id}")
    public ResponseEntity<?> send(@PathVariable("id") String id) {
        log.info("[EmailController] - sending email by given id: " + id);

        return generateResponseEntity(emailFacadeService.send(id));
    }

    /**
     * Send all pending email
     * @return ResponseEntity
     */
    @PostMapping("/send/all")
    public ResponseEntity<?> sendAllPending() {
        log.info("[EmailController] - sending all pending emails");

        return generateResponseEntity(emailFacadeService.sendAllPending());
    }

}