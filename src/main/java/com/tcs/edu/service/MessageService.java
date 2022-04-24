package com.tcs.edu.service;

import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;

import static com.tcs.edu.decorator.SeverityLevelDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;
import static com.tcs.edu.printer.ConsolePrinter.print;

public class MessageService {

    /**
     * @apiNote Сервис преобразования строки и вывода на консоль
     * @param level    уровень важности
     * @param messageOrder порядок вывода сообщений
     * @param message  сообщение, которое необходимо вывести
     * @param messages дополнительные сообщения, которые необходимо вывести
     * @implNote при незаданном level выводятся сообщения с level=MINOR,
     * при незаданном messageOrder сообщения выводятся в порядке messages
     */
    public static void process(Severity level, MessageOrder messageOrder, String message, String... messages) {
        if (level == null) {
            level = Severity.MINOR;
        }
        if (messageOrder == null) {
            messageOrder = MessageOrder.ASC;
        }
        if (messageOrder.equals(MessageOrder.ASC)) {
            processAsc(level, message, messages);
        } else if (messageOrder.equals(MessageOrder.DESC)) {
            processDesc(level, message, messages);
        }
    }

    /**
     * @implNote Вывод сообщений в порядке messages
     */
    private static void processAsc(Severity level, String message, String... messages) {
        if (!(message == null)) {
            print(decorate(String.format("%s %s", message, mapToString(level))));
        }
        for (String currentMessage : messages) {
            if (!(currentMessage == null)) {
                print(decorate(String.format("%s %s", currentMessage, mapToString(level))));
            }
        }
    }

    /**
     * @implNote Вывод сообщений в обратном порядке messages
     */
    private static void processDesc(Severity level, String message, String... messages) {
        for (int i = messages.length - 1; i >= 0; i--) {
            if (!(messages[i] == null)) {
                print(decorate(String.format("%s %s", messages[i], mapToString(level))));
            }
        }
        if (!(message == null)) {
            print(decorate(String.format("%s %s", message, mapToString(level))));
        }
    }

    public static void process(Severity level, String message, String... messages) {
        process(level, MessageOrder.ASC, message, messages);
    }

    public static void process(MessageOrder messageOrder, String message, String... messages) {
        process(null, messageOrder, message, messages);
    }

    public static void process(String message, String... messages) {
        process(null, null, message, messages);
    }
}
