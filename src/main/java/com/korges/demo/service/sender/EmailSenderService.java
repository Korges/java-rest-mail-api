package com.korges.demo.service.sender;

import com.korges.demo.model.entity.Email;

public interface EmailSenderService {

    Email send(Email email);

}