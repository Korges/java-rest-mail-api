package com.korges.demo.service.sender;

import com.korges.demo.model.entity.Email;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Override
    public Email send(Email email) {
        return email;
    }
}