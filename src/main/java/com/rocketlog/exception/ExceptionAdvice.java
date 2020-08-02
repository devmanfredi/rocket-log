package com.rocketlog.exception;

import com.rocketlog.dto.exception.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(MessageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO emailExistsException(MessageException exception) {
        return MessageDTO.builder().message(exception.getMessage())
                .code(400)
                .date(LocalDateTime.now())
                .build();
    }
}
