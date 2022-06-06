package com.tcs.edu.decorator;

import com.tcs.edu.domain.Message;

/**
 * Интерфейс декоратора сообщений
 */
public interface MessageDecorator {
    Message decorate(Message message);
}
