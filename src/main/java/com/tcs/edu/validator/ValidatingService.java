package com.tcs.edu.validator;

import com.tcs.edu.domain.Message;

public abstract class ValidatingService {
    public boolean isArgNotValid(String message) {
        return message == null || message.isEmpty();
    }

    public boolean isArgNotValid(Object object) {
        return object == null;
    }

    public boolean isArgNotValid(Message... messages) {
        return messages == null || messages.length == 0;
    }
}
