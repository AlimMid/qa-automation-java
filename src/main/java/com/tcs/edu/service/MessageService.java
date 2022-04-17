package com.tcs.edu.service;

import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.SeverityLevelDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;
import static com.tcs.edu.printer.ConsolePrinter.print;

public class MessageService {

    /**
     * @apiNote Сервис преобразования строки и вывода на консоль
     */
    public static void process(Severity level, String message, String... messages) {
        print(decorate(String.format("%s %s", message, mapToString(level))));
        for (String currentMessage : messages) {
            print(decorate(String.format("%s %s", currentMessage, mapToString(level))));
        }
    }

    public static void process(String message, String... messages) {
        process(null, message, messages);
    }
}
