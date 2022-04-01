package com.tcs.edu.decorator;

import java.time.Instant;

/**
 * @author a.midov
 * @apiNote Класс содержит единственный метод добавляющий текущее время к заданной строке
 */
public class TimestampMessageDecorator {

    /**
     *
     * @param message (String), произвольная строка над которым будут совершены определенные действия
     * @return (String) строка, которая содержит текущее время плюс исходная строка переданная в аргументе
     * @apiNote Метод содержит локальную переменную decoratedMessage, в которой хранится строка - склейка текущего времени и сообщения
     */
    public static String decorate(String message) {
        String decoratedMessage = Instant.now() + " " + message;
        return decoratedMessage;
    }
}
