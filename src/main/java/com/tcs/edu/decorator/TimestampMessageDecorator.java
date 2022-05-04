package com.tcs.edu.decorator;

import java.time.Instant;

/**
 * @author a.midov
 * @apiNote Класс содержит метод, добавляющий текущее время к заданной строке, а также методы инкремента и сброса счетчика
 */
public class TimestampMessageDecorator implements MessageDecorator{
    private Integer PAGE_SIZE = 4;
    private Integer messageCount = 0;

    /**
     *
     * @param message (String), произвольная строка над которым будут совершены определенные действия
     * @return (String) строка, которая содержит текущее время плюс исходная строка переданная в аргументе
     * @apiNote Метод содержит локальную переменную decoratedMessage, в которой хранится строка - склейка текущего времени и сообщения
     */
    @Override
    public String decorate(String message) {
        updateCounter();
        var decoratedMessage = String.format("%3d %s %s", messageCount, Instant.now(), message);
        if (messageCount % PAGE_SIZE == 0) {
            decoratedMessage = String.format("%s%s", decoratedMessage, "\n---");
        }
        return decoratedMessage;
    }

    private void updateCounter() {
        messageCount++;
    }

    @Override
    public void resetCounter() {
        messageCount = 0;
    }
}
