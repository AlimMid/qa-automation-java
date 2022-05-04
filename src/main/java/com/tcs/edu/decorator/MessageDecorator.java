package com.tcs.edu.decorator;

/**
 * Интерфейс декоратора сообщений
 */
public interface MessageDecorator {
    String decorate(String message);
    void resetCounter();
}
