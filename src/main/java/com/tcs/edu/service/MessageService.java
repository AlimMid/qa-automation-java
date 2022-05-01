package com.tcs.edu.service;

import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.domain.Message;
import static com.tcs.edu.decorator.SeverityLevelDecorator.mapToString;
import static com.tcs.edu.decorator.TimestampMessageDecorator.decorate;
import static com.tcs.edu.decorator.TimestampMessageDecorator.resetCounter;
import static com.tcs.edu.printer.ConsolePrinter.print;

public class MessageService {

    /**
     * @param messageOrder порядок вывода сообщений
     * @param doubling     признак дедупликации сообщений
     * @param messages     список объектов Message
     * @apiNote Сервис преобразования сообщений и вывода
     */
    public static void log(MessageOrder messageOrder, Doubling doubling, Message... messages) {
        if (doubling != null) {
            if (doubling.equals(Doubling.DOUBLES)) {
                log(messageOrder, messages);
            } else if (doubling.equals(Doubling.DISTINCT)) {
                log(messageOrder, deduplicate(messages));
            }
        }
    }

    public static void log(MessageOrder messageOrder, Message... messages) {
        if (messageOrder != null) {
            if (messageOrder.equals(MessageOrder.ASC)) {
                log(messages);
            } else if (messageOrder.equals(MessageOrder.DESC)) {
                log(reverse(messages));
            }
        }
    }

    public static void log(Message... messages) {
        if (messages != null && messages.length != 0) {
            for (Message currentMessage : messages) {
                if (currentMessage != null) {
                    print(decorate(String.format("%s %s", currentMessage.getBody(), mapToString(currentMessage.getSeverity()))));
                }
            }
            resetCounter();
            System.out.println("-----------------------------------------------------");
        }
    }

    public static void log(Doubling doubling, Message... messages) {
        log(MessageOrder.ASC, doubling, messages);
    }

    /**
     * @param messages массив сообщений
     * @return массив сообщений без дубликатов
     * @implNote Дедупликатор сообщений
     */
    public static Message[] deduplicate(Message... messages) {
        Message[] messagesOutput = new Message[messages.length];
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
    private static Message[] reverse(Message... messages) {
        int k = messages.length;
        Message[] messagesReverse = new Message[k];
        for (int i = 0; i < k; i++) {
            messagesReverse[i] = messages[k - i - 1];
        }
        return messagesReverse;
    }

    /**
     * @param message  Message
     * @param messages Message[]
     * @return boolean, если message содержится в массиве messages, то true, иначе false
     * @implNote Метод проверяет cовпадение поля body объекта message с полем body одного из объектов массива messages.
     * Если message = null или не найдено ни одного совпадения, то возвращает false.
     */
    public static boolean checkContains(Message message, Message[] messages) {
        if (message != null) {
            for (Message s : messages) {
                if (s != null && s.getBody().equals(message.getBody())) {
                    return true;
                }
            }
        }
        return false;
    }
}
