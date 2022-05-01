package com.tcs.edu.service;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;

import static com.tcs.edu.decorator.SeverityLevelDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;
import static com.tcs.edu.printer.ConsolePrinter.print;

public class MessageService {

    /**
     * @param level        уровень важности
     * @param messageOrder порядок вывода сообщений
     * @param doubling     признак дедупликации сообщений
     * @param messages     дополнительные сообщения, которые необходимо вывести
     * @apiNote Сервис преобразования сообщений и вывода на консоль
     * //     * @implNote при незаданном level выводятся сообщения с level=MINOR,
     * //     * при незаданном messageOrder сообщения выводятся в порядке messages
     */
    public static void log(Severity level, MessageOrder messageOrder, Doubling doubling, String... messages) {
        if (doubling != null) {
            if (doubling.equals(Doubling.DOUBLES)) {
                log(level, messageOrder, messages);
            } else if (doubling.equals(Doubling.DISTINCT)) {
                log(level, messageOrder, deduplicate(messages));
            }
        }
    }

    public static void log(Severity level, MessageOrder messageOrder, String... messages) {
        if (messageOrder != null) {
            if (messageOrder.equals(MessageOrder.ASC)) {
                log(level, messages);
            } else if (messageOrder.equals(MessageOrder.DESC)) {
                log(level, reverse(messages));
            }
        }
    }

    public static void log(Severity level, String... messages) {
        if (level != null && messages != null && messages.length != 0) {
            for (String currentMessage : messages) {
                if (currentMessage != null) {
                    print(decorate(String.format("%s %s", currentMessage, mapToString(level))));
                }
            }
        }
    }

    public static void log(MessageOrder messageOrder, Doubling doubling, String... messages) {
        log(Severity.MINOR, messageOrder, doubling, messages);
    }

    public static void log(Severity level, Doubling doubling, String... messages) {
        log(level, MessageOrder.ASC, doubling, messages);
    }

    public static void log(Doubling doubling, String... messages) {
        log(Severity.MINOR, MessageOrder.ASC, doubling, messages);
    }

    public static void log(MessageOrder messageOrder, String... messages) {
        log(Severity.MINOR, messageOrder, Doubling.DOUBLES, messages);
    }

    public static void log(String... messages) {
        log(Severity.MINOR, MessageOrder.ASC, Doubling.DOUBLES, messages);
    }

    /**
     * @param messages массив сообщений
     * @return массив сообщений без дубликатов
     * @implNote Дедупликатор сообщений
     */
    public static String[] deduplicate(String... messages) {
        String[] messagesOutput = new String[messages.length];
        if (messages.length != 0) {
            messagesOutput[0] = messages[0];
            int k = 1;
            for (int i = 1; i < messages.length; i++) {
                if (!checkContains(messages[i], messagesOutput)) {
                    messagesOutput[k] = messages[i];
                    k++;
                }
            }
        }
        return messagesOutput;
    }

    /**
     * @param messages массив сообщений
     * @return массив сообщений в обратном порядке
     * @implNote реверс массива сообщений
     */
    private static String[] reverse(String... messages) {
        int k = messages.length;
        String[] messagesReverse = new String[k];
        for (int i = 0; i < k; i++) {
            messagesReverse[i] = messages[k - i - 1];
        }
        return messagesReverse;
    }

    /**
     * @param message  String
     * @param messages String[]
     * @return boolean, если message содержится в массиве messages, то true, иначе false
     * @implNote Метод проверяет вхождение строки в массив строк
     */
    public static boolean checkContains(String message, String[] messages) {
        for (String s : messages) {
            if (s != null && s.equals(message)) {
                return true;
            }
        }
        return false;
    }
}
