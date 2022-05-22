package com.tcs.edu.validator;

import com.tcs.edu.domain.Message;

public abstract class ValidatingService {
    public void isArgValid(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("message is null");
        }
        if (message.getBody() == null) {
            throw new IllegalArgumentException("message.body is null");
        }
        if (message.getBody().isEmpty()) {
            throw new IllegalArgumentException("message.body is empty");
        }
    }

    public void isArgValid(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }

    public void isArgValid(Message... messages) {
        if (messages == null) {
            throw new IllegalArgumentException("vararg object is null");
        }
        if (messages.length == 0) {
            throw new IllegalArgumentException("vararg object is empty");
        }
    }
}
