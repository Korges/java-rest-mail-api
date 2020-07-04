package com.korges.demo.repository;

import com.korges.demo.model.entity.Email;
import com.korges.demo.model.enums.EmailStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {

    List<Email> findAllByStatus(EmailStatus emailStatus);

}