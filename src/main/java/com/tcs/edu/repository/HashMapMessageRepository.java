package com.tcs.edu.repository;

import com.tcs.edu.domain.Message;

import java.util.HashMap;
import java.util.UUID;

public class HashMapMessageRepository implements MessageRepository{
    private HashMap<UUID, Message> messages = new HashMap<>();

    @Override
    public UUID create(Message message) {
        UUID key = UUID.randomUUID();
        message.setId(key);
        messages.put(key, message);
        return key;
    }
}
