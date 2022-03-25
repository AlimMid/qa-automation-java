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
     */
    public static String decorate(String message) {
        return Instant.now() + " " + message;
    }
}
