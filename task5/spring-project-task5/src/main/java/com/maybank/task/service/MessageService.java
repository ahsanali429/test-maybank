package com.maybank.task.service;

import com.maybank.task.model.Message;
import com.maybank.task.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public String getMessage() {
        return messageRepository.findById(1L).orElse(new Message(1L, "Hello World from MYSQL")).getMessage();
    }
}
