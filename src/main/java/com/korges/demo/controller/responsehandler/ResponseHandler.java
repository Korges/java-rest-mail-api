package com.korges.demo.controller.responsehandler;

import com.korges.demo.model.dto.Error;
import com.korges.demo.model.entity.Email;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    private ResponseHandler() {}

    public static <T> ResponseEntity<Object> generateResponseEntity(Either<Error, T> either) {
        return either
                .map(ResponseHandler::createObject)
                .getOrElseGet(ResponseHandler::createErrorResponse);
    }

    public static ResponseEntity<Object> generateResponseEntity(List<Either<Error, Email>> list) {
        return new ResponseEntity<>(list
                .map(ResponseHandler::mapEither), HttpStatus.OK);
    }

    private static Object mapEither(Either<Error, Email> e) {
        return e.isRight() ? e.get() : e.getLeft();
    }

    private static ResponseEntity<Object> createObject(Object object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    private static ResponseEntity<Object> createErrorResponse(Error errorEnum) {
        return new ResponseEntity<>(
                errorEnum.getError().getMessage(),
                errorEnum.getError().getCode()
        );
    }

}
